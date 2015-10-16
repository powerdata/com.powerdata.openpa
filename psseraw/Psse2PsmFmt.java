package com.powerdata.openpa.psseraw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;


public class Psse2PsmFmt extends PsseProcessor 
{
	@FunctionalInterface
	private interface ToolConstructor
	{
		PsseRecWriter apply(PsseClass pc, PsseRepository rep) throws IOException;
	}
	
	@FunctionalInterface
	private interface ToolConstructorBus
	{
		PsseRecWriter apply(PsseClass pc, File dir, Map<String,String> busMap) throws IOException;
	}
	
	private Map<String,PsseRecWriter> _wmap = new HashMap<>();
	PsseRepository _rep;
	static PsseRecWriter _defw = new PsseRecWriter()
	{
		@Override
		public void writeRecord(PsseClass pclass, String[] record) throws PsseProcException {}
	};
	
	public Psse2PsmFmt(Reader rawpsse, String specversion, File outdir) throws IOException,
			PsseProcException 
	{
		super(rawpsse, specversion);
		_rep = new PsseRepository(outdir);
		setupWriters();
	}

	private void setupWriters() throws IOException
	{
		PsseClassSet pcs = getPsseClassSet();

//		PsseClass busclass = pcs.getBus();
//		PsseClass txclass = pcs.getTransformer();
//		PsseBusTool busw = new PsseBusTool(busclass, _rep);
//		
		addToMap(pcs.getAreaInterchange(),		PsseAreaTool::new);
		addToMap(pcs.getOwner(),				PsseOwnerTool::new);
		addToMap(pcs.getLoad(),					PsseLoadTool::new);
		addToMap(pcs.getBus(),						PsseBusTool::new);
		addToMap(pcs.getGenerator(),			PsseGenTool::new);
		addToMap(pcs.getNontransformerBranch(),	PsseLineTool::new);
		addToMap(pcs.getSwitchedShunt(), 		PsseSwitchedShuntTool::new);
		addToMap(pcs.getTransformer(),			PsseTransformerTool::new);
	}

	private void addToMap(PsseClass psseClass, ToolConstructor tc) throws IOException
	{
		_wmap.put(psseClass.getClassName(), tc.apply(psseClass, _rep));
	}
	
	private void addToMap(PsseClass psseClass, PsseRecWriter w)
	{
		_wmap.put(psseClass.getClassName(), w);
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
		
		if (!outdir.exists()) outdir.mkdirs();
		
		Reader psseReader = new BufferedReader(new FileReader(psse));
		Psse2PsmFmt toPsm = new Psse2PsmFmt(psseReader, "30", outdir);
		
		toPsm.process();
		psseReader.close();
		toPsm.cleanup();
		
	}

	public void cleanup()
	{
		_rep.cleanup();
	}

	@Override
	protected PsseRecWriter getWriter(String psseClassName)
	{
		PsseRecWriter w = _wmap.get(psseClassName);
		if (w == null)
		{
			System.err.format("No writer found for %s\n", psseClassName);
			w = _defw;
		}
		return w;
	}
}
	
//	@Override
//<<<<<<< Updated upstream
//	public void cleanup() 
//	{
//		for(int i = 0; i < _writers.size(); ++i)
//		{
//			_writers.get(i).close();
//		}
//	}
//	
//	private PrintWriter getWriter(String fileName) throws FileNotFoundException
//	{
//		PrintWriter pw;
//		
//		//Create the writer list if it doesn't exist yet
//		if (_writers == null) _writers = new ArrayList<>();
//		
//		//Create the writer map if it doesn't exist yet
//		if (_writerMap == null) _writerMap = new TObjectIntHashMap<>(5, 1f, -1);
//		
//		//check if the requested writer exists
//		if (_writerMap.get(fileName) == _writerMap.getNoEntryValue())
//		{
//			//Add to the map
//			_writerMap.put(fileName, _writers.size());
//			//Create writer and add it to the array
//			pw = new PrintWriter(new File(_outdir.getAbsolutePath()+"/"+fileName+".csv"));
//			//Write column headers for the new csv
////			pw.println(getHeaders(fileName));
//			_writers.add(pw);
//		}
//		else
//		{
//			pw = _writers.get(_writerMap.get(fileName));
//		}
//		
//		return pw;
//	}
//	
//	public void dataToFile() throws FileNotFoundException
//	{
//		//Nodes
//		if(_nodes != null && _nodes.size() > 0) writeNodes();
//		
//		//Loads
//		if(_loads != null && _loads.size() > 0) writeLoads();
//		
//		//Gens
//		if(_gens != null && _gens.size() > 0) writeGens();
//		
//		//Areas
//		if(_areas != null && _areas.size() > 0) writeAreas();
//		
//		//Lines
//		if(_lines != null && _lines.size() > 0) writeLines();
//		
//		//Tfmrs
//		if(_tfmrs != null && _tfmrs.size() > 0) writeTfmrs();
//		
//		//Phase Shifters
//		if(_phaseShifters != null && _phaseShifters.size() > 0) writePhaseShifters();
//		
//		//Owners
//		if(_owners != null && _owners.size() > 0) writeOwners();
//	}
//	
//	private void writeNodes() throws FileNotFoundException
//	{
//		PrintWriter nodeW = getWriter("Node");
//		nodeW.println(_nodes.get(0).getHeaders());
//		for(int i = 0; i < _nodes.size(); ++i)
//		{
//			nodeW.println(_nodes.get(i).toCsv());
//		}
//	}
//	
//	private void writeLoads() throws FileNotFoundException
//	{
//		PrintWriter loadW = getWriter("Load");
//		PrintWriter loadCaseW = getWriter("PsmCaseLoad");
//		loadW.println(_loads.get(0).getHeaders(PsseLoadTool.LoadFiles.Load));
//		loadCaseW.println(_loads.get(0).getHeaders(PsseLoadTool.LoadFiles.PsmCaseLoad));
//		for(int i = 0; i < _loads.size(); ++i)
//		{
//			loadW.println(_loads.get(i).toCsv(PsseLoadTool.LoadFiles.Load));
//			loadCaseW.println(_loads.get(i).toCsv(PsseLoadTool.LoadFiles.PsmCaseLoad));
//		}
//	}
//	
//	private void writeGens() throws FileNotFoundException
//	{
//		PrintWriter genW = getWriter("GeneratingUnit");
//		PrintWriter genCaseW = getWriter("PsmCaseGeneratingUnit");
//		PrintWriter synchW = getWriter("SynchronousMachine");
//		PrintWriter synchCaseW = getWriter("PsmCaseSynchronousMachine");
//		PrintWriter mvarCrvW = getWriter("ReactiveCapabilityCurve");
//		
//		genW.println(_gens.get(0).getHeaders(PsseGenTool.GenFiles.GeneratingUnit));
//		genCaseW.println(_gens.get(0).getHeaders(PsseGenTool.GenFiles.PsmCaseGeneratingUnit));
//		synchW.println(_gens.get(0).getHeaders(PsseGenTool.GenFiles.SynchronousMachine));
//		synchCaseW.println(_gens.get(0).getHeaders(PsseGenTool.GenFiles.PsmCaseSynchronousMachine));
//		mvarCrvW.println(_gens.get(0).getHeaders(PsseGenTool.GenFiles.ReactiveCapabilityCurve));
//		
//		for(int i = 0; i < _gens.size(); ++i)
//		{
//			genW.println(_gens.get(i).toCsv(PsseGenTool.GenFiles.GeneratingUnit));
//			genCaseW.println(_gens.get(i).toCsv(PsseGenTool.GenFiles.PsmCaseGeneratingUnit));
//			synchW.println(_gens.get(i).toCsv(PsseGenTool.GenFiles.SynchronousMachine));
//			synchCaseW.println(_gens.get(i).toCsv(PsseGenTool.GenFiles.PsmCaseSynchronousMachine));
//			mvarCrvW.println(_gens.get(i).toCsv(PsseGenTool.GenFiles.ReactiveCapabilityCurve));
//		}
//	}
//	
//	private void writeLines() throws FileNotFoundException
//	{
//		PrintWriter lineW = getWriter("Line");
//		lineW.println(_lines.get(0).getHeaders());
//		for(int i = 0; i < _lines.size(); ++i)
//		{
//			lineW.println(_lines.get(i).toCsv(""));
//		}
//	}
//	
//	private void writeAreas() throws FileNotFoundException
//	{
//		PrintWriter areaW = getWriter("ControlArea");
//		areaW.println(_areas.get(0).getHeaders());
//		for(int i = 0; i < _areas.size(); ++i)
//		{
//			areaW.println(_areas.get(i).toCsv());
//		}
//	}
//	
//	private void writeTfmrs() throws FileNotFoundException
//	{
//		PrintWriter tfmrW 		= getWriter("Transformer");
//		PrintWriter wdgW 		= getWriter("TransformerWinding");
//		PrintWriter ratioW 		= getWriter("RatioTapChanger");
//		PrintWriter ratioCaseW 	= getWriter("PsmCaseRatioTapChanger");
//		
//		tfmrW.println(_tfmrs.get(0).getHeaders(PsseTransformerTool.TfmrFiles.Transformer));
//		wdgW.println(_tfmrs.get(0).getHeaders(PsseTransformerTool.TfmrFiles.TransformerWinding));
//		ratioW.println(_tfmrs.get(0).getHeaders(PsseTransformerTool.TfmrFiles.RatioTapChanger));
//		ratioCaseW.println(_tfmrs.get(0).getHeaders(PsseTransformerTool.TfmrFiles.PsmCaseRatioTapChanger));
//		
//		//2 winding transformers
//		for(int i = 0; i < _tfmrs.size(); ++i)
//		{
////			System.out.println("\n"+i+" | "+_tfmrs.size());
//			PsseTransformerTool t = _tfmrs.get(i);
//	
//			if(t != null)
//			{
////				System.out.println(_tfmrs.get(i).toCsv(PsseTransformerTool.TfmrFiles.Transformer));
////				System.out.println(_tfmrs.get(i).toCsv(PsseTransformerTool.TfmrFiles.TransformerWinding));
////				System.out.println(_tfmrs.get(i).toCsv(PsseTransformerTool.TfmrFiles.RatioTapChanger));
////				System.out.println(_tfmrs.get(i).toCsv(PsseTransformerTool.TfmrFiles.PsmCaseRatioTapChanger));
//				
//				tfmrW.println(_tfmrs.get(i).toCsv(PsseTransformerTool.TfmrFiles.Transformer));
//				wdgW.println(_tfmrs.get(i).toCsv(PsseTransformerTool.TfmrFiles.TransformerWinding));
//				ratioW.println(_tfmrs.get(i).toCsv(PsseTransformerTool.TfmrFiles.RatioTapChanger));
//				ratioCaseW.println(_tfmrs.get(i).toCsv(PsseTransformerTool.TfmrFiles.PsmCaseRatioTapChanger));
//			}
//		}
//	}
//	
//	private void writePhaseShifters() throws FileNotFoundException
//=======
//	protected PsseRecWriter getWriter(String psseClassName) 
//>>>>>>> Stashed changes
//	{
//		PsseRecWriter w = _wmap.get(psseClassName);
//		if (w == null)
//		{
//			System.err.format("No writer found for %s\n", psseClassName);
//			w = _defw;
//		}
//		return w;
//	}
//	
//}

//class PssePSMWriter implements PsseRecWriter
//{
//	static final int _invalidOffset = -1;
//	static final String _invalidData = "";
//	
////	private List<PsseBusTool> _nodes;
////	private List<PsseLoadTool> _loads;
////	private List<PsseGenTool> _gens;
////	private List<PsseAreaTool> _areas;
////	private List<PsseLineTool> _lines;
////	private List<PsseTransformerTool> _tfmrs;
////	private List<PssePhaseShifterTool> _phaseShifters;
////	private List<PsseOwnerTool> _owners;
////	private List<PsseSwitchedShuntTool> _shunts;
////	private List<PsseSvcTool> _svcs;
//	
//	private TObjectIntMap<String> _writerMap;
//	private List<PrintWriter> _writers;
//	private File _outdir;
//
//	public PssePSMWriter(File outdir) 
//	{
//		_outdir = outdir;
//	}
//
//	@Override
//	public void writeRecord(PsseClass pclass, String[] record)
//			throws PsseProcException 
//	{	
//		List<PsseField[]> lines = pclass.getLines();
//		
//		switch(pclass.getClassName().toLowerCase())
//		{
//		case "bus":
//			if(_nodes == null) _nodes = new ArrayList<>();
//			_nodes.add(new PsseBusTool(lines.get(0), record));
//			break;
//		case "load":
//			if(_loads == null) _loads = new ArrayList<>();
//			_loads.add(new PsseLoadTool(lines.get(0), record));
//			break;
//		case "generator":
//			if(_gens == null) _gens = new ArrayList<>();
//			_gens.add(new PsseGenTool(lines.get(0),record));
//			break;
//		case "areainterchange":
//			if(_areas == null) _areas = new ArrayList<>();
//			_areas.add(new PsseAreaTool(lines.get(0), record));
//			break;
//		case "transformer":
//			switch(PsseTransformerTool.getTfmrType(lines, record))
//			{
//			case PhaseShifter:
//				if(_phaseShifters == null) _phaseShifters = new ArrayList<>();
//				_phaseShifters.add(new PssePhaseShifterTool(lines,record));
//				break;
//			case ThreeWinding:
//				if(_nodes == null) _nodes = new ArrayList<>();
//				if(_tfmrs == null) _tfmrs = new ArrayList<>();
//				
//				Psse3WdgTool tfmr = new Psse3WdgTool(lines,record);
//				_tfmrs.add(tfmr.getTfmr1());
//				_tfmrs.add(tfmr.getTfmr2());
//				_tfmrs.add(tfmr.getTfmr3());
//				_nodes.add(tfmr.getNode());
//				
//				break;
//			case TwoWinding:
//				if(_tfmrs == null) _tfmrs = new ArrayList<>();
//				_tfmrs.add(new PsseTransformerTool(lines,record));
//				break;
//			}
//			break;
//		case "nontransformerbranch":
//			if(_lines == null) _lines = new ArrayList<>();
//			_lines.add(new PsseLineTool(lines.get(0),record));
//			break;
//		case "switchedshunt":
//			switch(PsseSwitchedShuntTool.getShuntType(lines.get(0), record))
//			{
//			case SVC:
//				if(_svcs == null) _svcs = new ArrayList<>();
//				_svcs.add(new PsseSvcTool(lines.get(0),record));
//				break;
//			case SwitchedShunt:
//				if(_shunts == null) _shunts = new ArrayList<>();
//				_shunts.add(new PsseSwitchedShuntTool(lines.get(0),record));
//				break;
//			}
//			break;
//		case "owner":
//			if(_owners == null) _owners = new ArrayList<>();
//			_owners.add(new PsseOwnerTool(lines.get(0),record));
//			break;
//		default:
////				System.out.println("[writeRecord] No processor for "+pclass.getClassName().toLowerCase());	
//		}
//	}
//	
//	@Override
//	public void cleanup() 
//	{
//		for(int i = 0; i < _writers.size(); ++i)
//		{
//			_writers.get(i).close();
//		}
//	}
//	
//	private PrintWriter getWriter(String fileName) throws FileNotFoundException
//	{
//		PrintWriter pw;
//		
//		//Create the writer list if it doesn't exist yet
//		if (_writers == null) _writers = new ArrayList<>();
//		
//		//Create the writer map if it doesn't exist yet
//		if (_writerMap == null) _writerMap = new TObjectIntHashMap<>(5, 1f, -1);
//		
//		//check if the requested writer exists
//		if (_writerMap.get(fileName) == _writerMap.getNoEntryValue())
//		{
//			//Add to the map
//			_writerMap.put(fileName, _writers.size());
//			//Create writer and add it to the array
//			pw = new PrintWriter(new File(_outdir.getAbsolutePath()+"/"+fileName+".csv"));
//			//Write column headers for the new csv
////			pw.println(getHeaders(fileName));
//			_writers.add(pw);
//		}
//		else
//		{
//			pw = _writers.get(_writerMap.get(fileName));
//		}
//		
//		return pw;
//	}
//	
//	public void dataToFile() throws FileNotFoundException
//	{
//		//Nodes
//		if(_nodes != null && _nodes.size() > 0) writeNodes();
//		
//		//Loads
//		if(_loads != null && _loads.size() > 0) writeLoads();
//		
//		//Gens
//		if(_gens != null && _gens.size() > 0) writeGens();
//		
//		//Areas
//		if(_areas != null && _areas.size() > 0) writeAreas();
//		
//		//Lines
//		if(_lines != null && _lines.size() > 0) writeLines();
//		
//		//Tfmrs
//		if(_tfmrs != null && _tfmrs.size() > 0) writeTfmrs();
//		
//		//Phase Shifters
//		if(_phaseShifters != null && _phaseShifters.size() > 0) writePhaseShifters();
//		
//		//Owners
//		if(_owners != null && _owners.size() > 0) writeOwners();
//	}
//	
//	private void writeNodes() throws FileNotFoundException
//	{
//		PrintWriter nodeW = getWriter("Node");
//		nodeW.println(_nodes.get(0).getHeaders());
//		for(int i = 0; i < _nodes.size(); ++i)
//		{
//			nodeW.println(_nodes.get(i).toCsv());
//		}
//	}
//	
//	private void writeLoads() throws FileNotFoundException
//	{
//		PrintWriter loadW = getWriter("Load");
//		PrintWriter loadCaseW = getWriter("PsmCaseLoad");
//		loadW.println(_loads.get(0).getHeaders(PsseLoadTool.LoadFiles.Load));
//		loadCaseW.println(_loads.get(0).getHeaders(PsseLoadTool.LoadFiles.PsmCaseLoad));
//		for(int i = 0; i < _loads.size(); ++i)
//		{
//			loadW.println(_loads.get(i).toCsv(PsseLoadTool.LoadFiles.Load));
//			loadCaseW.println(_loads.get(i).toCsv(PsseLoadTool.LoadFiles.PsmCaseLoad));
//		}
//	}
//	
//	private void writeGens() throws FileNotFoundException
//	{
//		PrintWriter genW = getWriter("GeneratingUnit");
//		PrintWriter genCaseW = getWriter("PsmCaseGeneratingUnit");
//		PrintWriter synchW = getWriter("SynchronousMachine");
//		PrintWriter synchCaseW = getWriter("PsmCaseSynchronousMachine");
//		PrintWriter mvarcapW = getWriter("ReactiveCapabilityCurve");
//		
//		genW.println(_gens.get(0).getHeaders(PsseGenTool.GenFiles.GeneratingUnit));
//		genCaseW.println(_gens.get(0).getHeaders(PsseGenTool.GenFiles.PsmCaseGeneratingUnit));
//		synchW.println(_gens.get(0).getHeaders(PsseGenTool.GenFiles.SynchronousMachine));
//		synchCaseW.println(_gens.get(0).getHeaders(PsseGenTool.GenFiles.PsmCaseSynchronousMachine));
//		mvarcapW.println(_gens.get(0).getHeaders(PsseGenTool.GenFiles.ReactiveCapabilityCurve));
//		
//		for(int i = 0; i < _gens.size(); ++i)
//		{
//			genW.println(_gens.get(i).toCsv(PsseGenTool.GenFiles.GeneratingUnit));
//			genCaseW.println(_gens.get(i).toCsv(PsseGenTool.GenFiles.PsmCaseGeneratingUnit));
//			synchW.println(_gens.get(i).toCsv(PsseGenTool.GenFiles.SynchronousMachine));
//			synchCaseW.println(_gens.get(i).toCsv(PsseGenTool.GenFiles.PsmCaseSynchronousMachine));
//			mvarcapW.println(_gens.get(i).toCsv(PsseGenTool.GenFiles.ReactiveCapabilityCurve));
//		}
//	}
//	
//	private void writeLines() throws FileNotFoundException
//	{
//		PrintWriter lineW = getWriter("Line");
//		lineW.println(_lines.get(0).getHeaders());
//		for(int i = 0; i < _lines.size(); ++i)
//		{
//			lineW.println(_lines.get(i).toCsv(""));
//		}
//	}
//	
//	private void writeAreas() throws FileNotFoundException
//	{
//		PrintWriter areaW = getWriter("ControlArea");
//		areaW.println(_areas.get(0).getHeaders());
//		for(int i = 0; i < _areas.size(); ++i)
//		{
//			areaW.println(_areas.get(i).toCsv());
//		}
//	}
//	
//	private void writeTfmrs() throws FileNotFoundException
//	{
//		PrintWriter tfmrW 		= getWriter("Transformer");
//		PrintWriter wdgW 		= getWriter("TransformerWinding");
//		PrintWriter ratioW 		= getWriter("RatioTapChanger");
//		PrintWriter ratioCaseW 	= getWriter("PsmCaseRatioTapChanger");
//		
//		tfmrW.println(_tfmrs.get(0).getHeaders(PsseTransformerTool.TfmrFiles.Transformer));
//		wdgW.println(_tfmrs.get(0).getHeaders(PsseTransformerTool.TfmrFiles.TransformerWinding));
//		ratioW.println(_tfmrs.get(0).getHeaders(PsseTransformerTool.TfmrFiles.RatioTapChanger));
//		ratioCaseW.println(_tfmrs.get(0).getHeaders(PsseTransformerTool.TfmrFiles.PsmCaseRatioTapChanger));
//		
//		//2 winding transformers
//		for(int i = 0; i < _tfmrs.size(); ++i)
//		{
////			System.out.println("\n"+i+" | "+_tfmrs.size());
//			PsseTransformerTool t = _tfmrs.get(i);
//	
//			if(t != null)
//			{
////				System.out.println(_tfmrs.get(i).toCsv(PsseTransformerTool.TfmrFiles.Transformer));
////				System.out.println(_tfmrs.get(i).toCsv(PsseTransformerTool.TfmrFiles.TransformerWinding));
////				System.out.println(_tfmrs.get(i).toCsv(PsseTransformerTool.TfmrFiles.RatioTapChanger));
////				System.out.println(_tfmrs.get(i).toCsv(PsseTransformerTool.TfmrFiles.PsmCaseRatioTapChanger));
//				
//				tfmrW.println(_tfmrs.get(i).toCsv(PsseTransformerTool.TfmrFiles.Transformer));
//				wdgW.println(_tfmrs.get(i).toCsv(PsseTransformerTool.TfmrFiles.TransformerWinding));
//				ratioW.println(_tfmrs.get(i).toCsv(PsseTransformerTool.TfmrFiles.RatioTapChanger));
//				ratioCaseW.println(_tfmrs.get(i).toCsv(PsseTransformerTool.TfmrFiles.PsmCaseRatioTapChanger));
//			}
//		}
//	}
//	
//	private void writePhaseShifters() throws FileNotFoundException
//	{
//		PrintWriter phaseW 		= getWriter("PhaseTapChanger");
//		PrintWriter phaseCaseW 	= getWriter("PsmCasePhaseTapChanger");
//		PrintWriter tfmrW 		= getWriter("Transformer");
//		PrintWriter wdgW 		= getWriter("TransformerWinding");
//		
//		phaseW.println(_phaseShifters.get(0).getHeaders(PssePhaseShifterTool.PhaseFiles.PhaseTapChanger));
//		phaseCaseW.println(_phaseShifters.get(0).getHeaders(PssePhaseShifterTool.PhaseFiles.PsmCasePhaseTapChanger));
//		
//		//Phase shifters
//		for(int i = 0; i < _phaseShifters.size(); ++i)
//		{
//			tfmrW.println(_phaseShifters.get(i).toCsv(PssePhaseShifterTool.PhaseFiles.Transformer));
//			wdgW.println(_phaseShifters.get(i).toCsv(PssePhaseShifterTool.PhaseFiles.TransformerWinding));
//			phaseW.println(_phaseShifters.get(i).toCsv(PssePhaseShifterTool.PhaseFiles.PhaseTapChanger));
//			phaseCaseW.println(_phaseShifters.get(i).toCsv(PssePhaseShifterTool.PhaseFiles.PsmCasePhaseTapChanger));
//		}
//	}
//	
//	private void writeOwners() throws FileNotFoundException
//	{
//		PrintWriter ownerW = getWriter("Organization");
//		ownerW.println(_owners.get(0).getHeaders());
//		for(int i = 0; i < _owners.size(); ++i)
//		{
//			ownerW.println(_owners.get(i).toCSV());
//		}
//	}
//}
