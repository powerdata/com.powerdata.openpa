package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseObject;

public abstract class PsseModel
{
	public static PsseModel Open(String uri) throws PsseModelException
	{
		String[] tok = uri.split(":", 1);
		switch(tok[0].toLowerCase())
		{
			case "pssecsv": 
				String dpath = tok[1].split("=")[1];
				return new com.powerdata.openpa.psse.csv.PsseModelIn(dpath);
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
	
//	/** find a Bus by ID (moved to InputList)*/ 
//	public BusIn getBus(String id) throws PsseModelException {return getBuses().get(id);}
	
	public abstract ImpCorrTblList getImpCorrTables() throws PsseModelException;
	public abstract AreaList getAreas() throws PsseModelException;
	public abstract OwnerList getOwners() throws PsseModelException;
	public abstract ZoneList getZones() throws PsseModelException;
	
	
	public void log(LogSev severity, BaseObject obj, String msg) throws PsseModelException
	{
		_log.log(severity, obj, msg);
	}
	
}	


