package com.powerdata.openpa;


import gnu.trove.impl.hash.TObjectHash;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import com.powerdata.openpa.Gen.Type;
import com.powerdata.openpa.tools.QueryString;
import com.powerdata.openpa.tools.SimpleCSV;
import com.powerdata.openpa.impl.AreaListI;
import com.powerdata.openpa.impl.BusListI;
import com.powerdata.openpa.impl.GenListI;
import com.powerdata.openpa.impl.LineListI;
import com.powerdata.openpa.impl.LoadListI;
import com.powerdata.openpa.impl.OwnerListI;
import com.powerdata.openpa.impl.PAModelI;
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
import com.powerdata.pd3.PDDB;
import com.powerdata.pd3.PDDBException;

public class PFlowPsmModelBldr extends PflowModelBuilder 
{
	File _dir;
	
	//CSV files
	SimpleCSV _busCSV;
	SimpleCSV _switchCSV;
	SimpleCSV _lineCSV;
	SimpleCSV _areaCSV;
	SimpleCSV _stationCSV;
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
	
	//Case CSV files
	SimpleCSV _loadCaseCSV;
	SimpleCSV _shuntCapCaseCSV;
	SimpleCSV _shuntReacCaseCSV;
	SimpleCSV _genCaseCSV;
	SimpleCSV _synchCaseCSV;
	SimpleCSV _genToSynchCSV;
	
	
	//Not yet importing
	SimpleCSV _voltageRelayCSV;
	SimpleCSV _curRelayCSV;
	SimpleCSV _freqRelayCSV;
	SimpleCSV _loadAreaCSV;
	SimpleCSV _modelParmsCSV;
	SimpleCSV _orgCSV;
	SimpleCSV _primeMoverCSV;
	SimpleCSV _ratioTapChgCSV;
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
	TIntIntMap _vlevMap;
	
	//Arrays
	int[] _busVlev;
	
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
		System.out.println("[loadPrep]");
		//Load prep does nothing, yet
		//This is where PD3 calls columnPrep
	}

	@Override
	protected BusListI loadBuses() throws PAModelException 
	{
		System.out.println("[loadBuses]");
		try 
		{
			System.out.println("Loading buses");
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
		System.out.println("[loadSwitches]");
		try 
		{
			_switchCSV = new SimpleCSV(new File(_dir, "Switch.csv"));
			_switchTypeCSV = new SimpleCSV(new File(_dir, "SwitchType.csv"));
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
		System.out.println("[loadLines]");
		try
		{
			_lineCSV = new SimpleCSV(new File(_dir, "Line.csv"));
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
		System.out.println("[loadAreas]");
		//TODO Incomplete
		//Don't currently have all the necessary data
		try
		{
			_areaCSV = new SimpleCSV(new File(_dir, "ControlArea.csv"));
			//AreaListI(PAModelI model, int[] busref, int narea)
			return AreaListI.Empty;
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected OwnerListI loadOwners() throws PAModelException 
	{
		System.out.println("[loadOwners] Returns empty");
		// TODO Incomplete
		// Couldn't find a csv for this one in the document
		return OwnerListI.Empty;
	}

	@Override
	protected StationListI loadStations() throws PAModelException 
	{
		System.out.println("[loadStations] Returns empty");
		//TODO Incomplete
		//Don't currently have all the necessary data
		try
		{
			_stationCSV = new SimpleCSV(new File(_dir, "Substation.csv"));
			return StationListI.Empty;
		}
		catch (IOException e) 
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected VoltageLevelListI loadVoltageLevels() throws PAModelException 
	{
		System.out.println("[loadVoltageLevels]");
		if(_busCSV == null) 
		{
			System.out.println("[loadVoltageLevels] _busCSV is null");
			loadBuses();
		}

		//(PAModelI model, int[] busref, int nvl)
		int[] vlev = buildVlev();
		System.out.println("[loadVoltageLevels] built vlev with length of "+vlev.length);
		System.out.println("[loadVoltageLevels] _vlevMap.size = "+_vlevMap.size());
		return new VoltageLevelListI(_m, vlev, _vlevMap.size());
	}

	@Override
	protected IslandList loadIslands() throws PAModelException 
	{
		System.out.println("[loadIslands] Returns empty");
		// TODO Incomplete
		//Didn't see an island csv in the document
		// PD3ModelBldr looks like it creates a view using bus keys 
		return IslandList.Empty;
	}

	@Override
	protected SVCListI loadSVCs() throws PAModelException 
	{
		System.out.println("[loadSVCs] Done");
		try
		{
			_svcCSV = new SimpleCSV(new File(_dir, "SVC.csv"));
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
		System.out.println("[loadSwitchedShunts] Returns empty list");
		// TODO Incomplete
		// Didn't see a csv in doc
		// PD3 builder returns an empty list
		
		return SwitchedShuntListI.Empty;
	}

	@Override
	protected TwoTermDCLineListI loadTwoTermDCLines() throws PAModelException 
	{
		System.out.println("[loadTwoTermDCLines] Returns empty list");
		// TODO Incomplete
		// Didn't see a csv in doc
		// PD3 builder returns an empty list
		return TwoTermDCLineListI.Empty;
	}

	@Override
	protected ShuntCapListI loadShuntCapacitors() throws PAModelException 
	{
		System.out.println("[loadShuntCapacitors] Done");
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
		System.out.println("[loadShuntReactors] Done");
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
		System.out.println("[loadLoads] Done");
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
		System.out.println("[loadGens] Done");
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
		System.out.println("[loadSeriesCapacitors] Done");
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
		System.out.println("[loadSeriesReactors] Done");
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
		System.out.println("[loadPhaseShifters] Done");
		try
		{
			_phaseTapChgCSV = new SimpleCSV(new File(_dir, "PhaseTapChanger.csv"));
			return new PhaseShifterListI(_m, _phaseTapChgCSV.getRowCount());
		}
		catch (IOException e)
		{
			throw new PAModelException(e);
		}
	}

	@Override
	protected TransformerListI loadTransformers() throws PAModelException 
	{
		System.out.println("[loadTransformers] Done");
		try
		{
			_transformerCSV = new SimpleCSV(new File(_dir, "Transformer.csv"));
			_tfmrWindingCSV = new SimpleCSV(new File(_dir, "TransformerWinding.csv"));
			return new TransformerListI(_m, _transformerCSV.getRowCount());
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
		
		//Planning on using getRowCount to see if keys.length = numRows.
		//If they aren't the same then I'll have to loop through the keys and build the array myself
		switch(ctype)
		{
		//Bus
		case BusID:
			return (R)_busCSV.get("ID");
		case BusNAME:
			return (R)_busCSV.get("Name");
		case BusVM: //Returns float
		case BusVA:	//Returns float
			return null;
		case BusFREQSRCPRI:
			return (R) _busCSV.getInts("FrequencySourcePriority");
		case BusAREA: //Returns area object 
		case BusOWNER:
		case BusSTATION:
			return null;
		case BusVLEV:
			if(_busVlev == null) buildVlev();
			return (R) _busVlev;
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
			return null;
		case GenTYPE:
			String[] genList = _genCSV.get("GeneratingUnitType");
			Type[] genType = new Type[genList.length];
			for(int i = 0; i < genList.length; i++)
			{
				genType[i] = Type.valueOf(genList[i]);
			}
			return (R) genType;
		case GenMODE: // Mode
			return (R) getGenMode();
		case GenOPMINP: // float
			return (R) _genCSV.getFloats("MinOperatingMW");
		case GenOPMAXP: // float
			return (R) _genCSV.getFloats("MaxOperatingMW");
		case GenMINQ:
		case GenMAXQ: // float
		case GenPS: // float
		case GenQS: // float
		case GenAVR: 
		case GenVS: // float
		case GenREGBUS: // Bus
			return null;
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
			return null;
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
			return null;
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
		case SvcOOS:
		case SvcQS:
			return null;
		case SvcQMIN:
			return (R) _svcCSV.getFloats("MinMVAr");
		case SvcQMAX:
			return (R) _svcCSV.getFloats("MaxMVAr");
		case SvcAVR:
		case SvcVS:
		case SvcSLOPE: // float
			return (R) _svcCSV.getFloats("Slope");
		case SvcREGBUS:
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
		case IslandFREQ:
		case IslandEGZSTATE:
			return null;
		//Station
		case StationID:
			return (R) _stationCSV.get("ID");
		case StationNAME:
			return (R) _stationCSV.get("Name");
		//Voltage Level - No csv
		case VlevID:
			System.out.println("[VlevID]");
			return (R) _vlevMap.keys();
		case VlevNAME:
			System.out.println("[VlevNAME]");
			return (R) _vlevMap.keys();
		case VlevBASKV:
			System.out.println("[VlevBASKV]");
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
		case LinePFROM:
		case LineQFROM:
		case LinePTO:
		case LineQTO:
			return null;
		case LineR:
			return (R) _lineCSV.getFloats("R");
		case LineX:
			return (R) _lineCSV.getFloats("X");
		case LineBFROM:
		case LineBTO:
			return null;
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
		case SercapPFROM:
		case SercapQFROM:
		case SercapPTO:
		case SercapQTO:
			return null;
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
		case SerreacPFROM:
		case SerreacQFROM:
		case SerreacPTO:
			return null;
		case SerreacR:
			return (R) _seriesReacCSV.getFloats("R");
		case SerreacX:
			return (R) _seriesReacCSV.getFloats("X");
		case SerreacRATLT:
			return (R) _seriesReacCSV.getFloats("NormalOperatingLimit");
		//Phase Shifter
		case PhashID:
		case PhashNAME:
		case PhashBUSFROM:
		case PhashBUSTO:
		case PhashOOS:
		case PhashPFROM:
		case PhashQFROM:
		case PhashPTO:
		case PhashQTO:
		case PhashR:
		case PhashX:
		case PhashGMAG:
		case PhashBMAG:
		case PhashANG:
		case PhashTAPFROM:
		case PhashTAPTO:
		case PhashCTRLMODE:
			return null;
		//Transformer
		case TfmrID:
		case TfmrNAME:
		case TfmrBUSFROM:
		case TfmrBUSTO:
		case TfmrOOS:
		case TfmrPFROM:
		case TfmrQFROM:
		case TfmrPTO:
		case TfmrQTO:
		case TfmrR:
		case TfmrX:
		case TfmrGMAG:
		case TfmrBMAG:
		case TfmrANG:
		case TfmrTAPFROM:
		case TfmrTAPTO:
			return null;
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
		case SwPFROM:
		case SwQFROM:
		case SwPTO:
		case SwQTO:
		case SwSTATE:
		case SwOPLD:
			return (R) operableUnderLoad();
		case SwENAB:
			return null;
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
	
	private int[] buildVlev() throws PAModelException
	{
		if (_vlevMap == null) 
		{
			_vlevMap = new TIntIntHashMap();
		}
		if(_busCSV == null) System.out.println("[buildVlev] _busCSV is null");
		float[] kv = _busCSV.getFloats("NominalKV");
		if(_busVlev == null) _busVlev = new int[kv.length];
		int offset = 0;
		
		for(int i = 0; i < kv.length; ++i)
		{
			//Check to see if the voltage level exists in the map
			if(!_vlevMap.containsKey((int) kv[i]))
			{
				System.out.println("[buildVlev] _vlevMap.put("+offset+", "+kv[i]+")");
				_vlevMap.put((int) kv[i], offset);
				offset++;
			}
			//Add the the bus voltage to the array
			_busVlev[i] = _vlevMap.get((int) kv[i]);
		}
		
		System.out.println("[buildVlev] Returning busVlev");
		return _busVlev;
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
	
	private Gen.Mode[] getGenMode()
	{
		String[] gens = _genCSV.get("genControlMode");
		Gen.Mode genModes[] = new Gen.Mode[gens.length];
		
		for(int i = 0; i < gens.length; ++i)
		{
			genModes[i] = null;
		}
		
		return genModes;
	}
	
	private float[] getLoadCaseData(String col)
	{
		String[] loadIDs = _loadCSV.get("ID");
		float[] caseData = _loadCaseCSV.getFloats(col);
		float[] data = new float[loadIDs.length];
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
		
		return data;
	}
	
	private float[] getShuntReacData(String col)
	{
		String[] shuntIDs = _shuntReacCSV.get("ID");
		float[] caseData = _shuntReacCaseCSV.getFloats(col);
		float[] data = new float[shuntIDs.length];
		//check to see if the hashmap has been created yet;
		if(_shuntReacMap == null) buildShuntReacMap();
		
		
		//NOT FINISHED!
		return null;
	}
	
	private float[] getShuntCapData(String col)
	{
		String[] shuntIDs = _shuntCapCSV.get("ID");
		float[] caseData = _shuntCapCaseCSV.getFloats(col);
		float[] data = new float[shuntIDs.length];
		//check to see if the hashmap has been created yet;
		if(_shuntCapMap == null) buildShuntCapMap();
		
		//NOT FINISHED!
		return null;
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
			
			for (int i = 0; i < genIDs.length; ++i)
			{
				data[i] = unsortedData[_genToSynchMap.get(genIDs[i])];
			}
		}
		else if(csv.toLowerCase().equals("synchcase"))
		{
			String[] synchIDs = _synchMachineCSV.get("ID");
			unsortedData = _synchCaseCSV.getFloats(col);
			for(int i = 0; i < genIDs.length; ++i)
			{
				data[i] = unsortedData[_synchMap.get(synchIDs[_genToSynchMap.get(genIDs[i])])];
			}
		}
		else if(csv.toLowerCase().equals("gencase"))
		{
			unsortedData = _genCaseCSV.getFloats(col);
			
			for (int i = 0; i < genIDs.length; ++i)
			{
				data[i] = unsortedData[_genMap.get(genIDs[i])];
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
			System.out.println("Searching SynchronousMachine.csv for "+col);
			unsortedData = _synchMachineCSV.get(col);
			System.out.println("Got data from the csv");
			
			for (int i = 0; i < genIDs.length; ++i)
			{
				//System.out.println("data["+i+"]: "+unsortedData[_genToSynchMap.get(genIDs[i])]);
				data[i] = unsortedData[_genToSynchMap.get(genIDs[i])];
			}
		}
		else if(csv.toLowerCase().equals("synchcase"))
		{
			System.out.println();
			String[] synchIDs = _synchMachineCSV.get("ID");
			unsortedData = _synchCaseCSV.get(col);
			for(int i = 0; i < genIDs.length; ++i)
			{
				data[i] = unsortedData[_synchMap.get(synchIDs[_genToSynchMap.get(genIDs[i])])];
			}
		}
		else if(csv.toLowerCase().equals("gencase"))
		{
			unsortedData = _genCaseCSV.get(col);
			
			for (int i = 0; i < genIDs.length; ++i)
			{
				data[i] = unsortedData[_genMap.get(genIDs[i])];
			}
		}
		else return null;
		
		System.out.println("Returning data with length "+data.length);
		return data;
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
		System.out.println("Building shunt reac map");
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
		System.out.println("Building shunt cap map");
		String[] caseIDs = _shuntCapCaseCSV.get("ID");
		
		_shuntCapMap = new TObjectIntHashMap<>(caseIDs.length);
		for(int i = 0; i < caseIDs.length; ++i)
		{
			_shuntCapMap.put(caseIDs[i], i);
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
