package com.powerdata.openpa.psseraw;

/**
 * Provide an abstract method to get a set of PsseClass objects appropriate to
 * the given version
 * 
 * @author chris@powerdata.com
 * 
 */
public abstract class PsseClassSet
{
	public static final int MaxConfigVerMajor = 30;
	
	public static PsseClassSet GetClassSetForVersion(String version) throws PsseProcException
	{
		int ix = version.indexOf('.');
		String svmaj = null;
		svmaj = (ix == -1) ? version : version.substring(0, ix);
		
		int vmaj = Integer.parseInt(svmaj);
		PsseClassSet rv = null;
		
		if (vmaj <= 29)
		{
			rv = new PsseClassSetVersion29();
		}
		else if (vmaj <= MaxConfigVerMajor)
		{
			rv = new PsseClassSetVersion30();
		}
		else
		{
			throw new PsseProcException("Not configured for Version "+version);
		}
		
		return rv;
	}
	
	public abstract PsseClass[] getPsseClasses();
	public abstract int getVersionMajor();
	
	public abstract PsseClass getBus();
	public abstract PsseClass getLoad();
	public abstract PsseClass getGenerator();
	public abstract PsseClass getNontransformerBranch();
	public abstract PsseClass getTransformer();
	public abstract PsseClass getAreaInterchange();
	public abstract PsseClass getTwoTermDCLine();
	public abstract PsseClass getVSC_DCLine();
	public abstract PsseClass getSwitchedShunt();
	public abstract PsseClass getTxImpedanceCorrection();
	/** AC Converter Records - inner record for Multi Terminal DC Lines */
	public abstract PsseClass getMultiTermDC_ACConv();
	/** DC Bus Records - inner record for Multi Terminal DC Lines */
	public abstract PsseClass getMultiTermDCBus();
	/** DC Link Records - inner record for Multi Terminal DC Lines */
	public abstract PsseClass getMultiTermDCLink();
	public abstract PsseClass getMultiTermDCLine();
	public abstract PsseClass getMultiSectionLine();
	public abstract PsseClass getZone();
	public abstract PsseClass getInterAreaTransfer();
	public abstract PsseClass getOwner();
	public abstract PsseClass getFACTSDevice();
}
