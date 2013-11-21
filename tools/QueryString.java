package com.powerdata.openpa.tools;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Utiltiy to parse query strings.
 * 
 * A query string has the form: field=value&field2=value ...
 * 
 * The field names can be repeated.  Sometimes the order of the fields is
 * important, other times only the values matter.  This utility allows
 * different approaches depending on which of these is important.
 * 
 * The one kludge is that in most cases there will be exactly one value
 * per field, however, since more are possible it is not enough to simply
 * return a String value for a field name, as a result the caller is
 * forced to check the return value for null and check the count.
 * 
 * @author marck
 *
 */
public class QueryString
{
	String _query[][];
	HashMap<String,String[]> _fields = new HashMap<String,String[]>();
	public QueryString(String query)
	{
		this(query, false);
	}
	public QueryString(String query, boolean keepQuotes)
	{
		// split based on &
		String pairs[] = new StringParse(query,"&", keepQuotes).getTokens();
		// split based on = and index them
		_query = new String[pairs.length][];
		HashMap<String,ArrayList<String>> fields = new HashMap<String,ArrayList<String>>();
		for(int i=0; i<pairs.length; i++)
		{
			_query[i] = new StringParse(pairs[i],"=").getTokens();
			ArrayList<String> vals = fields.get(_query[i][0]);
			if (vals == null)
			{
				vals = new ArrayList<String>();
				fields.put(_query[i][0], vals);
			}
			vals.add(_query[i][1]);
		}
		// convert the ArrayList to String[]
		for(String k : fields.keySet())
		{
			_fields.put(k, fields.get(k).toArray(new String[0]));
		}
	}
	public int count() { return _query.length; }
	public String[] get(int i) { return _query[i]; }
	public String getField(int i) { return _query[i][0]; }
	public String getVal(int i) { return _query[i][1]; }
	public String[] get(String field) { return _fields.get(field); }
	public boolean containsKey(String field) { return _fields.containsKey(field); }
	public int getFieldCount() { return _fields.size(); }
	public HashMap<String,String[]> getMap() { return _fields; }
	public String[][] getQuery() { return _query; }
}
