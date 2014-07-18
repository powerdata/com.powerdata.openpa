package com.powerdata.openpa.impl;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.powerdata.openpa.*;
import com.powerdata.openpa.ColChange;

/**
 * Open PA model.  Provides access to power network and equipment 
 * 
 * @author chris@powerdata.com
 *
 */
public class PAModelI implements PAModel
{
	BusListI 			_buses;
	SwitchListI		_switches;
	LineListI		_lines;
	IslandList			_islands;
	AreaListI		_areas;
	OwnerListI		_owners;
	StationListI 	_stations;
	VoltageLevelListI _vlevs;
	TransformerListI	_transformers;
	PhaseShifterListI 	_phshifts;
	SeriesReacListI 		_serreacs;
	SeriesCapListI 		_sercaps;
	GenListI 			_gens;
	LoadListI 			_loads;
	ShuntReacListI 		_shuntreacs;
	ShuntCapListI 		_shuntcaps;
	TwoTermDCLineListI 	_t2dclines;
	SwitchedShuntListI 	_swshunts;
	SVCListI 			_svcs;
	 
	
	public PAModelI(){}
	
	Map<ListMetaType,AbstractPAList<? extends BaseObject>> _lists = new EnumMap<>(ListMetaType.class);
	
	public BaseList<? extends BaseObject> getList(ListMetaType type)
	{
		return _lists.get(type);
	}

	protected void loadComplete()
	{
		_lists.put(ListMetaType.Area, _areas);
		_lists.put(ListMetaType.Owner, _owners);
		_lists.put(ListMetaType.Station, _stations);
		_lists.put(ListMetaType.VoltageLevel, _vlevs);
		_lists.put(ListMetaType.Bus, _buses);
		_lists.put(ListMetaType.Switch, _switches);
		_lists.put(ListMetaType.Line, _lines);
		_lists.put(ListMetaType.SeriesCap, _sercaps);
		_lists.put(ListMetaType.SeriesReac, _serreacs);
		_lists.put(ListMetaType.Transformer, _transformers);
		_lists.put(ListMetaType.PhaseShifter, _phshifts);
		_lists.put(ListMetaType.TwoTermDCLine, _t2dclines);
		_lists.put(ListMetaType.Gen, _gens);
		_lists.put(ListMetaType.Load, _loads);
		_lists.put(ListMetaType.ShuntReac, _shuntreacs);
		_lists.put(ListMetaType.ShuntCap, _shuntcaps);
		_lists.put(ListMetaType.SVC, _svcs);
		_lists.put(ListMetaType.SwitchedShunt, _swshunts);
	}
	
	
	public IslandList getIslands()
	{
		return _islands;
	}
	
	@Nodump
	public IslandList refreshIslands()
	{
		_islands = new IslandListI(this);
		return _islands;
	}
	
	public AreaList getAreas()
	{
		return _areas;
	}
	
	public OwnerList getOwners()
	{
		return _owners;
	}
	
	public StationList getStations()
	{
		return _stations;
	}
	
	public VoltageLevelList getVoltageLevels()
	{
		return _vlevs;
	}

	@Override
	public BusList getBuses()
	{
		return _buses;
	}

	@Override
	public SwitchList getSwitches()
	{
		return _switches;
	}

	@Override
	public LineList getLines()
	{
		return _lines;
	}

	@Override
	public SeriesReacList getSeriesReactors()
	{
		return _serreacs;
	}

	@Override
	public SeriesCapList getSeriesCapacitors()
	{
		return _sercaps;
	}

	@Override
	public TransformerList getTransformers()
	{
		return _transformers;
	}

	@Override
	public PhaseShifterList getPhaseShifters()
	{
		return _phshifts;
	}

	@Override
	public GenList getGenerators()
	{
		return _gens;
	}

	@Override
	public LoadList getLoads()
	{
		return _loads;
	}

	@Override
	public ShuntReacList getShuntReactors()
	{
		return _shuntreacs;
	}

	@Override
	public ShuntCapList getShuntCapacitors()
	{
		return _shuntcaps;
	}

	@Override
	public TwoTermDCLineList getTwoTermDCLines()
	{
		return _t2dclines;
	}

	@Override
	public SwitchedShuntList getSwitchedShunts()
	{
		return _swshunts;
	}

	@Override
	public SVCList getSVCs()
	{
		return _svcs;
	}
	
	
	Set<ColChange> _changedCols = new HashSet<>();
	
	/** call for an event */
	public Set<ColChange> getChanges()
	{
		return _changedCols;
	}
	
	public void clearChanges()
	{
		_changedCols.forEach(c -> c.clear());
		_changedCols.clear();
	}
	
	void setChange(ColChange c)
	{
		_changedCols.add(c);
	}
	
	public static void main(String... args) throws PAModelException
	{
		String uri = null;
//		File outdir = new File(System.getProperty("user.dir"));
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
//				case "outdir":
//					outdir = new File(args[i++]);
//					break;
			}
		}
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri "
					+ "[ --outdir output_directory (deft to $CWD ]\n");
			System.exit(1);
		}
		
		PflowModelBuilder bldr = PflowModelBuilder.Create(uri); 
		
		PAModel m = bldr.load();
		m.getBuses().stream().filter(b -> ((b.getIndex() % 2) == 0))
				.forEach(b -> b.setVM(-1f));		

		for(ColChange o : m.getChanges())
		{
				System.out.format("%s %s\n", o.getListMeta(), o.getColMeta());
				int[] ndx = o.getNdxs(), keys = o.getKeys();
				String[] v = o.stringValues();
				for(int i=0; i < o.size(); ++i)
					System.out.format("\t%d %d %s\n", ndx[i], keys[i], v[i]);
		}
		
		m = bldr.load();
		m.getBuses().stream().filter(b -> ((b.getIndex() % 2) == 0))
				.forEach(b -> b.setVM(-1f));		

		for(ColChange o : m.getChanges())
		{
				System.out.format("%s %s\n", o.getListMeta(), o.getColMeta());
				int[] ndx = o.getNdxs(), keys = o.getKeys();
				String[] v = o.stringValues();
				for(int i=0; i < o.size(); ++i)
					System.out.format("\t%d %d %s\n", ndx[i], keys[i], v[i]);
		}
		
	}

	@Override
	public BusList getSingleBus()
	{
		return new SingleBusList(this);
	}

	@Override
	public OneTermDevList getOneTermDevices() throws PAModelException
	{
		return new OneTermDevList(this);
	}

	@Override
	public TwoTermDevList getTwoTermDevices() throws PAModelException
	{
		return new TwoTermDevList(this);
	}

	@Override
	public ACBranchList getACBranches() throws PAModelException
	{
		return new ACBranchList(this);
	}

	@Override
	public GroupList createGroups(BusGrpMap map)
	{
		return new GroupList(this, map);
	}
}
