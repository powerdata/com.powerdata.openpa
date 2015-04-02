package com.powerdata.openpa.tools.psmfmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.IntFunction;
import com.powerdata.openpa.PAModelException;



abstract class Export
{
	static protected class FmtInfo
	{
		String col;
		IntFunction<String> value;
		FmtInfo(String col, IntFunction<String> v) {this.col = col; this.value = v;}
		
	}

	public void export(File outputdir) throws PAModelException, IOException
	{
		PrintWriter pw = new PrintWriter(new BufferedWriter(
			new FileWriter(new File(outputdir, getPsmFmtName()+".csv"))));
		printHeader(pw);
		printData(pw);
		pw.close();
	}

	protected void printData(PrintWriter pw)
	{
		printData(pw, getFmtInfo(), getCount());
	}
	
	protected void printData(PrintWriter pw, FmtInfo[] fi, int n)
	{
		for(int i=0; i < n; ++i)
		{
			boolean first = true;
			for (FmtInfo f : fi)
			{
				if (f != null)
				{
					if (first)
						first = false;
					else
						pw.print(',');
					pw.print(f.value.apply(i));
				}
			}
			pw.println();
		}
	}

	protected void printHeader(PrintWriter pw)
	{
		boolean first = true;
		for (FmtInfo f : getFmtInfo())
		{
			if (f != null)
			{
				if (first)
					first = false;
				else
					pw.print(',');
				pw.format("\"%s\"", f.col);
			}
		}
		pw.println();
	}

	protected abstract int getCount();

	protected abstract FmtInfo[] getFmtInfo();

	protected abstract String getPsmFmtName();
}
