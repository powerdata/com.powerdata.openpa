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
	/** Step-up transformer impedance */
	public Complex getTxZ(int ndx) throws PsseModelException
	{
		return PAMath.rebaseZ100(new Complex(getRT(ndx), getXT(ndx)), getMBASE(ndx));
	}
	/** max active power (PT) p.u. */
	public float getMaxActvPwr(int ndx) throws PsseModelException {return PAMath.mw2pu(getPT(ndx));}
	/** min active power (PB) p.u. */
	public float getMinActvPwr(int ndx) throws PsseModelException {return PAMath.mw2pu(getPB(ndx));}

	public void setMode(int ndx, GenMode mode) throws PsseModelException {};

	/* raw methods */

	public abstract String getI(int ndx) throws PsseModelException;
	public String getID(int ndx) throws PsseModelException {return "1";}
	public float getPG(int ndx) throws PsseModelException {return 0F;}
	public float getQG(int ndx) throws PsseModelException {return 0F;}
	public float getQT(int ndx) throws PsseModelException {return 9999F;}
	public float getQB(int ndx) throws PsseModelException  {return -9999F;}
	public float getVS(int ndx) throws PsseModelException {return 1F;}
	public String getIREG(int ndx) throws PsseModelException {return getI(ndx);}
	public float getMBASE(int ndx) throws PsseModelException {return _model.getSBASE();}
	public float getZR(int ndx) throws PsseModelException {return 0F;}
	public float getZX(int ndx) throws PsseModelException {return 1F;}
	public float getRT(int ndx) throws PsseModelException {return 0F;}
	public float getXT(int ndx) throws PsseModelException {return 0F;}
	public float getGTAP(int ndx) throws PsseModelException {return 1F;}
	public int getSTAT(int ndx) throws PsseModelException {return 1;}
	public float getRMPCT(int ndx) throws PsseModelException {return 100F;}
	public float getPT(int ndx) throws PsseModelException {return 9999F;}
	public float getPB(int ndx) throws PsseModelException {return -9999F;}

	public OwnershipList getOwnership(int ndx) throws PsseModelException {return OwnershipList.Empty;}//TODO: implement
	
	

}
