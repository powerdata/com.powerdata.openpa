package com.powerdata.openpa.psse;

public abstract class SwitchList extends PsseBaseInputList<Switch>
{
	public static final SwitchList Empty = new SwitchList()
	{
		@Override
		public BusIn getBus1(int ndx) throws PsseModelException {return null;}
		@Override
		public BusIn getBus2(int ndx) throws PsseModelException {return null;}
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	protected SwitchList(){super();}
	public SwitchList(PsseModel model) {super(model);}

	/** Get a Switch by it's index. */
	@Override
	public Switch get(int ndx) { return new Switch(ndx,this); }
	/** Get an SwitchIn by it's ID. */
	@Override
	public Switch get(String id) { return super.get(id); }

	public abstract BusIn getBus1(int ndx) throws PsseModelException;
	public abstract BusIn getBus2(int ndx) throws PsseModelException;
	public String getName(int ndx) throws PsseModelException {return "";}
	public SwitchState getState(int ndx) throws PsseModelException {return SwitchState.Closed;}
	public void setState(int ndx, SwitchState state) throws PsseModelException {}
}
