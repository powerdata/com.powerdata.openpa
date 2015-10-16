package com.powerdata.openpa.psseraw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.HashMap;
/**
 * Implementation of a PsseProcessor that generates CSV files.
 * 
 * @author chris@powerdata.com
 *
 */
public class Psse2CSV extends PsseProcessor
{
	PsseCSVWriter _wrtr;
	
	public Psse2CSV(Reader rawpsse, String specversion, File outdir) throws IOException,
			PsseProcException
	{
		super(rawpsse, specversion);
		_wrtr = new PsseCSVWriter(outdir);
	}

	@Override
	protected PsseRecWriter getWriter(String pclass) {return _wrtr;}

	public void cleanup() {_wrtr.cleanup();}

	public static void main(String[] args) throws Exception
	{
		File cwd = new File(System.getProperty("user.dir"));
		File outdir = cwd;
		String spsse = null;
		String sversion = null;

		int narg = args.length;
		for (int i = 0; i < narg;)
		{
			String a = args[i++];
			if (a.startsWith("-"))
			{
				int idx = (a.charAt(1) == '-') ? 2 : 1;
				switch (a.substring(idx))
				{
					case "d":
					case "dir":
					case "directory":
						outdir = new File(args[i++]);
						break;
					case "p":
					case "psse":
						spsse = args[i++];
						break;
					case "v":
					case "ver":
					case "version":
						sversion = args[i++];
						break;
					case "h":
					case "help":
						showHelp(false);
					default:
						System.out
								.println("parameter " + a + " not understood");
						showHelp(true);
				}
			}
		}

		File psse = resolveInputFile(cwd, spsse);
		if (psse == null)
		{
			System.err.println("Unable to locate pss/e file");
			showHelp(true);
		}

		Reader rpsse = new BufferedReader(new FileReader(psse));
		Psse2CSV p2c = new Psse2CSV(rpsse, sversion, outdir);
		
		PsseHeader hdr = p2c.getHeader();
		System.out.println("Loading File: "+psse);
		System.out.println("Change Code: "+hdr.getChangeCode());
		System.out.println("System Base MVA: "+hdr.getSystemBaseMVA());
		System.out.format("Case Time: %tc\n", hdr.getCaseTime());
		System.out.format("Import Time: %tc\n", hdr.getImportTime());
		System.out.println("Heading 1: "+hdr.getHeading1());
		System.out.println("Heading 2: "+hdr.getHeading2());
		String hver = hdr.getVersion();
		if (hver == null)
		{
			hver = String.format("%s - overridden", sversion);
		}
		System.out.println("Version: "+hver);
		
		p2c.process();
		rpsse.close();
		p2c.cleanup();
		
	}

	protected static File resolveInputFile(File cwd, String spsse)
	{
		File psse = null;
		if (spsse != null)
		{
			psse = new File(spsse);
		}
		else
		{
			File[] flist = cwd.listFiles(new FilenameFilter()
			{
				@Override
				public boolean accept(File dir, String name)
				{
					return name.toLowerCase().endsWith(".raw");
				}
			});
			if (flist.length > 0)
				psse = flist[0];
		}
		return psse;
	}

	private static void showHelp(boolean err)
	{
		System.out
				.println("Psse2CSV --dir output_directory "+
						"--psse raw_psse_file [ --force-version use-version ] [ --help ]");
		System.exit(err ? 1 : 0);
	}
}

class PsseCSVWriter implements PsseRecWriter
{
	protected static final char _FldDelim = ',';
	protected static final char _QuoteChar = '\'';
	
	protected File _dir;
	protected HashMap<String,PrintWriter> _fmap = new HashMap<>();
	
	public PsseCSVWriter(File dir) { _dir = dir; }

	@Override
	public void writeRecord(PsseClass pclass, String[] record)
			throws PsseProcException
	{
		try
		{
			PrintWriter out = getWriter(pclass);
			int irec = 0;
			for (PsseField[] line : pclass.getLines())
			{
				for (PsseField fld : line)
				{
					if (irec > 0) out.print(_FldDelim); 
					boolean quote = fld.getType() == PsseFieldType.String;
					if (quote) out.print(_QuoteChar);
					out.print(record[irec++]);
					if (quote) out.print(_QuoteChar);
				}
			}
			out.println();
		} catch (IOException ioe)
		{
			throw new PsseProcException(ioe);
		}
	}

	public void cleanup()
	{
		for (PrintWriter pw : _fmap.values())
			pw.close();

	}

	protected PrintWriter getWriter(PsseClass pc) throws IOException
	{
		String clname = pc.getClassName();
		PrintWriter pw = _fmap.get(clname);
		if (pw == null)
		{
			File f = new File(_dir, clname+".csv");
			pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
			int irec=0;
			for (PsseField[] line : pc.getLines())
			{
				for (PsseField fld : line)
				{
					if (irec++ > 0) pw.print(_FldDelim);
					pw.print(_QuoteChar);
					pw.print(fld.getName());
					pw.print(_QuoteChar);
				}
				
			}
			pw.println();
			_fmap.put(clname, pw);
		}
		return pw;
	}

}
