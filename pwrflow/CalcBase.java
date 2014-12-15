package com.powerdata.openpa.pwrflow;

import java.util.EnumMap;
import java.util.Map;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.ACBranchListIfc;
import com.powerdata.openpa.FixedShunt;
import com.powerdata.openpa.FixedShuntList;
import com.powerdata.openpa.FixedShuntListIfc;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.OutOfService;
import com.powerdata.openpa.OutOfServiceList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.impl.GroupMap;
import com.powerdata.openpa.tools.ComplexList;

public abstract class CalcBase
{
//	int[] _oosndx;
	PAModelException _e = null;

	/*
	public static int[] GetInService(OutOfServiceList<? extends OutOfService> list) throws PAModelException
	{
		boolean[] oos = list.isOutOfSvc();
		int n = oos.length;
		int[] map = new int[n];
		for(int i=0; i < n; ++i)
			map[i] = oos[i]?1:0;
		return new GroupMap(map,2).get(0);
	}
	*/
	/*
	public static <T extends ACBranch, U extends ACBranchListIfc<T>> 
		ComplexList ComputeListY(U list) throws PAModelException
	{
		int nbr = list.size();
		ComplexList y = new ComplexList(nbr, true);
		ComplexList z = new ComplexList(list.getR(), list.getX());
		for(int i=0; i < nbr; ++i)
			y.set(i, z.get(i).inv());
		return y;
		
	}
	*/
	/*
	public interface Composite
	{
		CalcBase getCalc();
	}

	public static class BranchComposite implements Composite
	{
		ACBranchList _branches;
		ACBranchFlow _calc;
		ComplexList _y;
		public BranchComposite(float sbase, ACBranchListIfc<? extends ACBranch> branches, BusRefIndex bri)
			throws PAModelException
		{
			_branches = new ACBranchList(branches);
			_y = ComputeListY(branches);
			_calc = new ACBranchFlow(sbase, bri, branches, _y);
		}
		public ACBranchList getBranches() {return _branches;}
		@Override
		public ACBranchFlow getCalc() {return _calc;}
		public ComplexList getY() {return _y;}
	}
	
	public static class FixedShuntComposite implements Composite
	{
		FixedShuntList _shunts;
		FixedShuntCalc _calc;
		public FixedShuntComposite(float sbase, FixedShuntListIfc<? extends FixedShunt> shunts, BusRefIndex bri)
			throws PAModelException
		{
			_shunts = new FixedShuntList(shunts);
			_calc = new FixedShuntCalc(sbase, bri, shunts);
		}

		public FixedShuntList getFixedShunts() {return _shunts;}
		@Override
		public FixedShuntCalc getCalc() {return _calc;}
	}
	
	public static Map<ListMetaType,BranchComposite> getBranchComposite(PAModel m, BusRefIndex bri)
		throws PAModelException
	{
		Map<ListMetaType,BranchComposite> rv = new EnumMap<>(ListMetaType.class);
		float sbase = m.getSBASE();
		rv.put(ListMetaType.Line, new BranchComposite(sbase, m.getLines(), bri));
		rv.put(ListMetaType.SeriesCap, new BranchComposite(sbase, m.getSeriesCapacitors(), bri));
		rv.put(ListMetaType.SeriesReac, new BranchComposite(sbase, m.getSeriesReactors(), bri));
		rv.put(ListMetaType.Transformer, new BranchComposite(sbase, m.getTransformers(), bri));
		rv.put(ListMetaType.PhaseShifter, new BranchComposite(sbase, m.getPhaseShifters(), bri));
		return rv;
	}
	
	public static Map<ListMetaType,FixedShuntComposite> getFixedShuntComposite(PAModel m, BusRefIndex bri) throws PAModelException
	{
		Map<ListMetaType,FixedShuntComposite> rv = new EnumMap<>(ListMetaType.class);
		float sbase = m.getSBASE();
		rv.put(ListMetaType.ShuntCap, new FixedShuntComposite(sbase, m.getShuntCapacitors(), bri));
		rv.put(ListMetaType.ShuntReac, new FixedShuntComposite(sbase, m.getShuntReactors(), bri));
		
		return rv;
	}
	
	public int[] getInSvc()
	{
		return _oosndx;
	}
	
	protected CalcBase(OutOfServiceList baseList) throws PAModelException
	{
		_oosndx = GetInService(baseList);
	}
	*/
	public abstract void applyMismatches(float[] pmm, float[] qmm);
	public abstract void calc(float[] va, float[] vm);
	
	public PAModelException getErr()
	{
		PAModelException rv = _e;
		_e = null;
		return rv;
	}
}
