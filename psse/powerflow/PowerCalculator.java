package com.powerdata.openpa.psse.powerflow;

import java.util.List;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.ACBranchList;
import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
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
import com.powerdata.openpa.psse.util.ImpedanceFilter;
import com.powerdata.openpa.tools.Complex;
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

	/**
	 * Create a new power calculator
	 * @param model
	 * @throws PsseModelException 
	 */
	public PowerCalculator(PsseModel model) throws PsseModelException
	{
		_model = model;
		_dbg = null;
		_zfilt = new ImpedanceFilter(_model.getBranches());
	}
	/**
	 * Create a new power calculator
	 * @param model
	 * @prarm zfilt Default impedance filter 
	 * @throws PsseModelException 
	 */
	public PowerCalculator(PsseModel model, ImpedanceFilter zfilt) throws PsseModelException
	{
		_model = model;
		_dbg = null;
		_zfilt = zfilt;
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
				int fbndx = br.getFromBus().getIndex();
				int tbndx = br.getToBus().getIndex();
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
		BusList blist = _model.getBuses();
		int nbus = blist.size();
		float[] pmm = new float[nbus], qmm = new float[nbus];
		LoadList ldlist = _model.getLoads();
		GenList genlist = _model.getGenerators();
		
		applyBranches(pmm, qmm, calcACBranchFlows(va, vm));
		applyShunts(qmm, calcShunts(vm), _model.getShunts());
		applyShunts(qmm, calcSVCs(vm), _model.getSvcs());
		for(Load l : ldlist)
		{
			if (l.isInSvc())
			{
				int bndx = l.getBus().getIndex();
				pmm[bndx] -= l.getPpu();
				qmm[bndx] -= l.getQpu();
			}
		}
		for(Gen g : genlist)
		{
			Bus b = g.getBus();
			BusTypeCode btc = b.getBusType();
			if (g.isInSvc()/* && btc != BusTypeCode.Slack*/)
			{
				int bndx = b.getIndex();
				pmm[bndx] += g.getPpu();
				if (btc == BusTypeCode.Load)
					qmm[bndx] += g.getQpu();
			}
		}
		if (_dbg != null)
		{
			_dbg.setMismatches(pmm, qmm);
			_dbg.setVoltages(va, vm);
		}
		return new float[][] {pmm, qmm};
	}

	void applyBranches(float[] pmm, float[] qmm,
			float[][] brflows) throws PsseModelException
	{
		ACBranchList brlist = _model.getBranches();
		int nbr = brlist.size();
		for(int i=0; i < nbr; ++i)
		{
			ACBranch branch = brlist.get(i);
			int fbndx = branch.getFromBus().getIndex();
			int tbndx = branch.getToBus().getIndex();
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
			int bndx = list.get(i).getBus().getIndex();
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
			int bndx = shunt.getBus().getIndex();
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
			int bndx = svc.getBus().getIndex();
			float bvm = vm[bndx];
			q[i] = svc.getBINIT() / 100f * bvm * bvm;
		}
		if (_dbg != null) _dbg.setSVCs(q);
		return q;
	}
	
	public float[][] getRTVoltages() throws PsseModelException
	{
		BusList blist = _model.getBuses();
		int nbus = blist.size();
		float[] va = new float[nbus], vm = new float[nbus];
		for(int i=0; i < nbus; ++i)
		{
			va[i] = blist.getVArad(i);
			vm[i] = blist.getVMpu(i);
		}
		return new float[][] {va, vm};
	}
	
}

