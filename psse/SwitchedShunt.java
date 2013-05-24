package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PComplex;

public class SwitchedShunt extends BaseObject
{
	protected SwitchedShuntList<?> _list;
	
	public SwitchedShunt(int ndx, SwitchedShuntList<?> list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getDebugName() throws PsseModelException {return "";}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}
	
//	/* Convenience methods */
//	/** Load bus (I) */ 
//	public Bus getBus() throws PsseModelException {return _list.getBus(_ndx);}
//	
//	/* Raw PSS/e methods */
//
//	/** bus number or name */
//	public String getI() throws PsseModelException {return _list.getI(_ndx);}
//	public int getMODSW() throws PsseModelException {return _list.getMODSW(_ndx);}
	
	
}
