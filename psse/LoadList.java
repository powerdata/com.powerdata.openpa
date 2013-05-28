package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PAMath;

public abstract class LoadList<T extends Load> extends PsseBaseList<T>
{
	public LoadList(PsseModel model) {super(model);}

	/* Standard object retrieval */

	/** Get a Load by it's index. */
	@Override
	@SuppressWarnings("unchecked")
	public T get(int ndx) { return (T) new Load(ndx,this); }
	/** Get a Load by it's ID. */
	@Override
	public T get(String id) { return super.get(id); }

	/* convenience methods */
	
	public abstract Bus getBus(int ndx) throws PsseModelException;
	public abstract boolean getInSvc(int ndx) throws PsseModelException;
	public abstract AreaInterchange getAreaObj(int ndx) throws PsseModelException;
	public abstract Zone getZoneObj(int ndx) throws PsseModelException;
	public abstract float getActvPwr(int ndx) throws PsseModelException;
	public abstract float getReacPwr(int ndx) throws PsseModelException;
	public abstract Complex getPwr(int ndx) throws PsseModelException;
	public abstract float getActvPwrI(int ndx) throws PsseModelException;
	public abstract float getReacPwrI(int ndx) throws PsseModelException;
	public abstract Complex getPwrI(int ndx) throws PsseModelException;
	public abstract float getActvPwrY(int ndx) throws PsseModelException;
	public abstract float getReacPwrY(int ndx) throws PsseModelException;
	public abstract Complex getPwrY(int ndx) throws PsseModelException;
	public abstract Owner getOwnerObj(int ndx) throws PsseModelException;

	/* convenience defaults */
	
	public Bus getDeftBus(int ndx) throws PsseModelException {return _model.getBus(getObjectID(ndx));}
	public boolean getDeftInSvc(int ndx) throws PsseModelException {return getSTATUS(ndx) == 1;}
	public AreaInterchange getDeftAreaObj(int ndx) throws PsseModelException {return _model.getAreas().get(String.valueOf(getAREA(ndx)));}
	public Zone getDeftZoneObj(int ndx) throws PsseModelException  {return _model.getZones().get(String.valueOf(getZONE(ndx)));}
	public float getDeftActvPwr(int ndx) throws PsseModelException {return PAMath.mw2pu(getPL(ndx));}
	public float getDeftReacPwr(int ndx) throws PsseModelException {return PAMath.mw2pu(getQL(ndx));}
	public Complex getDeftPwr(int ndx) throws PsseModelException   {return new Complex(getActvPwr(ndx), getReacPwr(ndx));}
	public float getDeftActvPwrI(int ndx) throws PsseModelException {return PAMath.mw2pu(getIP(ndx));}
	public float getDeftReacPwrI(int ndx) throws PsseModelException {return PAMath.mw2pu(getIQ(ndx));}
	public Complex getDeftPwrI(int ndx) throws PsseModelException {return new Complex(getActvPwrI(ndx), getReacPwrI(ndx));}
	public float getDeftActvPwrY(int ndx) throws PsseModelException {return PAMath.mw2pu(getYP(ndx));}
	public float getDeftReacPwrY(int ndx) throws PsseModelException {return PAMath.mw2pu(getYQ(ndx));}
	public Complex getDeftPwrY(int ndx) throws PsseModelException {return new Complex(getActvPwrY(ndx), getReacPwrY(ndx));}
	public Owner getDeftOwnerObj(int ndx) throws PsseModelException {return _model.getOwners().get(String.valueOf(getOWNER(ndx)));}

	/* raw methods */

	public abstract String getI(int ndx) throws PsseModelException;
	public abstract String getID(int ndx) throws PsseModelException;
	public abstract int getSTATUS(int ndx) throws PsseModelException;
	public abstract int getAREA(int ndx) throws PsseModelException;
	public abstract int getZONE(int ndx) throws PsseModelException;
	public abstract float getPL(int ndx) throws PsseModelException;
	public abstract float getQL(int ndx) throws PsseModelException;
	public abstract float getIP(int ndx) throws PsseModelException;
	public abstract float getIQ(int ndx) throws PsseModelException;
	public abstract float getYP(int ndx) throws PsseModelException;
	public abstract float getYQ(int ndx) throws PsseModelException;
	public abstract int getOWNER(int ndx) throws PsseModelException;


	/* raw defaults */
	
	public String getDeftID(int ndx) throws PsseModelException {return "1";}
	public int getDeftSTATUS(int ndx) throws PsseModelException {return 1;}
	public int getDeftAREA(int ndx) throws PsseModelException {return getBus(ndx).getAREA();}
	public int getDeftZONE(int ndx) throws PsseModelException {return getBus(ndx).getZONE();}
	public float getDeftPL(int ndx) throws PsseModelException {return 0F;}
	public float getDeftQL(int ndx) throws PsseModelException {return 0F;}
	public float getDeftIP(int ndx) throws PsseModelException {return 0F;}
	public float getDeftIQ(int ndx) throws PsseModelException {return 0F;}
	public float getDeftYP(int ndx) throws PsseModelException {return 0F;}
	public float getDeftYQ(int ndx) throws PsseModelException {return 0F;}
	public int getDeftOWNER(int ndx) throws PsseModelException {return getBus(ndx).getOWNER();}
	
}
