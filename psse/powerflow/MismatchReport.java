package com.powerdata.openpa.psse.powerflow;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.OneTermDev;
import com.powerdata.openpa.psse.OneTermDevList;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.LinkNet;

public class MismatchReport
{
	PsseModel _model;
	LinkNet _brnet = new LinkNet();
	LinkNet _otnet = new LinkNet();
	int _nbus;
	
	public static interface MismatchReporter
	{
		public void report(int bus, int[] acbranches, int[] onetermdevs) throws Exception;
	}
	
	public MismatchReport(PsseModel model) throws PsseModelException
	{
		_model = model;
		_nbus = model.getBuses().size();
		
		OneTermDevList otdevs = model.getOneTermDevs();
		
		_brnet.ensureCapacity(_nbus+1, model.getBranches()
				.size());
		_otnet.ensureCapacity(_nbus+1, otdevs.size());

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
		for(OneTermDev otd : model.getOneTermDevs())
		{
			int bndx = (otd.isInSvc()) ? otd.getBus().getIndex() : _nbus;
			_otnet.addBranch(bndx, _nbus);
		}
	}
	
	public void report(MismatchReporter r) throws Exception
	{
		for (int i=0; i < _nbus; ++i)
		{
			r.report(i, _brnet.findBranches(i), _otnet.findBranches(i));
		}
	}

	public void setBranchFlows(float[] pfrm, float[] qfrm, float[] pto,
			float[] qto)
	{
		// TODO Auto-generated method stub
		
	}

	public void setShunts(float[] q)
	{
		// TODO Auto-generated method stub
		
	}

	public void setSVCs(float[] q)
	{
		// TODO Auto-generated method stub
		
	}

	public void setVoltages(float[] va, float[] vm)
	{
		// TODO Auto-generated method stub
		
	}
	
}