package com.powerdata.openpa.psse;

import java.io.IOException;

import com.powerdata.openpa.tools.BaseObject;

public class Generator extends BaseObject
{

	protected GeneratorList<?> _list;
	
	public Generator(int ndx, GeneratorList<?> list)
	{
		super(ndx);
		_list = list;
	}

	@Override
	public String getObjectID() {return _list.getObjectID(_ndx);}
	
	/* Convenience methods */

	/** Generator bus */ 
	public Bus getBus() throws IOException {return _list.getBus(_ndx);}
	/** Generator active power output p.u. on 100 MVA base */
	public float getActvPwr() {return _list.getActvPwr(_ndx);}
	/** Generator reactive power output p.u. on 100 MVA base */
	public float getReacPwr() {return _list.getReacPwr(_ndx);}
	/** Maximum generator reactive power output (p.u. on 100MVA base) */
	public float getMaxReacPwr() {return _list.getMaxReacPwr(_ndx);}
	/** Minimum generator reactive power output (p.u. on 100 MVA base) */
	public float getMinReacPwr() {return _list.getMinReacPwr(_ndx);}
	/** remote regulated bus.  Null if local */
	public Bus getRemoteRegBus() throws IOException {return _list.getRemoteRegBus(_ndx);}
	/** machine resistance p.u. on 100 MVA base */
	public float getMachR() {return _list.getMachR(_ndx);}
	/** machine reactance p.u. on 100 MVA base */
	public float getMachX() {return _list.getMachX(_ndx);}
	/** Step-up transformer resistance entered in p.u. on 100 MVA base */
	public float getStepupR() {return _list.getStepupR(_ndx);}
	/** Step-up transformer reactance entered in p.u. on 100 MVA base */
	public float getStepupX() {return _list.getStepupX(_ndx);}
	/** get initial branch status (STAT) as a boolean.  Returns true if in service */
	public boolean inService() {return _list.inService(_ndx);}
	/** max active power in MW */
	public float getMaxActvPwr() {return _list.getMaxActvPwr(_ndx);}
	/** min active power in MW */
	public float getMinActvPwr() {return _list.getMinActvPwr(_ndx);}
	

	
	/* Raw PSS/e methods */
	
	/** bus number or name */
	public String getI() {return _list.getI(_ndx);}
	/** Machine identifier */
	public String getID() {return _list.getID(_ndx);}
	/** Generator active power output in MW */
	public float getPG() {return _list.getPG(_ndx);}
	/** Generator reactive power output in MVAr */
	public float getQG() {return _list.getQG(_ndx);}
	/** Maximum generator reactive power output (MVAr) */
	public float getQT() {return _list.getQT(_ndx);}
	/** Minimum generator reactive power output (MVAr) */
	public float getQB() {return _list.getQB(_ndx);}
	/** Regulated voltage setpoint entered in p.u. */
	public float getVS() {return _list.getVS(_ndx);}
	/** remote regulated bus number or name.  Set to 0 if regulating local bus */
	public String getIREG() {return _list.getIREG(_ndx);}
	/** total MVA base of units represented in this machine */
	public float getMBASE() {return _list.getMBASE(_ndx);}
	/** machine resistance p.u. on MBASE base */
	public float getZR() {return _list.getZR(_ndx);}
	/** machine reactance p.u. on MBASE base */
	public float getZX() {return _list.getZX(_ndx);}
	/** Step-up transformer resistance entered in p.u. on MBASE base */
	public float getRT() {return _list.getRT(_ndx);}
	/** Step-up transformer reactance entered in p.u. on MBASE base */
	public float getXT() {return _list.getXT(_ndx);}
	/** Step-up transformer off-nominal turns ratio entered in p.u. */
	public float getGTAP() {return _list.getGTAP(_ndx);}
	/** Initial machine status (1 is in-service, 0 means out of service) */
	public int getSTAT() {return _list.getSTAT(_ndx);}
	/** Percent of the total Mvar required to hold the voltage at the bus controlled by this
	    bus "I" that are to be contributed by the generation at bus "I" */
	public float getRMPCT() {return _list.getRMPCT(_ndx);}
	/** max active power in MW */
	public float getPT() {return _list.getPT(_ndx);}
	/** min active power in MW */
	public float getPB() {return _list.getPB(_ndx);}
	
	
	/** return Ownership as a list */
	public OwnershipList<?> getOwnership() {return _list.getOwnership(_ndx);}
	
	

}
