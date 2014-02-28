package com.powerdata.openpa.psse;

import com.powerdata.openpa.psse.Gen.GenRegMode;
import com.powerdata.openpa.tools.PAMath;

public abstract class GenList extends PsseBaseList<Gen>
{
	public static final GenList Empty = new GenList()
	{
		@Override
		public String getI(int ndx) throws PsseModelException {return null;}
		@Override
		public OwnershipList getOwnership(int ndx) {return null;}
		@Override
		public String getObjectID(int ndx) {return null;}
		@Override
		public int size() {return 0;}
		@Override
		public long getKey(int ndx) {return -1;}
		@Override
		public Gen getByKey(long key) {return null;}
	};
	
	protected GenList() {super();}
	
	public GenList(PsseModel model) throws PsseModelException 
	{
		super(model);
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
	public Bus getBus(int ndx) throws PsseModelException {return _model.getBus(getI(ndx));}
	/** remote regulated bus.  (IREG) Null if local */
	public Bus getRemoteRegBus(int ndx) throws PsseModelException {return _model.getBus(getIREG(ndx));}
	/** get the Generator mode */
	public GenMode getMode(int ndx) throws PsseModelException
	{
		if (getSTAT(ndx) == 0)
		{
			return GenMode.OFF;
		}
		else
		{
			float pg = getP(ndx);
			float qg = getQ(ndx);
			if (pg == 0f && qg == 0f) return GenMode.OFF;
			if (pg >= -1f && pg <= 1f && qg != 0f) return GenMode.CON;
			if (pg < -1f) return GenMode.PMP;
			return GenMode.ON;
		}
	}

	public GenType getType(int ndx) throws PsseModelException {return GenType.Unknown;}

	/** reactive power limits */
	public Limits getReactiveLimits(int ndx) throws PsseModelException
	{
		return new Limits(PAMath.mvar2pu(getQB(ndx)),
				PAMath.mvar2pu(getQT(ndx)));
	}

	/** active power limits */
	public Limits getActiveLimits(int ndx) throws PsseModelException
	{
		return new Limits(PAMath.mw2pu(getPB(ndx)), PAMath.mw2pu(getPT(ndx)));
	}
	public boolean isInSvc(int ndx) throws PsseModelException {return getSTAT(ndx) == 1;}
	@Override
	public String getObjectName(int ndx) throws PsseModelException
	{
		return getBus(ndx).getObjectName()+":"+getID(ndx);
	}
	public void setMode(int ndx, GenMode mode) throws PsseModelException {}
	public float getPpu(int ndx) throws PsseModelException {return PAMath.mw2pu(getP(ndx));}
	public void setPpu(int ndx, float p) throws PsseModelException {}
	public float getQpu(int ndx) throws PsseModelException {return PAMath.mvar2pu(getQ(ndx));}
	public void setQpu(int ndx, float q) throws PsseModelException {}

	/* raw methods */

	/** bus number or name */
	public abstract String getI(int ndx) throws PsseModelException;
	/** Machine identifier */
	public String getID(int ndx) throws PsseModelException {return "1";}
	/** Generator active power output in MW */
	public float getP(int ndx) throws PsseModelException {return 0f;}
	/** Generator active power output in MW */
	public void setP(int ndx, float p) throws PsseModelException {}
	/** Generator reactive power output in MVAr */
	public float getQ(int ndx) throws PsseModelException {return 0f;}
	/** Generator reactive power output in MVAr */
	public void setQ(int ndx, float q) throws PsseModelException {}
	/** Maximum generator reactive power output (MVAr) */
	public float getQT(int ndx) throws PsseModelException {return 9999f;}
	/** Minimum generator reactive power output (MVAr) */
	public float getQB(int ndx) throws PsseModelException  {return -9999f;}
	/** Regulated voltage setpoint entered in p.u. */
	public float getVS(int ndx) throws PsseModelException {return 1f;}
	public void setVS(int ndx, float vmpu) throws PsseModelException {}
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

	/** get MW setpoint */
	public float getPS(int ndx) throws PsseModelException {return getP(ndx);}
	/** set MW setpoint */
	public void setPS(int ndx, float mw) throws PsseModelException {}

	@Deprecated /* use getRegMode instead */
	public boolean isInAvr(int ndx) throws PsseModelException
	{
		return !((getQ(ndx) == getQB(ndx)) && (getQ(ndx) == getQT(ndx))); 
	}

	public GenRegMode getRegMode(int ndx) throws PsseModelException
	{
		return ((getQ(ndx) == getQB(ndx)) && (getQ(ndx) == getQT(ndx))) ? 
				GenRegMode.ReactivePower : GenRegMode.Voltage;
	}

	public void setRegMode(int ndx, GenRegMode mode) throws PsseModelException {}
	public float getQS(int ndx) throws PsseModelException {return 0;}
	public void setQS(int ndx, float mvar) throws PsseModelException {}

	public void setPT(int ndx, float mw) throws PsseModelException {} 
	public void setPB(int ndx, float mw) throws PsseModelException {}

	public void setQT(int ndx, float mvar) throws PsseModelException {}
	public void setQB(int ndx, float mvar) throws PsseModelException {}

	public void setInSvc(int ndx, boolean state) throws PsseModelException {}
}
