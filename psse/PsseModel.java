package com.powerdata.openpa.psse;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import com.powerdata.openpa.tools.BaseObject;

public abstract class PsseModel
{
	/** static translations of scheme to input class */
	static HashMap<String,String> _SchemeToInputClass = new HashMap<String,String>();
	/** static translations of scheme to output class */
	static HashMap<String,String> _SchemeToOutputClass = new HashMap<String,String>();
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
	 * Set a scheme to output class name translation.
	 * @param scheme
	 * @param pkg
	 */
	public static void SetSchemeOutputClass(String scheme, String pkg)
	{
		_SchemeToOutputClass.put(scheme, pkg);
	}
	/**
	 * Create a new input class using a uri.  The scheme needs to have been
	 * mapped in the scheme to input class translations.
	 * @param uri
	 * @return
	 * @throws PsseModelException
	 */
	public static PsseInputModel OpenInput(String uri) throws PsseModelException
	{
		String[] tok = uri.split(":", 2);
		String clsnm = _SchemeToInputClass.get(tok[0]);
		if (clsnm == null) throw new PsseModelException("Scheme not defined for Input: "+tok[0]);
		
		try
		{
			Class<?> cls = Class.forName(clsnm);
			Constructor<?> con = cls.getConstructor(new Class[] {String.class});
			return (PsseInputModel) con.newInstance(new Object[]{tok[1]});
		}
		catch (Exception e)
		{
			throw new PsseModelException("Scheme "+tok[0]+" "+e);
		}
	}
	/**
	 * Create a new output class using a uri.  The scheme needs to have been
	 * mapped in the scheme to output class translations.
	 * @param uri
	 * @return
	 * @throws PsseModelException
	 */
	public static PsseOutputModel OpenOutput(String uri) throws PsseModelException
	{
		String[] tok = uri.split(":", 2);
		String clsnm = _SchemeToOutputClass.get(tok[0]);
		if (clsnm == null) throw new PsseModelException("Scheme not defined for Output: "+tok[0]);
		
		try
		{
			Class<?> cls = Class.forName(clsnm);
			Constructor<?> con = cls.getConstructor(new Class[] {String.class});
			return (PsseOutputModel) con.newInstance(new Object[]{tok[1]});
		}
		catch (Exception e)
		{
			throw new PsseModelException("Scheme "+tok[0]+" "+e);
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
	
}	


