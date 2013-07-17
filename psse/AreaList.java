package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.PAMath;

public abstract class AreaList extends PsseBaseInputList<Area>
{
	public static final AreaList Empty = new AreaList()
	{
		@Override
		public int getI(int ndx) throws PsseModelException {return 0;}
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	
	
	public AreaList() {super();}
	public AreaList(PsseModel model) {super(model);}

	/* Standard object retrieval */
	/** Get an AreaInterchange by it's index. */
	@Override
	public Area get(int ndx) { return new Area(ndx,this); }
	/** Get an AreaInterchange by it's ID. */
	@Override
	public Area get(String id) { return super.get(id); }
	
	/* Convenience methods */
	/** Area slack bus for area interchange control */ 
	public BusIn getSlackBus(int ndx) throws PsseModelException
	{
		return _model.getBus(getISW(ndx));
	}
	/** Desired net interchange (PDES) leaving the area entered p.u. */
	public float getIntExport(int ndx) throws PsseModelException {return PAMath.mw2pu(getPDES(ndx));}
	/** Interchange tolerance bandwidth (PTOL) in p.u. */
	public float getIntTol(int ndx) throws PsseModelException {return PAMath.mw2pu(getPTOL(ndx));}
	
	/* Raw values */
	/** Area number */
	public abstract int getI(int ndx) throws PsseModelException;
	/** Area slack bus for area interchange control */
	public String getISW(int ndx) throws PsseModelException {return "0";}
	/** Desired net interchange leaving the area entered in MW */
	public String getARNAME(int ndx) throws PsseModelException {return "";}
	/** Interchange tolerance bandwidth entered in MW */
	public float getPDES(int ndx) throws PsseModelException {return 0F;}
	/** Alphanumeric identifier assigned to area */
	public float getPTOL(int ndx) throws PsseModelException {return 10F;}

}
