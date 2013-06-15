package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public abstract class PsseModel implements BaseGroup
{
	public static PsseModel Open(String uri) throws PsseModelException
	{
		//TODO:  We must have some fully-featured URI parsing already available, this is just 
		// a quick prototype to see what the API changes look like
		String[] tok = uri.split(":", 1);
		switch(tok[0].toLowerCase())
		{
			case "pssecsv": 
				String dpath = tok[1].split("=")[1];
				return new com.powerdata.openpa.psse.csv.PsseModel(dpath);
			default:
				throw new PsseModelException("URI not supported: "+uri);
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
	
	/** get system base MVA */
	public abstract float getSBASE();
	/** default system base MVA */
	public float getDeftSBASE() {return 100F;}
	
	/** find a Bus by ID */ 
	public Bus getBus(String id) throws PsseModelException {return getBuses().get(id);}
	
	public abstract ImpCorrTblList getImpCorrTables() throws PsseModelException;
	public abstract AreaList getAreas() throws PsseModelException;
	public abstract OwnerList getOwners() throws PsseModelException;
	public abstract ZoneList getZones() throws PsseModelException;
	
	
	@Override
	public IslandList getIslands() throws PsseModelException {return new IslandList(this);}
	@Override
	public GroupList getGroup(String type) throws PsseModelException
	{
		//TODO:
		return null;
		
	}
	@Override
	public String[] getGroupTypes() {return GroupType.values();}
	
	public void log(LogSev severity, BaseObject obj, String msg) throws PsseModelException
	{
		_log.log(severity, obj, msg);
	}
	
}	


