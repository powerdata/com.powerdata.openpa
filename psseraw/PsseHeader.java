package com.powerdata.openpa.psseraw;

import java.io.IOException;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses out the 1st 3 lines in the PSS/e file 
 * @author chris@powerdata.com
 *
 */
public class PsseHeader
{
//	private static Pattern _CaseInfoPattern =
//			Pattern.compile("\\s*,*[\\s/]+");

	private static Pattern[] _VersionPattern = 
	{
		Pattern.compile("PSS/E-"),
		Pattern.compile("PSS\\(R\\)E-"),
		Pattern.compile("PSS\\(tm\\)E-"),
		Pattern.compile("PSS\\(R\\)E "),
		Pattern.compile("RAWD VER ")
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
	
	public PsseHeader(LineNumberReader rdr) throws IOException
	{
		String l1 = rdr.readLine();
		_heading1 = rdr.readLine();
		_heading2 = rdr.readLine();
		
		int start = 0;
		for (int i=0; i < l1.length(); ++i)
			if (l1.charAt(i) == ' ') ++start; else break;
		
		StringBuilder sb = new StringBuilder();
		int end = start;
		for(int i=start; i < l1.length(); ++i, ++end)
		{
			char c = l1.charAt(i);
			if (c != ',' && c != ' ')
				sb.append(c); else break;
		}
		_changeCode = Integer.valueOf(sb.toString());
		for(int i=++end; i < l1.length(); ++i, ++end)
			if (l1.charAt(i) != ' ') break;
		
		sb = new StringBuilder();
		for(int i = end; i < l1.length(); ++i, ++end)
		{
			char c = l1.charAt(i);
			if (c != Character.LINE_SEPARATOR && c != '/' && c != ' ' && c != ',') sb.append(c); else break;
		}
		_systemBaseMVA = Float.parseFloat(sb.toString().trim());
		
		// see if we can determine the version
		String rstring = l1.substring(end);
		for(Pattern vp : _VersionPattern)
		{
			Matcher v = vp.matcher(rstring);
			if (v.find())
			{
				StringBuilder sbv = new StringBuilder();
				for(int i=v.end(); i < rstring.length(); ++i)
				{
					char c = rstring.charAt(i);
					if (c != ' ') sbv.append(c); else break;
				}
				_version = sbv.toString();
			}
		}

		// parse the date if it exists
		if (rstring == null || !rstring.isEmpty())
		{
			int ixm = -1;
			rstring = rstring.substring(end);
			for (String m : _Months)
			{
				if ((ixm = rstring.indexOf(m)) != -1)
				{
					parseDate(rstring.substring(ixm));
				}
			}
		}		
		else
		{
			_caseTime = new GregorianCalendar();
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
