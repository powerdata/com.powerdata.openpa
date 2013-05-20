package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class NontransformerBranch extends BaseObject
{

	protected NontransformerBranchList<?> _list;
	
	public NontransformerBranch(int ndx, NontransformerBranchList<?> list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}
	
	/* Convenience methods */

	/** From-side bus */
	public Bus getFromBus() {return _list.getFromBus(_ndx);}
	/** To-side bus */
	public Bus getToBus() {return _list.getToBus(_ndx);}
	/** Get "metered" end */
	public MeteredEnd getMeteredEnd() {return _list.getMeteredEnd(_ndx);}
	/** get initial branch status (ST) as a boolean.  Returns true if in service */
	public boolean inService() {return _list.inService(_ndx);}

	/* Raw PSS/e methods */
	
	/** From-side bus number or name */
	public String getI() {return _list.getI(_ndx);}
	/** To-side bus number or name */
	public String getJ() {return _list.getJ(_ndx);}
	/** circuit identifier */
	public String getCKT() {return _list.getCKT(_ndx);}
	/** Branch resistance entered in p.u. */
	public float getR() {return _list.getR(_ndx);}
	/** Branch reactance entered in p.u. */
	public float getX() {return _list.getX(_ndx);}
	/** Branch charging susceptance entered in p.u. */
	public float getB() {return _list.getB(_ndx);}
	/** First loading rating entered in MVA */
	public float getRATEA() {return _list.getRATEA(_ndx);}
	/** Second loading rating entered in MVA */
	public float getRATEB() {return _list.getRATEB(_ndx);}
	/** Third loading rating entered in MVA */
	public float getRATEC() {return _list.getRATEC(_ndx);}
	/** Complex conductance of line shunt at bus "I" */ 
	public float getGI() {return _list.getGI(_ndx);}
	/** Complex susceptance of line shunt at bus "I" */ 
	public float getBI() {return _list.getBI(_ndx);}
	/** Complex conductance of line shunt at bus "J" */ 
	public float getGJ() {return _list.getGJ(_ndx);}
	/** Complex susceptance of line shunt at bus "J" */ 
	public float getBJ() {return _list.getBJ(_ndx);}
	/** Initial branch status (1 is in-service, 0 means out of service) */
	public int getST() {return _list.getST(_ndx);}
	/** Line length  entered in user-selected units */
	public float getLEN() {return _list.getLEN(_ndx);}
	
	/** return Ownership as a list */
	public OwnershipList<?> getOwnership() {return _list.getOwnership(_ndx);}
	
	

}
