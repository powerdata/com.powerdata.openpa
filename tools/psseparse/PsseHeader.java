package com.powerdata.openpa.tools.psseparse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses out information 
 * @author chris
 *
 */
public class PsseHeader
{
	private static Pattern _CaseInfoPattern =
			Pattern.compile("\\s*,*\\s+");

	private static Pattern[] _VersionPattern = 
	{
		Pattern.compile("PSS/E-"),
		Pattern.compile("PSS\\(R\\)E-"),
		Pattern.compile("PSS\\(R\\)E ")
	};
	
	private static String[]	_Months = new String[] {"JAN", "FEB", "MAR",
			"APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
	
	private static SimpleDateFormat _DateFormat =
			new SimpleDateFormat("MMM dd yyyy  HH:mm");
	
	private Integer _changeCode;
	private Float _systemBaseMVA;
	private GregorianCalendar _caseTime;
	private GregorianCalendar _importTime;
	private String _heading1;
	private String _heading2;
	private String _version;
	
	public PsseHeader(String l1, String l2, String l3)
	{
		_heading1 = l2;
		_heading2 = l3;
		
		// get the change code
		Matcher cim = _CaseInfoPattern.matcher(l1);
		int end=-1;
		if (cim.find())
		{
			_changeCode = Integer.valueOf(l1.substring(0, cim.start()).trim());
			end = cim.end();
		}
		if (cim.find())
		{
			_systemBaseMVA = Float.valueOf(l1.substring(end, cim.start()).trim());
			end = cim.end();
		}
		
		// see if we can determine the version
		String rstring = l1.substring(end);
		for(Pattern vp : _VersionPattern)
		{
			Matcher v = vp.matcher(rstring);
			if (v.find())
			{
				cim = _CaseInfoPattern.matcher(rstring);
				cim.find(v.end());
				_version = rstring.substring(v.end(), cim.start());
				end = cim.end();
				break;
			}
		}

		// parse the date if it exists
		int ixm = -1;
		rstring = rstring.substring(end);
		for(String m : _Months)
		{
			if ((ixm = rstring.indexOf(m))!= -1)
			{
				parseDate(rstring.substring(ixm));
			}
		}
		
		// track the time we run
		_importTime = new GregorianCalendar();
	}

	private void parseDate(String string)
	{
		Date d = null;
		try
		{
			d = _DateFormat.parse(string);
		} catch (ParseException e) {}
		
		if (d != null)
		{
			_caseTime = new GregorianCalendar();
			_caseTime.setTime(d);
		}
		
	}
	
	public Integer getChangeCode() {return _changeCode;}
	public Float getSystemBaseMVA() {return _systemBaseMVA;}
	public GregorianCalendar getCaseTime() {return _caseTime;}
	public GregorianCalendar getImportTime() {return _importTime;}
	public String getHeading1() {return _heading1;}
	public String getHeading2() {return _heading2;}
	public String getVersion() {return _version;}
}
