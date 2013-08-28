package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PComplex;

public class Bus extends PsseBaseObject
{
	protected BusList _list;
	
	public Bus(int ndx, BusList list)
	{
		super(list,ndx);
		_list = list;
	}

	@Override
	public String getDebugName() throws PsseModelException {return getNAME();}
	
	/* Groups */
	public SwitchList getSwitches() throws PsseModelException { return _list.getSwitches(_ndx); }
	public GenList getGenerators() throws PsseModelException { return _list.getGenerators(_ndx); }
	public LoadList getLoads() throws PsseModelException { return _list.getLoads(_ndx); }
	public LineList getLines() throws PsseModelException { return _list.getLines(_ndx); }
	public TransformerList getTransformers() throws PsseModelException { return _list.getTransformers(_ndx); }
	public ShuntList getShunts() throws PsseModelException { return _list.getShunts(_ndx); }
	public SvcList getSvcs() throws PsseModelException { return _list.getSvcs(_ndx); }
	public PhaseShifterList getPhaseShifters() throws PsseModelException {return _list.getPhaseShifters(_ndx);}

	/* Convenience methods */
	/** request that this node be islolated */
	public SwitchList isolate() throws PsseModelException { return _list.isolate(_ndx); }
	/** enumerated IDE code */
	public BusTypeCode getBusType() throws PsseModelException {return _list.getBusType(_ndx);}
	/** Area */
	public Area getAreaObject() throws PsseModelException {return _list.getAreaObject(_ndx);}
	/** Zone */
	public Zone getZoneObject() throws PsseModelException {return _list.getZoneObject(_ndx);}
	/** Owner */
	public Owner getOwnerObject() throws PsseModelException {return _list.getOwnerObject(_ndx);}
	/** get complex shunt admittance to ground */
	public Complex getShuntY() throws PsseModelException {return _list.getShuntY(_ndx);}
	/** complex bus voltage */
	public PComplex getVoltage() throws PsseModelException {return _list.getVoltage(_ndx);}
	/** test if the bus is energized */
	public boolean isEnergized() throws PsseModelException { return _list.isEnergized(_ndx); }
	/** get frequency source priority */
	public int getFrequencySourcePriority() throws PsseModelException {return _list.getFrequencySourcePriority(_ndx);}
	/** get the island number */
	public int getIsland() throws PsseModelException { return _list.getIsland(_ndx); }

	/* Raw PSS/e methods */
	
	/** Bus number */
	public int getI() throws PsseModelException {return _list.getI(_ndx);}
	/** Alphanumeric identifier */
	public String getNAME() throws PsseModelException {return _list.getNAME(_ndx);}
	/** Bus base voltage */
	public float getBASKV() throws PsseModelException {return _list.getBASKV(_ndx);}
	/** Bus type code */
	public int getIDE() throws PsseModelException {return _list.getIDE(_ndx);}
	/** Active component of shunt admittance to ground in MW at unity voltage*/
	public float getGL() throws PsseModelException {return _list.getGL(_ndx);}
	/** Reactive component of shunt admittance to ground in MVAr at unity voltage*/
	public float getBL() throws PsseModelException {return _list.getBL(_ndx);}
	/** Area number */
	public int getAREA() throws PsseModelException {return _list.getAREA(_ndx);}
	/** Zone number */
	public int getZONE() throws PsseModelException {return _list.getZONE(_ndx);}
	/** Bus voltage magnitude p.u.*/
	public float getVM() throws PsseModelException {return _list.getVM(_ndx);}
	/** Bus voltage phase angle in degrees */
	public float getVA() throws PsseModelException {return _list.getVA(_ndx);}
	/** Owner number */
	public int getOWNER() throws PsseModelException {return _list.getOWNER(_ndx);}

	public void setRTMismatch(Complex mismatch) throws PsseModelException {_list.setRTMismatch(_ndx, mismatch);}
	public Complex getRTMismatch() throws PsseModelException {return _list.getRTMismatch(_ndx);}
	public float getRTFrequency() throws PsseModelException {return _list.getRTFrequency(_ndx);}
	/** get real-time voltage magnitude p.u. on bus base KV */
	public float getRTVmag() throws PsseModelException {return _list.getRTVMag(_ndx);}
	/** get real-time voltage angle in radians */
	public float getRTVang() throws PsseModelException {return _list.getRTVAng(_ndx);}
}
