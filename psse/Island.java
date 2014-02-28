package com.powerdata.openpa.psse;

public class Island extends PsseBaseObject
{
	public static final Island	DeEnergizedIsland	= new Island(-1, IslandList.Empty)
	{
		@Override
		public BusList getBuses() throws PsseModelException {return BusList.Empty;}
		@Override
		public GenList getGenerators() throws PsseModelException {return GenList.Empty;}
		@Override
		public LoadList getLoads() throws PsseModelException {return LoadList.Empty;}
		@Override
		public LineList getLines() throws PsseModelException {return LineList.Empty;}
		@Override
		public TransformerList getTransformers() throws PsseModelException {return TransformerList.Empty;}
		@Override
		public PhaseShifterList getPhaseShifters() throws PsseModelException {return PhaseShifterList.Empty;}
		@Override
		public ShuntList getShunts() throws PsseModelException {return ShuntList.Empty;}
		@Override
		public SvcList getSvcs() throws PsseModelException {return SvcList.Empty;}
		@Override
		public SwitchList getSwitches() throws PsseModelException {return SwitchList.Empty;}
		@Override
		public BusList getBusesForType(BusTypeCode bustype) throws PsseModelException {return BusList.Empty;}
	};
	protected IslandList _list;
	
	public Island(int ndx, IslandList list)
	{
		super(list,ndx);
		_list = list;
	}

	public BusList getBuses() throws PsseModelException {return _list.getBuses(_ndx);}
	public GenList getGenerators() throws PsseModelException {return _list.getGenerators(_ndx);}
	public LoadList getLoads() throws PsseModelException {return _list.getLoads(_ndx);}
	public LineList getLines() throws PsseModelException {return _list.getLines(_ndx);}
	public TransformerList getTransformers() throws PsseModelException {return _list.getTransformers(_ndx);}
	public PhaseShifterList getPhaseShifters() throws PsseModelException {return _list.getPhaseShifters(_ndx);}
	public ShuntList getShunts() throws PsseModelException { return _list.getShunts(_ndx); }
	public SvcList getSvcs() throws PsseModelException { return _list.getSvcs(_ndx); }
	public SwitchList getSwitches() throws PsseModelException {return _list.getSwitches(_ndx);}
	
	public BusList getBusesForType(BusTypeCode bustype) throws PsseModelException
	{
		return _list.getBusesForType(_ndx, bustype);
	}
	
	public boolean isEnergized() throws PsseModelException {return _list.isEnergized(_ndx);}
}
