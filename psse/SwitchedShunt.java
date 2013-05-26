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
	
	/* Convenience methods */
	/** Load bus (I) */ 
	public Bus getBus() throws PsseModelException {return _list.getBus(_ndx);}
	
	/* Raw PSS/e methods */

	/** bus number or name */
//	public String getI() throws PsseModelException {return _list.getI(_ndx);}
//	/** Control mode */
//	public int getMODSW() throws PsseModelException {return _list.getMODSW(_ndx);}
//	/** controlled upper limit either voltage or reactive power p.u. */
//	public int getVSWHI() throws PsseModelException {return _list.getVSWHI(_ndx);}
//	/** controlled lower limit either voltage or reactive power p.u. */
//	public int getVSWLO() throws PsseModelException {return _list.getVSWLO(_ndx);}
//	/** controlled bus */
//	public String getSWREM() throws PsseModelException {return _list.getSWREM(_ndx);}
//	/** percent of total MVAr required to hold bus voltage contributed by this shunt */
//	public float getRMPCT()  throws PsseModelException {return _list.getRMPCT(_ndx);}
//	public String getRMIDNT()  throws PsseModelException {return _list.getRMIDNT(_ndx);}
	
}
