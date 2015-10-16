package com.powerdata.openpa.psseraw;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Keep track of some data elements to be used by multiple classes
 * 
 * @author chris@powerdata.com
 *
 */
public class PsseRepository
{
	public enum PsmFormat
	{
		ControlArea,
		CurrentRelay,
		FrequencyRelay,
		GeneratingUnit,
		Line,
		Load,
		LoadArea,
		ModelParameters,
		Node,
		Organization,
		PhaseTapChanger,
		RatioTapChanger,
		PrimeMover,
		ReactiveCapabilityCurve,
		RelayOperate,
		SeriesCapacitor,
		SeriesReactor,
		ShuntCapacitor,
		ShuntReactor,
		Substation,
		SynchronousMachine,
		SVC,
		Switch,
		SwitchType,
		Transformer,
		TransformerWinding,
		VoltageRelay;
	}
	
	public enum CaseFormat
	{
		Parameter,
		ControlArea,
		GeneratingUnit,
		LoadAreaCurve,
		Load,
		Line,
		Node,
		RatioTapChanger,
		PhaseTapChanger,
		SeriesCapacitor,
		SeriesReactor,
		ShuntCapacitor,
		ShuntReactor,
		SVC,
		Switch,
		SynchronousMachine,
		TransformerWinding;
	}
	
	static Map<PsmFormat,String> _WhdrMap = new EnumMap<>(PsmFormat.class);
	static Map<CaseFormat,String> _ChdrMap = new EnumMap<>(CaseFormat.class);
	
	
	static
	{
		_WhdrMap.put(PsmFormat.ControlArea, "ID,Name");
		_ChdrMap.put(CaseFormat.ControlArea, "ID,NetInterchange");

		_WhdrMap.put(PsmFormat.Load, "ID,Name,Node");
		_ChdrMap.put(CaseFormat.Load, "ID,MW,MVAr,InService");

		_WhdrMap.put(PsmFormat.Node, "ID,Name,NominalKV,Organization,ControlArea,IsBusBarSection");
		_ChdrMap.put(CaseFormat.Node, "ID,Ang,Mag");

		_WhdrMap.put(PsmFormat.Organization, "ID,Name");
		
		String shdr = "ID,Name,Node,MVAr,HasRegulator,RegulatedNode,MinKV,MaxKV";
		String schdr = "ID,Enabled,InService";
		_WhdrMap.put(PsmFormat.ShuntReactor, shdr);
		_ChdrMap.put(CaseFormat.ShuntReactor, schdr);
		
		_WhdrMap.put(PsmFormat.ShuntCapacitor, shdr);
		_ChdrMap.put(CaseFormat.ShuntCapacitor, schdr);
		
		_WhdrMap.put(PsmFormat.GeneratingUnit, "ID,Name,MinOperatingMW,MaxOperatingMW");
		_ChdrMap.put(CaseFormat.GeneratingUnit, "ID,MW,InService");
		
		_WhdrMap.put(PsmFormat.SynchronousMachine, "ID,Name,Node,GeneratingUnit,RegulatedNode,R,X");
		_ChdrMap.put(CaseFormat.SynchronousMachine, "ID,AVRMode,KVSetPoint,MVArSetpoint,MVAr,InService");
		
		_WhdrMap.put(PsmFormat.ReactiveCapabilityCurve, "ID,SynchronousMachine,MW,MinMVAr,MaxMVAr");
		
		_WhdrMap.put(PsmFormat.Line, "ID,Name,Circuit,Node1,Node2,R,X,Bch,NormalOperatingLimit,ShortTermLimit,EmergencyLimit");
		_ChdrMap.put(CaseFormat.Line, "ID,InService");
		
		_WhdrMap.put(PsmFormat.Transformer, "ID,Name,Circuit,WindingCount");
		
		_WhdrMap.put(PsmFormat.TransformerWinding, 
			"ID,Name,Transformer,Node1,Node2,R,X,Gmag,Bmag,PhaseShift,NormalOperatingLimit");
		_ChdrMap.put(CaseFormat.TransformerWinding, "ID,InService");
		
		_WhdrMap.put(PsmFormat.RatioTapChanger, "ID,Name,TransformerWinding,TapNode," +
			"NeutralKV,MinRatio,MaxRatio,TapPositions,IsRegulating,MinKV,MaxKV," +
			"RegulatedNode");
		
		_ChdrMap.put(CaseFormat.RatioTapChanger, "ID,LTCEnable,Ratio");
		
		_WhdrMap.put(PsmFormat.PhaseTapChanger, "ID,Name,TransformerWinding,TapNode," +
			"NeutralKV,MinAng,MaxAng,TapPositions,IsRegulating,MinRegMW,MaxRegMW");
		
		_ChdrMap.put(CaseFormat.PhaseTapChanger, "ID,ControlStatus,PhaseShift");
	}

	static public class BusInfo
	{
		static public BusInfo EMPTY = new BusInfo("", "", -1f, "", "");
		String _id, _name;
		float _baskv;
		String _own;
		String _area;
		BusInfo(String id, String name, float baskv, String areaid, String ownid)
		{
			_id = id;
			_name = name;
			_baskv = baskv;
			_area = areaid;
			_own = ownid;
		}
		
		public String getId() {return _id;}
		public String getName() {return _name;}
		public float getBaskv() {return _baskv;}
		public String getArea() {return _area;}
		public String getOwner() {return _own;}
	}
	
	Map<String,BusInfo> _busInfo = new HashMap<>();

	
	Map<PsmFormat,PrintWriter> _wrtrmap = new EnumMap<>(PsmFormat.class);
	Map<CaseFormat,PrintWriter> _casewmap = new EnumMap<>(CaseFormat.class);
	Map<PsmFormat,String> _hdrmap = new EnumMap<>(PsmFormat.class);
	
	private File _dir;
	
	public PsseRepository(File dir)
	{
		_dir = dir;
	}
	
	public File getDir() {return _dir;}
	
	Function<PsmFormat,PrintWriter> _psmwrtr = i ->
	{
		PrintWriter pw = null; 
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(
				new File(_dir, String.format("%s.csv",i.toString())))));
			pw.println(_WhdrMap.get(i));
		} catch (IOException ioe) {ioe.printStackTrace(); return null;}
		return pw;
	};
	
	Function<CaseFormat,PrintWriter> _casewrtr = i ->
	{
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(
				new File(_dir, String.format("PsmCase%s.csv",i.toString())))));
			pw.println(_ChdrMap.get(i));
		} catch (IOException ioe) {ioe.printStackTrace(); return null;}
		return pw;
	};
	
	Consumer<PrintWriter> _cleaner = i -> {i.flush(); i.close();};

	public PrintWriter findWriter(PsmFormat f) {return _wrtrmap.computeIfAbsent(f, _psmwrtr);}
	public PrintWriter findWriter(CaseFormat f) {return _casewmap.computeIfAbsent(f, _casewrtr);}
	
	
	public void cleanup()
	{
		_wrtrmap.values().forEach(_cleaner);
		_casewmap.values().forEach(_cleaner);
	}
	
	public void mapBusInfo(String id, String name, float baskv, String ownid, String areaid)
	{
		_busInfo.put(id, new BusInfo(id, name, baskv, ownid, areaid));
	}
	
	public String getBusName(String id)
	{
		return _busInfo.getOrDefault(id, BusInfo.EMPTY).getName();
	}
	public BusInfo getBusInfo(String id)
	{
		return _busInfo.getOrDefault(id, BusInfo.EMPTY);
	}
}
