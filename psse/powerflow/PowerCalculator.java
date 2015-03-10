package com.powerdata.openpa.psse.powerflow;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.ACBranchList;
import com.powerdata.openpa.psse.BusTypeCode;
import com.powerdata.openpa.psse.Gen;
import com.powerdata.openpa.psse.GenList;
import com.powerdata.openpa.psse.Load;
import com.powerdata.openpa.psse.LoadList;
import com.powerdata.openpa.psse.OneTermDev;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.SVC;
import com.powerdata.openpa.psse.Shunt;
import com.powerdata.openpa.psse.ShuntList;
import com.powerdata.openpa.psse.SvcList;
import com.powerdata.openpa.psse.TwoTermDCLine;
import com.powerdata.openpa.psse.TwoTermDCLine.CtrlMode;
import com.powerdata.openpa.psse.TwoTermDCLineList;
import com.powerdata.openpa.psse.util.BusGroup;
import com.powerdata.openpa.psse.util.BusGroup2TDevList;
import com.powerdata.openpa.psse.util.ImpedanceFilter;
import com.powerdata.openpa.psse.util.MinZMagFilter;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PAMath;
/**
 * Utility to calculate branch flows and bus mismatches.
 * 
 * @author chris@powerdata.com
 *
 */
public class PowerCalculator
{
	PsseModel _model;
	MismatchReport _dbg;
	ImpedanceFilter _zfilt;
	/** topological nodes */
	BusGroup2TDevList _tn;

	/**
	 * Create a new power calculator
	 * @param model
	 * @throws PsseModelException 
	 */
	public PowerCalculator(PsseModel model, BusGroup2TDevList tn) throws PsseModelException
	{
		_model = model;
		_dbg = null;
		/*
		 * TODO:  clean up impedance filters to operate more flexibly and
		 * probably not require a branch list.  Otherwise, the filter will 
		 * not operate correctly on sublists;
		 */
		_zfilt = new ImpedanceFilter(_model.getBranches());
		_tn = tn;
	}
	/**
	 * Create a new power calculator
	 * @param model
	 * @param zfilt impedance filter used to force impedance within certain bounds  
	 * @throws PsseModelException 
	 */
	public PowerCalculator(PsseModel model, ImpedanceFilter zfilt, BusGroup2TDevList tn)
			throws PsseModelException
	{
		_model = model;
		_dbg = null;
		_zfilt = zfilt;
		_tn = tn;
	}

	public void setDebugEnabled(MismatchReport dbg) {_dbg = dbg;}
	public void setDebugDisabled() {_dbg = null;}
	public void setImpedanceFilter(ImpedanceFilter zfilt) {_zfilt = zfilt;}
	public ImpedanceFilter getImpedanceFilter() {return _zfilt;}
	
	/**
	 * Calculate AC branch flows on all AC branches in model
	 * 
	 * @param vang
	 *            Bus voltage angles
	 * @param vmag
	 *            Bus voltage magnitudes
	 * @return <p>
	 *         System active and reactive branch flows in order of all system
	 *         branches from PsseModel.getBranches()
	 *         </p>
	 *         <ul>
	 *         <li>Offset 0 - Array of from-side active power flows (p.u.)</li>
	 *         <li>Offset 1 - Array of from-side reactive power flows (p.u.)</li>
	 *         <li>Offset 2 - Array of to-side active power flows (p.u.)</li>
	 *         <li>Offset 3 - Array of to-side reactive power flows (p.u.)</li>
	 *         </ul>
	 * @throws PsseModelException
	 */
	public float[][] calcACBranchFlows(float[] vang, float[] vmag)
			throws PsseModelException
	{
		return calcACBranchFlows(_model.getBranches(), vang, vmag, _zfilt);
	}
	
	public float[][] calcACBranchFlows(List<? extends ACBranch> branches,
			float[] vang, float[] vmag) throws PsseModelException
	{
		return calcACBranchFlows(branches, vang, vmag, new ImpedanceFilter(
				branches));
	}
	
	/**
	 * Calculate AC branch flows
	 * 
	 * @param branches
	 *            List of branches to calculate.
	 * @param vang
	 *            Bus voltage angles
	 * @param vmag
	 *            Bus voltage magnitudes
	 * @return <p>
	 *         System active and reactive branch flows in order of branches
	 *         </p>
	 *         <ul>
	 *         <li>Offset 0 - Array of from-side active power flows (p.u.)</li>
	 *         <li>Offset 1 - Array of from-side reactive power flows (p.u.)</li>
	 *         <li>Offset 2 - Array of to-side active power flows (p.u.)</li>
	 *         <li>Offset 3 - Array of to-side reactive power flows (p.u.)</li>
	 *         </ul>
	 * @throws PsseModelException
	 */
	public float[][] calcACBranchFlows(List<? extends ACBranch> branches,
			float[] vang, float[] vmag, ImpedanceFilter zfilt) throws PsseModelException
	{
		int nbr = branches.size();
		float[] pfrm = new float[nbr], pto = new float[nbr],
				qfrm = new float[nbr], qto = new float[nbr];
		for (int i=0; i < nbr; ++i)
		{
			ACBranch br = branches.get(i);
			if (br.isInSvc())
			{
				int fbndx = _tn.findGrpNdx(br.getFromBus());
				int tbndx = _tn.findGrpNdx(br.getToBus());
				float fvm = vmag[fbndx], tvm = vmag[tbndx],
						fva = vang[fbndx], tva = vang[tbndx];
				
				float shift = fva - tva - br.getPhaseShift();

				float tvmpq = fvm * tvm
						/ (br.getFromTap() * br.getToTap());
				float tvmp2 = fvm * fvm
						/ (br.getFromTap() * br.getFromTap());
				float tvmq2 = tvm * tvm / (br.getToTap() * br.getToTap());

				float ctvmpq = tvmpq * (float) Math.cos(shift);
				float stvmpq = tvmpq * (float) Math.sin(shift);

				Complex y = zfilt.getY(i);
				float gcos = ctvmpq * y.re();
				float bcos = ctvmpq * y.im();
				float gsin = stvmpq * y.re();
				float bsin = stvmpq * y.im();

				pfrm[i] = -(-gcos - bsin + tvmp2 * y.re());
				qfrm[i] = -(-gsin + bcos - tvmp2 * (y.im() + br.getBmag()+br.getFromBchg()));
				pto[i] = -(-gcos + bsin + tvmq2 * y.re());
				qto[i] = -(gsin + bcos - tvmq2 * (y.im() + br.getBmag()+br.getToBchg()));
			}
		}
		if (_dbg != null) _dbg.setBranchFlows(pfrm, qfrm, pto, qto);
		return new float[][] {pfrm, qfrm, pto, qto};
	}
	
	public float[][] calculateMismatches(float[][] v) throws PsseModelException
	{
		return calculateMismatches(v[0], v[1]);
	}
	
	public float[][] calculateMismatches(float[] va, float[] vm)
			throws PsseModelException
	{
		int nbus = _tn.size();
		float[] pmm = new float[nbus], qmm = new float[nbus];
		LoadList ldlist = _model.getLoads();
		GenList genlist = _model.getGenerators();
		
		applyTwoTermDCLine(pmm, qmm, calcTwoTermDCLines(_model.getTwoTermDCLines(), vm));
		applyBranches(pmm, qmm, calcACBranchFlows(va, vm));
		applyShunts(qmm, calcShunts(vm), _model.getShunts());
		applyShunts(qmm, calcSVCs(vm), _model.getSvcs());
		for(Load l : ldlist)
		{
			if (l.isInSvc())
			{
				int bndx = _tn.findGrpNdx(l.getBus());
				pmm[bndx] -= l.getPpu();
				qmm[bndx] -= l.getQpu();
			}
		}
		float sbase = _model.getSBASE();
		for(Gen g : genlist)
		{
			BusGroup b = _tn.findGroup(g.getBus());
			BusTypeCode btc = b.getBusType();
			if (g.isInSvc())
			{
				int bndx = b.getIndex();
				pmm[bndx] += PAMath.mva2pu(g.getPS(), sbase);
				if (btc == BusTypeCode.Load)
					qmm[bndx] += PAMath.mva2pu(g.getQS(), sbase);
			}
			//TODO: should not be converting to per-unit here
		}
		if (_dbg != null)
		{
			_dbg.setMismatches(pmm, qmm);
			_dbg.setVoltages(va, vm);
		}
		return new float[][] {pmm, qmm};
	}

	protected void applyTwoTermDCLine(float[] pmm, float[] qmm,
			TwoTermDCLineResultList dclresults) throws PsseModelException
	{
		TwoTermDCLineList dclines = _model.getTwoTermDCLines();
		for (int i=0; i < dclines.size(); ++i)
		{
			TwoTermDCLine l = dclines.get(i);
			TwoTermDCLineResult r = dclresults.get(i);
			int fn = _tn.findGrpNdx(l.getFromBus());
			int tn = _tn.findGrpNdx(l.getToBus());
			pmm[fn] += r.getMWR();
			pmm[tn] += r.getMWI();
			qmm[fn] += r.getMVArR();
			qmm[tn] += r.getMVArI();
		}
	}
	
	void applyBranches(float[] pmm, float[] qmm,
			float[][] brflows) throws PsseModelException
	{
		ACBranchList brlist = _model.getBranches();
		int nbr = brlist.size();
		for(int i=0; i < nbr; ++i)
		{
			ACBranch branch = brlist.get(i);
			int fbndx = _tn.findGrpNdx(branch.getFromBus());
			int tbndx = _tn.findGrpNdx(branch.getToBus());
			pmm[fbndx] += brflows[0][i];
			qmm[fbndx] += brflows[1][i];
			pmm[tbndx] += brflows[2][i];
			qmm[tbndx] += brflows[3][i];
		}
	}

	void applyShunts(float[] qmm, float[] qshunt,
			List<? extends OneTermDev> list) throws PsseModelException
	{
		for (int i = 0; i < list.size(); ++i)
		{
			int bndx = _tn.findGrpNdx(list.get(i).getBus());
			qmm[bndx] += qshunt[i];
		}
	}

	/**
	 * Calculate fixed shunt reactive power on all system shunts defined in
	 * PsseModel.getShunts()
	 * 
	 * @param vm
	 *            current solved Bus voltage magnitudes
	 * @return shunt reactive power for all model shunts from
	 *         PsseModel.getShunts()
	 * @throws PsseModelException
	 */
	public float[] calcShunts(float[] vm) throws PsseModelException
	{
		return calcShunts(_model.getShunts(), vm);
	}

	/**
	 * Calculate fixed shunt reactive power
	 * @param shunts List of shunts
	 * @param vm current solved Bus voltage magnitudes
	 * @return shunt reactive power 
	 * @throws PsseModelException
	 */
	public float[] calcShunts(ShuntList shunts, float[] vm) throws PsseModelException
	{
		int nsh = shunts.size();
		float[] q = new float[nsh];

		for (int i = 0; i < nsh; ++i)
		{
			Shunt shunt = shunts.get(i);
			int bndx = _tn.findGrpNdx(shunt.getBus());
			float bvm = vm[bndx];
			q[i] = shunt.getBpu() * bvm * bvm;
		}
		if (_dbg != null) _dbg.setShunts(q);
		return q;
	}

	/**
	 * Calculate SVC reactive power for all model SVC's using PsseModel.getSvcs()
	 * 
	 * @param vm
	 *            Bus solved voltage magnitudes
	 * @return SVC reactive power in order of PsseModel.getSvcs()
	 * @throws PsseModelException
	 */
	public float[] calcSVCs(float[] vm) throws PsseModelException
	{
		return calcSVCs(_model.getSvcs(), vm);
	}
	
	/**
	 * Calculate SVC reactive power
	 * 
	 * @param svcs
	 *            List of SVC's to calculate
	 * @param vm
	 *            Bus solved voltage magnitudes
	 * @return SVC reactive power in order of SvcList
	 * @throws PsseModelException
	 */
	public float[] calcSVCs(SvcList svcs, float[] vm) throws PsseModelException
	{
		int nsh = svcs.size();
		float[] q = new float[nsh];

		//TODO:  Make this an actual SVC calculation, not a fixed shunt
		for (int i = 0; i < nsh; ++i)
		{
			SVC svc = svcs.get(i);
			int bndx = _tn.findGrpNdx(svc.getBus());
			float bvm = vm[bndx];
			q[i] = svc.getBINIT() / 100f * bvm * bvm;
		}
		if (_dbg != null) _dbg.setSVCs(q);
		return q;
	}
	
	/** 
	 * Get current Realtime voltage information (model bus order)
	 * @return Array of float arrays, 0: va, 1: vm
	 * @throws PsseModelException
	 */
	public float[][] getRTVoltages() throws PsseModelException
	{
		int nbus = _tn.size();
		float[] va = new float[nbus], vm = new float[nbus];
		for(int i=0; i < nbus; ++i)
		{
			va[i] = _tn.getVArad(i);
			vm[i] = _tn.getVMpu(i);
		}
		return new float[][] {va, vm};
	}
	
	/**
	 * Currently uses a simplified approach to leave constant angle and calculate tap algebraically
	 * @param lines List of 2-terminal dc lines
	 * @param vm solved voltage magnitudes
	 * @return List of results for each line
	 * @throws PsseModelException
	 */
	public TwoTermDCLineResultList calcTwoTermDCLines(TwoTermDCLineList lines,
			float[] vm) throws PsseModelException
	{
		int nl = lines.size();
		float[] rtap = new float[nl];
		float[] itap = new float[nl];
		float[] alpha = new float[nl];
		float[] gamma = new float[nl];
		float[] rmw = new float[nl];
		float[] rmvar = new float[nl];
		float[] imw = new float[nl];
		float[] imvar = new float[nl];
		
		float sbase = _model.getSBASE();
		for (int i=0; i < nl; ++i)
		{
			TwoTermDCLine dcl = lines.get(i);
			if (dcl.getCtrlMode() != CtrlMode.Blocked)
			{
				int rbus = _tn.findGrpNdx(dcl.getFromBus());
				int ibus = _tn.findGrpNdx(dcl.getToBus());
				float[] setpt = getDCSetpoints(dcl);
				float isp = setpt[0], vdr = setpt[1], vdi = setpt[2];
				float[] rresult = calc2TDCRect(dcl, isp, vdr, vm[rbus]);
				float[] iresult = calc2TDInv(dcl, isp, vdi, vm[ibus]);
				alpha[i] = rresult[0];
				rtap[i] = rresult[1];
				rmw[i] = PAMath.mva2pu(-rresult[2], sbase);
				rmvar[i] = PAMath.mva2pu(-rresult[3], sbase);
				gamma[i] = iresult[0];
				itap[i] = iresult[1];
				imw[i] = PAMath.mva2pu(iresult[2], sbase);
				imvar[i] = PAMath.mva2pu(-iresult[3], sbase);
			}
		}
		if (_dbg != null)
		{
			_dbg.setTwoTermDCLineFlows(rmw, rmvar, imw, imvar);
		}
		
		return new TwoTermDCLineResultList(lines, rtap, itap, alpha, gamma,
				rmw, rmvar, imw, imvar);
	}

	/**
	 * Calculate Inverter quantities
	 * @param dcl Two Terminal DC Line object
	 * @param isp DC current set point (kA)
	 * @param vd Scheduled DC Inverter voltage (kV)
	 * @param vm Converter AC Bus voltage magnitude (per-unit)
	 * @return
	 * @throws PsseModelException 
	 */
	protected float[] calc2TDInv(TwoTermDCLine dcl, float isp, float vdi,
			float vm) throws PsseModelException
	{
		return calc2TConverter(isp, vdi, vm, dcl.getEBASI(), dcl.getNBI(),
				dcl.getGAMMNrad(), dcl.getGAMMXrad(), dcl.getTRI(), dcl.getTMXI(),
				dcl.getTMNI(), dcl.getSTPI(), dcl.getXCI());
	}
	/**
	 * Calculate Rectifier quantities
	 * @param dcl Two Terminal DC Line object
	 * @param isp DC current set point (kA)
	 * @param vd Scheduled DC Rectifier voltage (kV)
	 * @param vm Converter AC Bus voltage magnitude (per-unit)
	 * @return
	 * @throws PsseModelException 
	 */
	protected float[] calc2TDCRect(TwoTermDCLine dcl, float isp, float vdr,
			float vm) throws PsseModelException
	{
		return calc2TConverter(isp, vdr, vm, dcl.getEBASR(), dcl.getNBR(),
				dcl.getALFMNrad(), dcl.getALFMXrad(), dcl.getTRR(), dcl.getTMXR(),
				dcl.getTMNR(), dcl.getSTPR(), dcl.getXCR());
	}

	/**
	 * Calculate 2-terminal dc line converter quantities
	 * @param isp DC Current set point (kA)
	 * @param vd DC Voltage set point
	 * @param vm AC converter bus magnitude
	 * @param ebas AC transformer primary side base voltage (KV)
	 * @param nb Number of rectifier bridges in series
	 * @param angmn Minimum converter operating angle
	 * @param angmx Maximum converter operating angle
	 * @param tr Converter Transformer ratio
	 * @param tmx Maximum tap ratio
	 * @param tmn Minimum tap ratio
	 * @param stp Tap step size
	 * @param xc Commutation reactance (ohms)
	 * @return array of solved quantities 0: angle, 1:tap, 2:mw, 3:mvar
	 */
	protected float[] calc2TConverter(float isp, float vd, float vm, float ebas, int nb,
			float angmn, float angmx, float tr, float tmx,
			float tmn, float stp, float xc)
	{
		float[] rv = new float[4];
		/* force minimum angle */
		rv[0] = angmn;
		
		float vd0 = (vd + isp * nb * PAMath.THREEOVERPI * xc)
				/ ((float) Math.cos(angmn));
		/* calculated tap */
		rv[1] = PAMath.THREESQRT2OVERPI * nb * tr * vm * ebas / vd0;
		float cosphi = vd / vd0;
		
		/* active power (MW) */
		rv[2] = isp * vd;
		/* reactive power (MVAr) */
		rv[3] = rv[2] * ((float) Math.tan(Math.acos(cosphi)));
		return rv;
	}
	/**
	 * Determine dc current, rectifier and inverter dc voltage setpoints
	 * @param dcl DC Line
	 * @return array of results  0: current, 1:rectifier voltage, 2:inverter voltage
	 * @throws PsseModelException
	 */
	protected float[] getDCSetpoints(TwoTermDCLine dcl) throws PsseModelException
	{
		CtrlMode cmode = dcl.getCtrlMode();
		float isp = 0;
		float demand = dcl.getSETVL();
		float rdc = dcl.getRDC();
		float rcomp = dcl.getRCOMP();
		float vsp = dcl.getVSCHD();
		float rrcomp = rdc - rcomp;
		if (cmode == CtrlMode.Current)
		{
			isp = demand/1000f;
		}
		else if (cmode == CtrlMode.Power)
		{
			float r = rrcomp;
			/** power specified at inverter? */
			boolean ispinv = false;
			if (demand < 0)
			{
				demand *= -1f;
				r = rcomp;
				ispinv = true;
			}
			
			if (r == 0)
			{
				isp = demand / vsp;
			}
			else
			{
				/* quadratic in both E and I */
				float p = vsp/r, q = -demand/r;
				if (ispinv)
				{
					p *= -1;
					q *= -1;
				}
				float np2 = -p/2;
				float sqt = (float) Math.sqrt(np2*np2-q);
				float isp1 = np2 + sqt;
				float isp2 = np2 - sqt;
				/* find the reasonable root */
				float guess = demand / vsp;
				isp = Math.abs((Math.abs(isp1-guess) < Math.abs(isp2-guess)) ? isp1 : isp2);
			}
		}
		float[] rv = new float[3];
		/* current set point */
		rv[0] = isp;
		/* voltage setpoint at rectifier */
		rv[1] = vsp + isp * rrcomp;
		/* voltage setpoint at inverter */
		rv[2] = vsp - isp * rcomp;

		return rv;
	}
	
	public static void main(String[] args) throws Exception
	{
		String uri = null;
		PrintWriter pws = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(System.out)));
		for(int i=0; i < args.length;)
		{
			String s = args[i++].toLowerCase();
			int ssx = 1;
			if (s.startsWith("--")) ++ssx;
			switch(s.substring(ssx))
			{
				case "uri":
					uri = args[i++];
					break;
				case "output":
					pws = new PrintWriter(new BufferedWriter(new FileWriter(args[i++])));
					break;
			}
		}
		
		final PrintWriter pw = pws;
		
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri [ -output path_to_output_file ]");
			System.exit(1);
		}
		PsseModel model = PsseModel.Open(uri);
		BusGroup2TDevList tn = new BusGroup2TDevList(model);
		PowerCalculator pc = new PowerCalculator(model, 
			new MinZMagFilter(model.getBranches(), 0.00001f), 
			tn);
		MismatchReport mmr = new MismatchReport(model, tn);
		pc.setDebugEnabled(mmr);
		pc.calculateMismatches(pc.getRTVoltages());
		mmr.report(pws);
		pws.close();
	}
}

