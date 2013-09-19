package com.powerdata.openpa.psse.powerflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.ACBranchList;
import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.OneTermDev;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.LinkNet;
import com.powerdata.openpa.tools.PAMath;

public class MismatchReport
{
	PsseModel _model;
	LinkNet _brnet = new LinkNet();
	LinkNet _otnet = new LinkNet();
	int _nbus;
	List<List<? extends OneTermDev>> _otdevorder;
	int _ngen, _nload, _nshunt, _nsvc;
	float[] _brflow, _shq, _svcq, _pfrm, _pto, _qfrm, _qto, _vm, _va, _pmm, _qmm;
//	PrintWriter _out;
	File _odir;
	
	public MismatchReport(PsseModel model, File odir) throws PsseModelException
	{
		_odir = odir;
		_model = model;
		_nbus = model.getBuses().size();
		
		_otdevorder = new ArrayList<List<? extends OneTermDev>>();
		_otdevorder.add(_model.getGenerators());
		_otdevorder.add(_model.getLoads());
		_otdevorder.add(_model.getShunts());
		_otdevorder.add(_model.getSvcs());
		
		_ngen = _otdevorder.get(0).size();
		_nload = _otdevorder.get(1).size();
		_nshunt = _otdevorder.get(2).size();

		int otsize = 0;
		for(List<? extends OneTermDev> l : _otdevorder)
			otsize += l.size();
		
		_brnet.ensureCapacity(_nbus+1, model.getBranches()
				.size());
		_otnet.ensureCapacity(_nbus+1, otsize);

		for(ACBranch branch : model.getBranches())
		{
			int fndx = _nbus;
			int tndx = _nbus;
			
			if (branch.isInSvc())
			{
				fndx = branch.getFromBus().getIndex();
				tndx = branch.getToBus().getIndex();
			}
			_brnet.addBranch(fndx, tndx);
		}

		for (List<? extends OneTermDev> otlist : _otdevorder)
		{
			for (OneTermDev otd : otlist)
			{
				int bndx = (otd.isInSvc()) ? otd.getBus().getIndex() : _nbus;
				_otnet.addBranch(bndx, _nbus);
			}
		}
	}
	
	public void report(String iter) throws IOException, PsseModelException
	{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				new File(_odir, "mismatch" + iter + ".csv"))));
		
		out.println("BusID,BusName,Type,VA,VM,Pmm,Qmm,MaxMM,DevID,DevName,Pdev,Qdev");
		for (int i=0; i < _nbus; ++i)
		{
			_report(out, i, _brnet.findBranches(i), _otnet.findBranches(i));
		}
		out.close();
	}

	
	void _report(PrintWriter out, int i, int[] branches, int[] otdevs) throws PsseModelException
	{
		BusList buses = _model.getBuses();
		Bus b = buses.get(i);
//		if (!b.isEnergized()) return;
		//TODO:  shoudl island really ever be able to be -1?
		if (branches.length == 0 && otdevs.length == 0) return;
		ACBranchList acbr = _model.getBranches();
		float mmm = Math.max(Math.abs(_pmm[i]), Math.abs(_qmm[i]));

		String btmp = String.format("\"%s\",\"%s\",\"%s\",%f,%f,%f,%f,%f,",
				b.getObjectID(), b.getObjectName(), b.getBusType(), PAMath.rad2deg(_va[i]),
				_vm[i] * b.getBASKV(), PAMath.pu2mw(_pmm[i]),
				PAMath.pu2mvar(_qmm[i]), mmm);
		for(int acbranch : branches)
		{
			ACBranch acb = acbr.get(acbranch);
			int fbus = acb.getFromBus().getIndex();
			float pp, qq;
			if (fbus == i)
			{
				pp = _pfrm[acbranch];
				qq = _qfrm[acbranch];
			}
			else
			{
				pp = _pto[acbranch];
				qq = _qto[acbranch];
			}
			out.print(btmp);
			out.format("\"%s\",\"%s\",%f,%f\n", acb.getObjectID(),
					acb.getObjectName(), PAMath.pu2mw(pp), PAMath.pu2mvar(qq));

		}
		
		for(int otdev : otdevs)
		{
			out.print(btmp);
			printDevice(out, otdev);
		}

	}

	void printDevice(PrintWriter out, int otdev) throws PsseModelException
	{
		OneTermDev od;
		float pval, qval;
		if (otdev < _ngen)
		{
			od = _model.getGenerators().get(otdev);
			pval = od.getRTP();
			qval = od.getRTQ();
		}
		else if ((otdev -= _ngen) < _nload)
		{
			od = _model.getLoads().get(otdev);
			pval = od.getRTP();
			qval = od.getRTQ();
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

		out.format("\"%s\",\"%s\",%f,%f\n",
				od.getObjectID(), od.getObjectName(), PAMath.pu2mw(pval), PAMath.pu2mvar(qval)); 

	}

	public void setBranchFlows(float[] pfrm, float[] qfrm, float[] pto,
			float[] qto)
	{
		_pfrm = pfrm;
		_qfrm = qfrm;
		_pto = pto;
		_qto = qto;
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