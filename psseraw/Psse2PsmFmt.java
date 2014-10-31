package com.powerdata.openpa.psseraw;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Psse2PsmFmt extends PsseProcessor 
{
	
	List<PsseRecWriter> _writerList;
	File _outdir;
	static PssePSMWriter _csvWriter;
	
	public Psse2PsmFmt(Reader rawpsse, String specversion, File outdir) throws IOException,
			PsseProcException 
	{
		super(rawpsse, specversion);
		System.out.println("[constructor]");
		_writerList = new ArrayList<>();
		_outdir = outdir;
		_csvWriter = new PssePSMWriter(_outdir);
	}

	public static void main(String[] args) throws IOException, PsseProcException
	{
		File outdir = null;
		File psse = null;
		
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
						outdir = new File(args[i++]);
						System.out.println("[main] outdir = "+outdir.getName());
						break;
					case "p":
					case "psse":
						psse = new File(args[i++]);
						System.out.println("[main] psse = "+psse.getName());
						break;
					default:
						System.out.println("parameter " + a + " not understood");
				}
			}
		}
		
		if (psse == null)
		{
			System.err.println("Unable to locate PSS/E file");
		}
		
		Reader psseReader = new BufferedReader(new FileReader(psse));
		Psse2PsmFmt toPsm = new Psse2PsmFmt(psseReader, "30", outdir);
		
		toPsm.process();
		psseReader.close();
		toPsm.cleanup();
		
	}

	public void cleanup() { _csvWriter.cleanup();}
	
	@Override
	public void process() throws PsseProcException, IOException 
	{
		// TODO Auto-generated method stub
		
		for (PsseClass pc : getPsseClassSet().getPsseClasses())
		{
//			PsseRecWriter w = getWriter(pc.getClassName());
			PsseRecWriter w = _csvWriter;
			if (!pc.getLines().isEmpty())
			{
				pc.processRecords(_rdr, w, _cset);
//				System.out.println("[process] pc class = "+pc.getClassName());
			}
		}
		
//		System.out.println("[process] Buses: "+_cset.getBus().getLines());
		
		//super.process();
	}
	
	protected PsseRecWriter getWriter(String psseClassName, String colName)
	{
		System.out.println("[getWriter] 2 arg writer");
		
		return _csvWriter;
	}

	@Override
	protected PsseRecWriter getWriter(String psseClassName) {
		// TODO Auto-generated method stub
		System.out.println("[getWriter] 1 arg writer");
		
		
		return _csvWriter;
	}
	
}

class PssePSMWriter implements PsseRecWriter
{
	static final int _invalidOffset = -1;
	
	protected TObjectIntMap<String> _writerMap;
	protected TObjectIntMap<String> _busMap;
	protected TObjectIntMap<String> _loadMap;
	protected TObjectIntMap<String> _genMap;
	protected TObjectIntMap<String> _areaMap;
	protected TObjectIntMap<String> _tfmrMap;
	
	protected List<PrintWriter> _writers;
	protected File _outdir;

	public PssePSMWriter(File outdir) 
	{
		_outdir = outdir;
	}

	@Override
	public void writeRecord(PsseClass pclass, String[] record)
			throws PsseProcException 
	{	
		List<PsseField[]> lines = pclass.getLines();
		int n = lines.size();
		try
		{
			switch(pclass.getClassName().toLowerCase())
			{
			case "bus":
				for(int i = 0; i < n; ++i)
				{
//					System.out.println("[writRecord] bus "+i+"/"+n);
					processBus(lines.get(i), record);
				}
				break;
			case "load":
				for(int i = 0; i < n; ++i)
				{
//					System.out.println("[writRecord] load "+i+"/"+n);
					processLoad(lines.get(i), record);
				}
				break;
			case "generator":
			{
				for(int i = 0; i < n; ++i)
				{
//					System.out.println("[writRecord] load "+i+"/"+n);
					processGenerator(lines.get(i), record);
				}
				break;
			}
			case "areainterchange":
			{
				for(int i = 0; i < n; ++i)
				{
					processArea(lines.get(i), record);
				}
				break;
			}
			case "transformer":
			{
				for(int i = 0; i < n; ++i)
				{
					processTransformer(lines.get(i), record);
				}
				break;
			}
			default:
				System.out.println("[writeRecord] No processor for "+pclass.getClassName().toLowerCase());	
			}
		}
		catch(Exception e)
		{
			System.err.println("PssePSMWriter issue with writing csv : "+e);
		}
		
	}
	
	@Override
	public void cleanup() 
	{
		for(int i = 0; i < _writers.size(); ++i)
		{
			System.out.println("[cleanup] Closing Writer "+(i+1)+"/"+_writers.size());
			_writers.get(i).close();
		}
	}
	
	private void processBus(PsseField[] fld, String[] record) throws FileNotFoundException
	{
		// Bus data order
		// ID, Name, NominalKV, Substation, IsBusBarSection, FrequencySourcePriority
		
		List<String>data = new ArrayList<>();
		String[] nodeHeaders = {"i", "name", "basekv"};
		
		if(_busMap == null)_busMap = buildMap(fld);
		
//		data.add(getData(record, _busMap.get("i"))); //ID
//		data.add(getData(record, _busMap.get("name"))); //Name
//		data.add(getData(record, _busMap.get("basekv"))); //NominalKV
		
		for(int i = 0; i < nodeHeaders.length; ++i)
		{
			data.add(getData(record, _busMap.get(nodeHeaders[i])));
		}
		
		PrintWriter pw = getWriter("Node");
		pw.println(buildCsvLine(data));

	}
	
	private void processLoad(PsseField[] fld, String[] record) throws FileNotFoundException
	{
		List<String> data = new ArrayList<>();
		PrintWriter pw;
		
		if(_loadMap == null) _loadMap = buildMap(fld);
		
		//Load.csv
		data.add(getData(record, _loadMap.get("id"))+"_"+getData(record, _loadMap.get("i"))); //ID
		data.add(getData(record, _loadMap.get("id"))); //Name
		data.add(getData(record, _loadMap.get("i"))); //Node
		
		pw = getWriter("Load");
		pw.println(buildCsvLine(data));
		
		//PsmCaseLoad.csv
		data.clear();
		data.add(getData(record, _loadMap.get("id"))+"_"+getData(record, _loadMap.get("i"))); //ID
		data.add(getData(record, _loadMap.get("pl"))); //MW
		data.add(getData(record, _loadMap.get("ql"))); //MVAr
		
		pw = getWriter("PsmCaseLoad");
		pw.println(buildCsvLine(data));
	}
	
	private void processGenerator(PsseField[] fld, String[] record) throws FileNotFoundException
	{
		List<String> data = new ArrayList<>();
		String[] genHeaders = {"id","pb","pt"};
		String[] genCaseHeaders = {"pg,vs"};
		PrintWriter pw;
		
		if(_genMap == null) _genMap = buildMap(fld);
		
		//GeneratingUnit.csv
		data.add(getData(record,_genMap.get("id"))+"_"+getData(record,_genMap.get("i"))); //ID
		for(int i = 0; i < genHeaders.length; ++i)
		{
			data.add(getData(record, _genMap.get(genHeaders[i])));
		}
		
		pw = getWriter("GeneratingUnit");
		pw.println(buildCsvLine(data));
		
		//PsmCaseGeneratingUnit.csv
		data.clear();
		data.add(getData(record,_genMap.get("id"))+"_"+getData(record,_genMap.get("i")));
		for(int i = 0; i < genCaseHeaders.length; ++i)
		{
			data.add(getData(record, _genMap.get(genCaseHeaders[i])));
		}
		
		pw = getWriter("PsmCaseGeneratingUnit");
		pw.println(buildCsvLine(data));
	}
	
	private void processArea(PsseField[] fld, String[] record) throws FileNotFoundException
	{
		List<String> data = new ArrayList<>();
		PrintWriter pw;
		
		if(_areaMap == null) _areaMap = buildMap(fld);
		
		data.add(getData(record, _areaMap.get("i")));
		data.add(getData(record, _areaMap.get("arname")));
		
		pw = getWriter("ControlArea");
		pw.println(buildCsvLine(data));
	}
	
	private void processTransformer(PsseField[] fld, String[] record) throws FileNotFoundException
	{
		List<String> data = new ArrayList<>();
		PrintWriter pw;
		
		if(_tfmrMap == null) _tfmrMap = buildMap(fld);
		
		//Transformer.csv
		data.add(getData(record, _tfmrMap.get("name"))+"_"+getData(record, _tfmrMap.get("i"))); //ID
		data.add(getData(record, _tfmrMap.get("name"))); //Name
		
		int wdgCount = 0;//Winding Count
		if(_tfmrMap.get("i") != _tfmrMap.getNoEntryValue()) wdgCount++;
		if(_tfmrMap.get("j") != _tfmrMap.getNoEntryValue()) wdgCount++;
		if(_tfmrMap.get("k") != _tfmrMap.getNoEntryValue()) wdgCount++;
		data.add(""+wdgCount);
		
		pw = getWriter("Transformer");
		pw.println(buildCsvLine(data));
		
		//TransformerWinding.csv
		
		//PsmCaseTransformerWinding.csv
		
		//RatioTapChanger.csv
		
		//PsmCaseRatioTapChanger.csv
	}
	
	private TObjectIntMap<String> buildMap(PsseField[] fld)
	{
		TObjectIntMap<String> map = new TObjectIntHashMap<>(fld.length, 1f, _invalidOffset);
		
		for(int i = 0; i < fld.length; ++i)
		{
			map.put(fld[i].getName().toLowerCase(), i);
		}
		
		return map;
	}
	
	private PrintWriter getWriter(String fileName) throws FileNotFoundException
	{
		PrintWriter pw;
		
		//Create the writer list if it doesn't exist yet
		if (_writers == null) _writers = new ArrayList<>();
		
		//Create the writer map if it doesn't exist yet
		if (_writerMap == null) _writerMap = new TObjectIntHashMap<>(5, 1f, -1);
		
		//check if the requested writer exists
		if (_writerMap.get(fileName) == _writerMap.getNoEntryValue())
		{
			//Add to the map
			_writerMap.put(fileName, _writers.size());
			//Create writer and add it to the array
			System.out.println("[getWriter] Creating PrintWriter with path "+_outdir.getAbsolutePath()+"/"+fileName+".csv");
			pw = new PrintWriter(new File(_outdir.getAbsolutePath()+"/"+fileName+".csv"));
			//Write column headers for the new csv
			pw.println(getHeaders(fileName));
			_writers.add(pw);
		}
		else
		{
			pw = _writers.get(_writerMap.get(fileName));
		}
		
		return pw;
	}
	
	private String getData(String[] record, int offset)
	{
		if(offset == _invalidOffset) return "";
		
		return record[offset];
	}
	
	private String getHeaders(String fileName)
	{
		String h;
		switch(fileName.toLowerCase())
		{
		case "node":
			h = "ID,Name,NominalKV";
			break;
		case "load":
			h = "ID,Name,Node";
			break;
		case "psmcaseload":
			h = "ID,MW,MVAr";
			break;
		case "generatingunit":
//			h = "ID,Name,MinOperatingMW,MaxOperatingMW,GeneratingUnitType,GenControlMode";
			h = "ID,Name,MinOperatingMW,MaxOperatingMW";
			break;
		case "psmcasegeneratingunit":
//			h = "ID,MW,MWSetPoint,GeneratorOperatingMode";
			h = "ID,MW,MWSetPoint";
			break;
		case "controlarea":
			h = "ID,Name";
			break;
		case "transformer":
			h = "ID,Name,WindingCount";
			break;
		default:
			h = "HeadersNotFound";
		}
		
		System.out.println("[getHeaders] Header for "+fileName+" = "+h);
		return h;
	}
	
	private String buildCsvLine(List<String> data)
	{
		String line = "";
		int n = data.size();
		
		for(int i = 0; i < n; ++i)
		{
			line += data.get(i);
			if(i < (n - 1))
			{
				line += (",");
			}
		}
		
		return line;
	}
}