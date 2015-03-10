package com.powerdata.openpa.psse.powerflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import com.powerdata.openpa.psse.ACBranchList;
import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.Gen;
import com.powerdata.openpa.psse.Island;
import com.powerdata.openpa.psse.IslandList;
import com.powerdata.openpa.psse.OneTermDev;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.TwoTermDCLineList;
import com.powerdata.openpa.psse.TwoTermDev;
import com.powerdata.openpa.psse.util.BusGroup;
import com.powerdata.openpa.psse.util.BusGroup2TDevList;
import com.powerdata.openpa.tools.LinkNet;
import com.powerdata.openpa.tools.PAMath;
/**
 * Report mismatches for each bus 
 * 
 * @author chris@powerdata.com
 *
 */
public class MismatchReport
{
	PsseModel							_model;
	LinkNet								_brnet	= new LinkNet();
	LinkNet								_otnet	= new LinkNet();
	int									_nbus;
	Object[]		_otdevorder;
	Object[]		_brorder;
	int									_ngen, _nload, _nshunt, _nsvc, _nbr;
	float[]								_shq, _svcq, _pfrm, _pto,
			_qfrm, _qto, _vm, _va, _pmm, _qmm;
	BusGroup2TDevList _tn;
	IslandList _islands;


	//	PrintWriter _out;
	@SuppressWarnings("unchecked")
	public MismatchReport(PsseModel model, BusGroup2TDevList tn) throws PsseModelException
	{
		_model = model;
		 _islands = model.getIslands();
		_tn = tn;
		_nbus = _tn.size();
		
		_otdevorder = new Object[] {
				_model.getGenerators(), _model.getLoads(), _model.getShunts(),
				_model.getSvcs() };
		
		_ngen = _model.getGenerators().size();
		_nload = _model.getLoads().size();
		_nshunt = _model.getShunts().size();

		int otsize = 0;
		for(Object l : _otdevorder)
			otsize += ((List<? extends OneTermDev>) l).size();
		
		TwoTermDCLineList t2dclines = model.getTwoTermDCLines();
		ACBranchList acbranch = model.getBranches();
		_nbr = acbranch.size()+t2dclines.size();
		_brnet.ensureCapacity(_nbus+1, _nbr);
		_otnet.ensureCapacity(_nbus+1, otsize);
		_brorder = new Object[] { acbranch, t2dclines };
		for(Object olist : _brorder)
		{
			List<? extends TwoTermDev> list = (List<? extends TwoTermDev>) olist; 
			for(TwoTermDev dev : list)
			{
				int fndx = _tn.findGrpNdx(dev.getFromBus());
				int tndx = _tn.findGrpNdx(dev.getToBus());
				_brnet.addBranch(fndx, tndx);
			}
		}
		
		for (Object otlist : _otdevorder)
		{
			for (OneTermDev otd : (List<? extends OneTermDev>)otlist)
			{
				int bndx = (otd.isInSvc()) ? _tn.findGrpNdx(otd.getBus()) : _nbus;
				_otnet.addBranch(bndx, _nbus);
			}
		}
		
		_pfrm = new float[_nbr];
		_qfrm = new float[_nbr];
		_pto = new float[_nbr];
		_qto = new float[_nbr];
	}
	
	public void report(PrintWriter out) throws IOException, PsseModelException
	{
		out.println("BusNdx,BusID,BusName,Energized,Island,Type,VA,VM,Pmm,Qmm,MaxMM,DevID,DevName,Pdev,Qdev,Pmin,Pmax,Qmin,Qmax,AtPlim,AtQlim");
		for (int i=0; i < _nbus; ++i)
		{
			_report(out, i, _brnet.findBranches(i), _otnet.findBranches(i));
		}
	}

	public void report(File odir, String iter) throws IOException, PsseModelException
	{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				new File(odir, "mismatch" + iter + ".csv"))));
		report(out);
		out.close();
	}
	
	public void report(File out) throws IOException, PsseModelException
	{
		PrintWriter fout = new PrintWriter(new BufferedWriter(new FileWriter(out)));
		report(fout);
		fout.close();
	}

	void _report(PrintWriter out, int i, int[] branches, int[] otdevs) throws PsseModelException
	{
		BusGroup b = _tn.get(i);

		if (branches.length == 0 && otdevs.length == 0) return;
		float mmm = Math.max(Math.abs(_pmm[i]), Math.abs(_qmm[i]));

		Island island = _islands.findForBus(b.getBuses().get(0));
		
		String btmp = String.format(
				"%d,\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',%f,%f,%f,%f,%f,", 
				i, b.getObjectID(), b.getDebugName(),
				island.isEnergized() ? "true" : "false", island.getIndex(), 
				b.getBusType(), PAMath.rad2deg(_va[i]), _vm[i], 
				PAMath.pu2mva(_pmm[i], _model.getSBASE()), PAMath.pu2mva(_qmm[i], _model.getSBASE()), mmm);

		for(int ttdev : branches)
		{
			TwoTermDev ttd = null;
			int t = ttdev;
			for(Object blist : _brorder)
			{
				@SuppressWarnings("unchecked")
				List<? extends TwoTermDev> list = (List<? extends TwoTermDev>) blist;
				if (t < list.size()) {ttd = list.get(t); break;}
				t -= list.size();
			}
			
			int fbus = _tn.findGrpNdx(ttd.getFromBus());
			float pp, qq;
			if (fbus == i)
			{
				pp = _pfrm[ttdev];
				qq = _qfrm[ttdev];
			}
			else
			{
				pp = _pto[ttdev];
				qq = _qto[ttdev];
			}
			out.print(btmp);
			out.format("\'%s\',\'%s\',%f,%f\n", ttd.getObjectID(),
					ttd.getObjectName(), PAMath.pu2mva(pp, _model.getSBASE()), PAMath.pu2mva(qq, _model.getSBASE()));

		}
		
		for(int otdev : otdevs)
		{
			out.print(btmp);
			printDevice(out, otdev, _pmm[i], _qmm[i], b, b.getBusType(), island.isEnergized());
		}

	}

	void printDevice(PrintWriter out, int otdev, float pmm, float qmm, BusGroup b, BusTypeCode btype, boolean isener) throws PsseModelException
	{
		OneTermDev od;
		float pval, qval;
		String pmax="", pmin="", qmax="", qmin="",atplim="",atqlim="";
		if (otdev < _ngen)
		{
			Gen god = _model.getGenerators().get(otdev);
			od = god;
			pval = !isener ? 0f : PAMath.mva2pu(god.getPS(), _model.getSBASE());
			qval = (isener && btype == BusTypeCode.Load) ? PAMath.mva2pu(god.getQS(), _model.getSBASE()) : 0f;
			float pb = god.getPB(), pt = god.getPT(), qb = god.getQB(), qt = god.getQT();
			pmax = String.format("%f", pt);
			pmin = String.format("%f", pb);
			qmax = String.format("%f", qt);
			qmin = String.format("%f", qb);
			float pvmm = pval + pmm, qvmm = qval + qmm;
			float sbase = _model.getSBASE();
			if ((pvmm+0.005f) < PAMath.mva2pu(pb, _model.getSBASE()))
			{
				atplim = String.format("%f", PAMath.pu2mva(pvmm, sbase) - pb);
			}
			else if ((pvmm-0.005f) > PAMath.mva2pu(pt, sbase))
			{
				atplim = String.format("%f", PAMath.pu2mva(pvmm, sbase) - pt);
			}
			if ((qvmm+0.005f) < PAMath.mva2pu(qb, sbase))
			{
				atqlim = String.format("%f", PAMath.pu2mva(qvmm, sbase) - qb);
			}
			else if ((qvmm-0.005f) > PAMath.mva2pu(qt, sbase))
			{
				atqlim = String.format("%f", PAMath.pu2mva(qvmm, sbase) - qt);
			}
			
		}
		else if ((otdev -= _ngen) < _nload)
		{
			od = _model.getLoads().get(otdev);
			pval = -od.getPpu();
			qval = -od.getQpu();
		}
		else if ((otdev -= _nload) < _nshunt)
		{
			od = _model.getShunts().get(otdev);
			pval = 0;
			qval = _shq[otdev];
		}
		else
		{
			otdev -= _nshunt;
			od = _model.getSvcs().get(otdev);
			pval = 0;
			qval = _svcq[otdev];
		}

		float sbase = _model.getSBASE();
		out.format("\"%s\",\"%s\",%f,%f,%s,%s,%s,%s,%s,%s\n",
				od.getObjectID(), od.getDebugName(), PAMath.pu2mva(pval, sbase), PAMath.pu2mva(qval, sbase),
				pmin, pmax, qmin, qmax, atplim, atqlim);

	}

	public void setBranchFlows(float[] pfrm, float[] qfrm, float[] pto,
			float[] qto)
	{
		System.arraycopy(pfrm, 0, _pfrm, 0, pfrm.length);
		System.arraycopy(qfrm, 0, _qfrm, 0, qfrm.length);
		System.arraycopy(pto, 0, _pto, 0, pto.length);
		System.arraycopy(qto, 0, _qto, 0, qto.length);
	}

	public void setTwoTermDCLineFlows(float[] pfrm, float[] qfrm, float[] pto,
			float[] qto) throws PsseModelException
	{
		int nbr = _model.getBranches().size();
		System.arraycopy(pfrm, 0, _pfrm, nbr, pfrm.length);
		System.arraycopy(qfrm, 0, _qfrm, nbr, qfrm.length);
		System.arraycopy(pto, 0, _pto, nbr, pto.length);
		System.arraycopy(qto, 0, _qto, nbr, qto.length);
	}

	public void setShunts(float[] q)
	{
		_shq = q;
	}

	public void setSVCs(float[] q)
	{
		_svcq = q;
	}

	public void setVoltages(float[] va, float[] vm)
	{
		_vm = vm;
		_va = va;
	}
	
	public void setMismatches(float[] pmm, float[] qmm)
	{
		_pmm = pmm;
		_qmm = qmm;
	}
}