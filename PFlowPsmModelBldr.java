package com.powerdata.openpa;


import gnu.trove.map.TFloatIntMap;
import gnu.trove.map.TIntFloatMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TFloatIntHashMap;
import gnu.trove.map.hash.TIntFloatHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.powerdata.openpa.Gen.Type;
import com.powerdata.openpa.tools.QueryString;
import com.powerdata.openpa.tools.SimpleCSV;
import com.powerdata.openpa.impl.AreaListI;
import com.powerdata.openpa.impl.BusListI;
import com.powerdata.openpa.impl.GenListI;
import com.powerdata.openpa.impl.IslandListI;
import com.powerdata.openpa.impl.LineListI;
import com.powerdata.openpa.impl.LoadListI;
import com.powerdata.openpa.impl.OwnerListI;
import com.powerdata.openpa.impl.PhaseShifterListI;
import com.powerdata.openpa.impl.SVCListI;
import com.powerdata.openpa.impl.SeriesCapListI;
import com.powerdata.openpa.impl.SeriesReacListI;
import com.powerdata.openpa.impl.ShuntCapListI;
import com.powerdata.openpa.impl.ShuntReacListI;
import com.powerdata.openpa.impl.StationListI;
import com.powerdata.openpa.impl.SwitchListI;
import com.powerdata.openpa.impl.SwitchedShuntListI;
import com.powerdata.openpa.impl.TransformerListI;
import com.powerdata.openpa.impl.TwoTermDCLineListI;
import com.powerdata.openpa.impl.VoltageLevelListI;

public class PFlowPsmModelBldr extends PflowModelBuilder 
{
	File _dir;
	
	//CSV files
	SimpleCSV _busCSV;
	SimpleCSV _switchCSV;
	SimpleCSV _lineCSV;
	SimpleCSV _areaCSV;
	SimpleCSV _substationCSV;
	SimpleCSV _svcCSV;
	SimpleCSV _shuntCapCSV;
	SimpleCSV _shuntReacCSV;
	SimpleCSV _loadCSV;
	SimpleCSV _genCSV;
	SimpleCSV _seriesCapCSV;
	SimpleCSV _seriesReacCSV;
	SimpleCSV _transformerCSV;
	SimpleCSV _switchTypeCSV;
	SimpleCSV _tfmrWindingCSV;
	SimpleCSV _phaseTapChgCSV;
	SimpleCSV _synchMachineCSV;
	SimpleCSV _ratioTapChgCSV;
	
	//Case CSV files
	SimpleCSV _loadCaseCSV;
	SimpleCSV _shuntCapCaseCSV;
	SimpleCSV _shuntReacCaseCSV;
	SimpleCSV _genCaseCSV;
	SimpleCSV _synchCaseCSV;
	SimpleCSV _genToSynchCSV;
	SimpleCSV _ratioTapChgCaseCSV;
	SimpleCSV _phaseTapChgCaseCSV;
	SimpleCSV _switchCaseCSV;
	SimpleCSV _lineCaseCSV;
	SimpleCSV _windingCaseCSV;
	SimpleCSV _svcCaseCSV;
	
	
	//Not yet importing
	SimpleCSV _voltageRelayCSV;
	SimpleCSV _curRelayCSV;
	SimpleCSV _freqRelayCSV;
	SimpleCSV _loadAreaCSV;
	SimpleCSV _modelParmsCSV;
	SimpleCSV _orgCSV;
	SimpleCSV _primeMoverCSV;
	SimpleCSV _reacCapCurveCSV;
	SimpleCSV _relayOperateCSV;
	
	//Hashmaps
	TObjectIntMap<String> _loadMap;
	TObjectIntMap<String> _shuntReacMap;
	TObjectIntMap<String> _shuntCapMap;
	TObjectIntMap<String> _genMap;
	TObjectIntMap<String> _synchMap;
	TObjectIntMap<String> _genToSynchMap;
	TObjectIntMap<String> _genToSynchCaseMap;
	TFloatIntMap _vlevMap;
	TObjectIntMap<String> _areaMap;
	TObjectIntMap<String> _tfmrRatioTapMap;
	TObjectIntMap<String> _tfmrPhaseTapMap;
	TObjectIntMap<String> _transformerMap;
	TObjectIntMap<String> _windingMap;
	TObjectIntMap<String> _wdgInPhaseMap;
	TObjectIntMap<String> _wdgInRatioMap;
	TObjectIntMap<String> _wdgToTfmrMap;
	TObjectIntMap<String> _switchCaseMap;
	TObjectIntMap<String> _lineMap;
	TObjectIntMap<String> _windingCaseMap;
	TObjectIntMap<String> _stationOffsetMap;
	TObjectIntMap<String> _svcCaseMap;
	
	//Arrays / Lists
	int[] _busAreaIndex;
	int[] _busStationIndex;
	float[] _vlevFloat;
	List<String> _transformerIDs;
	List<String> _phaseShifterIDs;
	
	public PFlowPsmModelBldr(String parms) throws PAModelException
	{
		QueryString q = new QueryString(parms);
		if(!q.containsKey("dir")) throw new PAModelException("Missing dir= in query string.");
		String basedb = q.get("dir")[0];
		
		_dir = new File(basedb);
		if(!_dir.exists())
		{
			_dir.mkdirs();
		}
	}
	
	public PFlowPsmModelBldr(File dir)
	{
		_dir = dir;
	}
	
	@Override
	protected void loadPrep() 
	{
		//Load prep does nothing, yet
		//This is where PD3 calls columnPrep
	}

	@Override
	protected BusListI loadBuses() throws PAModelException 
	{
		try 
		{
			if(_busCSV == null) _busCSV = new SimpleCSV(new File(_dir, "Node.csv"));
			return new BusListI(_m, _busCSV.getRowCount());
		} 
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected SwitchListI loadSwitches() throws PAModelException 
	{
		try 
		{
			_switchCSV = new SimpleCSV(new File(_dir, "Switch.csv"));
			_switchTypeCSV = new SimpleCSV(new File(_dir, "SwitchType.csv"));
			_switchCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseSwitch.csv"));
			return new SwitchListI(_m, _switchCSV.getRowCount());
		} 
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected LineListI loadLines() throws PAModelException 
	{
		try
		{
			_lineCSV = new SimpleCSV(new File(_dir, "Line.csv"));
			_lineCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseLine.csv"));
			return new LineListI(_m, _lineCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected AreaListI loadAreas() throws PAModelException 
	{
		//TODO Incomplete
		//Don't currently have all the necessary data
		try
		{
			_areaCSV = new SimpleCSV(new File(_dir, "ControlArea.csv"));
			//AreaListI(PAModelI model, int[] busref, int narea)
			if(_busAreaIndex == null) buildBusAreaIndex();
			return new AreaListI(_m, _busAreaIndex, _areaCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected OwnerListI loadOwners() throws PAModelException 
	{
		// TODO Incomplete
		// Couldn't find a csv for this one in the document
		return OwnerListI.Empty;
	}

	@Override
	protected StationListI loadStations() throws PAModelException 
	{
		//TODO Incomplete
		//Don't currently have all the necessary data
		try
		{
			_substationCSV = new SimpleCSV(new File(_dir, "Substation.csv"));
			if(_busStationIndex == null) buildBusStationIndex();
			return new StationListI(_m, _busStationIndex, _busStationIndex.length);
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected VoltageLevelListI loadVoltageLevels() throws PAModelException 
	{
		if(_busCSV == null) 
		{
//			System.out.println("[loadVoltageLevels] _busCSV is null");
			loadBuses();
		}

		//(PAModelI model, int[] busref, int nvl)
		
		if(_vlevFloat == null || _vlevMap == null) buildVlev();
		
		return new VoltageLevelListI(_m, getBusVlev(), _vlevMap.size());
	}

	@Override
	protected IslandList loadIslands() throws PAModelException 
	{
		// TODO Incomplete
		
		IslandList islands = new IslandListI(_m);
		
		
		return islands;
	}

	@Override
	protected SVCListI loadSVCs() throws PAModelException 
	{
		try
		{
			_svcCSV = new SimpleCSV(new File(_dir, "SVC.csv"));
			_svcCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseSVC.csv"));
			return new SVCListI(_m, _svcCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected SwitchedShuntListI loadSwitchedShunts() throws PAModelException 
	{
		// TODO Incomplete
		// Didn't see a csv in doc
		// PD3 builder returns an empty list
		
		return SwitchedShuntListI.Empty;
	}

	@Override
	protected TwoTermDCLineListI loadTwoTermDCLines() throws PAModelException 
	{
		// TODO Incomplete
		// Didn't see a csv in doc
		// PD3 builder returns an empty list
		return TwoTermDCLineListI.Empty;
	}

	@Override
	protected ShuntCapListI loadShuntCapacitors() throws PAModelException 
	{
		try
		{
			_shuntCapCSV = new SimpleCSV(new File(_dir, "ShuntCapacitor.csv"));
			_shuntCapCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseShuntCapacitor.csv"));
			return new ShuntCapListI(_m, _shuntCapCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected ShuntReacListI loadShuntReactors() throws PAModelException 
	{
		try
		{
			_shuntReacCSV = new SimpleCSV(new File(_dir, "ShuntReactor.csv"));
			_shuntReacCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseShuntReactor.csv"));
			return new ShuntReacListI(_m, _shuntReacCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected LoadListI loadLoads() throws PAModelException 
	{
		try
		{
			_loadCSV = new SimpleCSV(new File(_dir, "Load.csv"));
			_loadCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseLoad.csv"));
			return new LoadListI(_m, _loadCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected GenListI loadGens() throws PAModelException 
	{
		try
		{
			_genCSV = new SimpleCSV(new File(_dir, "GeneratingUnit.csv"));
			_genCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseGeneratingUnit.csv"));
			_synchMachineCSV = new SimpleCSV(new File(_dir, "SynchronousMachine.csv"));
			_synchCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseSynchronousMachine.csv"));
			return new GenListI(_m, _genCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected SeriesCapListI loadSeriesCapacitors() throws PAModelException 
	{
		try
		{
			_seriesReacCSV = new SimpleCSV(new File(_dir, "SeriesCapacitor.csv"));
			return new SeriesCapListI(_m, _seriesReacCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected SeriesReacListI loadSeriesReactors() throws PAModelException 
	{
		try
		{
			_seriesReacCSV = new SimpleCSV(new File(_dir, "SeriesReactor.csv"));
			return new SeriesReacListI(_m, _seriesReacCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected PhaseShifterListI loadPhaseShifters() throws PAModelException 
	{
		try
		{
			if(_phaseTapChgCSV == null)
			{
				_transformerCSV = new SimpleCSV(new File(_dir, "Transformer.csv"));
				_tfmrWindingCSV = new SimpleCSV(new File(_dir, "TransformerWinding.csv"));
				_ratioTapChgCSV = new SimpleCSV(new File(_dir, "RatioTapChanger.csv"));
				_ratioTapChgCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseRatioTapChanger.csv"));
				_phaseTapChgCSV = new SimpleCSV(new File(_dir, "PhaseTapChanger.csv"));
			}
			if(_transformerMap == null) buildTransformerMaps();
			return new PhaseShifterListI(_m, _phaseShifterIDs.size());
		}
		catch (IOException e)
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected TransformerListI loadTransformers() throws PAModelException 
	{
		try
		{
			if(_transformerCSV == null)
			{
				_transformerCSV = new SimpleCSV(new File(_dir, "Transformer.csv"));
				_tfmrWindingCSV = new SimpleCSV(new File(_dir, "TransformerWinding.csv"));
				_ratioTapChgCSV = new SimpleCSV(new File(_dir, "RatioTapChanger.csv"));
				_ratioTapChgCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseRatioTapChanger.csv"));
				_phaseTapChgCSV = new SimpleCSV(new File(_dir, "PhaseTapChanger.csv"));
				_windingCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseTransformerWinding.csv"));
			}
			
			
			if(_transformerMap == null) buildTransformerMaps();
			return new TransformerListI(_m, _transformerIDs.size());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	protected <R> R load(ListMetaType ltype, ColumnMeta ctype, int[] keys)
			throws PAModelException 
	{
		// TODO In progress
		
		switch(ctype)
		{
		//Bus
		case BusID:
			return (R)_busCSV.get("ID");
		case BusNAME:
			return (R)_busCSV.get("Name");
		case BusVM: //Returns float
		case BusVA:	//Returns float
			return (R) returnZero(_busCSV.getRowCount());
		case BusFREQSRCPRI:
			return (R) _busCSV.getInts("FrequencySourcePriority");
		case BusAREA:
			return (R) _busAreaIndex;
		case BusOWNER:
			return null;
		case BusSTATION:
			if(_busStationIndex == null) buildBusStationIndex();
			return (R) _busStationIndex;
		case BusVLEV:
			return (R) getBusVlev();
		//Gen
		case GenID:
			return (R) _genCSV.get("ID");
		case GenNAME:
			return (R) _genCSV.get("Name");
		case GenBUS:
			return (R) getBusesById(getGenDataString("Node", "synch"));
		case GenP:
			return (R) getGenDataFloat("MW", "gencase");
		case GenQ:
			return (R) getGenDataFloat("MVAr", "synchcase");
		case GenOOS:
			return (R) returnFalse(_genCSV.getRowCount());
		case GenTYPE:
			String[] genList = _genCSV.get("GeneratingUnitType");
			Type[] genType = new Type[genList.length];
			for(int i = 0; i < genList.length; i++)
			{
				genType[i] = Type.valueOf(genList[i]);
			}
			return (R) genType;
		case GenMODE:
			return (R) getGenMode();
		case GenOPMINP:
			return (R) _genCSV.getFloats("MinOperatingMW");
		case GenOPMAXP:
			return (R) _genCSV.getFloats("MaxOperatingMW");
		case GenMINQ:
		case GenMAXQ:
			return (R) returnZero(_genCSV.getRowCount());
		case GenPS:
			return (R) getGenDataFloat("MWSetPoint", "gencase");
		case GenQS:
			return (R) getGenDataFloat("MVArSetpoint", "synchcase");
		case GenAVR: 
			return (R) getAVRMode();
		case GenVS:
			return (R) getGenDataFloat("KVSetPoint", "synchcase");
		case GenREGBUS:
			return (R) getGenRegBus();
		//Load
		case LoadID:
			return	(R) _loadCSV.get("ID");
		case LoadNAME:
			return	(R) _loadCSV.get("Name");
		case LoadBUS:
			return (R) getBusesById(_loadCSV.get("Node"));
		case LoadP:
			return (R) getLoadCaseData("MW");
		case LoadQ:
			return (R) getLoadCaseData("MVAr");
		case LoadOOS:
			return (R) returnFalse(_loadCSV.getRowCount());
		case LoadPMAX:
			return (R) _m.getLoads().getP();
		case LoadQMAX:
			return (R) _m.getLoads().getQ();
		//Shunt Capacitor
		case ShcapID:
			return (R) _shuntCapCSV.get("ID");
		case ShcapNAME:
			return (R) _shuntCapCSV.get("Name");
		case ShcapBUS: 
			return (R) getBusesById(_shuntCapCSV.get("Node"));
		case ShcapP:
			return (R) getShuntCapData("MW");
		case ShcapQ:
			return (R) getShuntCapData("MVAr");
		case ShcapOOS:
			return (R) returnFalse(_shuntCapCSV.getRowCount());
		case ShcapB:
			return (R) _shuntCapCSV.get("MVAr");
		//Shunt Reactor
		case ShreacID:
			return (R) _shuntReacCSV.get("ID");
		case ShreacNAME:
			return (R) _shuntReacCSV.get("Name");
		case ShreacBUS:
			return (R) getBusesById(_shuntReacCSV.get("Node"));
		case ShreacP:
			return (R) getShuntReacData("MW");
		case ShreacQ:
			return (R) getShuntReacData("MVAr");
		case ShreacOOS:
			return (R) returnFalse(_shuntReacCSV.getRowCount());
		case ShreacB:
			return (R) _shuntReacCSV.getFloats("MVAr");
		//SVC
		case SvcID:
			return (R) _svcCSV.get("ID");
		case SvcNAME:
			return (R) _svcCSV.get("Name");
		case SvcBUS:
			return (R) getBusesById(_svcCSV.get("Node"));
		case SvcP:
		case SvcQ:
			return (R) returnZero(_svcCSV.getRowCount());
		case SvcOOS:
			return (R) returnFalse(_svcCSV.getRowCount());
		case SvcQS:
			return (R) getSVCData("MVArSetpoint");
		case SvcQMIN:
			return (R) _svcCSV.getFloats("MinMVAr");
		case SvcQMAX:
			return (R) _svcCSV.getFloats("MaxMVAr");
		case SvcAVR:
			return (R) getSVCData("VoltageSetpoint");
		case SvcVS:
		case SvcSLOPE: // float
			return (R) _svcCSV.getFloats("Slope");
		case SvcREGBUS:
			return (R) getBusesById(_svcCSV.get("Node"));
		case SvcOMODE:
			return null;
		//Switched Shunt
		case SwshID:
		case SwshNAME:
		case SwshP:
		case SwshQ:
		case SwshOOS:
		case SwshB:
			return null;
		//Area
		case AreaID:
			return (R) _areaCSV.get("ID");
		case AreaNAME:
			return (R) _areaCSV.get("Name");
		//Owner - No csv
		case OwnerID:
		case OwnerNAME:
			return null;
		//Island - No csv
		case IslandID:
		case IslandNAME:
			String[] ids = new String[_m.getIslands().size()];
			for(int i = 0; i < ids.length; ++i) { ids[i] = ""+i; }
			return (R) ids;
		case IslandFREQ:
			return (R) returnZero(_m.getIslands().size());
		case IslandEGZSTATE:
			return null;
		//Station
		case StationID:
			return (R) _substationCSV.get("ID");
		case StationNAME:
			return (R) _substationCSV.get("Name");
		//Voltage Level - No csv
		case VlevID:
			return (R) returnAsString(_vlevMap.keys());
		case VlevNAME:
			return (R) returnAsString(_vlevMap.keys());
		case VlevBASKV:
			return (R) _vlevMap.keys();
		//Line
		case LineID:
			return (R) _lineCSV.get("ID");
		case LineNAME:
			return (R) _lineCSV.get("Name");
		case LineBUSFROM:
			return (R) getBusesById(_lineCSV.get("Node1"));
		case LineBUSTO:
			return (R) getBusesById(_lineCSV.get("Node2"));
		case LineOOS:
			return (R) returnFalse(_lineCSV.getRowCount());
		case LinePFROM:
			return (R) getLineCaseData("FromMW");
		case LineQFROM:
			return (R) getLineCaseData("FromMVAr");
		case LinePTO:
			return (R) getLineCaseData("ToMW");
		case LineQTO:
			return (R) getLineCaseData("ToMVAr");
		case LineR:
			return (R) _lineCSV.getFloats("R");
		case LineX:
			return (R) _lineCSV.getFloats("X");
		case LineBFROM:
		case LineBTO:
			return (R) returnZero(_lineCSV.getRowCount());
		case LineRATLT:
			return (R) _lineCSV.getFloats("NormalOperatingLimit");
		//Series Capacitor
		case SercapID:
			return (R) _seriesCapCSV.get("ID");
		case SercapNAME:
			return (R) _seriesCapCSV.get("Name");
		case SercapBUSFROM:
			return (R) getBusesById(_seriesCapCSV.get("Node1"));
		case SercapBUSTO:
			return (R) getBusesById(_seriesCapCSV.get("Node2"));
		case SercapOOS:
			return (R) returnFalse(_seriesCapCSV.getRowCount());
		case SercapPFROM:
		case SercapQFROM:
		case SercapPTO:
		case SercapQTO:
			return (R) returnZero(_seriesCapCSV.getRowCount());
		case SercapR:
			return (R) _seriesCapCSV.getFloats("R");
		case SercapX:
			return (R) _seriesCapCSV.getFloats("X");
		case SercapRATLT:
			return (R) _seriesCapCSV.getFloats("NormalOperatingLimit");
		//Series Reactor
		case SerreacID:
			return (R) _seriesReacCSV.get("ID");
		case SerreacNAME:
			return (R) _seriesReacCSV.get("Name");
		case SerreacBUSFROM:
			return (R) getBusesById(_seriesReacCSV.get("Node1"));
		case SerreacBUSTO:
			return (R) getBusesById(_seriesReacCSV.get("Node2"));
		case SerreacOOS:
			return (R) returnFalse(_seriesReacCSV.getRowCount());
		case SerreacPFROM:
		case SerreacQFROM:
		case SerreacPTO:
			return (R) returnZero(_seriesReacCSV.getRowCount());
		case SerreacR:
			return (R) _seriesReacCSV.getFloats("R");
		case SerreacX:
			return (R) _seriesReacCSV.getFloats("X");
		case SerreacRATLT:
			return (R) _seriesReacCSV.getFloats("NormalOperatingLimit");
		//Phase Shifter
		case PhashID:
			//Build maps if they don't exist
			if(_transformerMap == null) buildTransformerMaps();
			return (R) _phaseShifterIDs;
		case PhashNAME:
			return (R) getTransformerDataStrings("Name", "transformer", false);
		case PhashBUSFROM:
			return (R) getBusesById(getTransformerDataStrings("Node1", "winding", false));
		case PhashBUSTO:
			return (R) getBusesById(getTransformerDataStrings("Node2", "winding", false));
		case PhashOOS:
			return (R) returnFalse(_phaseShifterIDs.size());
		case PhashPFROM:
			return (R) getWindingCaseData("FromMW", false);
		case PhashQFROM:
			return (R) getWindingCaseData("FromMVAr", false);
		case PhashPTO:
			return (R) getWindingCaseData("ToMW", false);
		case PhashQTO:
			return (R) getWindingCaseData("ToMVAr", false);
		case PhashR:
			return (R) getTransformerDataFloats("R", "winding", false);
		case PhashX:
			return (R) getTransformerDataFloats("X", "winding", false);
		case PhashGMAG:
			return null;
		case PhashBMAG:
			return (R) getTransformerDataFloats("Bmag", "winding", false);
		case PhashANG:
		case PhashTAPFROM:
		case PhashTAPTO:
		case PhashCTRLMODE:
			return null;
		case PhashRATLT:
			return (R) getTransformerDataFloats("NormalOperatingLimit", "winding", false);
		//Transformer
		case TfmrID:
			//Build maps if they don't exist
			if(_transformerMap == null) buildTransformerMaps();
			return (R) _transformerIDs;
		case TfmrNAME:
			return (R) getTransformerDataStrings("Name", "transformer", true);
		case TfmrBUSFROM:
			return (R) getBusesById(getTransformerDataStrings("Node1", "winding", true));
		case TfmrBUSTO:
			return (R) getBusesById(getTransformerDataStrings("Node2", "winding", true));
		case TfmrOOS:
			return (R) returnFalse(_transformerIDs.size());
		case TfmrPFROM:
			return (R) getWindingCaseData("FromMW", true);
		case TfmrQFROM:
			return (R) getWindingCaseData("FromMVAr", true);
		case TfmrPTO:
			return (R) getWindingCaseData("ToMW", true);
		case TfmrQTO:
			return (R) getWindingCaseData("ToMVAr", true);
		case TfmrR:
			return (R) getTransformerDataFloats("R", "winding", true);
		case TfmrX:
			return (R) getTransformerDataFloats("X", "winding", true);
		case TfmrGMAG:
			return null;
		case TfmrBMAG:
			return (R) getTransformerDataFloats("Bmag", "winding", true);
		case TfmrANG:
		case TfmrTAPFROM:
		case TfmrTAPTO:
			return null;
		case TfmrRATLT:
			return (R) getTransformerDataFloats("NormalOperatingLimit", "winding", true);
		//Switch
		case SwID:
			return (R) _switchCSV.get("ID");
		case SwNAME:
			return (R) _switchCSV.get("Name");
		case SwBUSFROM:
			return (R) getBusesById(_switchCSV.get("Node1"));
		case SwBUSTO:
			return (R) getBusesById(_switchCSV.get("Node2"));
		case SwOOS:
			return (R) returnFalse(_switchCSV.getRowCount());
		case SwPFROM:
		case SwQFROM:
		case SwPTO:
		case SwQTO:
			return (R) returnZero(_switchCSV.getRowCount());
		case SwSTATE:
			return (R) getSwitchState();
		case SwOPLD:
			return (R) operableUnderLoad();
		case SwENAB:
			boolean[] switches = new boolean[_switchCSV.getRowCount()];
			Arrays.fill(switches, true);
			return (R) switches;
		//T2dc
		case T2dcID:
		case T2dcNAME:
		case T2dcBUSFROM:
		case T2dcBUSTO:
		case T2dcOOS:
		case T2dcPFROM:
		case T2dcQFROM:
		case T2dcPTO:
		case T2dcQTO:
			return null;
		default:
			return null;
		}
	}
	
	private float[] returnZero(int size)
	{
		float[] data = new float[size];
		
		Arrays.fill(data, 0);
		
		return data;
	}
	
	private boolean[] returnFalse(int size)
	{
		boolean[] data = new boolean[size];
		
		Arrays.fill(data, false);
		
		return data;
	}
	
	private String[] returnAsString(float[] fs)
	{
		String[] asString = new String[fs.length];
		
		for(int i = 0; i < fs.length; ++i)
		{
			asString[i] = ""+fs[i];
		}
		
		return asString;
	}
	
	private void buildVlev() throws PAModelException
	{
		_vlevMap = new TFloatIntHashMap();
		TIntFloatMap tempMap = new TIntFloatHashMap();
		
//		if(_busCSV == null) System.out.println("[buildVlev] _busCSV is null");
		float[] kv = _busCSV.getFloats("NominalKV");
		int offset = 0;
		
		for(int i = 0; i < kv.length; ++i)
		{
			//Check to see if the voltage level exists in the map
			if(!tempMap.containsValue(kv[i]))
			{
				//New level found, add it to the map
//				System.out.println("[buildVlev] tempMap.put("+offset+", "+kv[i]+")");
				tempMap.put(offset, kv[i]);
				offset++;
			}
		}
		
		//Now that we know how many voltage levels there are we can create proper maps & arrays.
		//This is probably quite poorly done so please remind me to fix it!
		_vlevFloat = new float[offset-1];
		for(int i = 0; i < offset-1; ++i)
		{
			_vlevFloat[i] = tempMap.get(i);
			_vlevMap.put(tempMap.get(i), i);
		}
	}
	
	private void buildAreaMap()
	{
		if(_areaCSV == null)System.out.println("[buildAreaMap] _areaCSV is null");
		String[] areaIDs = _areaCSV.get("ID");
		_areaMap = new TObjectIntHashMap<>(areaIDs.length);
		
		for(int i = 0; i < areaIDs.length; ++i)
		{
			_areaMap.put(areaIDs[i], i);
		}	
	}
	
	private void buildBusAreaIndex() throws PAModelException
	{
		if(_areaMap == null) buildAreaMap();
		if(_stationOffsetMap == null) buildSubstationMap();
		if(_busCSV == null) loadBuses();
		String[] stationIDs = _busCSV.get("Substation");
		String[] areaIDs = _substationCSV.get("ControlArea");
		_busAreaIndex = new int[stationIDs.length];
		
		for(int i = 0; i < stationIDs.length; ++i)
		{
//			System.out.println("\n[getBusAreaOffsets] stationIDs["+i+"] = "+stationIDs[i]);
//			System.out.println("[getBusAreaOffsets] _stationOffsetMap.get("+stationIDs[i]+") = "+_stationOffsetMap.get(stationIDs[i]));
//			System.out.println("[getBusAreaOffsets] areaIDs["+_stationOffsetMap.get(stationIDs[i])+"] = "+areaIDs[_stationOffsetMap.get(stationIDs[i])]);
//			System.out.println("[getBusAreaOffsets] _areaMap.get("+areaIDs[_stationOffsetMap.get(stationIDs[i])]+") = "+_areaMap.get(areaIDs[_stationOffsetMap.get(stationIDs[i])]));

			_busAreaIndex[i] = _areaMap.get(areaIDs[_stationOffsetMap.get(stationIDs[i])]);
		}
	}
	private void buildBusStationIndex() throws PAModelException
	{
		if(_stationOffsetMap == null) buildSubstationMap();
		if(_busCSV == null) loadBuses();
		String[] substationIDs = _busCSV.get("Substation");
		_busStationIndex = new int[substationIDs.length];
		
		
		for(int i = 0; i < substationIDs.length; ++i)
		{
			_busStationIndex[i] = _stationOffsetMap.get(substationIDs[i]);
		}
	}
	
	private int[] getBusVlev() throws PAModelException
	{
		if(_vlevMap == null) buildVlev();
		
		float[] kv = _busCSV.getFloats("NominalKV");
		int[] busVlev = new int[kv.length];
		
		for(int i = 0; i < busVlev.length; ++i)
		{
			busVlev[i] = _vlevMap.get((int)kv[i]);
//			System.out.println("\n[getBusVlev] busVlev["+i+"] = "+busVlev[i]);
		}
		
		return busVlev;
	}
	
	private boolean[] operableUnderLoad()
	{
		boolean[] isOperable = new boolean[_switchCSV.getRowCount()];
		String switchTypeID, open, close = null;
		String[] switchIDList = _switchCSV.get("ID");
		
		for(int i = 0; i < isOperable.length; ++i)
		{
			switchTypeID = _switchCSV.get("SwitchType", i);
			//Figure out if Switch type id is given in the switch csv
			if(!switchTypeID.equals(null))
			{
				//Switch Type exists, find it in the SwitchType csv
				for(int j = 0; j < switchIDList.length; ++j)
				{
					if(switchIDList[j].equals(switchTypeID))
					{
						open = _switchTypeCSV.get("OpenUnderLoad", j).toLowerCase();
						close = _switchTypeCSV.get("CloseUnderLoad", j).toLowerCase();
						isOperable[i] = (open.equals("true") && close.equals("true"))?true:false;
					}
				}
			}
			else
			{
				isOperable[i] = false;
			}
		}
		
		return isOperable;
	}
	
	private Gen.Mode[] getGenMode() throws PAModelException
	{
		if(_genCSV == null) loadGens();
		String[] opMode = _genCSV.get("GenControlMode");
		Gen.Mode genModes[] = new Gen.Mode[opMode.length];
		if(opMode.equals(null))
		{
			System.err.println("[PFlowPsmModelBldr] Error generator column \"GenControlMode\". Does it exist in the CSV?");
			Arrays.fill(genModes, null);
		}
		else
		{
			for(int i = 0; i < opMode.length; ++i)
			{
				genModes[i] = Gen.Mode.valueOf(opMode[i].toUpperCase());
			}
		}
		
		return genModes;
	}
	
	private boolean[] getAVRMode() throws PAModelException
	{
		String[] genAVR = getGenDataString("AVRMode","synchcase");
		boolean[] avr = new boolean[genAVR.length];
		
		for(int i = 0; i < genAVR.length; ++i)
		{
			avr[i] = (genAVR[i].toLowerCase().equals("off"))?false:true;
		}
		
		return avr;
	}
	
	private float[] getLoadCaseData(String col)
	{
		String[] loadIDs = _loadCSV.get("ID");
		float[] data = new float[loadIDs.length];
		float[] caseData = _loadCaseCSV.getFloats(col);
		if(caseData == null) 
		{
			System.err.println("[PFlowPsmModelBldr] Error loading load case column \"\""+col+"\"\". Does it exist in the CSV?");
			Arrays.fill(data, 0);
		}
		else
		{
			//check to see if the hashmap has been created yet;
			if(_loadMap == null) buildLoadMap();
			
			for(int i = 0; i < data.length; ++i)
			{
				//Debugging
	//			System.out.println("\n=========\nCol: "+col);
	//			System.out.println("loadIDs["+i+"]: "+loadIDs[i]);
	//			System.out.println("_loadMap: "+_loadMap.get(loadIDs[i]));
	//			System.out.println("caseData: "+caseData[_loadMap.get(loadIDs[i])]);
				
				data[i] = caseData[_loadMap.get(loadIDs[i])];
			}
		}
		
		return data;
	}
	
	private float[] getShuntReacData(String col)
	{
		String[] shuntIDs = _shuntReacCSV.get("ID");
		float[] caseData = _shuntReacCaseCSV.getFloats(col);
		float[] data = new float[shuntIDs.length];
		//check to see if the hashmap has been created yet;
		if(_shuntReacMap == null) buildShuntReacMap();
		
		if(caseData == null) 
		{
			System.err.println("[PFlowPsmModelBldr] Error loading shunt reactor case column \""+col+"\". Does it exist in the CSV?");
			Arrays.fill(data, 0);
		}
		
		//NOT FINISHED!
		return data;
	}
	
	private float[] getShuntCapData(String col)
	{
		String[] shuntIDs = _shuntCapCSV.get("ID");
		float[] caseData = _shuntCapCaseCSV.getFloats(col);
		float[] data = new float[shuntIDs.length];
		//check to see if the hashmap has been created yet;
		if(_shuntCapMap == null) buildShuntCapMap();
		
		if(caseData == null) 
		{
			System.err.println("[PFlowPsmModelBldr] Error loading shunt capacitor case column \""+col+"\". Does it exist in the CSV?");
			Arrays.fill(data, 0);
		}
		
		//NOT FINISHED!
		return data;
	}
	
	private int[] getGenRegBus() throws PAModelException
	{
		String[] genIDs = _synchMachineCSV.get("GeneratingUnit");
		String[] unsortedBuses = _synchMachineCSV.get("RegulatedNode");
		String[] busIDs = new String[genIDs.length];
		
		if(unsortedBuses == null)
		{
			unsortedBuses = _synchMachineCSV.get("Node");
		}
		
		for(int i = 0; i < genIDs.length; ++i)
		{
			busIDs[_genMap.get(genIDs[i])] = unsortedBuses[i];
		}
		
		return getBusesById(busIDs);
	}
	
	private float[] getGenDataFloat(String col, String csv)
	{
		String[] genIDs = _genCSV.get("ID");
		float[] unsortedData;
		float[] data = new float[_genCaseCSV.getRowCount()];
		
		//Build maps if they are empty
		if(_genMap == null || _genToSynchMap == null) buildGeneratorMaps();
		
		//Figure out what csv we are dealing with
		if(csv.toLowerCase().equals("synch"))
		{
			unsortedData = _synchMachineCSV.getFloats(col);
			if(unsortedData == null) 
			{
				System.err.println("[PFlowPsmModelBldr] Error loading synchronous machine column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, 0);
			}
			else
			{
				for (int i = 0; i < genIDs.length; ++i)
				{
					data[i] = unsortedData[_genToSynchMap.get(genIDs[i])];
				}
			}
		}
		else if(csv.toLowerCase().equals("synchcase"))
		{
			String[] synchIDs = _synchMachineCSV.get("ID");
			unsortedData = _synchCaseCSV.getFloats(col);
			if(unsortedData == null) 
			{
				System.err.println("[PFlowPsmModelBldr] Error loading synchronous machine case column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, 0);
			}
			else
			{
				for(int i = 0; i < genIDs.length; ++i)
				{
					data[i] = unsortedData[_synchMap.get(synchIDs[_genToSynchMap.get(genIDs[i])])];
				}
			}
		}
		else if(csv.toLowerCase().equals("gencase"))
		{
			unsortedData = _genCaseCSV.getFloats(col);
			if(unsortedData == null) 
			{
				System.err.println("[PFlowPsmModelBldr] Error loading generator case column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, 0);
			}
			else
			{
				for (int i = 0; i < genIDs.length; ++i)
				{
					data[i] = unsortedData[_genMap.get(genIDs[i])];
				}
			}
		}
		else return null;
		
		return data;
	}
	
	private String[] getGenDataString(String col, String csv)
	{
		String[] genIDs = _genCSV.get("ID");
		String[] unsortedData;
		String[] data = new String[_genCaseCSV.getRowCount()];
		
		//Build maps if they are empty
		if(_genMap == null || _genToSynchMap == null) buildGeneratorMaps();
		
		//Figure out what csv we are dealing with
		if(csv.toLowerCase().equals("synch"))
		{
			unsortedData = _synchMachineCSV.get(col);
			if(unsortedData == null)
			{
				System.err.println("[PFlowPsmModelBldr] Error loading synchronous machine column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, "");
			}
			else
			{
				for (int i = 0; i < genIDs.length; ++i)
				{
					//System.out.println("data["+i+"]: "+unsortedData[_genToSynchMap.get(genIDs[i])]);
					data[i] = unsortedData[_genToSynchMap.get(genIDs[i])];
				}				
			}
		}
		else if(csv.toLowerCase().equals("synchcase"))
		{
			String[] synchIDs = _synchMachineCSV.get("ID");
			unsortedData = _synchCaseCSV.get(col);
			if(unsortedData == null) 
			{
				System.err.println("[PFlowPsmModelBldr] Error loading synchronous machine case column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, "");
			}
			else
			{				
				for(int i = 0; i < genIDs.length; ++i)
				{
					data[i] = unsortedData[_synchMap.get(synchIDs[_genToSynchMap.get(genIDs[i])])];
				}
			}
		}
		else if(csv.toLowerCase().equals("gencase"))
		{
			unsortedData = _genCaseCSV.get(col);
			if(unsortedData == null) 
			{
				System.err.println("[PFlowPsmModelBldr] Error loading generator case column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, "");
			}
			else
			{
				for (int i = 0; i < genIDs.length; ++i)
				{
					data[i] = unsortedData[_genMap.get(genIDs[i])];
				}				
			}
		}
		else return null;
		
		return data;
	}
	
	private float[] getLineCaseData(String col)
	{
		if(_lineMap == null) buildLineMap();
	
		String[] ids = _lineCSV.get("ID");
		float[] unsortedData = _lineCaseCSV.getFloats(col);
		float[] data = new float[ids.length];
		if(unsortedData == null) 
		{
			System.err.println("[PFlowPsmModelBldr] Error loading line case column \""+col+"\". Does it exist in the CSV?");
			Arrays.fill(data, 0);
		}
		else
		{
			for(int i = 0; i < ids.length; ++i)
			{
				data[i] = unsortedData[_lineMap.get(ids[i])];
			}
		}
		
		return data;
	}
	
	private float[] getSVCData(String col)
	{
		//Build maps if they don't exist
		if(_svcCaseMap == null) buildSVCMap();
		String[] ids = _svcCaseCSV.get("ID");
		float[] unsortedData = _svcCaseCSV.getFloats(col);
		float[] data = new float[ids.length];
		if(unsortedData == null) 
		{
			System.err.println("[PFlowPsmModelBldr] Error loading line case column \""+col+"\". Does it exist in the CSV?");
			Arrays.fill(data, 0);
		}
		else
		{
			for(int i = 0; i < ids.length; ++i)
			{
				data[i] = unsortedData[_svcCaseMap.get(ids[i])];
			}
		}
		
		return data;
	}
	
	private String[] getTransformerDataStrings(String col, String csv, boolean isTfmr)
	{
		//Build maps if they don't exist
		if(_transformerMap == null) buildTransformerMaps();
		List<String> ids = (isTfmr)?_transformerIDs:_phaseShifterIDs;
		
		
		
		String[] data = new String[ids.size()];
		String[] unsortedData;
		
		if(csv.toLowerCase().equals("transformer"))
		{
			unsortedData = _transformerCSV.get(col);
			if(unsortedData == null) 
			{
				System.err.println("[PFlowPsmModelBldr] Error loading transformer column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, "");
			}
			else
			{
				for(int i = 0; i < ids.size(); ++i)
				{
					data[i] = unsortedData[_transformerMap.get(ids.get(i))];
				}
			}
		}
		else if(csv.toLowerCase().equals("winding"))
		{
			unsortedData = _tfmrWindingCSV.get(col);
			if(unsortedData == null)
			{
				System.err.println("[PFlowPsmModelBldr] Error loading winding column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, "");
			}
			else
			{
				for(int i = 0; i < ids.size(); ++i)
				{
					data[i] = unsortedData[_wdgToTfmrMap.get(ids.get(i))];
				}
			}
		}
		
		return data;
	}
	
	private float[] getTransformerDataFloats(String col, String csv, boolean isTfmr)
	{
		//Build maps if they don't exist
		if(_transformerMap == null) buildTransformerMaps();
		List<String> ids = (isTfmr)?_transformerIDs:_phaseShifterIDs;
		
		float[] data = new float[ids.size()];
		float[] unsortedData;
		
		if(csv.toLowerCase().equals("transformer"))
		{
			unsortedData = _transformerCSV.getFloats(col);
			if(unsortedData == null)
			{
				System.err.println("[PFlowPsmModelBldr] Error loading transformer column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, 0);
			}
			else
			{
				for(int i = 0; i < ids.size(); ++i)
				{
					data[i] = unsortedData[_transformerMap.get(ids.get(i))];
				}				
			}
		}
		else if(csv.toLowerCase().equals("winding"))
		{
			unsortedData = _tfmrWindingCSV.getFloats(col);
			if(unsortedData == null) 
			{
				System.err.println("[PFlowPsmModelBldr] Error loading winding column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, 0);
			}
			else
			{
				for(int i = 0; i < ids.size(); ++i)
				{
					data[i] = unsortedData[_wdgToTfmrMap.get(ids.get(i))];
				}				
			}
		}
		
		return data;
	}
	
	private float[] getWindingCaseData(String col, boolean isTfmr)
	{
		float[] unsortedData = _windingCaseCSV.getFloats(col);
		List<String> ids = (isTfmr)?_transformerIDs:_phaseShifterIDs;
		String[] wdgIDs = _tfmrWindingCSV.get("ID");
		float[] data = new float[ids.size()];
		if(unsortedData == null) 
		{
			System.err.println("[PFlowPsmModelBldr] Error loading winding case column \""+col+"\". Does it exist in the CSV?");
			Arrays.fill(data, 0);
		}
		else
		{
			for(int i = 0; i < ids.size(); ++i)
			{
				//Have tfmr/phase ID. Need to winding ID.
				//Using the winding ID we'll get the case offset which will give us the float from unsorted date
//			System.out.println("\n============\n[getWindingCaseData] Col: \""+col+"\"\n[getWindingCaseData] IDs["+i+"/"+ids.size()+" | "+_ratioTapChgCSV.getRowCount()+"] = "+ids.get(i));
//			System.out.println("[getWindingCaseData] wdgIDs["+_wdgToTfmrMap.get(ids.get(i))+"] = "+wdgIDs[_wdgToTfmrMap.get(ids.get(i))]);
//			System.out.println("[getWindingCaseData] data["+_windingCaseMap.get(wdgIDs[_wdgToTfmrMap.get(ids.get(i))])+"] = "+unsortedData[_windingCaseMap.get(wdgIDs[_wdgToTfmrMap.get(ids.get(i))])]);
				data[i] = unsortedData[_windingCaseMap.get(wdgIDs[_wdgToTfmrMap.get(ids.get(i))])];
			}			
		}
		
		return data;
	}
	
	private void buildTransformerMaps()
	{	
//		String[] ratioCaseIDs 	= _ratioTapChgCaseCSV.get("ID");
		String[] ratioTapIDs 	= _ratioTapChgCSV.get("ID");
		String[] wdgInRatioIDs 	= _ratioTapChgCSV.get("TransformerWinding");
		String[] tfmrInWdgIDs 	= _tfmrWindingCSV.get("Transformer");
		String[] windingIDs 	= _tfmrWindingCSV.get("ID");
		String[] phaseTapIDs	= _phaseTapChgCSV.get("ID");
		String[] wdgInPhaseIDs  = _phaseTapChgCSV.get("TransformerWinding");
		String[] allIDs 		= _transformerCSV.get("ID");
		String[] wdgCaseIDs		= _windingCaseCSV.get("ID");

		_transformerMap 	= new TObjectIntHashMap<>(allIDs.length);
		_windingMap			= new TObjectIntHashMap<>(windingIDs.length);
		_windingCaseMap		= new TObjectIntHashMap<>(windingIDs.length);
		_tfmrRatioTapMap 	= new TObjectIntHashMap<>(wdgInRatioIDs.length); //Key = Transformer, Value = Ratio Tap Offset
		_wdgInPhaseMap 		= new TObjectIntHashMap<>(phaseTapIDs.length);
		_wdgInRatioMap 		= new TObjectIntHashMap<>(ratioTapIDs.length);
		_wdgToTfmrMap		= new TObjectIntHashMap<>(windingIDs.length); //Key = transformer, value = Winding Offset
		_tfmrPhaseTapMap	= new TObjectIntHashMap<>(phaseTapIDs.length);
		
		if(_transformerIDs == null) _transformerIDs = new ArrayList<String>();
		if(_phaseShifterIDs == null) _phaseShifterIDs = new ArrayList<String>();
		
		//Are transformer and winding csv's always the same length?
		//Build maps based on transformer length
		for(int i = 0; i < allIDs.length; ++i)
		{
			_transformerMap.put(allIDs[i], i);
		}
		//Build maps based on Winding length
		for(int i = 0; i < windingIDs.length; ++i)
		{
			_windingMap.put(windingIDs[i], i);
			_wdgToTfmrMap.put(tfmrInWdgIDs[i], i);
			_windingCaseMap.put(wdgCaseIDs[i], i);
//			System.out.println("[buildTransformerMaps] _windingCaseMap.put("+wdgCaseIDs[i]+", "+i+")");
		}
		
		//Build maps based only on Phase Tap
		for(int i = 0; i < phaseTapIDs.length; ++i)
		{
//			System.out.println("[buildTransformerMaps] _wdgInPhaseMap.put("+wdgInPhaseIDs[i]+", "+i+")");
			_wdgInPhaseMap.put(wdgInPhaseIDs[i], i);
		}
		
		
		//Build maps based only on Ratio Tap
		for(int i = 0; i < ratioTapIDs.length; ++i)
		{
//			System.out.println("[buildTransformerMaps] wdgInRationIDs["+i+"/"+ratioTapIDs.length+"] = "+wdgInRatioIDs[i]);
			_wdgInRatioMap.put(wdgInRatioIDs[i], i);
		}
		
		//Array of transformers and phase shifters ?
		for(int i = 0; i < allIDs.length; ++i)
		{
			//Have to use the transformer ID to get the winding ID
			//Use the winding ID to see if it is a transformer or phase shifter
			
			//First figure out if the winding is for a transformer or phase shifter
			//System.out.println("allIDs["+i+"/"+allIDs.length+"] = "+allIDs[i]);
			if(_wdgInRatioMap.containsKey(windingIDs[i]))
			{
				//ID belongs to a transformer
				//Place id in transformer array list
				_transformerIDs.add(tfmrInWdgIDs[i]);
//				System.out.println("transformerIDs["+tfmrOff+"/"+ratioTapIDs.length+"] = "+tfmrInWdgIDs[i]);
				//Figure out the transformer ID key to ratio tap offset map
				_tfmrRatioTapMap.put(tfmrInWdgIDs[i], _wdgInRatioMap.get(windingIDs[i]));
			}
			else if(_wdgInPhaseMap.containsKey(windingIDs[i]))
			{
				_phaseShifterIDs.add(tfmrInWdgIDs[i]);
//				System.out.println("phaseShifterIDs["+phaseOff+"/"+phaseTapIDs.length+"] = "+tfmrInWdgIDs[i]);
				_tfmrPhaseTapMap.put(tfmrInWdgIDs[i], _wdgInPhaseMap.get(windingIDs[i]));
			}
			else
			{
				System.err.println("[buildTransformerMaps] ID not found: "+windingIDs[i]);
			}
		}
		
	}
	
	private void buildSubstationMap() throws PAModelException
	{
		if(_substationCSV == null) loadStations();
		String[] subIDs = _substationCSV.get("ID");
		_stationOffsetMap = new TObjectIntHashMap<>(subIDs.length);
		
		for(int i = 0; i < subIDs.length; ++i)
		{
			_stationOffsetMap.put(subIDs[i], i);
		}
	}
	
	private void buildGeneratorMaps()
	{
		String[] genCaseIDs = _genCaseCSV.get("ID");
		String[] synchCaseIDs = _synchCaseCSV.get("ID");
		String[] synchGenIDs = _synchMachineCSV.get("GeneratingUnit");
		
		
		_genMap = new TObjectIntHashMap<>(genCaseIDs.length);
		_synchMap = new TObjectIntHashMap<>(synchCaseIDs.length);
		_genToSynchMap = new TObjectIntHashMap<>(genCaseIDs.length);
		_genToSynchCaseMap = new TObjectIntHashMap<>(genCaseIDs.length);
		
		
		for(int i = 0; i < genCaseIDs.length; ++i)
		{
			_genMap.put(genCaseIDs[i], i); // Takes generating unit ID
			_synchMap.put(synchCaseIDs[i], i); // Takes synch machine ID
			_genToSynchMap.put(synchGenIDs[i], i); //Take generating ID
		}
		
		//Need to build the _genToSynchCaseMap after the _genToSynchMap
//		for(int i = 0; i < genCaseIDs.length; ++i)
//		{
//			//data[i] = unsortedData[_synchMap.get(synchIDs[_genToSynchMap.get(genIDs[i])])];
//			//Give it gen id
//			//Recieve synch case offset
//			//(generatorID, offset at generator in _synchCaseCSV)
//			//
//			_genToSynchCaseMap.put(synchGenIDs[_], );
//		}
	}
	
	private void buildSwitchMap()
	{
		String[] caseIDs = _switchCaseCSV.get("ID");
		_switchCaseMap = new TObjectIntHashMap<>(caseIDs.length);
		
		for(int i = 0; i < caseIDs.length; ++i)
		{
			_switchCaseMap.put(caseIDs[i], i);
		}
	}
	
	private void buildSVCMap()
	{
		String[] caseIDs = _svcCaseCSV.get("ID");
		_svcCaseMap = new TObjectIntHashMap<>(caseIDs.length);
		
		for(int i = 0; i < caseIDs.length; ++i)
		{
			_svcCaseMap.put(caseIDs[i], i);
		}
	}
	
	private Switch.State[] getSwitchState()
	{
		if (_switchCaseMap == null) buildSwitchMap();
		String[] unsortedData = _switchCaseCSV.get("SwitchPosition");
		String[] ids = _switchCSV.get("ID");
		Switch.State[] state = new Switch.State[ids.length];
		for(int i = 0; i < ids.length; ++i)
		{
			state[i] = (unsortedData[_switchCaseMap.get(ids[i])].equals("Open"))?Switch.State.Open:Switch.State.Closed;
		}
		
		return state;
	}
	
	private void buildLoadMap()
	{
		String[] caseIDs = _loadCaseCSV.get("ID");
		
		_loadMap = new TObjectIntHashMap<>(caseIDs.length);
		for(int i = 0; i < caseIDs.length; ++i)
		{
			_loadMap.put(caseIDs[i], i);
		}

	}
	
	private void buildShuntReacMap()
	{
		String[] caseIDs = _shuntReacCaseCSV.get("ID");
		
		_shuntReacMap = new TObjectIntHashMap<>(caseIDs.length);
		for(int i = 0; i < caseIDs.length; ++i)
		{
			System.out.println("");
			_shuntReacMap.put(caseIDs[i], i);
		}
	}
	
	private void buildShuntCapMap()
	{
		String[] caseIDs = _shuntCapCaseCSV.get("ID");
		
		_shuntCapMap = new TObjectIntHashMap<>(caseIDs.length);
		for(int i = 0; i < caseIDs.length; ++i)
		{
			_shuntCapMap.put(caseIDs[i], i);
		}
	}
	
	private void buildLineMap()
	{
		String[] lineIDs = _lineCaseCSV.get("ID");
		
		_lineMap = new TObjectIntHashMap<>(lineIDs.length);
		for(int i = 0; i < lineIDs.length; ++i)
		{
			_lineMap.put(lineIDs[i], i);
		}
	}
	
	private int[] getBusesById(String[] ids) throws PAModelException 
	{
		int[] indexes = _m.getBuses().getIndexesFromIDs(ids);
//		for(int i = 0; i < ids.length; ++i)
//		{
//			System.out.println("Bus Index["+i+"]: "+indexes[i]);
//		}
		return indexes;
	}

}
