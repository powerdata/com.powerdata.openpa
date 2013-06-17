package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PAMath;
import com.powerdata.openpa.tools.PComplex;

public abstract class BusInList extends PsseBaseInputList<BusIn>
{
	public BusInList(PsseInputModel model) {super(model);}

	/* Standard object retrieval */

	/** Get a Bus by it's index. */
	@Override
	public BusIn get(int ndx) { return new BusIn(ndx,this); }
	/** Get a Bus by it's ID. */
	@Override
	public BusIn get(String id) { return super.get(id); }
	
	/* convenience methods */
	
	public abstract BusTypeCode getBusType(int ndx) throws PsseModelException;
	public abstract AreaIn getAreaObject(int ndx) throws PsseModelException;
	public abstract ZoneIn getZoneObject(int ndx) throws PsseModelException;
	public abstract OwnerIn getOwnerObject(int ndx) throws PsseModelException;
	public abstract float getShuntG(int ndx) throws PsseModelException;
	public abstract float getShuntB(int ndx) throws PsseModelException;
	public abstract float getVaRad(int ndx) throws PsseModelException;
	public abstract Complex getShuntY(int ndx) throws PsseModelException;
	public abstract PComplex getVoltage(int ndx) throws PsseModelException;


	/* convenience defaults */
	
	public BusTypeCode getDeftBusType(int ndx) throws PsseModelException {return BusTypeCode.fromCode(getIDE(ndx));}
	public AreaIn getDeftAreaObject(int ndx) throws PsseModelException
	{
		return _model.getAreas().get(getAREA(ndx));
	}
	public ZoneIn getDeftZoneObject(int ndx) throws PsseModelException {return _model.getZones().get(getZONE(ndx));}
	public OwnerIn getDeftOwnerObject(int ndx) throws PsseModelException {return _model.getOwners().get(getOWNER(ndx));}
	public float getDeftShuntG(int ndx) throws PsseModelException {return PAMath.mw2pu(getGL(ndx));}
	public float getDeftShuntB(int ndx) throws PsseModelException {return PAMath.mvar2pu(getBL(ndx));}
	public float getDeftVaRad(int ndx)  throws PsseModelException {return PAMath.deg2rad(getVA(ndx));}
	public Complex getDeftShuntY(int ndx) throws PsseModelException {return new Complex(getShuntG(ndx), getShuntB(ndx));}
	public PComplex getDeftVoltage(int ndx) throws PsseModelException {return new PComplex(getVM(ndx), getVaRad(ndx));}

	
	/* raw methods */

	public abstract int getI(int ndx) throws PsseModelException;
	public abstract String getNAME(int ndx) throws PsseModelException;
	public abstract float getBASKV(int ndx) throws PsseModelException;
	public abstract int getIDE(int ndx) throws PsseModelException;
	public abstract float getGL(int ndx) throws PsseModelException;
	public abstract float getBL(int ndx) throws PsseModelException;
	public abstract int getAREA(int ndx) throws PsseModelException;
	public abstract int getZONE(int ndx) throws PsseModelException;
	public abstract float getVM(int ndx) throws PsseModelException;
	public abstract float getVA(int ndx) throws PsseModelException;
	public abstract int getOWNER(int ndx) throws PsseModelException;

	/* defaults */

	public String getDeftNAME(int ndx) {return "";}
	public float getDeftBASKV(int ndx) {return 0F;}
	public int getDeftIDE(int ndx) {return 1;}
	public float getDeftGL(int ndx) {return 0F;}
	public float getDeftBL(int ndx) {return 0F;}
	public int getDeftAREA(int ndx) {return 1;}
	public int getDeftZONE(int ndx) {return 1;}
	public float getDeftVM(int ndx) {return 1F;}
	public float getDeftVA(int ndx) {return 0F;}
	public int getDeftOWNER(int ndx) {return 1;}

}
