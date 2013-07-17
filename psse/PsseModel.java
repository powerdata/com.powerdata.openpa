package com.powerdata.openpa.psse;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import com.powerdata.openpa.tools.BaseObject;

public class PsseModel implements EquipGroup
{
	/** static translations of scheme to input class */
	static HashMap<String,String> _SchemeToInputClass = new HashMap<String,String>();
	/** seed the class translations with some defaults */
	static
	{
		SetSchemeInputClass("pssecsv", "com.powerdata.openpa.psse.csv.PsseInputModel");
		SetSchemeInputClass("pd2cim", "com.powerdata.pa.psse.pd2cim.PsseInputModel");
	}
	/**
	 * Set a scheme to input class name translation.
	 * @param scheme
	 * @param pkg
	 */
	public static void SetSchemeInputClass(String scheme, String pkg)
	{
		_SchemeToInputClass.put(scheme, pkg);
	}
	/**
	 * Create a new input class using a uri.  The scheme needs to have been
	 * mapped in the scheme to input class translations.
	 * @param uri
	 * @return
	 * @throws PsseModelException
	 */
	public static PsseModel OpenInput(String uri) throws PsseModelException
	{
		System.out.println("uri: "+uri);
		String[] tok = uri.split(":", 2);
		String clsnm = _SchemeToInputClass.get(tok[0]);
		if (clsnm == null) throw new PsseModelException("Scheme not defined for Input: "+tok[0]);
		
		try
		{
			Class<?> cls = Class.forName(clsnm);
			Constructor<?> con = cls.getConstructor(new Class[] {String.class});
			return (PsseModel) con.newInstance(new Object[]{tok[1]});
		}
		catch (Exception e)
		{
			throw new PsseModelException("Scheme "+tok[0]+" "+e, e);
		}
	}
	
	protected PsseModelLog _log = new PsseModelLog()
	{
		@Override
		public void log(LogSev severity, BaseObject obj, String msg) throws PsseModelException
		{
			String objclass = obj.getClass().getSimpleName();
			String objnm = obj.getDebugName();
			String objid = obj.getObjectID();
			((severity == LogSev.Error) ? System.err : System.out)
				.format("%s %s %s[%s] %s\n", objclass, objnm, objid, msg);
		}
	};
	
	public PsseModel() {} 
	public PsseModel(PsseModelLog log) {_log = log;} 
	
	public void log(LogSev severity, BaseObject obj, String msg) throws PsseModelException
	{
		_log.log(severity, obj, msg);
	}
	public long refresh() throws PsseModelException { return 0; }
	
	/** get system base MVA */
	public float getSBASE() {return 100f;}
	/** get psse version */
	public int getPsseVersion() {return 30;}

	/** find a Bus by ID */ 
	public BusIn getBus(String id) throws PsseModelException {return getBuses().get(id);}
	
	/* Model-specific lists */
	public ImpCorrTblInList getImpCorrTables() throws PsseModelException {return ImpCorrTblInList.Empty;}
	public AreaList getAreas() throws PsseModelException {return AreaList.Empty;}
	public OwnerInList getOwners() throws PsseModelException {return OwnerInList.Empty;}
	public ZoneInList getZones() throws PsseModelException {return ZoneInList.Empty;}
	public IslandInList getIslands() throws PsseModelException {return IslandInList.Empty;}

	/* equipment group lists */
	public BusInList getBuses() throws PsseModelException {return BusInList.Empty;}
	@Override
	public GenInList getGenerators() throws PsseModelException {return GenInList.Empty;}
	@Override
	public LoadInList getLoads() throws PsseModelException {return LoadInList.Empty;}
	@Override
	public LineInList getLines() throws PsseModelException {return LineInList.Empty;}
	@Override
	public TransformerInList getTransformers() throws PsseModelException
	{
		return TransformerInList.Empty;
	}
	@Override
	public PhaseShifterInList getPhaseShifters() throws PsseModelException
	{
		return PhaseShifterInList.Empty;
	}
	@Override
	public SwitchedShuntInList getSwitchedShunts() throws PsseModelException
	{
		return SwitchedShuntInList.Empty;
	}
	@Override
	public SwitchList getSwitches() throws PsseModelException {return null;}
}	


