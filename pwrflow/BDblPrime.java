package com.powerdata.openpa.pwrflow;

import java.util.Collection;
import java.util.List;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.FixedShunt;
import com.powerdata.openpa.FixedShuntListIfc;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.pwrflow.ACBranchExtList.ACBranchExt;
import com.powerdata.openpa.pwrflow.SVCCalcList.SVCCalc;
import com.powerdata.openpa.tools.PAMath;

public class BDblPrime<T extends ACBranchExt> extends BPrimeBase
{
	BusList _buses;
	float _sbase = 100f;
	SVCCalcList _svc;
	float[] _lastsvc;
	
	public BDblPrime(ACBranchAdjacencies<T> adj,
			Collection<FixedShuntListIfc<? extends FixedShunt>> fsh, 
			SVCCalcList svc, BusRefIndex bri)
			throws PAModelException
	{
		super(adj, new ACBranchBppElemBldr(adj));
		_buses = bri.getBuses();
		addFixedShunts(fsh);
		_svc = svc;
		_lastsvc = new float[_svc.size()];
//		addSVCs(m.getSVCs(), bri);
		//TODO:  dynamically adjust B'' for SVC's that are at their reactive limits
	}

	void addFixedShunts(Collection<? extends List<? extends FixedShunt>> lists)
		throws PAModelException
	{
		for(List<? extends FixedShunt> fshlist : lists)
		{
			for(FixedShunt fsh : fshlist)
			{
				_bdiag[_buses.getByBus(fsh.getBus()).getIndex()] -= 
					PAMath.mva2pu(fsh.getB(), _sbase);
			}
		}
	}
	public void adjustSVC() throws PAModelException
	{
		for (SVCCalc c : _svc)
		{
			int ix = c.getIndex();
			float last = _lastsvc[ix];
			int bidx = c.getBus().getIndex();
			_bdiag[bidx] += last;
			last = c.getBpp();
			_bdiag[bidx] -= last;
		}
	}
}
