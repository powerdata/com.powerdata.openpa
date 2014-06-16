package com.powerdata.openpa.tools;

import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;

/* PsseModel, no longer supported */
@Deprecated
public enum EqType
{
	UNKNOWN(0),
	SVC(1),
	AREA(2),
	BUS(3),
	GEN(4),
	LINE(5),
	LOAD(6),
	PHASESHIFTER(7),
	SHUNT(8),
	SWITCH(9),
	TRANSFORMER(10);

	private byte _id;
	private EqType(int id) { _id = (byte)id; }
	public byte getCode() { return _id; }
	public static EqType valueOf(int id)
	{
		switch(id)
		{
			case 0 : return UNKNOWN;
			case 1 : return SVC;
			case 2 : return AREA;
			case 3 : return BUS;
			case 4 : return GEN;
			case 5 : return LINE;
			case 6 : return LOAD;
			case 7 : return PHASESHIFTER;
			case 8 : return SHUNT;
			case 9 : return SWITCH;
			case 10: return TRANSFORMER;
		}
		return null;
	}
	public static BaseObject getObject(PsseModel mdl, EqType type, int ndx) throws PsseModelException
	{
		BaseObject eq = null;
		switch(type)
		{
			case UNKNOWN:		eq = null;
			case SVC:			eq = mdl.getSvcs().get(ndx); break;
			case AREA:			eq = mdl.getAreas().get(ndx); break;
			case BUS:			eq = mdl.getBuses().get(ndx); break;
			case GEN:			eq = mdl.getGenerators().get(ndx); break;
			case LINE:			eq = mdl.getLines().get(ndx); break;
			case LOAD:			eq = mdl.getLoads().get(ndx); break;
			case PHASESHIFTER:	eq = mdl.getPhaseShifters().get(ndx); break;
			case SHUNT:			eq = mdl.getShunts().get(ndx); break;
			case SWITCH:		eq = mdl.getSwitches().get(ndx); break;
			case TRANSFORMER:	eq = mdl.getTransformers().get(ndx); break;
		}
		return eq;
	}
	public static BaseObject getObject(PsseModel mdl, long id) throws PsseModelException
	{
		EqType t = valueOf((int)(id >> 32));
		int ndx  = (int)(id & 0xFFFFFFFF);
		return getObject(mdl,t,ndx);
	}
	public static EqType GetType(BaseObject obj)
	{
		if (obj instanceof com.powerdata.openpa.psse.Bus) return BUS;
		if (obj instanceof com.powerdata.openpa.psse.Switch) return SWITCH;
		if (obj instanceof com.powerdata.openpa.psse.Line) return LINE;
		if (obj instanceof com.powerdata.openpa.psse.Load) return LOAD;
		if (obj instanceof com.powerdata.openpa.psse.Gen) return GEN;
		if (obj instanceof com.powerdata.openpa.psse.Transformer) return TRANSFORMER;
		if (obj instanceof com.powerdata.openpa.psse.Shunt) return SHUNT;
		if (obj instanceof com.powerdata.openpa.psse.PhaseShifter) return PHASESHIFTER;
		if (obj instanceof com.powerdata.openpa.psse.SVC) return SVC;
		if (obj instanceof com.powerdata.openpa.psse.Area) return AREA;
		return UNKNOWN;
	}
	public static long GetID(BaseObject dev)
	{
		long type = GetType(dev).getCode();
		long ndx  = dev.getIndex();
		long id = (type << 32) | ndx;
		return id;		
	}
}
