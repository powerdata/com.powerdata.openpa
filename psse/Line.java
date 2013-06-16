package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;
import com.powerdata.openpa.tools.Complex;

public class Line extends BaseObject
{

	protected LineList _list;
	
	public Line(int ndx, LineList list)
	{
		super(ndx);
		_list = list;
	}

	
	@Override
	public String getDebugName() throws PsseModelException
	{
		return String.format("%s %s %s", getFromBus().getNAME(), 
				getToBus().getNAME(), getCKT());
	}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}
	
	/* Convenience methods */

	/** From-side bus */
	public BusIn getFromBus() throws PsseModelException {return _list.getFromBus(_ndx);}
	/** To-side bus */
	public BusIn getToBus() throws PsseModelException {return _list.getToBus(_ndx);}
	/** Get "metered" end */
	public LineMeterEnd getMeteredEnd() throws PsseModelException {return _list.getMeteredEnd(_ndx);}
	/** get initial branch status (ST) as a boolean.  Returns true if in service */
	public boolean getInSvc() throws PsseModelException {return _list.getInSvc(_ndx);}
	/** from-side charging susceptance, p.u. on 100MVA base at unity voltage */
	public float getFromBch() throws PsseModelException {return _list.getFromBch(_ndx);}
	/** to-side charging susceptance, p.u. on 100MVA base at unity voltage */
	public float getToBch() throws PsseModelException {return _list.getToBch(_ndx);}
	/** resistance in p.u. on 100MVA base */
	public float getR100() throws PsseModelException {return _list.getR100(_ndx);}
	/** reactance in p.u. on 100MVA base */
	public float getX100() throws PsseModelException {return _list.getX100(_ndx);}
	/** get complex impedance */
	public Complex getZ100() throws PsseModelException {return _list.getZ100(_ndx);}
	/** conductance of line shunt at from-side bus on 100 MVA base */ 
	public float getFromShuntG() throws PsseModelException {return _list.getFromShuntG(_ndx);}
	/** susceptance of line shunt at from-side bus */ 
	public float getFromShuntB() throws PsseModelException {return _list.getFromShuntB(_ndx);}
	/** complex admittance of line shunt at from-side bus on 100 MVA base */
	public Complex getFromShuntY() throws PsseModelException {return _list.getFromShuntY(_ndx);}
	/** conductance of line shunt at to-side bus */ 
	public float getToShuntG() throws PsseModelException {return _list.getToShuntG(_ndx);}
	/** susceptance of line shunt at to-side bus */ 
	public float getToShuntB() throws PsseModelException {return _list.getToShuntB(_ndx);}
	/** complex admittance of line shunt at to-side bus on 100 MVA base */
	public Complex getToShuntY() throws PsseModelException {return _list.getToShuntY(_ndx);}

	/* Raw PSS/e methods */
	
	/** From-side bus number or name */
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	/** To-side bus number or name */
	public String getJ() throws PsseModelException {return _list.getJ(_ndx);}
	/** circuit identifier */
	public String getCKT() throws PsseModelException {return _list.getCKT(_ndx);}
	/** Branch resistance entered in p.u. */
	public float getR() throws PsseModelException {return _list.getR(_ndx);}
	/** Branch reactance entered in p.u. */
	public float getX() throws PsseModelException {return _list.getX(_ndx);}
	/** Branch charging susceptance entered in p.u. */
	public float getB() throws PsseModelException {return _list.getB(_ndx);}
	/** First loading rating entered in MVA */
	public float getRATEA() throws PsseModelException {return _list.getRATEA(_ndx);}
	/** Second loading rating entered in MVA */
	public float getRATEB() throws PsseModelException {return _list.getRATEB(_ndx);}
	/** Third loading rating entered in MVA */
	public float getRATEC() throws PsseModelException {return _list.getRATEC(_ndx);}
	/** conductance of line shunt at bus "I" */ 
	public float getGI() throws PsseModelException {return _list.getGI(_ndx);}
	/** susceptance of line shunt at bus "I" */ 
	public float getBI() throws PsseModelException {return _list.getBI(_ndx);}
	/** conductance of line shunt at bus "J" */ 
	public float getGJ() throws PsseModelException {return _list.getGJ(_ndx);}
	/** susceptance of line shunt at bus "J" */ 
	public float getBJ() throws PsseModelException {return _list.getBJ(_ndx);}
	/** Initial branch status (1 is in-service, 0 means out of service) */
	public int getST() throws PsseModelException {return _list.getST(_ndx);}
	/** Line length  entered in user-selected units */
	public float getLEN() throws PsseModelException {return _list.getLEN(_ndx);}
	
	/** return Ownership as a list */
	public OwnershipList<?> getOwnership() throws PsseModelException {return _list.getOwnership(_ndx);}
	
}
