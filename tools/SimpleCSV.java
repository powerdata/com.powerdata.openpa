package com.powerdata.openpa.tools;

import java.io.BufferedReader;
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
 * Utility to easiy read and write CSV files.
 * @author marck
 */
public class SimpleCSV
{
	String _colNames[] = null;
	ArrayList<ArrayList<String>> _cols = new ArrayList<ArrayList<String>>();
	HashMap<String,ArrayList<String>> _colsByName = new HashMap<String,ArrayList<String>>();
	int _rowCount = 0;

	public SimpleCSV(){}
	public SimpleCSV(InputStream in) throws IOException
	{
		load(in);
	}
	public SimpleCSV(String filename) throws IOException
	{
		load(filename);
	}
	public String[] getColumnNames() { return _colNames; }
	public int getRowCount() { return _rowCount; }
	public int getColCount() { return (_colNames != null)?_colNames.length:0; }
	public String get(int col, int row)
	{
		return _cols.get(col).get(row);
	}
	public String get(String name, int row)
	{
		return _colsByName.get(name).get(row);
	}
	public String[] get(int col)
	{
		return _cols.get(col).toArray(new String[0]);
	}
	public String[] get(String col)
	{
		return _colsByName.get(col).toArray(new String[0]);
	}
	public void set(int col, int row, String val)
	{
		_cols.get(col).set(row, val);
	}
	public String set(String name, int row, String val)
	{
		return _colsByName.get(name).set(row,val);
	}
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
			_colsByName.put(_colNames[i], col);
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
		FileInputStream in = new FileInputStream(filename);
		load(in);
		in.close();
	}
	public void load(InputStream in) throws IOException
	{
		BufferedReader r = new BufferedReader(new InputStreamReader(in));
		_colNames = null;
		// assume a header
		String line = r.readLine();
		setHeader(new StringParse(line,",").getTokens());
		// load the data
		while((line = r.readLine()) != null)
		{
			if (line.startsWith("#")) continue;
			String vals[] = new StringParse(line,",").getTokens();
			for(int i=0; i<vals.length; i++)
			{
				_cols.get(i).add(vals[i]);
			}
			++_rowCount;
		}
	}
	static public boolean IsNumber(String s)
	{
		int len = s.length();
		for(int i=0; i<len; i++)
		{
			char c = s.charAt(i);
			switch(c)
			{
				case '+': if (i>0 || len == 1) return false;
				case '-': if (i>0 || len == 1) return false;
				case '.': if (len == 1) return false;
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
			s = s.replaceAll("\"", "\001");
			s = s.replaceAll("\\", "\002");
			// now replace with escaped versions
			s = s.replaceAll("\001", "\\\"");
			s = s.replaceAll("\002", "\\\\");
		}
		// if this is not a number then quote it
		if (!IsNumber(s))
		{
			s = "\""+s+"\"";
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
			SimpleCSV csv = new SimpleCSV("testdata/branches.csv");
			csv.save(System.out);
		}
		catch(Exception e)
		{
			System.out.println("ERROR: "+e);
		}
	}
}
