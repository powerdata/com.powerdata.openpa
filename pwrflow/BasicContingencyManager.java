package com.powerdata.openpa.pwrflow;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import com.powerdata.openpa.CloneModelBuilder;
import com.powerdata.openpa.Island;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.pwrflow.ContingencySet.Contingency;

/**
 * Run contingencies using simple scheduling algoriths, either serial or using
 * parallel streams
 * 
 * @author chris@powerdata.com
 *
 */
public abstract class BasicContingencyManager extends ContingencyManager
{
	static class CAWorkerNoRat extends CAWorker
	{
		public CAWorkerNoRat(PAModel model, String cname) {super(model, cname);}

		@Override
		Map<ListMetaType, int[]> getOverloads(Set<Island> collapsed) {return new EnumMap<>(ListMetaType.class);}
	}
	
	static BiFunction<PAModel, String, CAWorker> _useratings = CAWorker::new,
			_noratings = CAWorkerNoRat::new;
	
	private boolean _par = false;
	BiFunction<PAModel, String, CAWorker> _cawrkr = _useratings;
	IslandConv[] _startPFResults;
	boolean _enadbg = false;

	public BasicContingencyManager(PAModel m, IslandConv[] startPfResults)
	{
		super(m);
		_startPFResults = startPfResults;
		//TODO:  it would be useful to allow ModelBuilder().load to be called 
		// as many times as desired to create new models
	}

	public void setDebug(boolean d) {_enadbg = d;}
	
	public void setParallel(boolean p) {_par = p;}
	public boolean getParallel() {return _par;}
	public void setIgnoreRatings(boolean i) {_cawrkr = i ? _noratings : _useratings;}
	public boolean getIgnoreRatings() {return _cawrkr == _noratings;}
	
	@Override
	public void runSet(ContingencySet set)
	{
		Stream<Contingency> cstream = _par ? set.parallelStream() : set.stream();
		cstream.forEach(c -> applyContingency(c));
	}
	
	void applyContingency(Contingency c)
	{
		try
		{
			PAModel clm = new CloneModelBuilder(_model, _localcols).load();
			c.execute(clm);
			clm.refreshIslands();
			CAWorker w = _cawrkr.apply(clm, c.getName());
			if (_enadbg) w.setDbg(true);
			w.runContingency();
			report(c, w.getResults(_startPFResults), clm);
		}
		catch(PAModelException e)
		{
			recordException(c, e);
		}
	}

}
