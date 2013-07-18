package com.powerdata.openpa.psse;

public abstract class OwnerList extends PsseBaseList<Owner>
{
	public static final OwnerList Empty = new OwnerList()
	{
		@Override
		public int getI(int ndx) throws PsseModelException {return 0;}
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	protected OwnerList() {super();}
	public OwnerList(PsseModel model) {super(model);}

	/** Get an Owner by it's index. */
	@Override
	public Owner get(int ndx) { return new Owner(ndx,this); }
	/** Get an Owner by it's ID. */
	@Override
	public Owner get(String id) { return super.get(id); }

	/* raw data methods */
	public abstract int getI(int ndx) throws PsseModelException;
	public String getOWNAME(int ndx) {return "";}
	
	/* lists */
	public SwitchList getSwitches(int ndx) throws PsseModelException {return SwitchList.Empty;}
	public GenList getGenerators(int ndx) throws PsseModelException {return GenList.Empty;}
	public LoadList getLoads(int ndx) throws PsseModelException {return LoadList.Empty;}
	public LineList getLines(int ndx) throws PsseModelException {return LineList.Empty;}
	public TransformerList getTransformers(int ndx) throws PsseModelException {return TransformerList.Empty;}
	public SwitchedShuntList getSwitchedShunts(int ndx) throws PsseModelException {return SwitchedShuntList.Empty;}
	public PhaseShifterList getPhaseShifters(int ndx) throws PsseModelException {return PhaseShifterList.Empty;}
	
}
