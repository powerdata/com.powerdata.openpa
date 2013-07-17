package com.powerdata.openpa.psse;


public abstract class IslandInList extends PsseBaseInputList<IslandIn>
{
	public static final IslandInList Empty = new IslandInList()
	{
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	
	protected IslandInList() {super();}
	public IslandInList(PsseModel model) {super(model);}

	/** Get a Transformer by it's index. */
	@Override
	public IslandIn get(int ndx) { return new IslandIn(ndx,this); }
	/** Get a Transformer by it's ID. */
	@Override
	public IslandIn get(String id) { return super.get(id); }

	public BusInList getBuses(int ndx) {return BusInList.Empty;}
	public GenInList getGenerators(int ndx) {return GenInList.Empty;}
	public LoadInList getLoads(int ndx) {return LoadInList.Empty;}
	public LineInList getLines(int ndx) {return LineInList.Empty;}
	public TransformerInList getTransformers(int ndx)
	{
		return TransformerInList.Empty;
	}
	public PhaseShifterInList getPhaseShifters(int ndx)
	{
		return PhaseShifterInList.Empty;
	}
	public SwitchedShuntInList getSwitchedShunts(int ndx)
	{
		return SwitchedShuntInList.Empty;
	}
	public SwitchList getSwitches(int ndx) {return SwitchList.Empty;}
}
