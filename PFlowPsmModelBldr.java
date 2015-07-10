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
import com.powerdata.openpa.impl.TransformerListI;
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
	SimpleCSV _reacCapCurveCSV;
	SimpleCSV _phaseCaseCSV;
	SimpleCSV _orgCSV;

	
	//Case CSV files
	SimpleCSV _loadCaseCSV;
	SimpleCSV _shuntCapCaseCSV;
	SimpleCSV _shuntReacCaseCSV;
	SimpleCSV _seriesCapCaseCSV;
	SimpleCSV _seriesReacCaseCSV;
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
//	SimpleCSV _voltageRelayCSV;
//	SimpleCSV _curRelayCSV;
//	SimpleCSV _freqRelayCSV;
//	SimpleCSV _loadAreaCSV;
//	SimpleCSV _modelParmsCSV;
//	SimpleCSV _primeMoverCSV;
//	SimpleCSV _relayOperateCSV;
	
	//Hashmaps
	TObjectIntMap<String> _loadCaseMap;
	TObjectIntMap<String> _shuntReacCaseMap;
	TObjectIntMap<String> _shuntCapCaseMap;
	TObjectIntMap<String> _genCaseMap;
	TObjectIntMap<String> _syncCasehMap;
	TObjectIntMap<String> _genToSynchMap;
	TObjectIntMap<String> _genToSynchCaseMap;
	TFloatIntMap _vlevMap;
	TObjectIntMap<String> _areaMap;
	TObjectIntMap<String> _ownerMap;
	TObjectIntMap<String> _tfmrRatioTapMap;
	TObjectIntMap<String> _tfmrPhaseTapMap;
	TObjectIntMap<String> _transformerMap;
	TObjectIntMap<String> _windingMap;
	TObjectIntMap<String> _wdgInPhaseMap;
	TObjectIntMap<String> _wdgInRatioMap;
	TObjectIntMap<String> _ratioCaseMap;
	TObjectIntMap<String> _wdgToTfmrMap;
	TObjectIntMap<String> _switchCaseMap;
	TObjectIntMap<String> _lineCaseMap;
	TObjectIntMap<String> _windingCaseMap;
	TObjectIntMap<String> _stationOffsetMap;
	TObjectIntMap<String> _svcCaseMap;
	TObjectIntMap<String> _seriesReacCaseMap;
	TObjectIntMap<String> _seriesCapCaseMap;
	TObjectIntMap<String> _synchToCurveMap;
	TObjectIntMap<String> _switchTypeMap;
	
	//Arrays / Lists
	int[] _stationAreaIndex;
	int[] _busAreaIndex;
	int[] _busOwnerIndex;
	int[] _busStationIndex;
	int[] _stationOwnerIndex;
	boolean[] _typeIsOperable;
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
		try
		{
			_areaCSV = new SimpleCSV(new File(_dir, "ControlArea.csv"));
			if(_busAreaIndex == null) buildBusIndexes();
			return new AreaListI(_m, offsetsToKeys(_busAreaIndex), _areaCSV.getRowCount());
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected OwnerListI loadOwners() throws PAModelException 
	{
		try 
		{
			_orgCSV = new SimpleCSV(new File(_dir, "Organization.csv"));
			if(_busOwnerIndex == null) buildBusIndexes();
			return new OwnerListI(_m, offsetsToKeys(_busOwnerIndex), _orgCSV.getRowCount());
		} 
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected StationListI loadStations() throws PAModelException 
	{
		try
		{
			_substationCSV = new SimpleCSV(new File(_dir, "Substation.csv"));
			if(_busStationIndex == null) buildBusStationIndex();
			
			return new StationListI(_m, offsetsToKeys(_busStationIndex), _substationCSV.getRowCount());
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
			loadBuses();
		}
		
		if(_vlevFloat == null || _vlevMap == null) buildVlev();
		
		return new VoltageLevelListI(_m, offsetsToKeys(getBusVlev()), _vlevMap.size());
	}

	@Override
	protected IslandList loadIslands() throws PAModelException 
	{
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
	protected SwitchedShuntList loadSwitchedShunts() throws PAModelException 
	{
		// TODO Incomplete
		// Didn't see a csv in doc
		// PD3 builder returns an empty list
		
		return SwitchedShuntList.emptyList();
	}

	@Override
	protected TwoTermDCLineList loadTwoTermDCLines() throws PAModelException 
	{
		// TODO Incomplete
		// Didn't see a csv in doc
		// PD3 builder returns an empty list
		return TwoTermDCLineList.emptyList();
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
			_reacCapCurveCSV = new SimpleCSV(new File(_dir, "ReactiveCapabilityCurve.csv"));
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
			_seriesCapCSV = new SimpleCSV(new File(_dir, "SeriesCapacitor.csv"));
			_seriesCapCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseSeriesCapacitor.csv"));
			return new SeriesCapListI(_m, _seriesCapCSV.getRowCount());
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
			_seriesReacCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseSeriesReactor.csv"));
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
				_windingCaseCSV = new SimpleCSV(new File(_dir, "PsmCaseTransformerWinding.csv"));
				_phaseCaseCSV = new SimpleCSV(new File(_dir, "PsmCasePhaseTapChanger.csv"));
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
				_phaseCaseCSV = new SimpleCSV(new File(_dir, "PsmCasePhaseTapChanger.csv"));
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
			return (R) returnFalseNumber(_busCSV.getRowCount());
		case BusFREQSRCPRI:
			return (R) _busCSV.getInts("FrequencySourcePriority");
		case BusAREA:
			if(_busAreaIndex == null) loadAreas();
			return (R) _busAreaIndex;
		case BusOWNER:
			if(_stationOwnerIndex == null) loadOwners();
			return (R) _busOwnerIndex;
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
			return (R) getGenDataFloat("MinMVAr", "curve");
		case GenMAXQ:
			return (R) getGenDataFloat("MaxMVAr", "curve");
		case GenPS:
			return (R) getGenDataFloat("MWSetPoint", "gencase");
		case GenQS:
			return (R) getGenDataFloat("MVArSetpoint", "synchcase");
		case GenAVR:
			return (R) getGenAVRMode();
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
			return (R) invertValues(getLoadCaseData("MW"));
		case LoadQ:
			return (R) invertValues(getLoadCaseData("MVAr"));
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
			return (R) _shuntCapCSV.getFloats("MVAr");
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
			return (R) getSVCDataFloats("MW");
		case SvcQ:
			return (R) getSVCDataFloats("MVAr");
		case SvcOOS:
			return (R) returnFalse(_svcCSV.getRowCount());
		case SvcQS:
			return (R) getSVCDataFloats("MVArSetPoint");
		case SvcQMIN:
			return (R) _svcCSV.getFloats("MinMVAr");
		case SvcQMAX:
			return (R) _svcCSV.getFloats("MaxMVAr");
		case SvcAVR:
			return (R) getSvcAVR();
		case SvcVS:
			return (R) getSVCDataFloats("VoltageSetpoint");
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
		//Owner
		case OwnerID:
			return (R) _orgCSV.get("ID");
		case OwnerNAME:
			return (R) _orgCSV.get("Name");
		//Island
		case IslandID:
		case IslandNAME:
			String[] ids = new String[_m.getIslands().size()];
			for(int i = 0; i < ids.length; ++i) { ids[i] = ""+i; }
			return (R) ids;
		case IslandFREQ:
			return (R) returnFalseNumber(_m.getIslands().size());
		case IslandEGZSTATE:
			return null;
		//Station
		case StationID:
			return (R) _substationCSV.get("ID");
		case StationNAME:
			return (R) _substationCSV.get("Name");
		//Voltage Level
		case VlevID:
			return (R) returnAsString(_vlevFloat);
		case VlevNAME:
			return (R) returnAsString(_vlevFloat);
		case VlevBASKV:
			return (R) _vlevFloat;
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
			return (R) getLineB();
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
			return (R) getSeriesCapData("FromMW");
		case SercapPTO:
			return (R) getSeriesCapData("ToMW");
		case SercapQFROM:
			return (R) getSeriesCapData("FromMVAr");
		case SercapQTO:
			return (R) getSeriesCapData("ToMVAr");
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
		case SerreacQTO:
			return (R) getSeriesReacData("ToMVAr");
		case SerreacQFROM:
			return (R) getSeriesReacData("FromMVAr");
		case SerreacPTO:
			return (R) getSeriesReacData("ToMW");
		case SerreacPFROM:
			return (R) getSeriesReacData("FromMW");
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
			return (R) listToArray(_phaseShifterIDs);
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
			if(_transformerMap == null) buildTransformerMaps();
			return (R) returnFalseNumber(_phaseShifterIDs.size());
		case PhashBMAG:
			return (R) getTransformerDataFloats("Bmag", "winding", false);
		case PhashANG:
			return (R) getTransformerDataFloats("PhaseShift", "phasecase", false);
		case PhashTAPFROM:
		case PhashTAPTO:
			return (R) returnFalseNumber(_phaseShifterIDs.size());
		case PhashCTRLMODE:
			return (R) getPhaseCtrlMode();
		case PhashRATLT:
			return (R) getTransformerDataFloats("NormalOperatingLimit", "winding", false);
		//Transformer
		case TfmrID:
			//Build maps if they don't exist
			if(_transformerMap == null) buildTransformerMaps();
			return (R) listToArray(_transformerIDs);
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
			if(_transformerMap == null) buildTransformerMaps();
			System.out.println("[TfmrGMAG] called");
			return (R) returnFalseNumber(_transformerIDs.size());
		case TfmrBMAG:
			return (R) getTransformerDataFloats("Bmag", "winding", true);
		case TfmrANG:
			return (R) returnFalseNumber(_transformerIDs.size());
		case TfmrTAPFROM:
			return (R) getTransformerRatios(false);
		case TfmrTAPTO:
			return (R) getTransformerRatios(true);
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
			return (R) getSwitchData("FromMW");
		case SwQFROM:
			return (R) getSwitchData("FromMVAr");
		case SwPTO:
			return (R) getSwitchData("ToMW");
		case SwQTO:
			return (R) getSwitchData("ToMVAr");
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
	
	private float[] returnFalseNumber(int size)
	{
		float[] data = new float[size];
		Arrays.fill(data, -999);
		
		return data;
	}
	
	private int[] offsetsToKeys(int[] offsets)
	{
		int size = offsets.length;
		int[] keys = new int[size];
		
		for(int i = 0; i < size; ++i)
		{
			keys[i] = offsets[i] + 1;
		}
		
		return keys;
	}
	
	private String[] listToArray(List<String> l)
	{
		String[] sArray = new String[l.size()];
		
		for(int i = 0; i < sArray.length; ++i)
		{
			sArray[i] = l.get(i);
		}
		
		return sArray;
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
	
	private float[] invertValues(float[] origData)
	{
		int size = origData.length;
		float[] data = new float[size];
		
		for(int i = 0; i < size; ++i)
		{
			data[i] = origData[i] * -1;
		}
		
		return data;
	}
	
	private void buildVlev() throws PAModelException
	{
		_vlevMap = new TFloatIntHashMap();
		TIntFloatMap tempMap = new TIntFloatHashMap();
		
		float[] kv = _busCSV.getFloats("NominalKV");
		int offset = 0;
		
		for(int i = 0; i < kv.length; ++i)
		{
			//Check to see if the voltage level exists in the map
			if(!tempMap.containsValue(kv[i]))
			{
				//New level found, add it to the map
				tempMap.put(offset, kv[i]);
				offset++;
			}
		}
		
		//Now that we know how many voltage levels there are we can create proper maps & arrays.
		_vlevFloat = new float[offset];
		for(int i = 0; i < offset; ++i)
		{
			_vlevFloat[i] = tempMap.get(i);
			_vlevMap.put(tempMap.get(i), i);
		}
	}
	
	private void buildOwnerMap() throws PAModelException
	{
		if(_orgCSV == null) loadOwners();
		String[] ownerIDs = _orgCSV.hasCol("ID") ? _orgCSV.get("ID") : new String[0];
		_ownerMap = new TObjectIntHashMap<>(ownerIDs.length);
		
		for(int i = 0; i < ownerIDs.length; ++i)
		{
			_ownerMap.put(ownerIDs[i], i);
		}
			
	}
	
	private void buildAreaMap() throws PAModelException
	{
		if(_areaCSV == null)loadAreas();
		String[] areaIDs = _areaCSV.hasCol("ID") ? _areaCSV.get("ID") : new String[0];
		_areaMap = new TObjectIntHashMap<>(areaIDs.length);
		
		for(int i = 0; i < areaIDs.length; ++i)
		{
			_areaMap.put(areaIDs[i], i);
		}	
	}
	
	private void buildBusIndexes() throws PAModelException
	{
		if(_areaMap == null) buildAreaMap();
		if(_ownerMap == null) buildOwnerMap();
		if(_stationOffsetMap == null) buildSubstationMap();
		if(_busCSV == null) loadBuses();
		
		String[] busStationIDs = _busCSV.hasCol("Substation") ? _busCSV.get("Substation") : new String[0];
		String[] stationAreaIDs = _substationCSV.hasCol("ControlArea") ? _substationCSV.get("ControlArea") : new String[0];
		String[] stationOwnerIDs = _substationCSV.get("Organization");
		_stationAreaIndex = new int[stationAreaIDs.length];
		_stationOwnerIndex = new int[stationAreaIDs.length];
		_busAreaIndex = new int[busStationIDs.length];
		_busOwnerIndex = new int[busStationIDs.length];
		
		if(stationOwnerIDs == null)
		{
			//Create station indexes
			for(int i = 0; i < stationAreaIDs.length; ++i)
			{
				_stationAreaIndex[i] = _areaMap.get(stationAreaIDs[i]);
				_stationOwnerIndex[i] = 0;
			}
			//Create bus indexes
			for(int i = 0; i < busStationIDs.length; ++i)
			{
				_busAreaIndex[i] = _stationAreaIndex[_stationOffsetMap.get(busStationIDs[i])];
				_busOwnerIndex[i] = 1;
			}
		}
		else
		{
			//Create station indexes
			for(int i = 0; i < stationAreaIDs.length; ++i)
			{
				_stationAreaIndex[i] = _areaMap.get(stationAreaIDs[i]);
				_stationOwnerIndex[i] = _ownerMap.get(stationOwnerIDs[i]);
			}
			//Create bus indexes
			for(int i = 0; i < busStationIDs.length; ++i)
			{
				_busAreaIndex[i] = _stationAreaIndex[_stationOffsetMap.get(busStationIDs[i])];
				_busOwnerIndex[i] = _stationOwnerIndex[_stationOffsetMap.get(busStationIDs[i])];;
			}
		}
		
	}
	
	private void buildBusStationIndex() throws PAModelException
	{
		if(_stationOffsetMap == null) buildSubstationMap();
		if(_busCSV == null) loadBuses();
		String[] substationIDs = _busCSV.hasCol("Substation") ? _busCSV.get("Substation") : new String[0];
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
			busVlev[i] = _vlevMap.get(kv[i]);
		}
		
		return busVlev;
	}
	
	private boolean[] operableUnderLoad()
	{	
		if(_switchTypeMap == null) buildSwitchMaps();
		boolean[] isOperable = new boolean[_switchCSV.getRowCount()];
		String[] typeIDs = _switchCSV.get("SwitchType");
		
		if(typeIDs == null)
		{
			System.err.println("[PFlowPsmModelBldr] Could not load column \"SwitchType\" from Switch.csv");
			Arrays.fill(isOperable, false);
		}
		else
		{
			for(int i = 0; i < typeIDs.length; ++i)
			{
				isOperable[i] = _typeIsOperable[_switchTypeMap.get(typeIDs[i])];
			}
		}
		
		return isOperable;
	}
	
	private Gen.Mode[] getGenMode() throws PAModelException
	{
		if(_genCSV == null) loadGens();
		String[] opMode = _genCaseCSV.get("GeneratorOperatingMode");
		String[] id = _genCSV.get("ID");
		int n = _genCSV.getRowCount();
		Gen.Mode genModes[] = new Gen.Mode[n];
		for(int i = 0; i < n; ++i)
		{
			int cx = this._genCaseMap.get(id[i]);
			genModes[i] = Gen.Mode.valueOf(opMode[cx].toUpperCase());
		}
		
		return genModes;
	}
	
	private boolean[] getGenAVRMode() throws PAModelException
	{
		String[] genAVR = getGenDataString("AVRMode","synchcase");
		boolean[] avr = new boolean[genAVR.length];
		
		for(int i = 0; i < genAVR.length; ++i)
		{
			avr[i] = (genAVR[i].toLowerCase().equals("off"))?false:true;
		}
		
		return avr;
	}
	
	private boolean[] getSvcAVR()
	{
		String[] svcAVR = getSVCDataStrings("Mode");
		boolean[] avr = new boolean[svcAVR.length];
		
		for(int i = 0; i < svcAVR.length; ++i)
		{
			avr[i] = svcAVR[i].toLowerCase().equals("volt")?true:false;
		}
		
		return avr;
	}
	
	private float[] getLineB()
	{
		float[] lineB = _lineCSV.getFloats("Bch");
		float[] data = new float[lineB.length];
		
		for(int i = 0; i < lineB.length; ++i)
		{
			data[i] = lineB[i] / 2f;
		}
		
		return data;
	}
	
	private float[] getLoadCaseData(String col)
	{
		String[] loadIDs = _loadCSV.get("ID");
		float[] data = new float[loadIDs.length];
		float[] caseData = _loadCaseCSV.getFloats(col);
		if(caseData == null) 
		{
			System.err.println("[PFlowPsmModelBldr] Error loading load case column \"\""+col+"\"\". Does it exist in the CSV?");
			Arrays.fill(data, -999);
		}
		else
		{
			//check to see if the hashmap has been created yet;
			if(_loadCaseMap == null) buildLoadMap();
			
			for(int i = 0; i < data.length; ++i)
			{		
				data[i] = caseData[_loadCaseMap.get(loadIDs[i])];
			}
		}
		
		return data;
	}
	
	private float[] getShuntReacData(String col)
	{
		String[] shuntIDs = _shuntReacCSV.get("ID");
		float[] unsortedData = _shuntReacCaseCSV.getFloats(col);
		float[] data = new float[shuntIDs.length];
		//check to see if the hashmap has been created yet;
		if(_shuntReacCaseMap == null) buildShuntReacMap();
		
		if(unsortedData == null) 
		{
			System.err.println("[PFlowPsmModelBldr] Error loading shunt reactor case column \""+col+"\". Does it exist in the CSV?");
			Arrays.fill(data, -999);
		}
		else
		{
			for(int i = 0; i < shuntIDs.length; ++i)
			{
				data[i] = unsortedData[_shuntReacCaseMap.get(shuntIDs[i])];
			}
		}

		return data;
	}
	
	private float[] getShuntCapData(String col)
	{
		String[] shuntIDs = _shuntCapCSV.get("ID");
		float[] unsortedData = _shuntCapCaseCSV.getFloats(col);
		float[] data = new float[shuntIDs.length];
		//check to see if the hashmap has been created yet;
		if(_shuntCapCaseMap == null) buildShuntCapMap();
		
		if(unsortedData == null) 
		{
			System.err.println("[PFlowPsmModelBldr] Error loading shunt capacitor case column \""+col+"\". Does it exist in the CSV?");
			Arrays.fill(data, -999);
		}
		else
		{
			for(int i = 0; i < shuntIDs.length; ++i)
			{
				data[i] = unsortedData[_shuntCapCaseMap.get(shuntIDs[i])];
			}
		}
		
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
			busIDs[_genCaseMap.get(genIDs[i])] = unsortedBuses[i];
		}
		
		return getBusesById(busIDs);
	}
	
	private float[] getSwitchData(String col)
	{
		if(_switchCaseMap == null) buildSwitchMaps();
		
		String[] switchIDs = _switchCSV.get("ID");
		float[] unsortedData = _switchCaseCSV.getFloats(col);
		float[] data = new float[switchIDs.length];
		
		if(unsortedData == null)
		{
//			System.err.println("[PFlowPsmModelBldr] Unable to load column \""+col+"\" from PsmCaseSwitch.csv");
			Arrays.fill(data, -999);
		}
		else
		{
			for(int i = 0; i < switchIDs.length; ++i)
			{
				data[i] = unsortedData[_switchCaseMap.get(switchIDs.length)];
			}
		}
		
		return data;
	}
	
	private float[] getGenDataFloat(String col, String csv)
	{
		String[] genIDs = _genCSV.get("ID");
		float[] unsortedData;
		float[] data = new float[_genCaseCSV.getRowCount()];
		
		//Build maps if they are empty
		if(_genCaseMap == null || _genToSynchMap == null) buildGeneratorMaps();
		
		//Figure out what csv we are dealing with
		if(csv.toLowerCase().equals("synch"))
		{
			unsortedData = _synchMachineCSV.getFloats(col);
			if(unsortedData == null) 
			{
//				System.err.println("[PFlowPsmModelBldr] Error loading synchronous machine column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, -999);
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
//				System.err.println("[PFlowPsmModelBldr] Error loading synchronous machine case column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, -999);
			}
			else
			{
				for(int i = 0; i < genIDs.length; ++i)
				{
					data[i] = unsortedData[_syncCasehMap.get(synchIDs[_genToSynchMap.get(genIDs[i])])];
				}
			}
		}
		else if(csv.toLowerCase().equals("gencase"))
		{
			unsortedData = _genCaseCSV.getFloats(col);
			if(unsortedData == null) 
			{
//				System.err.println("[PFlowPsmModelBldr] Error loading generator case column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, -999);
			}
			else
			{
				for (int i = 0; i < genIDs.length; ++i)
				{
					data[i] = unsortedData[_genCaseMap.get(genIDs[i])];
				}
			}
		}
		else if(csv.toLowerCase().equals("curve"))
		{
			unsortedData = _reacCapCurveCSV.getFloats(col);
			String[] synchIDs = _synchMachineCSV.get("ID");
			
			if(unsortedData == null) 
			{
//				System.err.println("[PFlowPsmModelBldr] Error loading reactive capability curve column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, -999);
			}
			else
			{
				
				for (int i = 0; i < genIDs.length; ++i)
				{
					data[i] = unsortedData[_synchToCurveMap.get(synchIDs[_genToSynchMap.get(genIDs[i])])];
				}
			}
		}
		else
		{
			Arrays.fill(data, -999);
			return data;
		}
		
		return data;
	}
	
	private String[] getGenDataString(String col, String csv)
	{
		String[] genIDs = _genCSV.get("ID");
		String[] unsortedData;
		String[] data = new String[_genCaseCSV.getRowCount()];
		
		//Build maps if they are empty
		if(_genCaseMap == null || _genToSynchMap == null) buildGeneratorMaps();
		
		//Figure out what csv we are dealing with
		if(csv.toLowerCase().equals("synch"))
		{
			unsortedData = _synchMachineCSV.get(col);
			if(unsortedData == null)
			{
//				System.err.println("[PFlowPsmModelBldr] Error loading synchronous machine column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, "null");
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
			unsortedData = _synchCaseCSV.get(col);
			if(unsortedData == null) 
			{
//				System.err.println("[PFlowPsmModelBldr] Error loading synchronous machine case column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, "null");
			}
			else
			{				
				for(int i = 0; i < genIDs.length; ++i)
				{
					data[i] = unsortedData[_syncCasehMap.get(synchIDs[_genToSynchMap.get(genIDs[i])])];
				}
			}
		}
		else if(csv.toLowerCase().equals("gencase"))
		{
			unsortedData = _genCaseCSV.get(col);
			if(unsortedData == null) 
			{
//				System.err.println("[PFlowPsmModelBldr] Error loading generator case column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, "null");
			}
			else
			{
				for (int i = 0; i < genIDs.length; ++i)
				{
					data[i] = unsortedData[_genCaseMap.get(genIDs[i])];
				}				
			}
		}
		else return null;
		
		return data;
	}
	
	private float[] getLineCaseData(String col)
	{
		if(_lineCaseMap == null) buildLineMap();
	
		String[] ids = _lineCSV.get("ID");
		float[] unsortedData = _lineCaseCSV.getFloats(col);
		float[] data = new float[ids.length];
		if(unsortedData == null) 
		{
//			System.err.println("[PFlowPsmModelBldr] Error loading line case column \""+col+"\". Does it exist in the CSV?");
			Arrays.fill(data, -999);
		}
		else
		{
			for(int i = 0; i < ids.length; ++i)
			{
				if (_lineCaseMap.containsKey(ids[i]))
					data[i] = unsortedData[_lineCaseMap.get(ids[i])];
			}
		}
		
		return data;
	}
	
	private float[] getSeriesReacData(String col)
	{
		if(_seriesReacCaseMap == null) buildSeriesReactorMap();
		String[] ids = _seriesReacCSV.get("ID");
		float[] unsortedData = _seriesReacCaseCSV.getFloats(col);
		float[] data = new float[ids.length];
		
		if(unsortedData == null)
		{
//			System.err.println("[PFlowPsmModelBldr] Error loading series reactor case column \""+col+"\". Does it exist in the CSV?");
			Arrays.fill(data, -999);
		}
		else
		{
			for(int i = 0; i < ids.length; ++i)
			{
				data[i] = unsortedData[_seriesReacCaseMap.get(ids[i])];
			}
		}
		
		return data;
	}
	
	private float[] getSeriesCapData(String col)
	{
		if(_seriesCapCaseMap == null) buildSeriesCapacitorMap();
		String[] ids = _seriesCapCSV.get("ID");
		float[] unsortedData = _seriesCapCaseCSV.getFloats(col);
		float[] data = new float[ids.length];
		
		if(unsortedData == null)
		{
			System.err.println("[PFlowPsmModelBldr] Error loading series capacitor case column \""+col+"\". Does it exist in the CSV?");
			Arrays.fill(data, -999);
		}
		else
		{
			for(int i = 0; i < ids.length; ++i)
			{
				data[i] = unsortedData[_seriesCapCaseMap.get(ids[i])];
			}
		}
		
		return data;
	}
	
	private float[] getSVCDataFloats(String col)
	{
		//Build maps if they don't exist
		if(_svcCaseMap == null) buildSVCMap();
		String[] ids = _svcCaseCSV.get("ID");
		float[] unsortedData = _svcCaseCSV.getFloats(col);
		float[] data = new float[ids.length];
		if(unsortedData == null) 
		{
			System.err.println("[PFlowPsmModelBldr] Error loading svc case column \""+col+"\". Does it exist in the CSV?");
			Arrays.fill(data, -999);
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
	
	private String[] getSVCDataStrings(String col)
	{
		//Build maps if they don't exist
		if(_svcCaseMap == null) buildSVCMap();
		String[] ids = _svcCaseCSV.get("ID");
		String[] unsortedData = _svcCaseCSV.get(col);
		String[] data = new String[ids.length];
		if(unsortedData == null) 
		{
			System.err.println("[PFlowPsmModelBldr] Error loading line case column \""+col+"\". Does it exist in the CSV?");
			Arrays.fill(data, -999);
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
	
	private float[] getTransformerRatios(boolean isTo)
	{
		float[] data = new float[_transformerIDs.size()];
		float[] ratios = _ratioTapChgCaseCSV.getFloats("Ratio");
		String[] ratioIDs = _ratioTapChgCSV.get("ID");
		String[] wdgIDs = _ratioTapChgCSV.get("TransformerWinding");
		String[] tfmrIDs = _tfmrWindingCSV.get("Transformer");
		String[] tapNodeIDs = _ratioTapChgCSV.get("TapNode");
		String nodeCol = isTo ? "Node2":"Node1";
		String[] tfmrNode = _tfmrWindingCSV.get(nodeCol);
		
		for(int i = 0; i < ratios.length; ++i)
		{
			//check if the current tapNodeIDs is the to or from node
			if(tfmrNode[_windingMap.get(wdgIDs[i])].equals(tapNodeIDs[i]))
			{
				data[_transformerMap.get(tfmrIDs[_windingMap.get(wdgIDs[i])])] = ratios[_ratioCaseMap.get(ratioIDs[i])];
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
				Arrays.fill(data, "null");
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
				Arrays.fill(data, "null");
			}
			else
			{
				for(int i = 0; i < ids.size(); ++i)
				{
					data[i] = unsortedData[_wdgToTfmrMap.get(ids.get(i))];
				}
			}
		}
		else if(csv.toLowerCase().equals("phasecase"))
		{
			unsortedData = _phaseCaseCSV.get(col);
			String[] phaseCaseIDs = _phaseCaseCSV.get("ID");
			if(unsortedData == null)
			{
				System.err.println("[PFlowPsmModelBldr] Error phase shifter case column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, "null");
			}
			else
			{
				for(int i = 0; i < ids.size(); ++i)
				{
					data[_tfmrPhaseTapMap.get(phaseCaseIDs[i])] = unsortedData[i];
				}
			}
		}
		else if(csv.toLowerCase().equals("ratiocase"))
		{
			unsortedData = _ratioTapChgCaseCSV.get(col);
			String[] ratioCaseIDs = _ratioTapChgCaseCSV.get("ID");
			if(unsortedData == null)
			{
				System.err.println("[PFlowPsmModelBldr] Error ratio tap change case column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, "null");
			}
			else
			{
				for(int i = 0; i < ids.size(); ++i)
				{
					data[_tfmrRatioTapMap.get(ratioCaseIDs[i])] = unsortedData[i];
				}
			}
		}
		else
		{
			System.err.println("[PFlowPsmModelBldr] No functionality setup for csv \""+csv+"\"");
			Arrays.fill(data, "null");
		}
		
		return data;
	}
	
	private boolean[] getTransformerDataBools(String col, String csv, boolean isTfmr)
	{	
		String[] unsortedData = getTransformerDataStrings(col, csv, isTfmr);
		boolean[] data = new boolean[unsortedData.length];
		
		for(int i = 0; i < data.length; ++i)
		{
			data[i] = (unsortedData[i].toLowerCase().equals("true"))?true:false;
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
				Arrays.fill(data, -999);
			}
			else
			{
				for(int i = 0; i < ids.size(); ++i)
				{
					data[i] = unsortedData[_transformerMap.get(ids.get(i))];
				}				
			}
		}
		else if(csv.toLowerCase().equals("phasecase"))
		{
			unsortedData = _phaseCaseCSV.getFloats(col);
			String[] phaseCaseIDs = _phaseCaseCSV.get("ID");
			if(unsortedData == null)
			{
				System.err.println("[PFlowPsmModelBldr] Error phase shifter case column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, -999);
			}
			else
			{
				for(int i = 0; i < ids.size(); ++i)
				{
					data[_tfmrPhaseTapMap.get(phaseCaseIDs[i])] = unsortedData[i];
				}
			}
		}
		else if(csv.toLowerCase().equals("ratiocase"))
		{
			unsortedData = _ratioTapChgCaseCSV.getFloats(col);
			String[] ratioCaseIDs = _ratioTapChgCaseCSV.get("ID");
			if(unsortedData == null)
			{
				System.err.println("[PFlowPsmModelBldr] Error ratio tap change case column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, -999);
			}
			else
			{
				for(int i = 0; i < ids.size(); ++i)
				{
					data[_tfmrRatioTapMap.get(ratioCaseIDs[i])] = unsortedData[i];
				}
			}
		}
		else if(csv.toLowerCase().equals("winding"))
		{
			unsortedData = _tfmrWindingCSV.getFloats(col);
			if(unsortedData == null) 
			{
				System.err.println("[PFlowPsmModelBldr] Error loading winding column \""+col+"\". Does it exist in the CSV?");
				Arrays.fill(data, -999);
			}
			else
			{
				for(int i = 0; i < ids.size(); ++i)
				{
					data[i] = unsortedData[_wdgToTfmrMap.get(ids.get(i))];
				}				
			}
		}
		else
		{
			System.err.println("[PFlowPsmModelBldr] No functionality added for \""+csv+"\"");
		}
		return data;
	}
	
	private PhaseShifter.ControlMode[] getPhaseCtrlMode()
	{
		boolean[] baseData = getTransformerDataBools("ControlStatus", "phaseCase", false);
		PhaseShifter.ControlMode[] data = new PhaseShifter.ControlMode[baseData.length];
		
		for(int i = 0; i < data.length; ++i)
		{
			data[i] = baseData[i] ? PhaseShifter.ControlMode.FixedMW:PhaseShifter.ControlMode.FixedAngle;
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
			Arrays.fill(data, -999);
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
				String wid = wdgIDs[_wdgToTfmrMap.get(ids.get(i))];
				if (_windingCaseMap.containsKey(wid))
					data[i] = unsortedData[_windingCaseMap.get(wid)];
			}			
		}
		
		return data;
	}
	
	private void buildTransformerMaps()
	{	
		String[] ratioCaseIDs 	= _ratioTapChgCaseCSV.get("ID");
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
		_wdgInRatioMap		= new TObjectIntHashMap<>(wdgInRatioIDs.length);
		_wdgInPhaseMap 		= new TObjectIntHashMap<>(phaseTapIDs.length);
		_wdgToTfmrMap		= new TObjectIntHashMap<>(windingIDs.length); //Key = transformer, value = Winding Offset
		_tfmrPhaseTapMap	= new TObjectIntHashMap<>(phaseTapIDs.length);
		_ratioCaseMap 		= new TObjectIntHashMap<>(ratioTapIDs.length);
		
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
//			System.out.println("[buildTransformerMaps] _windingCaseMap.put("+wdgCaseIDs[i]+", "+i+")");
			if(wdgCaseIDs.length > 0) _windingCaseMap.put(wdgCaseIDs[i], i);
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
			_ratioCaseMap.put(ratioCaseIDs[i], i);
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
		String[] subIDs = _substationCSV.hasCol("ID") ? _substationCSV.get("ID") : new String[0];
		_stationOffsetMap = new TObjectIntHashMap<>(subIDs.length);
		
		for(int i = 0; i < subIDs.length; ++i)
		{
			_stationOffsetMap.put(subIDs[i], i);
		}
	}
	
	private void buildSeriesReactorMap()
	{
		String[] caseIDs = _seriesReacCaseCSV.get("ID");
		
		_seriesReacCaseMap = new TObjectIntHashMap<>(caseIDs.length);
		
		for(int i = 0; i < caseIDs.length; ++i)
		{
			_seriesReacCaseMap.put(caseIDs[i], i);
		}
	}
	
	private void buildSeriesCapacitorMap()
	{
		String[] caseIDs = _seriesCapCaseCSV.get("ID");
		
		_seriesCapCaseMap = new TObjectIntHashMap<>(caseIDs.length);
		
		for(int i = 0; i < caseIDs.length; ++i)
		{
			_seriesCapCaseMap.put(caseIDs[i], i);
		}
	}
	
	private void buildGeneratorMaps()
	{
		String[] genCaseIDs = _genCaseCSV.get("ID");
		String[] synchCaseIDs = _synchCaseCSV.get("ID");
		String[] synchGenIDs = _synchMachineCSV.get("GeneratingUnit");
		String[] reacSynchIDs = _reacCapCurveCSV.get("SynchronousMachine");
		
		_genCaseMap = new TObjectIntHashMap<>(genCaseIDs.length);
		_syncCasehMap = new TObjectIntHashMap<>(synchCaseIDs.length);
		_genToSynchMap = new TObjectIntHashMap<>(genCaseIDs.length);
		_genToSynchCaseMap = new TObjectIntHashMap<>(genCaseIDs.length);
		_synchToCurveMap = new TObjectIntHashMap<>(synchCaseIDs.length);
		
		for(int i = 0; i < genCaseIDs.length; ++i)
		{
			_genCaseMap.put(genCaseIDs[i], i); // Takes generating unit ID
			_syncCasehMap.put(synchCaseIDs[i], i); // Takes synch machine ID
			_genToSynchMap.put(synchGenIDs[i], i); //Takes generating ID
			if(i < reacSynchIDs.length)
				_synchToCurveMap.put(reacSynchIDs[i], i); //Takes synch machine ID
		}
	}
	
	private void buildSwitchMaps()
	{
		String[] caseIDs = _switchCaseCSV.get("ID");
		String[] typeIDs = _switchTypeCSV.get("ID");
		String[] open = _switchTypeCSV.get("OpenUnderLoad");
		String[] close = _switchTypeCSV.get("CloseUnderLoad");
		_typeIsOperable = new boolean[typeIDs.length];
		_switchCaseMap = new TObjectIntHashMap<>(caseIDs.length);
		_switchTypeMap = new TObjectIntHashMap<>(_switchTypeCSV.getRowCount());
		
		for(int i = 0; i < caseIDs.length; ++i)
		{
			_switchCaseMap.put(caseIDs[i], i);
		}
		
		for(int i = 0; i < typeIDs.length; ++i)
		{
			_switchTypeMap.put(typeIDs[i], i);
			//Build a boolean array at this time to figure out if a switch with this type is operable under load
			_typeIsOperable[i] = (open[i].toLowerCase().equals("true") && close[i].toLowerCase().equals("true"))?true:false;
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
		if (_switchCaseMap == null) buildSwitchMaps();
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
		
		_loadCaseMap = new TObjectIntHashMap<>(caseIDs.length);
		for(int i = 0; i < caseIDs.length; ++i)
		{
			_loadCaseMap.put(caseIDs[i], i);
		}

	}
	
	private void buildShuntReacMap()
	{
		String[] caseIDs = _shuntReacCaseCSV.get("ID");
		
		_shuntReacCaseMap = new TObjectIntHashMap<>(caseIDs.length);
		for(int i = 0; i < caseIDs.length; ++i)
		{
			_shuntReacCaseMap.put(caseIDs[i], i);
		}
	}
	
	private void buildShuntCapMap()
	{
		String[] caseIDs = _shuntCapCaseCSV.get("ID");
		
		_shuntCapCaseMap = new TObjectIntHashMap<>(caseIDs.length);
		for(int i = 0; i < caseIDs.length; ++i)
		{
			_shuntCapCaseMap.put(caseIDs[i], i);
		}
	}
	
	private void buildLineMap()
	{
		String[] caseIDs = _lineCaseCSV.get("ID");
		
		_lineCaseMap = new TObjectIntHashMap<>(caseIDs.length);
		for(int i = 0; i < caseIDs.length; ++i)
		{
			_lineCaseMap.put(caseIDs[i], i);
		}
	}
	
	private int[] getBusesById(String[] ids) throws PAModelException 
	{
		if(_busCSV == null) loadBuses();
		int[] indexes = _m.getBuses().getIndexesFromIDs(ids);
		return indexes;
	}

}
