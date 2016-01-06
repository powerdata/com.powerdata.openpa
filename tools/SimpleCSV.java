package com.powerdata.openpa.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Utility to easily read and write CSV files.
 * 
 * This requires that the first row in the file is the "header" and not
 * data.
 * 
 * A CSV model can be created by using the default constructor, then 
 * setting a header using setHeader().  After that call addRow() for
 * each new row.
 * 
 * @author marck
 */
public class SimpleCSV
{

	@Override
	public String toString()
	{
		return (_meta != null) ? String.format("SimpleCSV - %s",_meta) : super.toString();
	}
	/** Array of column names */
	String _colNames[] = null;
	/** Array of columns */
	ArrayList<ArrayList<String>> _cols = new ArrayList<ArrayList<String>>();
	/** Array of columns by name */
	HashMap<String,ArrayList<String>> _colsByName = new HashMap<String,ArrayList<String>>();
	/** Number of rows */
	int _rowCount = 0;
	/** meta information */
	String _meta;

	public SimpleCSV(){}
	public SimpleCSV(InputStream in) throws IOException
	{
		load(in);
	}
	public SimpleCSV(String filename) throws IOException
	{
		load(filename);
	}
	public SimpleCSV(File file) throws IOException
	{
		load(file);
	}
	
	public void setMeta(String meta) {_meta = meta;}
	public String getMeta() {return _meta;}
	
	public String[] getColumnNames() { return _colNames; }
	public int getRowCount() { return _rowCount; }
	public int getColCount() { return (_colNames != null)?_colNames.length:0; }
	//
	//	Get values based on column offset
	//
	public String get(int col, int row)
	{
		ArrayList<String> vals = _cols.get(col);
		return (vals == null)?"":vals.get(row);
	}
	public byte getByte(int col, int row) { return Byte.parseByte(get(col,row)); }
	public short getShort(int col, int row) { return Short.parseShort(get(col,row)); }
	public int getInt(int col, int row) { return Integer.parseInt(get(col,row)); }
	public long getLong(int col, int row) { return Long.parseLong(get(col,row)); }
	public float getFloat(int col, int row) { return Float.parseFloat(get(col,row)); }
	public double getDouble(int col, int row) { return Double.parseDouble(get(col,row)); }
	//
	//	Get values based on column name
	//
	public String get(String col, int row)
	{
		ArrayList<String> vals = _colsByName.get(col);
		return (vals == null)?"":vals.get(row);
	}
	public byte getByte(String col, int row) { return Byte.parseByte(get(col,row)); }
	public short getShort(String col, int row) { return Short.parseShort(get(col,row)); }
	public int getInt(String col, int row) { return Integer.parseInt(get(col,row)); }
	public long getLong(String col, int row) { return Long.parseLong(get(col,row)); }
	public float getFloat(String col, int row) { return Float.parseFloat(get(col,row)); }
	public double getDouble(String col, int row) { return Double.parseDouble(get(col,row)); }
	//
	//	Get entire columns
	//
	public String[] get(int col)
	{
		ArrayList<String> vals = _cols.get(col);
		return (vals == null)?new String[0]:vals.toArray(new String[0]);
	}
	public String[] get(String col)
	{
		ArrayList<String> vals = _colsByName.get(col);
		return (vals == null)?new String[0]:vals.toArray(new String[0]);
	}
	public boolean hasCol(String col) { return (_colsByName.get(col) != null); }
	public long[] getLongs(int col) { return getLongs(get(col)); }
	public long[] getLongs(String col) { return getLongs(get(col)); }
	public long[] getLongs(String svals[])
	{
		long fvals[] = null;
		int n = svals.length;
		if (svals != null)
		{
			fvals = new long[n];
			for(int i = 0; i < n; ++i)
			{
				fvals[i] = Long.parseLong(svals[i]);
			}
		}
		return fvals;
	}
	public float[] getFloats(int col) { return getFloats(get(col)); }
	public float[] getFloats(String col) { return getFloats(get(col)); }
	public float[] getFloats(String svals[])
	{
		float fvals[] = null;
		int n = svals.length;
		if (svals != null)
		{
			fvals = new float[n];
			for(int i=0; i<n; i++)
			{
				fvals[i] = Float.parseFloat(svals[i]);
			}
		}
		return fvals;
	}
	public short[] getShorts(int col) { return getShorts(get(col)); }
	public short[] getShorts(String col) { return getShorts(get(col)); }
	public short[] getShorts(String svals[])
	{
		short hvals[] = null;
		int n = svals.length;
		if (svals != null)
		{
			hvals = new short[n];
			for(int i=0; i<n; i++)
			{
				hvals[i] = Short.parseShort(svals[i]);
			}
		}
		return hvals;
	}

	public boolean[] getBooleans(int col) {return getBooleans(get(col));}
	public boolean[] getBooleans(String col) {return getBooleans(get(col));}
	public boolean[] getBooleans(String[] svals)
	{
		int n = svals.length;
		if(n == 0) return new boolean[0];
		boolean[] rv = new boolean[n];
		for(int i=0; i < n; ++i)
			rv[i] = Boolean.parseBoolean(svals[i]);
		return rv;
	}
	
	public int[] getInts(int col) { return getInts(get(col)); }
	public int[] getInts(String col) { return getInts(get(col)); }
	public int[] getInts(String svals[])
	{
		if (svals.length == 0) return new int[0];
		int fvals[] = null;
		int n = svals.length;
		if (svals != null)
		{
			fvals = new int[n];
			for(int i=0; i<n; i++)
			{
				fvals[i] = Integer.parseInt(svals[i]);
			}
		}
		return fvals;
	}
	public double[] getDoubles(int col) { return getDoubles(get(col)); }
	public double[] getDoubles(String col) { return getDoubles(get(col)); }
	public double[] getDoubles(String svals[])
	{
		double[] fvals = null;
		int n = svals.length;
		if (svals != null)
		{
			fvals = new double[n];
			for(int i=0; i<n; i++)
			{
				fvals[i] = (svals[i].trim().equals("")? 0F : Double.parseDouble(svals[i]));
			}
		}
		return fvals;
	}
	//
	//	Set values based on column offset
	//
	public void set(int col, int row, String val) { _cols.get(col).set(row, val); }
	public void set(int col, int row, byte val) { set(col, row, String.valueOf(val)); }
	public void set(int col, int row, short val) { set(col, row, String.valueOf(val)); }
	public void set(int col, int row, int val) { set(col, row, String.valueOf(val)); }
	public void set(int col, int row, long val) { set(col, row, String.valueOf(val)); }
	public void set(int col, int row, float val) { set(col, row, String.valueOf(val)); }
	public void set(int col, int row, double val) { set(col, row, String.valueOf(val)); }
	//
	//	Set values based on the column name
	//
	public String set(String col, int row, String val) { return _colsByName.get(col).set(row,val); }
	public void set(String col, int row, byte val) { set(col, row, String.valueOf(val)); }
	public void set(String col, int row, short val) { set(col, row, String.valueOf(val)); }
	public void set(String col, int row, int val) { set(col, row, String.valueOf(val)); }
	public void set(String col, int row, long val) { set(col, row, String.valueOf(val)); }
	public void set(String col, int row, float val) { set(col, row, String.valueOf(val)); }
	public void set(String col, int row, double val) { set(col, row, String.valueOf(val)); }
	
	public void setHeader(String header[])
	{
		_cols.clear();
		_colsByName.clear();
		_rowCount = 0;
		_colNames = header;
		for(int i=0; i<_colNames.length; i++)
		{
			ArrayList<String> col = new ArrayList<String>();
			_cols.add(col);
			if (!_colsByName.containsKey(_colNames[i]))
			{
				_colsByName.put(_colNames[i], col);
			}
			else if (_colNames[i].length() > 0)
			{
				//System.out.println("WARNING: Duplicate column name ignored: "+_colNames[i]);
			}
		}
	}
	public int addRow() throws Exception
	{
		if (_colNames == null) throw new Exception("ERROR in addRow(): CSV Header not set.");
		for(ArrayList<String> col : _cols) { col.add(""); }
		++_rowCount;
		return _rowCount;
	}
	public int deleteRow(int row) throws Exception
	{
		if (_colNames == null) throw new Exception("ERROR in deleteRow(): CSV Header not set.");
		if (row >= _rowCount) throw new Exception("ERROR in deleteRow(): Row out of range.");
		for(ArrayList<String> col : _cols) { col.remove(row); }
		--_rowCount;
		return _rowCount;		
	}
	public void load(String filename) throws IOException
	{
		load(new File(filename));
	}
	public void load(File file) throws IOException
	{
		if (!file.exists()) return;
		_meta = file.getName();
		FileInputStream in = new FileInputStream(file);
		load(in);
		in.close();
	}

	public void load(InputStream in) throws IOException
	{
		BufferedReader r = new BufferedReader(new InputStreamReader(in));
		_colNames = null;
		// assume a header
		int ccnt = 0;
		String line = r.readLine();
		if (line != null) {
			setHeader(new StringParse(line, ",").setQuoteChar('\'').getTokens());
			ccnt = _cols.size();
		}
		// load the data
		while((line = r.readLine()) != null)
		{
			if (line.startsWith("#")) continue;
			String vals[] = new StringParse(line,",").setQuoteChar('\'').getTokens();
			for(int i=0; i<ccnt; i++)
			{
				_cols.get(i).add((i<vals.length)?vals[i].trim():"");
			}
			++_rowCount;
		}
	}
	static public boolean IsNumber(String s)
	{
		int len = s.length();
		if (len == 0) return false;
		for(int i=0; i<len; i++)
		{
			char c = s.charAt(i);
			switch(c)
			{
				case '+': if (len == 1) return false;
				case '-': if (len == 1) return false;
				case '.': if (len == 1) return false;
				case 'e': if (len == 1) return false;
				case 'E': if (len == 1) return false;
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					break;
				default: return false;
			}
		}
		return true;
	}
	static public String Escape(String s)
	{
		// see if there is work to do
		if(s.indexOf('"') != -1 || s.indexOf('\\') != -1)
		{
			// replace with special characters
			s = s.replaceAll("'", "\001");
			s = s.replaceAll("\\", "\002");
			// now replace with escaped versions
			s = s.replaceAll("\001", "\\'");
			s = s.replaceAll("\002", "\\\\");
		}
		// if this is not a number then quote it
		if (!IsNumber(s))
		{
			s = "'"+s+"'";
		}
		return s;
	}
	public void save(String filename) throws IOException
	{
		OutputStream out = new FileOutputStream(filename);
		save(out);
		out.close();
	}
	public void save(OutputStream out)
	{
		PrintWriter w = new PrintWriter(new OutputStreamWriter(out));
		save(w);
	}
	public void save(PrintWriter w)
	{
		// write out the header
		for(int i=0; i<_colNames.length; i++)
		{
			if (i>0) w.print(",");
			w.print(_colNames[i]);
		}
		w.println();
		for(int r=0; r<_rowCount; r++)
		{
			for(int c=0; c<_colNames.length; c++)
			{
				if (c>0) w.print(",");
				w.print(Escape(get(c,r)));
			}
			w.println();
		}
		w.flush();
	}
	static public void main(String args[])
	{
		try
		{
			System.out.println(System.getProperty("user.dir"));
			SimpleCSV csv = new SimpleCSV("testdata/db/Branches.csv");
			csv.save(System.out);
		}
		catch(Exception e)
		{
			System.out.println("ERROR: "+e);
		}
	}
}
