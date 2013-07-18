package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PAMath;

public abstract class GenList extends PsseBaseList<Gen>
{
	public static final GenList Empty = new GenList()
	{
		@Override
		public String getI(int ndx) throws PsseModelException {return null;}
		@Override
		public OwnershipList getOwnership(int ndx) throws PsseModelException {return null;}
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	
	protected BusList _buses;
	
	protected GenList() {super();}
	
	public GenList(PsseModel model) throws PsseModelException 
	{
		super(model);
		_buses = model.getBuses();
	}
	
	/* Standard object retrieval */

	/** Get a Generator by it's index. */
	@Override
	public Gen get(int ndx) { return new Gen(ndx,this); }
	/** Get a Generator by it's ID. */
	@Override
	public Gen get(String id) { return super.get(id); }

	/* convenience methods */

	/** Generator bus (I) */ 
	public Bus getBus(int ndx) throws PsseModelException {return _buses.get(getI(ndx));}
	/** remote regulated bus.  (IREG) Null if local */
	public Bus getRemoteRegBus(int ndx) throws PsseModelException {return _buses.get(getIREG(ndx));}
	/** get the Generator mode */
	public GenMode getMode(int ndx) throws PsseModelException
	{
		if (getSTAT(ndx) == 0)
		{
			return GenMode.OFF;
		}
		else
		{
			float pg = getPG(ndx);
			float qg = getQG(ndx);
			if (pg == 0f && qg == 0f) return GenMode.OFF;
			if (pg >= -1f && pg <= 1f && qg != 0f) return GenMode.CON;
			if (pg < -1f) return GenMode.PMP;
			return GenMode.ON;
		}
	}
	/** get case complex power */
	public Complex getPwr(int ndx) throws PsseModelException
	{
		return new Complex(PAMath.mw2pu(getPG(ndx)),
				PAMath.mvar2pu(getQG(ndx)));
	}
	/** Maximum generator reactive power output (QT) p.u. */
	public float getMaxReacPwr(int ndx) throws PsseModelException {return PAMath.mvar2pu(getQT(ndx));}
	/** Minimum generator reactive power output (QB) p.u. */
	public float getMinReacPwr(int ndx) throws PsseModelException {return PAMath.mvar2pu(getQB(ndx));}
	/** machine impedance on 100 MVA base */
	public Complex getMachZ(int ndx) throws PsseModelException 
	{
		return PAMath.rebaseZ100(new Complex(getZR(ndx), getZX(ndx)), getMBASE(ndx));
	}
	/** max active power (PT) p.u. */
	public float getMaxActvPwr(int ndx) throws PsseModelException {return PAMath.mw2pu(getPT(ndx));}
	/** min active power (PB) p.u. */
	public float getMinActvPwr(int ndx) throws PsseModelException {return PAMath.mw2pu(getPB(ndx));}

	public void setMode(int ndx, GenMode mode) throws PsseModelException {};

	/* raw methods */

	/** bus number or name */
	public abstract String getI(int ndx) throws PsseModelException;
	/** Machine identifier */
	public String getID(int ndx) throws PsseModelException {return "1";}
	/** Generator active power output in MW */
	public float getPG(int ndx) throws PsseModelException {return 0f;}
	/** Generator reactive power output in MVAr */
	public float getQG(int ndx) throws PsseModelException {return 0f;}
	/** Maximum generator reactive power output (MVAr) */
	public float getQT(int ndx) throws PsseModelException {return 9999f;}
	/** Minimum generator reactive power output (MVAr) */
	public float getQB(int ndx) throws PsseModelException  {return -9999f;}
	/** Regulated voltage setpoint entered in p.u. */
	public float getVS(int ndx) throws PsseModelException {return 1f;}
	/** remote regulated bus number or name.  Set to 0 if regulating local bus */
	public String getIREG(int ndx) throws PsseModelException {return getI(ndx);}
	/** total MVA base of units represented in this machine */
	public float getMBASE(int ndx) throws PsseModelException {return _model.getSBASE();}
	/** machine resistance p.u. on MBASE base */
	public float getZR(int ndx) throws PsseModelException {return 0f;}
	/** machine reactance p.u. on MBASE base */
	public float getZX(int ndx) throws PsseModelException {return 1f;}
	/** Step-up transformer resistance entered in p.u. on MBASE base */
	public float getRT(int ndx) throws PsseModelException {return 0f;}
	/** Step-up transformer reactance entered in p.u. on MBASE base */
	public float getXT(int ndx) throws PsseModelException {return 0f;}
	/** Step-up transformer off-nominal turns ratio entered in p.u. */
	public float getGTAP(int ndx) throws PsseModelException {return 1f;}
	/** Initial machine status (1 is in-service, 0 means out of service) */
	public int getSTAT(int ndx) throws PsseModelException {return 1;}
	/** Percent of the total Mvar required to hold the voltage at the bus controlled by this
    bus "I" that are to be contributed by the generation at bus "I" */
	public float getRMPCT(int ndx) throws PsseModelException {return 100f;}
	/** max active power in MW */
	public float getPT(int ndx) throws PsseModelException {return 9999f;}
	/** min active power in MW */
	public float getPB(int ndx) throws PsseModelException {return -9999f;}

	public OwnershipList getOwnership(int ndx) throws PsseModelException {return OwnershipList.Empty;}//TODO: implement

	
	

}
