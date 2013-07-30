package com.powerdata.openpa.busmismatch;

import java.util.List;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.OneTermDev;
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
		List<?>[] otdevs = new List<?>[] { model.getGenerators(),
				model.getLoads(), model.getShunts(), model.getSvcs() };
		int numotdev = 0;
		for(List<?> list : otdevs)
			numotdev += list.size();
		_nbus = model.getBuses().size();
		
		_brnet.ensureCapacity(_nbus, model.getBranches()
				.size());
		_otnet.ensureCapacity(_nbus+1, numotdev+1);

		for(ACBranch branch : model.getBranches())
		{
			_brnet.addBranch(branch.getFromBus().getIndex(), branch.getToBus()
					.getIndex());
		}
		for(List<?> list : otdevs)
		{
			for (Object obj : list)
			{
				_otnet.addBranch(((OneTermDev) obj).getBus().getIndex(), _nbus);
			}
		}
	}
	
	public void report(MismatchReporter r) throws Exception
	{
		for (int i=0; i < _nbus; ++i)
		{
			r.report(i, _brnet.findBranches(i), _otnet.findBranches(i));
		}
	}
	
}