package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.PAMath;

public abstract class LoadList extends PsseBaseList<Load>
{
	public static final LoadList Empty = new LoadList()
	{
		@Override
		public String getI(int ndx) throws PsseModelException {return null;}
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	
	protected LoadList() {super();}
	public LoadList(PsseModel model) {super(model);}

	/* Standard object retrieval */

	/** Get a Load by it's index. */
	@Override
	public Load get(int ndx) { return new Load(ndx,this); }
	/** Get a Load by it's ID. */
	@Override
	public Load get(String id) { return super.get(id); }

	/* convenience methods */
	
	/** Load bus (I) */ 
	public Bus getBus(int ndx) throws PsseModelException {return _model.getBus(getI(ndx));}
	/** get load in-service status (STATUS) as a boolean.  Returns true if in service */
	public boolean isInSvc(int ndx) throws PsseModelException {return getSTATUS(ndx) == 1;}
	/** get Area Interchange record */
	public Area getAreaObj(int ndx) throws PsseModelException
	{
		return _model.getAreas().get(String.valueOf(getAREA(ndx)));
	}
	/** get Zone record */
	public Zone getZoneObj(int ndx) throws PsseModelException
	{
		return _model.getZones().get(String.valueOf(getZONE(ndx)));
	}
	/** return Owner */
	public Owner getOwnerObj(int ndx) throws PsseModelException
	{
		return _model.getOwners().get(String.valueOf(getOWNER(ndx)));
	}
	@Override
	public String getObjectName(int ndx) throws PsseModelException
	{
		return getBus(ndx).getNAME()+":"+getID(ndx);
	}

	/* raw methods */

	/** bus number or name */
	public abstract String getI(int ndx) throws PsseModelException;
	/** load identifies used to differentiate multiple loads at the same bus */
	public String getID(int ndx) throws PsseModelException {return "1";}
	/** in-service status of load (1 for in-service, 0 for out-of-service) */
	public int getSTATUS(int ndx) throws PsseModelException {return 1;}
	/** index of related area record.  Defaults to same area as bus I */
	public int getAREA(int ndx) throws PsseModelException {return getBus(ndx).getAREA();}
	/** index of related zone record.  Defaults to same zone as bus I */
	public int getZONE(int ndx) throws PsseModelException {return getBus(ndx).getZONE();}
	/** active power of constant MVA load in MW */
	public float getPL(int ndx) throws PsseModelException {return 0f;}
	/** reactive power of constant MVA load in MVAr */
	public float getQL(int ndx) throws PsseModelException {return 0f;}
	/** active power of constant current load MW at 1pu voltage */
	public float getIP(int ndx) throws PsseModelException {return 0f;}
	/** reactive power of constant current load MVAr at 1pu voltage */
	public float getIQ(int ndx) throws PsseModelException {return 0f;}
	/** active power of constant admittance load MW at 1pu voltage*/
	public float getYP(int ndx) throws PsseModelException {return 0f;}
	/** reactive power of constant admittance load MW at 1pu voltage*/
	public float getYQ(int ndx) throws PsseModelException {return 0f;}
	/** index of related OWNER record.  Defaults to same owner as bus I */
	public int getOWNER(int ndx) throws PsseModelException {return getBus(ndx).getOWNER();}
	
	/* Real-Time Methods */
	/** get the load MW */
	public float getRTMW(int ndx) throws PsseModelException { return getPL(ndx); }
	/** get the load MVar */
	public float getRTMVar(int ndx) throws PsseModelException { return getQL(ndx); }
	/** get the cold load MW */
	public float getRTColdMW(int ndx) throws PsseModelException { return 0f; }
	/** get the cold load MVar */
	public float getRTColdMVar(int ndx) throws PsseModelException { return 0f; }
	public void setRTMW(int ndx, float mw) throws PsseModelException { /* do nothing */ }
	public void setRTMVAr(int ndx, float mvar) throws PsseModelException { /* do nothing */ }
	public float getRTP(int ndx) throws PsseModelException {return PAMath.mw2pu(getPL(ndx));}
	public void setRTP(int ndx, float p) throws PsseModelException {}
	public float getRTQ(int ndx) throws PsseModelException {return PAMath.mw2pu(getQL(ndx));}
	public void setRTQ(int ndx, float q) throws PsseModelException {}
}
