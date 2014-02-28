package com.powerdata.openpa.psse;

public abstract class SwitchList extends PsseBaseList<Switch>
{
	public static final SwitchList Empty = new SwitchList()
	{
		@Override
		public Bus getFromBus(int ndx) {return null;}
		@Override
		public Bus getToBus(int ndx) {return null;}
		@Override
		public String getObjectID(int ndx) {return null;}
		@Override
		public int size() {return 0;}
		@Override
		public long getKey(int ndx) {return -1;}
	};
	protected SwitchList(){super();}
	public SwitchList(PsseModel model) {super(model);}

	/** Get a Switch by it's index. */
	@Override
	public Switch get(int ndx) { return new Switch(ndx,this); }
	/** Get an SwitchIn by it's ID. */
	@Override
	public Switch get(String id) { return super.get(id); }

	public abstract Bus getFromBus(int ndx) throws PsseModelException;
	public abstract Bus getToBus(int ndx) throws PsseModelException;
	@Deprecated // use getObjectName, this is redundant
	public String getName(int ndx) throws PsseModelException {return "";}
	public SwitchState getState(int ndx) throws PsseModelException {return SwitchState.Closed;}
	public void setState(int ndx, SwitchState state) throws PsseModelException {}
	public boolean canOperateUnderLoad(int ndx) throws PsseModelException {return true; }
	public String getI(int ndx) throws PsseModelException {return getFromBus(ndx).getObjectID();}
	public String getJ(int ndx) throws PsseModelException {return getToBus(ndx).getObjectID();}
	public boolean isInSvc(int ndx) throws PsseModelException {return true;}
	public void setInSvc(int ndx, boolean state) throws PsseModelException {}
}
