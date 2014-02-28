package com.powerdata.openpa.psse;

public abstract class StationList extends PsseBaseList<Station>
{
	public static final StationList Empty = new StationList()
	{
		@Override
		public String getObjectID(int ndx) {return null;}
		@Override
		public long getKey(int ndx) {return -1;}
	};
	protected StationList() {super();}

	public StationList(PsseModel model) {super(model);}

	@Override
	public Station get(int index) {return new Station(this, index);}

	@Override
	public int size() {return 0;}

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

	public SwitchList getSwitches(int ndx) throws PsseModelException {return SwitchList.Empty;}
	public ShuntList getShunts(int ndx) throws PsseModelException {return ShuntList.Empty;}
	public SvcList getSvcs(int ndx) throws PsseModelException {return SvcList.Empty;}
	public SwitchedShuntList getSwitchedShunts(int ndx) throws PsseModelException
	{
		return SwitchedShuntList.Empty;
	}

	public TwoTermDCLineList getTwoTermDCLines(int ndx) throws PsseModelException
	{
		return TwoTermDCLineList.Empty;
	}

}
