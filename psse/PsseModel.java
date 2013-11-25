package com.powerdata.openpa.psse;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import com.powerdata.openpa.psse.util.BusSubList;
import com.powerdata.openpa.psse.util.LogSev;
import com.powerdata.openpa.psse.util.PsseModelLog;
import com.powerdata.openpa.tools.AbstractBaseObject;
/**
 * 
 * @author marck@powerdata.com
 *
 */
public class PsseModel
{
	/** static translations of scheme to input class */
	static HashMap<String,String> _SchemeToInputClass = new HashMap<String,String>();
	/** seed the class translations with some defaults */
	static
	{
		SetSchemeInputClass("pssecsv", "com.powerdata.openpa.psse.csv.PsseRawModel");
		SetSchemeInputClass("psseraw", "com.powerdata.openpa.psse.csv.PsseRawModel");
		SetSchemeInputClass("pd2cim", "com.powerdata.pa.psse.pd2cim.PsseModel");
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
	public static PsseModel Open(String uri) throws PsseModelException
	{
		System.out.println("uri: "+uri);
		String[] tok = uri.split(":", 2);
		String clsnm = _SchemeToInputClass.get(tok[0]);
		if (clsnm == null) throw new PsseModelException("Scheme not defined for Input: "+tok[0]);
		
		try
		{
			Class<?> cls = Class.forName(clsnm);
			Constructor<?> con = cls.getConstructor(new Class[] {String.class});
			PsseModel rv = (PsseModel) con.newInstance(new Object[]{tok[1]});
			rv.setURI(uri);
			return rv;
		}
		catch (Exception e)
		{
			throw new PsseModelException("Scheme "+tok[0]+" "+e, e);
		}
	}
	
	protected PsseModelLog _log = new PsseModelLog()
	{
		@Override
		public void log(LogSev severity, AbstractBaseObject obj, String msg) throws PsseModelException
		{
			String objclass = obj.getClass().getSimpleName();
			String objnm = obj.getDebugName();
			String objid = obj.getObjectID();
			((severity == LogSev.Error) ? System.err : System.out)
				.format("%s %s[%s] %s\n", objclass, objnm, objid, msg);
		}
	};
	
	String _uri;
	
	public PsseModel() {} 
	public PsseModel(PsseModelLog log) {_log = log;} 
	
	public void log(LogSev severity, AbstractBaseObject obj, String msg) throws PsseModelException
	{
		_log.log(severity, obj, msg);
	}
	public long refresh() throws PsseModelException { return 0; }
	
	/** get system base MVA */
	public float getSBASE() {return 100f;}
	/** get psse version */
	public int getPsseVersion() {return 30;}

	/** find a Bus by ID */ 
	public Bus getBus(String id) throws PsseModelException {return getBuses().get(id);}
	
	/* Model-specific lists */
	public ImpCorrTblList getImpCorrTables() throws PsseModelException {return ImpCorrTblList.Empty;}
	public AreaList getAreas() throws PsseModelException {return AreaList.Empty;}
	public OwnerList getOwners() throws PsseModelException {return OwnerList.Empty;}
	public ZoneList getZones() throws PsseModelException {return ZoneList.Empty;}
	public IslandList getIslands() throws PsseModelException {return IslandList.Empty;}

	/* equipment group lists */
	public BusList getBuses() throws PsseModelException {return BusList.Empty;}
	public GenList getGenerators() throws PsseModelException {return GenList.Empty;}
	public LoadList getLoads() throws PsseModelException {return LoadList.Empty;}
	public LineList getLines() throws PsseModelException {return LineList.Empty;}
	public TransformerList getTransformers() throws PsseModelException
	{
		return TransformerList.Empty;
	}
	public PhaseShifterList getPhaseShifters() throws PsseModelException
	{
		return PhaseShifterList.Empty;
	}
	public SwitchList getSwitches() throws PsseModelException {return SwitchList.Empty;}
	public ShuntList getShunts() throws PsseModelException {return ShuntList.Empty;}
	public SvcList getSvcs() throws PsseModelException {return SvcList.Empty;}
	public SwitchedShuntList getSwitchedShunts() throws PsseModelException {return SwitchedShuntList.Empty;}
	
	/** for convience, get a list of all ac branches */
	public ACBranchList getBranches() throws PsseModelException
	{
		return new ACBranchList(getLines(), getTransformers(), getPhaseShifters());
	}
	public OneTermDevList getOneTermDevs() throws PsseModelException
	{
		return new OneTermDevList(getLoads(), getGenerators(), getShunts(), getSvcs());
	}
	public String getURI() {return _uri;}
	public void setURI(String uri) {_uri = uri;}
	
	public int[] getBusNdxForType(BusTypeCode bustype) throws PsseModelException {return new int[0];}

	public BusList getBusesForType(BusTypeCode bustype)
			throws PsseModelException
	{
		return new BusSubList(getBuses(), getBusNdxForType(bustype));
	}

}	

