package com.powerdata.openpa;

import java.lang.reflect.Array;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import com.powerdata.openpa.impl.AreaListI;
import com.powerdata.openpa.impl.BusListI;
import com.powerdata.openpa.impl.GenListI;
import com.powerdata.openpa.impl.ElectricalIslandListI;
import com.powerdata.openpa.impl.LineListI;
import com.powerdata.openpa.impl.LoadListI;
import com.powerdata.openpa.impl.ModelBuilderI;
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

public class CloneModelBuilder extends ModelBuilderI
{
	PAModel _srcmdl;

	@FunctionalInterface
	protected interface DataLoader<R>
	{
		R load() throws PAModelException;
	}

	protected Map<ColumnMeta,DataLoader<?>> _col = new EnumMap<>(ColumnMeta.class);
	Set<ColumnMeta> _local;
	
	public CloneModelBuilder(PAModel srcmdl, Set<ColumnMeta> local)
	{
		_srcmdl = srcmdl;
		_local = local;
	}
	
	@Override
	protected void loadPrep()
	{
		_col.put(ColumnMeta.BusID, () -> _srcmdl.getBuses().getID());
		_col.put(ColumnMeta.BusNAME, () -> _srcmdl.getBuses().getName());
		_col.put(ColumnMeta.BusVM, () -> _srcmdl.getBuses().getVM());
		_col.put(ColumnMeta.BusVA, () -> _srcmdl.getBuses().getVA());
		_col.put(ColumnMeta.BusFREQSRCPRI, () -> _srcmdl.getBuses().getFreqSrcPri());
		_col.put(ColumnMeta.BusAREA, () -> _m.getAreas().getIndexesFromKeys(
			extractKeys(_srcmdl.getBuses().getArea())));
		_col.put(ColumnMeta.BusOWNER, () -> _m.getOwners().getIndexesFromKeys(
			extractKeys(_srcmdl.getBuses().getOwner())));
		_col.put(ColumnMeta.BusSTATION, () -> _m.getStations().getIndexesFromKeys(
			extractKeys(_srcmdl.getBuses().getStation())));
		_col.put(ColumnMeta.BusVLEV, () -> _m.getVoltageLevels().getIndexesFromKeys(
			extractKeys(_srcmdl.getBuses().getVoltageLevel())));

		_col.put(ColumnMeta.GenID, () -> _srcmdl.getGenerators().getID());
		_col.put(ColumnMeta.GenNAME, () -> _srcmdl.getGenerators().getName());
		_col.put(ColumnMeta.GenBUS, () -> extractIndex(_srcmdl.getGenerators().getBus()));
		_col.put(ColumnMeta.GenP, () -> _srcmdl.getGenerators().getP());
		_col.put(ColumnMeta.GenQ, () -> _srcmdl.getGenerators().getQ());
		_col.put(ColumnMeta.GenINSVC, () -> _srcmdl.getGenerators().isInService());
		_col.put(ColumnMeta.GenTYPE, () -> _srcmdl.getGenerators().getType());
		_col.put(ColumnMeta.GenMODE, () -> _srcmdl.getGenerators().getMode());
		_col.put(ColumnMeta.GenOPMINP, () -> _srcmdl.getGenerators().getOpMinP());
		_col.put(ColumnMeta.GenOPMAXP, () -> _srcmdl.getGenerators().getOpMaxP());
		_col.put(ColumnMeta.GenMINQ, () -> _srcmdl.getGenerators().getMinQ());
		_col.put(ColumnMeta.GenMAXQ, () -> _srcmdl.getGenerators().getMaxQ());
		_col.put(ColumnMeta.GenPS, () -> _srcmdl.getGenerators().getPS());
		_col.put(ColumnMeta.GenQS, () -> _srcmdl.getGenerators().getQS());
		_col.put(ColumnMeta.GenAVR, () -> _srcmdl.getGenerators().isRegKV());
		_col.put(ColumnMeta.GenVS, () -> _srcmdl.getGenerators().getVS());
		_col.put(ColumnMeta.GenREGBUS, () -> extractIndex(_srcmdl.getGenerators().getRegBus()));
	
		_col.put(ColumnMeta.LoadID, () -> _srcmdl.getLoads().getID());
		_col.put(ColumnMeta.LoadNAME, () -> _srcmdl.getLoads().getName());
		_col.put(ColumnMeta.LoadBUS, () -> extractIndex(_srcmdl.getLoads().getBus()));
		_col.put(ColumnMeta.LoadP, () -> _srcmdl.getLoads().getP());
		_col.put(ColumnMeta.LoadQ, () -> _srcmdl.getLoads().getQ());
		_col.put(ColumnMeta.LoadINSVC, () -> _srcmdl.getLoads().isInService());
		_col.put(ColumnMeta.LoadPMAX, () -> _srcmdl.getLoads().getMaxP());
		_col.put(ColumnMeta.LoadQMAX, () -> _srcmdl.getLoads().getMaxQ());

		_col.put(ColumnMeta.ShcapID, () -> _srcmdl.getShuntCapacitors().getID());
		_col.put(ColumnMeta.ShcapNAME, () -> _srcmdl.getShuntCapacitors().getName());
		_col.put(ColumnMeta.ShcapBUS, () -> extractIndex(_srcmdl.getShuntCapacitors().getBus()));
		_col.put(ColumnMeta.ShcapP, () -> _srcmdl.getShuntCapacitors().getP());
		_col.put(ColumnMeta.ShcapQ, () -> _srcmdl.getShuntCapacitors().getQ());
		_col.put(ColumnMeta.ShcapINSVC, () -> _srcmdl.getShuntCapacitors().isInService());
		_col.put(ColumnMeta.ShcapB, () -> _srcmdl.getShuntCapacitors().getB());

		_col.put(ColumnMeta.ShreacID, () -> _srcmdl.getShuntReactors().getID());
		_col.put(ColumnMeta.ShreacNAME, () -> _srcmdl.getShuntReactors().getName());
		_col.put(ColumnMeta.ShreacBUS, () -> extractIndex(_srcmdl.getShuntReactors().getBus()));
		_col.put(ColumnMeta.ShreacP, () -> _srcmdl.getShuntReactors().getP());
		_col.put(ColumnMeta.ShreacQ, () -> _srcmdl.getShuntReactors().getQ());
		_col.put(ColumnMeta.ShreacINSVC, () -> _srcmdl.getShuntReactors().isInService());
		_col.put(ColumnMeta.ShreacB, () -> _srcmdl.getShuntReactors().getB());
		
		_col.put(ColumnMeta.SvcID, () -> _srcmdl.getSVCs().getID());
		_col.put(ColumnMeta.SvcNAME, () -> _srcmdl.getSVCs().getName());
		_col.put(ColumnMeta.SvcBUS, () -> extractIndex(_srcmdl.getSVCs().getBus()));
		_col.put(ColumnMeta.SvcP, () -> _srcmdl.getSVCs().getP());
		_col.put(ColumnMeta.SvcQ, () -> _srcmdl.getSVCs().getQ());
		_col.put(ColumnMeta.SvcINSVC, () -> _srcmdl.getSVCs().isInService());
		_col.put(ColumnMeta.SvcQMIN, () -> _srcmdl.getSVCs().getMinQ());
		_col.put(ColumnMeta.SvcQMAX, () -> _srcmdl.getSVCs().getMaxQ());
		_col.put(ColumnMeta.SvcAVR, () -> _srcmdl.getSVCs().isRegKV());
		_col.put(ColumnMeta.SvcVS, () -> _srcmdl.getSVCs().getVS());
		_col.put(ColumnMeta.SvcQS, () -> _srcmdl.getSVCs().getQS());
		_col.put(ColumnMeta.SvcREGBUS, () -> extractIndex(_srcmdl.getSVCs().getRegBus()));
		_col.put(ColumnMeta.SvcSLOPE, () -> _srcmdl.getSVCs().getSlope());
		_col.put(ColumnMeta.SvcOMODE, () -> _srcmdl.getSVCs().getOutputMode());

		_col.put(ColumnMeta.AreaID, () -> _srcmdl.getAreas().getID());
		_col.put(ColumnMeta.AreaNAME, () -> _srcmdl.getAreas().getName());
		
		_col.put(ColumnMeta.OwnerID, () -> _srcmdl.getOwners().getID());
		_col.put(ColumnMeta.OwnerNAME, () -> _srcmdl.getOwners().getName());
		
		_col.put(ColumnMeta.StationID, () -> _srcmdl.getStations().getID());
		_col.put(ColumnMeta.StationNAME, () -> _srcmdl.getStations().getName());

		_col.put(ColumnMeta.VlevID, () -> _srcmdl.getVoltageLevels().getID());
		_col.put(ColumnMeta.VlevNAME, () -> _srcmdl.getVoltageLevels().getName());
		_col.put(ColumnMeta.VlevBASKV, () -> _srcmdl.getVoltageLevels().getBaseKV());

		_col.put(ColumnMeta.LineID, () -> _srcmdl.getLines().getID());
		_col.put(ColumnMeta.LineNAME, () -> _srcmdl.getLines().getName());
		_col.put(ColumnMeta.LineBUSFROM, () -> extractIndex(_srcmdl.getLines().getFromBus()));
		_col.put(ColumnMeta.LineBUSTO, () -> extractIndex(_srcmdl.getLines().getToBus()));
		_col.put(ColumnMeta.LineINSVC, () -> _srcmdl.getLines().isInService());
		_col.put(ColumnMeta.LinePFROM, () -> _srcmdl.getLines().getFromP());
		_col.put(ColumnMeta.LineQFROM, () -> _srcmdl.getLines().getFromQ());
		_col.put(ColumnMeta.LinePTO, () -> _srcmdl.getLines().getToP());
		_col.put(ColumnMeta.LineQTO, () -> _srcmdl.getLines().getToQ());
		_col.put(ColumnMeta.LineR, () -> _srcmdl.getLines().getR());
		_col.put(ColumnMeta.LineX, () -> _srcmdl.getLines().getX());
		_col.put(ColumnMeta.LineBFROM, () -> _srcmdl.getLines().getFromBchg());
		_col.put(ColumnMeta.LineBTO, () -> _srcmdl.getLines().getToBchg());
		_col.put(ColumnMeta.LineRATLT, () -> _srcmdl.getLines().getLTRating());

		_col.put(ColumnMeta.SercapID, () -> _srcmdl.getSeriesCapacitors().getID());
		_col.put(ColumnMeta.SercapNAME, () -> _srcmdl.getSeriesCapacitors().getName());
		_col.put(ColumnMeta.SercapBUSFROM, () -> extractIndex(_srcmdl.getSeriesCapacitors().getFromBus()));
		_col.put(ColumnMeta.SercapBUSTO, () -> extractIndex(_srcmdl.getSeriesCapacitors().getToBus()));
		_col.put(ColumnMeta.SercapINSVC, () -> _srcmdl.getSeriesCapacitors().isInService());
		_col.put(ColumnMeta.SercapPFROM, () -> _srcmdl.getSeriesCapacitors().getFromP());
		_col.put(ColumnMeta.SercapQFROM, () -> _srcmdl.getSeriesCapacitors().getFromQ());
		_col.put(ColumnMeta.SercapPTO, () -> _srcmdl.getSeriesCapacitors().getToP());
		_col.put(ColumnMeta.SercapQTO, () -> _srcmdl.getSeriesCapacitors().getToQ());
		_col.put(ColumnMeta.SercapR, () -> _srcmdl.getSeriesCapacitors().getR());
		_col.put(ColumnMeta.SercapX, () -> _srcmdl.getSeriesCapacitors().getX());
		_col.put(ColumnMeta.SercapRATLT, () -> _srcmdl.getSeriesCapacitors().getLTRating());
		
		_col.put(ColumnMeta.SerreacID, () -> _srcmdl.getSeriesReactors().getID());
		_col.put(ColumnMeta.SerreacNAME, () -> _srcmdl.getSeriesReactors().getName());
		_col.put(ColumnMeta.SerreacBUSFROM, () -> extractIndex(_srcmdl.getSeriesReactors().getFromBus()));
		_col.put(ColumnMeta.SerreacBUSTO, () -> extractIndex(_srcmdl.getSeriesReactors().getToBus()));
		_col.put(ColumnMeta.SerreacINSVC, () -> _srcmdl.getSeriesReactors().isInService());
		_col.put(ColumnMeta.SerreacPFROM, () -> _srcmdl.getSeriesReactors().getFromP());
		_col.put(ColumnMeta.SerreacQFROM, () -> _srcmdl.getSeriesReactors().getFromQ());
		_col.put(ColumnMeta.SerreacPTO, () -> _srcmdl.getSeriesReactors().getToP());
		_col.put(ColumnMeta.SerreacQTO, () -> _srcmdl.getSeriesReactors().getToQ());
		_col.put(ColumnMeta.SerreacR, () -> _srcmdl.getSeriesReactors().getR());
		_col.put(ColumnMeta.SerreacX, () -> _srcmdl.getSeriesReactors().getX());
		_col.put(ColumnMeta.SerreacRATLT, () -> _srcmdl.getSeriesReactors().getLTRating());
		
		_col.put(ColumnMeta.PhashID, () -> _srcmdl.getPhaseShifters().getID());
		_col.put(ColumnMeta.PhashNAME, () -> _srcmdl.getPhaseShifters().getName());
		_col.put(ColumnMeta.PhashBUSFROM, () -> extractIndex(_srcmdl.getPhaseShifters().getFromBus()));
		_col.put(ColumnMeta.PhashBUSTO, () -> extractIndex(_srcmdl.getPhaseShifters().getToBus()));
		_col.put(ColumnMeta.PhashINSVC, () -> _srcmdl.getPhaseShifters().isInService());
		_col.put(ColumnMeta.PhashPFROM, () -> _srcmdl.getPhaseShifters().getFromP());
		_col.put(ColumnMeta.PhashQFROM, () -> _srcmdl.getPhaseShifters().getFromQ());
		_col.put(ColumnMeta.PhashPTO, () -> _srcmdl.getPhaseShifters().getToP());
		_col.put(ColumnMeta.PhashQTO, () -> _srcmdl.getPhaseShifters().getToQ());
		_col.put(ColumnMeta.PhashR, () -> _srcmdl.getPhaseShifters().getR());
		_col.put(ColumnMeta.PhashX, () -> _srcmdl.getPhaseShifters().getX());
		_col.put(ColumnMeta.PhashGMAG, () -> _srcmdl.getPhaseShifters().getGmag());
		_col.put(ColumnMeta.PhashBMAG, () -> _srcmdl.getPhaseShifters().getBmag());
		_col.put(ColumnMeta.PhashANG, () -> _srcmdl.getPhaseShifters().getShift());
		_col.put(ColumnMeta.PhashTAPFROM, () -> _srcmdl.getPhaseShifters().getFromTap());
		_col.put(ColumnMeta.PhashTAPTO, () -> _srcmdl.getPhaseShifters().getToTap());
		_col.put(ColumnMeta.PhashCTRLMODE, () -> _srcmdl.getPhaseShifters().getControlMode());
		_col.put(ColumnMeta.PhashRATLT, () -> _srcmdl.getPhaseShifters().getLTRating());
		_col.put(ColumnMeta.PhashHASREG, () -> _srcmdl.getPhaseShifters().hasReg());
		_col.put(ColumnMeta.PhashMXANG, () -> _srcmdl.getPhaseShifters().getMaxAng());
		_col.put(ColumnMeta.PhashMNANG, () -> _srcmdl.getPhaseShifters().getMinAng());
		_col.put(ColumnMeta.PhashMXMW, () -> _srcmdl.getPhaseShifters().getRegMaxMW());
		_col.put(ColumnMeta.PhashMNMW, () -> _srcmdl.getPhaseShifters().getRegMinMW());

		_col.put(ColumnMeta.TfmrID, () -> _srcmdl.getTransformers().getID());
		_col.put(ColumnMeta.TfmrNAME, () -> _srcmdl.getTransformers().getName());
		_col.put(ColumnMeta.TfmrBUSFROM, () -> extractIndex(_srcmdl.getTransformers().getFromBus()));
		_col.put(ColumnMeta.TfmrBUSTO, () -> extractIndex(_srcmdl.getTransformers().getToBus()));
		_col.put(ColumnMeta.TfmrINSVC, () -> _srcmdl.getTransformers().isInService());
		_col.put(ColumnMeta.TfmrPFROM, () -> _srcmdl.getTransformers().getFromP());
		_col.put(ColumnMeta.TfmrQFROM, () -> _srcmdl.getTransformers().getFromQ());
		_col.put(ColumnMeta.TfmrPTO, () -> _srcmdl.getTransformers().getToP());
		_col.put(ColumnMeta.TfmrQTO, () -> _srcmdl.getTransformers().getToQ());
		_col.put(ColumnMeta.TfmrR, () -> _srcmdl.getTransformers().getR());
		_col.put(ColumnMeta.TfmrX, () -> _srcmdl.getTransformers().getX());
		_col.put(ColumnMeta.TfmrGMAG, () -> _srcmdl.getTransformers().getGmag());
		_col.put(ColumnMeta.TfmrBMAG, () -> _srcmdl.getTransformers().getBmag());
		_col.put(ColumnMeta.TfmrANG, () -> _srcmdl.getTransformers().getShift());
		_col.put(ColumnMeta.TfmrTAPFROM, () -> _srcmdl.getTransformers().getFromTap());
		_col.put(ColumnMeta.TfmrTAPTO, () -> _srcmdl.getTransformers().getToTap());
		_col.put(ColumnMeta.TfmrRATLT, () -> _srcmdl.getTransformers().getLTRating());
		_col.put(ColumnMeta.TfmrMINREGKV, () -> _srcmdl.getTransformers().getMinKV());
		_col.put(ColumnMeta.TfmrMAXREGKV, () -> _srcmdl.getTransformers().getMaxKV());
		_col.put(ColumnMeta.TfmrREGBUS, () -> extractIndex(_srcmdl.getTransformers().getRegBus()));
		_col.put(ColumnMeta.TfmrTAPBUS, () -> _srcmdl.getTransformers().getTapBus());
		_col.put(ColumnMeta.TfmrREGENAB, () -> _srcmdl.getTransformers().isRegEnabled());
		_col.put(ColumnMeta.TfmrHASREG, () -> _srcmdl.getTransformers().hasLTC());
		_col.put(ColumnMeta.TfmrMNTPFROM, () -> _srcmdl.getTransformers().getFromMinTap());
		_col.put(ColumnMeta.TfmrMXTPFROM, () -> _srcmdl.getTransformers().getFromMaxTap());
		_col.put(ColumnMeta.TfmrMNTPTO, () -> _srcmdl.getTransformers().getToMinTap());
		_col.put(ColumnMeta.TfmrMXTPTO, () -> _srcmdl.getTransformers().getToMaxTap());
		_col.put(ColumnMeta.TfmrSTEPFROM, () -> _srcmdl.getTransformers().getFromStepSize());
		_col.put(ColumnMeta.TfmrSTEPTO, () -> _srcmdl.getTransformers().getToStepSize());

		_col.put(ColumnMeta.SwID, () -> _srcmdl.getSwitches().getID());
		_col.put(ColumnMeta.SwNAME, () -> _srcmdl.getSwitches().getName());
		_col.put(ColumnMeta.SwBUSFROM, () -> extractIndex(_srcmdl.getSwitches().getFromBus()));
		_col.put(ColumnMeta.SwBUSTO, () -> extractIndex(_srcmdl.getSwitches().getToBus()));
		_col.put(ColumnMeta.SwINSVC, () -> _srcmdl.getSwitches().isInService());
		_col.put(ColumnMeta.SwPFROM, () -> _srcmdl.getSwitches().getFromP());
		_col.put(ColumnMeta.SwQFROM, () -> _srcmdl.getSwitches().getFromQ());
		_col.put(ColumnMeta.SwPTO, () -> _srcmdl.getSwitches().getToP());
		_col.put(ColumnMeta.SwQTO, () -> _srcmdl.getSwitches().getToQ());
		_col.put(ColumnMeta.SwSTATE, () -> _srcmdl.getSwitches().getState());
		_col.put(ColumnMeta.SwOPLD, () -> _srcmdl.getSwitches().isOperableUnderLoad());
		_col.put(ColumnMeta.SwENAB, () -> _srcmdl.getSwitches().isEnabled());
		
		_col.put(ColumnMeta.T2dcID, () -> _srcmdl.getTwoTermDCLines().getID());
		_col.put(ColumnMeta.T2dcNAME, () -> _srcmdl.getTwoTermDCLines().getName());
		_col.put(ColumnMeta.T2dcBUSFROM, () -> extractIndex(_srcmdl.getTwoTermDCLines().getFromBus()));
		_col.put(ColumnMeta.T2dcBUSTO, () -> extractIndex(_srcmdl.getTwoTermDCLines().getToBus()));
		_col.put(ColumnMeta.T2dcINSVC, () -> _srcmdl.getTwoTermDCLines().isInService());
		_col.put(ColumnMeta.T2dcPFROM, () -> _srcmdl.getTwoTermDCLines().getFromP());
		_col.put(ColumnMeta.T2dcQFROM, () -> _srcmdl.getTwoTermDCLines().getFromQ());
		_col.put(ColumnMeta.T2dcPTO, () -> _srcmdl.getTwoTermDCLines().getToP());
		_col.put(ColumnMeta.T2dcQTO, () -> _srcmdl.getTwoTermDCLines().getToQ());
	}
	
	<T extends BaseObject> int[] extractIndex(T[] obj)
	{
		int n = obj.length;
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
			rv[i] = obj[i].getIndex();
		return rv;
	}

	@Override
	protected BusListI loadBuses() throws PAModelException
	{
		return new BusListI(_m, _srcmdl.getBuses().getKeys());
	}

	@Override
	protected SwitchListI loadSwitches() throws PAModelException
	{
		return new SwitchListI(_m, _srcmdl.getSwitches().getKeys());
	}

	@Override
	protected LineListI loadLines() throws PAModelException
	{
		return new LineListI(_m, _srcmdl.getLines().getKeys());
	}

	@Override
	protected AreaListI loadAreas() throws PAModelException
	{
		return new AreaListI(_m, _srcmdl.getAreas().getKeys(), extractKeys(_srcmdl.getBuses().getArea()));
	}
	
	<T extends BaseObject> int[] extractKeys(T[] obj)
	{
		int n = obj.length;
		int[] rv = new int[n];
		for(int i=0; i < n; ++i)
			rv[i] = obj[i].getKey();
		return rv;
	}

	@Override
	protected OwnerListI loadOwners() throws PAModelException
	{
		return new OwnerListI(_m, _srcmdl.getOwners().getKeys(), extractKeys(_srcmdl.getBuses().getOwner()));
	}

	@Override
	protected StationListI loadStations() throws PAModelException
	{
		return new StationListI(_m, _srcmdl.getStations().getKeys(), extractKeys(_srcmdl.getBuses().getStation()));
	}

	@Override
	protected VoltageLevelListI loadVoltageLevels() throws PAModelException
	{
		return new VoltageLevelListI(_m, _srcmdl.getVoltageLevels().getKeys(),
			extractKeys(_srcmdl.getBuses().getVoltageLevel()));
	}

	@Override
	protected ElectricalIslandList loadIslands() throws PAModelException
	{
		return new ElectricalIslandListI(_m);
	}

	@Override
	protected SVCListI loadSVCs() throws PAModelException
	{
		return new SVCListI(_m, _srcmdl.getSVCs().getKeys());
	}
	

	@Override
	protected SwitchedShuntListI loadSwitchedShunts() throws PAModelException
	{
		return new SwitchedShuntListI(_m, _srcmdl.getSwitchedShunts().getKeys());
	}

	@Override
	protected TwoTermDCLineListI loadTwoTermDCLines() throws PAModelException
	{
		return new TwoTermDCLineListI(_m, _srcmdl.getTwoTermDCLines().getKeys());
	}
	

	@Override
	protected ShuntCapListI loadShuntCapacitors() throws PAModelException
	{
		return new ShuntCapListI(_m, _srcmdl.getShuntCapacitors().getKeys());
	}

	@Override
	protected ShuntReacListI loadShuntReactors() throws PAModelException
	{
		return new ShuntReacListI(_m, _srcmdl.getShuntReactors().getKeys());
	}

	@Override
	protected LoadListI loadLoads() throws PAModelException
	{
		return new LoadListI(_m, _srcmdl.getLoads().getKeys());
	}

	@Override
	protected GenListI loadGens() throws PAModelException
	{
		return new GenListI(_m, _srcmdl.getGenerators().getKeys());
	}

	@Override
	protected SeriesCapListI loadSeriesCapacitors() throws PAModelException
	{
		return new SeriesCapListI(_m, _srcmdl.getSeriesCapacitors().getKeys());
	}

	@Override
	protected SeriesReacListI loadSeriesReactors() throws PAModelException
	{
		return new SeriesReacListI(_m, _srcmdl.getSeriesReactors().getKeys());
	}

	@Override
	protected PhaseShifterListI loadPhaseShifters() throws PAModelException
	{
		return new PhaseShifterListI(_m, _srcmdl.getPhaseShifters().getKeys());
	}

	@Override
	protected TransformerListI loadTransformers() throws PAModelException
	{
		return new TransformerListI(_m, _srcmdl.getTransformers().getKeys());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <R> R load(ListMetaType ltype, ColumnMeta ctype, int[] keys) throws PAModelException
	{
		R r = (R) _col.get(ctype).load();
		if (_local.contains(ctype))
		{
			Class<?> t = r.getClass().getComponentType();
			int len = keys.length;
			R v = (R) Array.newInstance(t, len);
			System.arraycopy(r, 0, v, 0, len);
			r = v;
		}
		return r;
	}

	@Override
	protected SteamTurbineList loadSteamTurbines() throws PAModelException
	{
		throw new PAModelException("Not yet implemented");
	}
}
