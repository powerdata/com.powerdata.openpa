package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public class AreaInterchange extends BaseObject
{
	protected AreaInterchangeList<?> _list;
	
	public AreaInterchange(int ndx, AreaInterchangeList<?> list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}

	/* convenience methods */
	
	/** Area slack bus for area interchange control */
	public Bus getSlackBus() {return _list.getSlackBus(_ndx);}
	/** Desired net interchange leaving the area entered Per-unit on 100MVA base */
	public float getIntExport() {return _list.getIntExport(_ndx);}
	/** Interchange tolerance bandwidth p.u. on 100MVA base */
	public float getIntTol() {return _list.getIntTol(_ndx);}
	

	/* raw PSS/e methods */
	
	/** Area number */
	public int getI() {return _list.getI(_ndx);}
	/** Area slack bus for area interchange control */
	public String getISW() {return _list.getISW(_ndx);}
	/** Desired net interchange leaving the area entered in MW */
	public float getPDES() {return _list.getPDES(_ndx);}
	/** Interchange tolerance bandwidth entered in MW */
	public float getPTOL() {return _list.getPTOL(_ndx);}
	/** Alphanumeric identifier assigned to area */
	public String getARNAME() {return _list.getARNAME(_ndx);}
	
}
