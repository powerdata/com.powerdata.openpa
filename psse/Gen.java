package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;
import com.powerdata.openpa.tools.Complex;

public class Gen extends BaseObject implements OneTermDev
{

	protected GenList _list;
	
	public Gen(int ndx, GenList list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getDebugName() throws PsseModelException
	{
		return String.format("%s %s", getBus().getNAME(), getID());
	}

	@Override
	public String getObjectID() throws PsseModelException {return _list.getObjectID(_ndx);}
	
	/* Convenience methods */

	/** Generator bus (I) */ 
	@Override
	public Bus getBus() throws PsseModelException {return _list.getBus(_ndx);}
	/** remote regulated bus.  (IREG) Null if local */
	public Bus getRemoteRegBus() throws PsseModelException {return _list.getRemoteRegBus(_ndx);}
	/** get the Generator mode */
	public GenMode getMode() throws PsseModelException {return _list.getMode(_ndx); }
	/** set the generator mode */
	public void setMode(GenMode mode) throws PsseModelException { _list.setMode(_ndx, mode); }
	/** get case complex power */
	public Complex getPwr() throws PsseModelException {return _list.getPwr(_ndx);}
	/** Maximum generator reactive power output (QT) p.u. */
	public float getMaxReacPwr() throws PsseModelException {return _list.getMaxReacPwr(_ndx);}
	/** Minimum generator reactive power output (QB) p.u. */
	public float getMinReacPwr() throws PsseModelException {return _list.getMinReacPwr(_ndx);}
	/** machine complex impedance */
	public Complex getMachZ() throws PsseModelException {return _list.getMachZ(_ndx);}
	/** Step-up transformer impedance */
	public Complex getTxZ() throws PsseModelException {return _list.getTxZ(_ndx);}
	/** max active power (PT) p.u. */
	public float getMaxActvPwr() throws PsseModelException {return _list.getMaxActvPwr(_ndx);}
	/** min active power (PB) p.u. */
	public float getMinActvPwr() throws PsseModelException {return _list.getMinActvPwr(_ndx);}

	/* Raw PSS/e methods */
	
	/** bus number or name */
	public String getI() throws PsseModelException {return _list.getI(_ndx);}
	/** Machine identifier */
	public String getID() throws PsseModelException {return _list.getID(_ndx);}
	/** Generator active power output in MW */
	public float getPG() throws PsseModelException {return _list.getPG(_ndx);}
	/** Generator reactive power output in MVAr */
	public float getQG() throws PsseModelException {return _list.getQG(_ndx);}
	/** Maximum generator reactive power output (MVAr) */
	public float getQT() throws PsseModelException {return _list.getQT(_ndx);}
	/** Minimum generator reactive power output (MVAr) */
	public float getQB() throws PsseModelException {return _list.getQB(_ndx);}
	/** Regulated voltage setpoint entered in p.u. */
	public float getVS() throws PsseModelException {return _list.getVS(_ndx);}
	/** remote regulated bus number or name.  Set to 0 if regulating local bus */
	public String getIREG() throws PsseModelException {return _list.getIREG(_ndx);}
	/** total MVA base of units represented in this machine */
	public float getMBASE() throws PsseModelException {return _list.getMBASE(_ndx);}
	/** machine resistance p.u. on MBASE base */
	public float getZR() throws PsseModelException {return _list.getZR(_ndx);}
	/** machine reactance p.u. on MBASE base */
	public float getZX() throws PsseModelException {return _list.getZX(_ndx);}
	/** Step-up transformer resistance entered in p.u. on MBASE base */
	public float getRT() throws PsseModelException {return _list.getRT(_ndx);}
	/** Step-up transformer reactance entered in p.u. on MBASE base */
	public float getXT() throws PsseModelException {return _list.getXT(_ndx);}
	/** Step-up transformer off-nominal turns ratio entered in p.u. */
	public float getGTAP() throws PsseModelException {return _list.getGTAP(_ndx);}
	/** Initial machine status (1 is in-service, 0 means out of service) */
	public int getSTAT() throws PsseModelException {return _list.getSTAT(_ndx);}
	/** Percent of the total Mvar required to hold the voltage at the bus controlled by this
	    bus "I" that are to be contributed by the generation at bus "I" */
	public float getRMPCT() throws PsseModelException {return _list.getRMPCT(_ndx);}
	/** max active power in MW */
	public float getPT() throws PsseModelException {return _list.getPT(_ndx);}
	/** min active power in MW */
	public float getPB() throws PsseModelException {return _list.getPB(_ndx);}
	
	
	/** return Ownership as a list */
	public OwnershipList getOwnership() throws PsseModelException {return _list.getOwnership(_ndx);}
	
	

}
