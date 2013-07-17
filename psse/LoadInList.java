package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PAMath;

public abstract class LoadInList extends PsseBaseInputList<LoadIn>
{
	public static final LoadInList Empty = new LoadInList()
	{
		@Override
		public String getI(int ndx) throws PsseModelException {return null;}
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	
	protected LoadInList() {super();}
	public LoadInList(PsseModel model) {super(model);}

	/* Standard object retrieval */

	/** Get a Load by it's index. */
	@Override
	public LoadIn get(int ndx) { return new LoadIn(ndx,this); }
	/** Get a Load by it's ID. */
	@Override
	public LoadIn get(String id) { return super.get(id); }

	/* convenience methods */
	
	/** Load bus (I) */ 
	public BusIn getBus(int ndx) throws PsseModelException {return _model.getBus(getObjectID(ndx));}
	/** get load in-service status (STATUS) as a boolean.  Returns true if in service */
	public boolean getInSvc(int ndx) throws PsseModelException {return getSTATUS(ndx) == 1;}
	/** get Area Interchange record */
	public Area getAreaObj(int ndx) throws PsseModelException
	{
		return _model.getAreas().get(String.valueOf(getAREA(ndx)));
	}
	/** get Zone record */
	public ZoneIn getZoneObj(int ndx) throws PsseModelException
	{
		return _model.getZones().get(String.valueOf(getZONE(ndx)));
	}
	/** get complex power (PL) */
	public Complex getPwr(int ndx) throws PsseModelException
	{
		return new Complex(PAMath.mw2pu(getPL(ndx)), PAMath.mvar2pu(getQL(ndx)));
	}
	/** Complex constant current load at 1pu voltage */
	public Complex getPwrI(int ndx) throws PsseModelException
	{
		return new Complex(PAMath.mw2pu(getIP(ndx)), PAMath.mvar2pu(getIQ(ndx)));
	}
	/** Complex constant admittance load at 1pu voltage */
	public Complex getPwrY(int ndx) throws PsseModelException
	{
		return new Complex(PAMath.mw2pu(getYP(ndx)), PAMath.mvar2pu(getYQ(ndx)));
	}
	/** return Owner */
	public OwnerIn getOwnerObj(int ndx) throws PsseModelException
	{
		return _model.getOwners().get(String.valueOf(getOWNER(ndx)));
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
	
}
