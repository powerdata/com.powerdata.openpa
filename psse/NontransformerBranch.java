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
	public String getID() {return _list.getID(_ndx);}
	
	/* Convenience methods */
	public Bus getFromNode() {return _list.getFromNode(_ndx);}
	public Bus getToNode() {return _list.getToNode(_ndx);}
	public MeteredEnd getMeteredEnd() {return _list.getMeteredEnd(_ndx);}
	public boolean inService() {return _list.inService(_ndx);}

	/* Raw PSS/e methods */
	public String getI() {return _list.getI(_ndx);}
	public String getJ() {return _list.getJ(_ndx);}
	public String getCKT() {return _list.getCKT(_ndx);}
	public float getR() {return _list.getR(_ndx);}
	public float getX() {return _list.getX(_ndx);}
	public float getB() {return _list.getB(_ndx);}
	public float getRATEA() {return _list.getRATEA(_ndx);}
	public float getRATEB() {return _list.getRATEB(_ndx);}
	public float getRATEC() {return _list.getRATEC(_ndx);}
	public float getGI() {return _list.getGI(_ndx);}
	public float getBI() {return _list.getBI(_ndx);}
	public float getGJ() {return _list.getGJ(_ndx);}
	public float getBJ() {return _list.getBJ(_ndx);}
	public int getSTAT() {return _list.getSTAT(_ndx);}
	public float getLEN() {return _list.getLEN(_ndx);}
	
	/** return Ownership as a list */
	public OwnershipList<?> getOwnership() {return _list.getOwnership(_ndx);}
	
	

}
