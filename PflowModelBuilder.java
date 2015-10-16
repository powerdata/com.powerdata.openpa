package com.powerdata.openpa;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import com.powerdata.openpa.impl.ModelBuilderI;


/**
 * Build Models with load options suitable by some power apps (i.e. a power
 * flow)
 * 
 * @author chris@powerdata.com
 * 
 */
public abstract class PflowModelBuilder extends ModelBuilderI
{
	/** static translations of scheme to input class */
	static HashMap<String,String> _SchemeToInputClass = new HashMap<String,String>();
	/** seed the class translations with some defaults */
	static
	{
		SetSchemeInputClass("pd3cim", "com.powerdata.pa.api.PflowPD3ModelBldr");
		SetSchemeInputClass("pd2cim", "com.powerdata.pa.api.PflowPD2ModelBldr");
		SetSchemeInputClass("psmfmt", "com.powerdata.openpa.PFlowPsmModelBldr");
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

	
	protected boolean	_flat	= false;
	float	_leastx	= 0;
	boolean _correctR = false;
	boolean _correctMagY = false;
	boolean	_doos	= false;
	boolean	_genregterm;
	/** disable branches with bad Z values */
	private boolean _svcdroop = false;

	/** ignore case voltage and set flat. Sets VM to 1 and VA to 0 if true */
	public boolean useFlatVoltage()
	{
		return _flat;
	}

	/** ignore case voltage and set flat. Sets VM to 1 and VA to 0 if true */
	public void enableFlatVoltage(boolean useflat)
	{
		_flat = useflat;
	}

	public boolean correctMagY() {return _correctMagY;}
	
	public void enableMagYCorrection(boolean e) {_correctMagY = e;}
	
	public boolean correctRVal(){return _correctR;}
	
	public void enableRCorrection(boolean e) {_correctR = e;}
	
	/**
	 * limit the branch reactance minimum magnitude, abs(X) > minx, defaults to
	 * 0 (no limit)
	 */
	public float getLeastX()
	{
		return _leastx;
	}

	/**
	 * limit the branch reactance minimum magnitude, abs(X) > minx, defaults to
	 * 0 (no limit)
	 */
	public void setLeastX(float leastx)
	{
		_leastx = leastx;
	}

	/** drop out-of-service branches */
	public boolean dropOOS()
	{
		return _doos;
	}

	/** enable / disable dropping out-of-service (de-energized) branches */
	public void enableDropOOS(boolean drop)
	{
		_doos = drop;
	}

	/**
	 * ignore case unit setpoints, and substitute case voltage at unit terminal
	 * bus
	 */
	public boolean getUnitRegOverride()
	{
		return _genregterm;
	}

	/**
	 * ignore case unit setpoints, and substitute case voltage at unit terminal
	 * bus
	 */
	public void setUnitRegOverride(boolean override)
	{
		_genregterm = override;
	}
	
	public boolean getSVCDroop()
	{
		return _svcdroop ;
	}
	
	public void setSVCDroop(boolean usedroop)
	{
		_svcdroop = usedroop;
	}
	
	/**
	 * Create a new PflowModelBuilder
	 * @param uri URI of source data
	 * @return ModelBuilder used to configure and build PAModel
	 * @throws PAModelException 
	 */
	public static PflowModelBuilder Create(String uri) throws PAModelException
	{
		String[] tok = uri.split(":", 2);
		String clsnm = _SchemeToInputClass.get(tok[0]);
		if (clsnm == null) throw new PAModelException("Scheme not defined for Input: "+tok[0]);
		
		try
		{
			Class<?> cls = Class.forName(clsnm);
			Constructor<?> con = cls.getConstructor(new Class[] {String.class});
			PflowModelBuilder rv = (PflowModelBuilder) con.newInstance(new Object[]{tok[1]});
			return rv;
		}
		catch (Exception e)
		{
			throw new PAModelException("Scheme "+tok[0]+" "+e, e);
		}
	}

}
