package com.powerdata.openpa.psse;

import com.powerdata.openpa.psse.util.BusSubList;


public abstract class IslandList extends PsseBaseList<Island>
{
	public static final IslandList Empty = new IslandList()
	{
		@Override
		public String getObjectID(int ndx) {return null;}
		@Override
		public int size() {return 0;}
		@Override
		public long getKey(int ndx) {return -1;}
		@Override
		public Island getByKey(long key) {return null;}
	};
	
	protected IslandList() {super();}
	public IslandList(PsseModel model) {super(model);}

	/** Get a Transformer by it's index. */
	@Override
	public Island get(int ndx)
	{
		return (ndx == -1) ? Island.DeEnergizedIsland : new Island(ndx, this);
	}

	/** Get an Island by it's ID. */
	@Override
	public Island get(String id) { return super.get(id); }
	
	public int findNdxForBus(int busndx) throws PsseModelException {return 0;}
	public int findNdxForBus(Bus bus) throws PsseModelException {return findNdxForBus(bus.getIndex());}
	public Island findForBus(int busndx) throws PsseModelException {return get(findNdxForBus(busndx));}
	public Island findForBus(Bus bus) throws PsseModelException {return findForBus(bus.getIndex());}

	public BusList getBuses(int ndx) throws PsseModelException {return BusList.Empty;}
	public GenList getGenerators(int ndx) throws PsseModelException {return GenList.Empty;}
	public LoadList getLoads(int ndx) throws PsseModelException {return LoadList.Empty;}
	public LineList getLines(int ndx) throws PsseModelException {return LineList.Empty;}
	public TransformerList getTransformers(int ndx) throws PsseModelException
	{
		return TransformerList.Empty;
	}
	public PhaseShifterList getPhaseShifters(int ndx) throws PsseModelException
	{
		return PhaseShifterList.Empty;
	}
	public ShuntList getShunts(int ndx) throws PsseModelException {return ShuntList.Empty;}
	public SvcList getSvcs(int ndx) throws PsseModelException {return SvcList.Empty;}
	public SwitchList getSwitches(int ndx) throws PsseModelException {return SwitchList.Empty;}

	public BusList getBusesForType(int ndx, BusTypeCode bustype) throws PsseModelException {return BusList.Empty;}
	public boolean isEnergized(int ndx) throws PsseModelException {return false;}
	public BusTypeCode getBusType(int ndx) throws PsseModelException {return BusTypeCode.Unknown;}
	public BusTypeCode getBusType(Bus b) throws PsseModelException {return getBusType(b.getRootIndex());}
	
	/** return buses for a type regardless of island */
	public BusList getBusesForType(BusTypeCode bustype) throws PsseModelException
	{
		int n = size();
		BusList[] list = new BusList[n];
		for (int i=0; i < n; ++i)
		{
			list[i] = getBusesForType(i, bustype);
		}
		
		int rvcnt = 0, j=0;
		for(BusList l : list) rvcnt += l.size();
		int[] ndx = new int[rvcnt];
		for(BusList l : list)
		{
			for (Bus b : l) ndx[j++] = b.getRootIndex();
		}
		return new BusSubList(_model.getBuses(), ndx);
	}
}
