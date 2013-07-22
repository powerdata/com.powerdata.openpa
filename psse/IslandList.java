package com.powerdata.openpa.psse;


public abstract class IslandList extends PsseBaseList<Island>
{
	public static final IslandList Empty = new IslandList()
	{
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	
	protected IslandList() {super();}
	public IslandList(PsseModel model) {super(model);}

	/** Get a Transformer by it's index. */
	@Override
	public Island get(int ndx) { return new Island(ndx,this); }
	/** Get a Transformer by it's ID. */
	@Override
	public Island get(String id) { return super.get(id); }

	public BusList getBuses(int ndx) {return BusList.Empty;}
	public GenList getGenerators(int ndx) {return GenList.Empty;}
	public LoadList getLoads(int ndx) {return LoadList.Empty;}
	public LineList getLines(int ndx) {return LineList.Empty;}
	public TransformerList getTransformers(int ndx)
	{
		return TransformerList.Empty;
	}
	public PhaseShifterList getPhaseShifters(int ndx)
	{
		return PhaseShifterList.Empty;
	}
	public ShuntList getShunts(int ndx) throws PsseModelException {return ShuntList.Empty;}
	public SvcList getSvcs(int ndx) throws PsseModelException {return SvcList.Empty;}
	public SwitchList getSwitches(int ndx) {return SwitchList.Empty;}
}
