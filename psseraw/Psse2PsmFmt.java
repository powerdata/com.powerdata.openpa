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

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.util.TransformerRaw;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.DeltaNetwork;

public class Psse2PsmFmt extends PsseProcessor 
{
	
	protected List<PsseRecWriter> _writerList;
	protected File _outdir;
	protected static PssePSMWriter _csvWriter;
	
	public Psse2PsmFmt(Reader rawpsse, String specversion, File outdir) throws IOException,
			PsseProcException 
	{
		super(rawpsse, specversion);
//		System.out.println("[constructor]");
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
		for (PsseClass pc : getPsseClassSet().getPsseClasses())
		{
			PsseRecWriter w = _csvWriter;
			if (!pc.getLines().isEmpty())
			{
				pc.processRecords(_rdr, w, _cset);
			}
		}
		//super.process();
	}

	@Override
	protected PsseRecWriter getWriter(String psseClassName) 
	{
		return _csvWriter;
	}
	
}

class PssePSMWriter implements PsseRecWriter
{
	static final int _invalidOffset = -1;
	static final String _invalidData = "";
	
	protected TObjectIntMap<String> _writerMap;
	protected TObjectIntMap<String> _busMap;
	protected TObjectIntMap<String> _loadMap;
	protected TObjectIntMap<String> _genMap;
	protected TObjectIntMap<String> _areaMap;
	protected TObjectIntMap<String> _lineMap;
	protected TObjectIntMap<String> _tfmrMap;
	protected TObjectIntMap<String> _ownerMap;
	protected TObjectIntMap<String> _shuntMap;

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

		//Debugging messages
//		for(int i = 0; i < lines.size(); ++i)
//		{
//			System.out.println("\n[writeRecord] pclass = "+pclass.getClassName());
//			for(int j = 0; j < lines.get(i).length; ++j)
//			{
//				System.out.println("[writeRecord] lines.get("+i+")["+j+"] = "+lines.get(i)[j].getName());
//			}
//		}
//		System.out.println("\n[writeRecord] pclass = "+pclass.getClassName());
//		for(int i = 0; i < record.length; ++i)
//		{
//			System.out.println("[writeRecord] record["+i+"/"+record.length+"] = "+record[i]);
//		}
		
		int n = lines.size();
		try
		{
			switch(pclass.getClassName().toLowerCase())
			{
			case "bus":
				processBus(lines.get(0), record);
				break;
			case "load":
				processLoad(lines.get(0), record);
				break;
			case "generator":
				processGenerator(lines.get(0), record);
				break;
			case "areainterchange":
				processArea(lines.get(0), record);
				break;
			case "transformer":
				processTransformer(lines, record);
				break;
			case "nontransformerbranch":
				//Gets broken down into lines / series reactors / series capacitors
				//First cut make it be lines
				processLine(lines.get(0), record);
				break;
			case "switchedshunt":
				if(_shuntMap == null) _shuntMap = buildMap(lines.get(0));
				int modsw = getInt(record, _shuntMap.get("modsw"));
				if(modsw == 3)
				{
					processSVC(lines.get(0), record);
				}
				else 
				{
					processSwitchedShunt(lines.get(0), record); //TODO Currently working on
				}
				break;
			case "owner":
				processOwner(lines.get(0), record);
				break;
			default:
//				System.out.println("[writeRecord] No processor for "+pclass.getClassName().toLowerCase());	
			}
		}
		catch(Exception e)
		{
			System.err.println("PssePSMWriter issue with writing csv | "+e);
		}
		
	}
	
	@Override
	public void cleanup() 
	{
		for(int i = 0; i < _writers.size(); ++i)
		{
//			System.out.println("[cleanup] Closing Writer "+(i+1)+"/"+_writers.size());
			_writers.get(i).close();
		}
	}
	
	private void processBus(PsseField[] fld, String[] record) throws FileNotFoundException
	{
		// Bus data order
		// ID, Name, NominalKV, Substation, IsBusBarSection, FrequencySourcePriority
		//Anything from PSSE is a busbar section
		
		List<String>data = new ArrayList<>();
		String[] nodeHeaders = {"i", "name", "baskv"};
		
		if(_busMap == null)_busMap = buildMap(fld);
		
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
		data.add(getData(record, _loadMap.get("id"))+"_"+getData(record, _loadMap.get("i"))+"_load"); //ID
		data.add(getData(record, _loadMap.get("id"))); //Name
		data.add(getData(record, _loadMap.get("i"))); //Node
		
		pw = getWriter("Load");
		pw.println(buildCsvLine(data));
		
		//PsmCaseLoad.csv
		data.clear();
		data.add(getData(record, _loadMap.get("id"))+"_"+getData(record, _loadMap.get("i"))+"_load"); //ID
		data.add(getData(record, _loadMap.get("pl"))); //MW
		data.add(getData(record, _loadMap.get("ql"))); //MVAr
		
		pw = getWriter("PsmCaseLoad");
		pw.println(buildCsvLine(data));
	}
	
	private void processGenerator(PsseField[] fld, String[] record) throws FileNotFoundException
	{
		List<String> data = new ArrayList<>();
		String[] genHeaders = {"id","pb","pt"};
		PrintWriter pw;
		
		if(_genMap == null) _genMap = buildMap(fld);
		
		Float pb = getFloat(record, _genMap.get("pb"));
		Float pt = getFloat(record, _genMap.get("pt"));
		Float pg = getFloat(record, _genMap.get("pg"));
		
		//GeneratingUnit.csv
		//"ID,Name,MinOperatingMW,MaxOperatingMW,GeneratingUnitType,GenControlMode"
		data.add(getData(record,_genMap.get("id"))+"_"+getData(record,_genMap.get("i"))+"_gu"); //ID
		for(int i = 0; i < genHeaders.length; ++i)
		{
			data.add(getData(record, _genMap.get(genHeaders[i])));
		}
		if(pb < 0)
		{
			if(!(pt < 1 && pb > -1))
			{
				data.add("Hydro");
			}
			else
			{
				data.add("Thermal");
			}
		}
		else
		{
			data.add("Thermal");
		}
		data.add("Setpoint");
		pw = getWriter("GeneratingUnit");
		pw.println(buildCsvLine(data));
		
		//PsmCaseGeneratingUnit.csv
		//"ID,MW,MWSetPoint,GeneratorOperatingMode"
		data.clear();
		data.add(getData(record,_genMap.get("id"))+"_"+getData(record,_genMap.get("i"))+"_gu");
		data.add(pg.toString());
		data.add(pg.toString());
		pw = getWriter("PsmCaseGeneratingUnit");
		pw.println(buildCsvLine(data));
		
		//SynchronousMachine.csv
		//"ID,Name,Node,GeneratingUnit,RegulatedNode"
		data.clear();
		data.add(getData(record,_genMap.get("id"))+"_"+getData(record,_genMap.get("i"))+"_sm"); //ID
		data.add(getData(record, _genMap.get("id"))); //Name
		data.add(getData(record, _genMap.get("i"))); //Node
		data.add(getData(record,_genMap.get("id"))+"_"+getData(record,_genMap.get("i"))+"_gu"); //GeneratingUnit
		String ireg = getData(record, _genMap.get("ireg"));
		if(ireg.equals("0")) ireg = "";
		data.add(ireg); //RegulatedNode
		pw = getWriter("SynchronousMachine");
		pw.println(buildCsvLine(data));
		
		//PsmCaseSynchronousMachine.csv
		//"ID,SynchronousMachingOperatingMode,AVRMode,KVSetPoint,MVArSetpoint,MVAr"
		data.clear();
		data.add(getData(record,_genMap.get("id"))+"_"+getData(record,_genMap.get("i"))+"_sm"); //ID
		//SynchronousOperatingMode
		if(pt < 1 && pb > -1)//CON
		{
			data.add("CON");
		}
		else if(pb >= 0 && pt >= pb)//GEN
		{
			data.add("GEN");
		}
		else if(pb < 0 && pt > 0 && pg < 0)//PMP
		{
			data.add("PMP");
		}
		else
		{
			data.add("");
		}
		
		if(getData(record, _genMap.get("qt")).equals(getData(record, _genMap.get("qg"))) && getData(record, _genMap.get("qt")).equals(getData(record, _genMap.get("qb"))))
		{
			data.add("OFF"); //AVRMode
			data.add(getData(record, _genMap.get("vs")));//KVSetPoint
			data.add(getData(record, _genMap.get("vs")));//MVArSetPoint
		}
		else
		{
			data.add("ON"); //AVRMode
			data.add(getData(record, _genMap.get("vs")));//KVSetPoint
			data.add(""); //MVArSetPoint
		}
		data.add(getData(record, _genMap.get("qg")));//MVAr
		pw = getWriter("PsmCaseSynchronousMachine");
		pw.println(buildCsvLine(data));
		
		//ReactiveCapabilityCurve.csv
		//ID,SynchronousMachine,MW,MinMVAr,MaxMVAr
		data.clear();
		data.add(getData(record,_genMap.get("id"))+"_"+getData(record,_genMap.get("i"))+"_crv2"); //ID
		data.add(getData(record,_genMap.get("id"))+"_"+getData(record,_genMap.get("i"))+"_sm"); //SynchronousMachine
		data.add(getData(record, _genMap.get("pg")));//MW
		data.add(getData(record, _genMap.get("qb")));//MinMVAr
		data.add(getData(record, _genMap.get("qt")));//MaxMVAr
		pw = getWriter("ReactiveCapabilityCurve");
		pw.println(buildCsvLine(data));
	}
	
	private void processArea(PsseField[] fld, String[] record) throws FileNotFoundException
	{
		List<String> data = new ArrayList<>();
		PrintWriter pw;
		
		if(_areaMap == null) _areaMap = buildMap(fld);
		
		data.add(getData(record, _areaMap.get("i"))+"_ca");
		data.add(getData(record, _areaMap.get("arname")));
		
		pw = getWriter("ControlArea");
		pw.println(buildCsvLine(data));
	}
	
	private void processTransformer(List<PsseField[]> lines, String[] record) throws FileNotFoundException, PsseModelException
	{
		if(_tfmrMap == null) _tfmrMap = buildMap(lines);
		
		//Determine if 2 or 3 winding transformer
		if(getFloat(record, _tfmrMap.get("k")) == 0 || _tfmrMap.get("k") == _tfmrMap.getNoEntryValue())
		{
			if(Math.abs(getInt(record, _tfmrMap.get("cod1"))) == 3)
			{
				processPhaseShifter(record);
			}
			else
			{
				process2Wdg(record);
			}
		}
		else
		{
			process3Wdg(record);
		}
	}
	
	private void process2Wdg(String[] record) throws FileNotFoundException
	{
		List<String> data = new ArrayList<>();
		PrintWriter pw;
		String tfmrID = getData(record, _tfmrMap.get("name"))+"_"+getData(record, _tfmrMap.get("i"))+"_"+getData(record, _tfmrMap.get("j"));
		
		//Debugging
//		System.out.println("[process2Wdg] Fields: "+Arrays.toString(_tfmrMap.keys()));
//		System.out.println("\n[process2Wdg] COD: "+getData(record, _tfmrMap.get("cod1")));
//		System.out.println("[process2Wdg] RMA: "+getData(record, _tfmrMap.get("rma1")));
//		System.out.println("[process2Wdg] RMI: "+getData(record, _tfmrMap.get("rmi1")));
//		System.out.println("[process2Wdg] VMA: "+getData(record, _tfmrMap.get("vma1")));
//		System.out.println("[process2Wdg] VMI: "+getData(record, _tfmrMap.get("vmi1")));
		
//		if(Math.abs(getInt(record, _tfmrMap.get("cod1"))) == 3) System.out.println("[process2Wdg] COD1 - Phase Shifter");
//		if(getInt(record, _tfmrMap.get("cod2")) == 3) System.out.println("[process2Wdg] COD2 - Phase Shifter");
//		if(getInt(record, _tfmrMap.get("cod3")) == 3) System.out.println("[process2Wdg] COD3 - Phase Shifter");

		//Transformer.csv
		data.add(tfmrID+"_tfmr"); //ID
		data.add(getData(record, _tfmrMap.get("name"))); //Name
		data.add("2"); //WindingCount
		pw = getWriter("Transformer");
		pw.println(buildCsvLine(data));
		
		//TransformerWinding.csv
		data.clear();
		data.add(tfmrID+"_wdg"); //ID
		data.add(getData(record, _tfmrMap.get("name"))); //Name
		data.add(tfmrID+"_tfmr"); //Transformer Winding
		data.add(getData(record, _tfmrMap.get("i")));//Node1
		data.add(getData(record, _tfmrMap.get("j")));//Node2
		data.add(getData(record, _tfmrMap.get("r1-2")));//R
		data.add(getData(record, _tfmrMap.get("x1-2")));//X
		data.add(getData(record, _tfmrMap.get("mag2")));//Bmag
		data.add(getData(record, _tfmrMap.get("rata1")));//NormalOperatingLimit
		pw = getWriter("TransformerWinding");
		pw.println(buildCsvLine(data));
		
		//PsmCaseTransformerWinding.csv don't need, doesn't exist
		
		
		//RatioTapChanger.csv - High
		data.clear();
		data.add(tfmrID+"_ftap"); //ID
		data.add(tfmrID+"_wdg"); //Transformer Winding
		data.add(getData(record, _tfmrMap.get("i"))); //TapNode
		pw = getWriter("RatioTapChanger");
		pw.println(buildCsvLine(data));
		
		//RatioTapChanger.csv - Low
		data.clear();
		data.add(tfmrID+"_ttap"); //ID
		data.add(tfmrID+"_wdg"); //Transformer Winding
		data.add(getData(record, _tfmrMap.get("j"))); //TapNode
		pw.println(buildCsvLine(data));
		
		//PsmCaseRatioTapChanger.csv - High
		data.clear();
		data.add(tfmrID+"_ftap"); //ID
		data.add(getData(record, _tfmrMap.get("windv1"))); //Ratio
		pw = getWriter("PsmCaseRatioTapChanger");
		pw.println(buildCsvLine(data));
		
		
		//PsmCaseRatioTapChanger.csv - Low
		data.clear();
		data.add(tfmrID+"_ttap"); //ID
		data.add(getData(record, _tfmrMap.get("windv2"))); //Ratio
		pw.println(buildCsvLine(data));
		
		//Ratio can be a different ratio for each winding
		//1kv side is always going to be 1.0 for the ratio
		//other side ratio will be windv1
		
		//NOMV1/2/3 is the winding neutral kv for the high side
		//Low side is always going to be 1.0
		
		//ANG1 
		
	}
	
	private void process3Wdg(String[] record) throws PsseModelException, FileNotFoundException
	{
		List<String> data = new ArrayList<>();
		PrintWriter pw;
		String name;
		String[] wdg = {"i", "j", "k"};
		float[] r = new float[3];
		float[] x = new float[3];
		//Need to convert from a 3 winding to 3 2 winding transformers
		//First going to create a node for the 3 transformers to connect to
		
		//Node.csv
		// ID, Name, NominalKV, Substation, IsBusBarSection, FrequencySourcePriority
		String nodeId = getData(record, _tfmrMap.get("i"))+"_"+getData(record, _tfmrMap.get("j"))+"_"+getData(record, _tfmrMap.get("k"));
		data.add(nodeId+"_3wdg"); //ID
		data.add(getData(record, _tfmrMap.get("name"))+"_3wdgNode"); //Name
		data.add("1"); //NominalKV
		pw = getWriter("Node");
		pw.println(buildCsvLine(data));
		
		
		//If cz != 1 then it needs to be converted
		float cz = getFloat(record, _tfmrMap.get("cz"));
		
		//Get R / X values
		r[0] = getFloat(record, _tfmrMap.get("r1-2"));
		r[1] = getFloat(record, _tfmrMap.get("r2-3"));
		r[1] = getFloat(record, _tfmrMap.get("r3-1"));
		x[0] = getFloat(record, _tfmrMap.get("x1-2"));
		x[1] = getFloat(record, _tfmrMap.get("x2-3"));
		x[1] = getFloat(record, _tfmrMap.get("x3-1"));
		
		//bmag value
		float bmag = getFloat(record, _tfmrMap.get("mag2"))/ 3f;
		
		if(cz != 1)
		{
			//Create Delta Network
			DeltaNetwork dn = convert3W(r,x);
			Complex[] cplx = {dn.getZ12(), dn.getZ23(), dn.getZ31()};
			
			for(int i = 0; i < 3; ++i)
			{
				r[i] = cplx[i].im();
				x[i] = cplx[i].re();
			}
			
			dn.getZ12().re(); //r
			dn.getZ12().im(); //x
			
		}
		
		//Create the individual transformers
		for(int i = 0; i < 3; ++i)
		{
			//Transformer.csv
			name = getData(record, _tfmrMap.get("name"));
			if(name.equals("") || name == null) name = wdg[i]+"_"+nodeId+"_tfmr";
			data.clear();
			data.add(wdg[i]+"_"+nodeId+"_tfmr");//ID
			data.add(name);//Name
			data.add("2");//Windings
			pw = getWriter("Transformer");
			pw.println(buildCsvLine(data));
			
			//TransformerWinding.csv
			data.clear();
			data.add(wdg[i]+"_"+nodeId+"_wdg");//ID
			data.add(wdg[i]+"_"+getData(record, _tfmrMap.get("name"))+"_wdg");//Name
			data.add(wdg[i]+"_"+nodeId+"_tfmr");//Transformer
			data.add(getData(record, _tfmrMap.get(wdg[i])));//Node1
			data.add(nodeId);//Node2
			data.add(""+r[i]);//R
			data.add(""+x[i]);//X
			data.add(""+bmag);//Bmag mag2/3 assign evenly
			data.add(getData(record, _tfmrMap.get("rata"+(1+i))));//NormalOperatingLimit rata1 / rata2 / rata3
			pw = getWriter("TransformerWinding");
			pw.println(buildCsvLine(data));
			
			//RatioTapChanger.csv - High
			data.clear();
			data.add(getData(record, _tfmrMap.get("name"))+"_"+getData(record, _tfmrMap.get(wdg[i]))+"_ftap"); //ID
			data.add(getData(record, _tfmrMap.get("name"))+"_"+getData(record, _tfmrMap.get(wdg[i]))+"_wdg"); //Transformer Winding
			data.add(getData(record, _tfmrMap.get(wdg[i]))); //TapNode
			
			//RatioTapChanger.csv - Low
			data.clear();
			data.add(getData(record, _tfmrMap.get("name"))+"_"+getData(record, _tfmrMap.get(wdg[i]))+"_ttap"); //ID
			data.add(getData(record, _tfmrMap.get("name"))+"_"+getData(record, _tfmrMap.get(wdg[i]))+"_wdg"); //Transformer Winding
			data.add(nodeId); //TapNode
		}
		
	}
	
	private void processPhaseShifter(String[] record) throws FileNotFoundException
	{
		List<String> data = new ArrayList<>();
		PrintWriter pw;
		String tfmrID = getData(record, _tfmrMap.get("name"))+"_"+getData(record, _tfmrMap.get("i"))+"_"+getData(record, _tfmrMap.get("j"));
		
		//Transformer.csv
		data.add(tfmrID+"_phase"); //ID
		data.add(getData(record, _tfmrMap.get("name"))); //Name
		data.add("2"); //WindingCount
		pw = getWriter("Transformer");
		pw.println(buildCsvLine(data));
		
		//TransformerWinding.csv
		data.clear();
		data.add(tfmrID+"_wdg"); //ID
		data.add(getData(record, _tfmrMap.get("name"))); //Name
		data.add(tfmrID+"_phase"); //Transformer Winding
		data.add(getData(record, _tfmrMap.get("i")));//Node1
		data.add(getData(record, _tfmrMap.get("j")));//Node2
		data.add(getData(record, _tfmrMap.get("r1-2")));//R
		data.add(getData(record, _tfmrMap.get("x1-2")));//X
		data.add(getData(record, _tfmrMap.get("mag2")));//Bmag
		data.add(getData(record, _tfmrMap.get("rata1")));//NormalOperatingLimit
		pw = getWriter("TransformerWinding");
		pw.println(buildCsvLine(data));
		
		//PhaseTapChanger.csv high
		data.clear();
		data.add(tfmrID+"_ptc1");//ID
		data.add(getData(record, _tfmrMap.get("i")));//TapNode
		data.add(tfmrID+"_wdg");//TransformerWinding
		pw = getWriter("PhaseTapChanger");
		pw.println(buildCsvLine(data));
		
		//PhaseTapChanger.csv low
		data.clear();
		data.add(tfmrID+"_ptc2");//ID
		data.add(getData(record, _tfmrMap.get("j")));//TapNode
		data.add(tfmrID+"_wdg");//TransformerWinding
		pw.println(buildCsvLine(data));
		
		//PsmCasePhaseTapChanger.csv high
		data.clear();
		data.add(tfmrID+"_ptc1");//ID
		data.add("true");//ControlStatus TODO: Test value only
		data.add(getData(record,_tfmrMap.get("rma1")));//PhaseShift
		pw = getWriter("PsmCasePhaseTapChanger");
		pw.println(buildCsvLine(data));
		
		//PsmCasePhaseTapChanger.csv low
		data.clear();
		data.add(tfmrID+"_ptc2");//ID
		data.add("true");//ControlStatus TODO: Test value only
		data.add(getData(record,_tfmrMap.get("rmi1")));//PhaseShift
		pw.println(buildCsvLine(data));
	}
	
	private void processSwitchedShunt(PsseField[] fld, String[] record) throws FileNotFoundException
	{
		List<String> data = new ArrayList<>();
		PrintWriter pw;
		List<Float> shuntB = new ArrayList<>();
		boolean isReac;
		
		if(_shuntMap == null) _shuntMap = buildMap(fld);
	
		for(int i = 1; i < 9; i ++)
		{
//			System.out.println("[processSwitchedShunt] N"+i+" = "+getData(record,_shuntMap.get("n"+i)));
			Float b = getFloat(record, _shuntMap.get("b"+i));
			if (!b.isNaN()) shuntB.add(b);
		}

		for(int i = 0; i < shuntB.size(); ++i)
		{
			isReac = (shuntB.get(i)< 0f)?true:false;
			int n = getInt(record,_shuntMap.get("n"+i));
			
			for(int j = 0; j < n; ++j)
			{
				//ShuntCapacitor.csv || ShuntReactor.csv
				data.clear();
				data.add(i+"_"+(j+1)+"_"+getData(record, _shuntMap.get("i"))+"_shunt");//ID
				data.add(i+"_"+(j+1)+"_"+getData(record, _shuntMap.get("i"))+"_shunt");//Name
				data.add(getData(record, _shuntMap.get("i")));//Node
				data.add(shuntB.get(i).toString());//MVAr
				pw = (isReac)?getWriter("ShuntReactor"):getWriter("ShuntCapacitor");
				pw.println(buildCsvLine(data));;
			}
		}
		
//		for(int i = 0; i < fld.length; ++i)
//		{		
//			System.out.println("[processSwitchedShunt] Shunt data: "+fld[i].getName());
//		}
	}
	
	private void processSVC(PsseField[] fld, String[] record) throws FileNotFoundException
	{
		List<String> data = new ArrayList<>();
		PrintWriter pw;
		List<Float> svcB = new ArrayList<>();
		
		if(_shuntMap == null) _shuntMap = buildMap(fld);
		
		for(int i = 1; i < 9; i ++)
		{
			Float b = getFloat(record, _shuntMap.get("b"+i));
			if (!b.isNaN()) svcB.add(b);
		}
		
		for(int i = 0; i < svcB.size(); ++i)
		{
			//SVC.csv
			data.clear();
			data.add(i+"_"+getData(record, _shuntMap.get("i"))+"_svc");//ID
			data.add(i+"_"+getData(record, _shuntMap.get("i"))+"_svc");//Name
			data.add(getData(record, _shuntMap.get("i")));//Node
			data.add(getData(record, _shuntMap.get("vswlo")));//MinMVAr - VSWLO
			data.add(getData(record, _shuntMap.get("vswhi")));//MaxMVAr - VSWHI
			data.add("");//Slope
			pw = getWriter("SVC");
			pw.println(buildCsvLine(data));
		}
	}
	
	private void processLine(PsseField[] fld, String[] record) throws FileNotFoundException
	{
		List<String> data = new ArrayList<>();
		PrintWriter pw;
		
		if(_lineMap == null) _lineMap = buildMap(fld);
		
		int node1 = getInt(record, _lineMap.get("i"));
		int node2 = getInt(record, _lineMap.get("j"));
		if(node1 < 0) node1 = node1 * -1;
		if(node2 < 0) node2 = node2 * -1;
		
		//Line.csv
		data.add(getData(record, _lineMap.get("i"))+"_"+getData(record, _lineMap.get("j"))+"_line");//ID
		data.add(getData(record, _lineMap.get("i"))+"_"+getData(record, _lineMap.get("j"))+"_line");//Name
		data.add(""+node1);//Node1
		data.add(""+node2);//Node2
		data.add(getData(record, _lineMap.get("r")));//R
		data.add(getData(record, _lineMap.get("x")));//X
		data.add(getData(record, _lineMap.get("b")));//Bch?
		data.add(getData(record, _lineMap.get("len")));//Length
		//NormalOperatingLimit - I think this is RateA?
		pw = getWriter("Line");
		pw.println(buildCsvLine(data));
		
		//PsmCaseLine.csv
		data.clear();
	}
	
	private void processOwner(PsseField[] fld, String[] record) throws FileNotFoundException
	{
		List<String> data = new ArrayList<>();
		PrintWriter pw;
		
		if(_ownerMap == null) _ownerMap = buildMap(fld);
		
		data.add(getData(record, _ownerMap.get("i"))+"_org");
		data.add(getData(record,_ownerMap.get("owname")));
		pw = getWriter("Organization");
		pw.println(buildCsvLine(data));
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
	
	private TObjectIntMap<String> buildMap(List<PsseField[]> lines)
	{
		int size = 0;
		int lSize = lines.size();
		
		//Determine the size of the map
		for(int i = 0; i < lSize; ++i)
		{
			size += lines.get(i).length;
		}
		
		TObjectIntMap<String> map = new TObjectIntHashMap<>(size, 1f, _invalidOffset);
		int offset = 0;
		
		for(int i = 0; i < lSize; ++i)
		{
			PsseField[] fld = lines.get(i);
			for(int j = 0; j < fld.length; ++j)
			{
				map.put(fld[j].getName().toLowerCase(), offset);
				offset++;
			}
		}
		
		return map;
	}
	
	private DeltaNetwork convert3W(float[] r, float[] x) throws PsseModelException
	{
		return new DeltaNetwork(
				new Complex(r[0], x[0]), //1_2
				new Complex(r[1], x[1]), //2_3
				new Complex(r[2], x[2])); //3_1
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
//			System.out.println("[getWriter] Creating PrintWriter with path "+_outdir.getAbsolutePath()+"/"+fileName+".csv");
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
		if(offset == _invalidOffset) return _invalidData;
		
		return record[offset];
	}
	
	private Float getFloat(String[] record, int offset)
	{
		if(offset == _invalidOffset || record[offset].equals("")) return Float.NaN;
		
		return Float.parseFloat(record[offset]);
	}
	
	private int getInt(String[] record, int offset)
	{
		if(offset == _invalidOffset || record[offset].equals("")) return -9999;
		
		return Integer.parseInt(record[offset]);
	}
	
	private String getHeaders(String fileName)
	{
		String h;
		switch(fileName.toLowerCase())
		{
		case "node":
//			h = "ID,Name,NominalKV,Substation,FrequencySourcePriority";
			h = "ID,Name,NominalKV";
			break;
		case "load":
			h = "ID,Name,Node";
			break;
		case "psmcaseload":
			h = "ID,MW,MVAr";
			break;
		case "generatingunit":
			h = "ID,Name,MinOperatingMW,MaxOperatingMW,GeneratingUnitType,GenControlMode";
			break;
		case "psmcasegeneratingunit":
//			h = "ID,MW,MWSetPoint,GeneratorOperatingMode";
			h = "ID,MW,MWSetPoint";
			break;
		case "synchronousmachine":
			h = "ID,Name,Node,GeneratingUnit,RegulatedNode";
			break;
		case "psmcasesynchronousmachine":
			h = "ID,SynchronousMachingOperatingMode,AVRMode,KVSetPoint,MVArSetpoint,MVAr";
			break;
		case "reactivecapabilitycurve":
			h = "ID,SynchronousMachine,MW,MinMVAr,MaxMVAr";
			break;
		case "controlarea":
			h = "ID,Name";
			break;
		case "transformer":
			h = "ID,Name,WindingCount";
			break;
		case "transformerwinding":
			h = "ID,Name,Transformer,Node1,Node2,R,X,Bmag,NormalOperatingLimit";
			break;
		case "ratiotapchanger":
			h = "ID,TransformerWinding,TapNode";
			break;
		case "psmcaseratiotapchanger":
			h = "ID,Ratio";
			break;
		case "phasetapchanger":
			h = "ID,TapNode,TransformerWinding";
			break;	
		case "psmcasephasetapchanger":
			h = "ID,ControlStatus,PhaseShift";
			break;	
		case "line":
//			h = "ID,Name,Node1,Node2,R,X,Bch,Length,NormalOperatingLimit";
			h = "ID,Name,Node1,Node2,R,X,Bch,Length";
			break;
		case "psmcaseline":
			h = "ID,FromMW,FromMVAr,ToMW,ToMVAr";
			break;
		case "shuntcapacitor":
		case "shuntreactor":
			h = "ID,Name,Node,MVAr";
			break;
		case "svc":
			h = "ID, Name, Node,MinMVAr,MaxMVAr,Slope";
			break;
		case "organization":
			h = "ID,Name";
			break;
		default:
			h = "HeadersNotFound";
		}
		
//		System.out.println("[getHeaders] Header for "+fileName+" = "+h);
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