package com.powerdata.openpa.impl;

import java.util.AbstractList;
import com.powerdata.openpa.*;
import com.powerdata.openpa.Gen.Mode;
import com.powerdata.openpa.Gen.Type;
import com.powerdata.openpa.PhaseShifter.ControlMode;
import com.powerdata.openpa.SVC.SVCState;
import com.powerdata.openpa.Switch.State;


public class EmptyLists
{
	private static abstract class EmptyBase<T extends BaseObject> extends AbstractList<T> implements BaseList<T>
	{
		@Override public T get(int index) {throw new IndexOutOfBoundsException();} 
		@Override public int size() {return 0;}
		@Override public boolean isEmpty() {return true;}
		@Override public int getKey(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public int[] getKeys() {return new int[0];}
		@Override public String getID(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public String[] getID() {return new String[0];} 
		@Override public void setID(String[] id) {} 
		@Override public void setID(int ndx, String id) {throw new IndexOutOfBoundsException();} 
		@Override public String getName(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public void setName(int ndx, String name) {throw new IndexOutOfBoundsException();} 
		@Override public String[] getName() {return new String[0];} 
		@Override public void setName(String[] name) {throw new UnsupportedOperationException();} 
		@Override public T getByKey(int key) {throw new UnsupportedOperationException();}
		@Override public T getByID(String id) {throw new UnsupportedOperationException();} 
		@Override public int getIndex(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public boolean objEquals(int ndx, Object obj) {throw new IndexOutOfBoundsException();}
		@Override public int objHash(int ndx) {throw new IndexOutOfBoundsException();}
	}
	
	private static abstract class EmptyGroup<T extends Group> extends EmptyBase<T> implements GroupListIfc<T>
	{
		@Override public BusList getBuses(int ndx)  {throw new IndexOutOfBoundsException();} 
		@Override public SwitchList getSwitches(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public LineList getLines(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public SeriesReacList getSeriesReactors(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public SeriesCapList getSeriesCapacitors(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public TransformerList getTransformers(int ndx)  {throw new IndexOutOfBoundsException();}
		@Override public PhaseShifterList getPhaseShifters(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public TwoTermDCLineList getTwoTermDCLines(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public GenList getGenerators(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public LoadList getLoads(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public ShuntReacList getShuntReactors(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public ShuntCapList getShuntCapacitors(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public SVCList getSVCs(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public int[] translateBusIndexes(int[] indexes) {return null;}
		@Override public T getByBus(Bus b) {return null;}
		
	}
	
	private static final class EmptyAreaList extends EmptyGroup<Area> implements AreaList {}
	
	private static final class EmptyBusList extends EmptyGroup<Bus> implements BusList
	{
		@Override public float getVM(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public void setVM(int ndx, float vm) {throw new IndexOutOfBoundsException();}
		@Override public float[] getVM() {return new float[0];} 
		@Override public void setVM(float[] vm)  {throw new UnsupportedOperationException();} 
		@Override public float getVA(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public void setVA(int ndx, float va) {throw new IndexOutOfBoundsException();} 
		@Override public float[] getVA() {return new float[0];} 
		@Override public void setVA(float[] va) {throw new UnsupportedOperationException();} 
		@Override public int getFreqSrcPri(int ndx)  {throw new IndexOutOfBoundsException();}
		@Override public void setFreqSrcPri(int ndx, int fsp) {throw new IndexOutOfBoundsException();} 
		@Override public int[] getFreqSrcPri() {return new int[0];} 
		@Override public void setFreqSrcPri(int[] fsp) {throw new IndexOutOfBoundsException();} 
		@Override public ElectricalIsland getIsland(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public Area getArea(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public void setArea(int ndx, Area a) {throw new IndexOutOfBoundsException();} 
		@Override public Area[] getArea() {return new Area[0];} 
		@Override public void setArea(Area[] a) {throw new UnsupportedOperationException();} 
		@Override public Station getStation(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public void setStation(int ndx, Station s) {throw new IndexOutOfBoundsException();} 
		@Override public Station[] getStation() {return new Station[0];} 
		@Override public void setStation(Station[] s) {throw new UnsupportedOperationException();} 
		@Override public Owner getOwner(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public void setOwner(int ndx, Owner o) {throw new IndexOutOfBoundsException();} 
		@Override public Owner[] getOwner() {return new Owner[0];} 
		@Override public void setOwner(Owner[] o) {throw new UnsupportedOperationException();} 
		@Override public VoltageLevel getVoltageLevel(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setVoltageLevel(int ndx, VoltageLevel l)  {throw new IndexOutOfBoundsException();}
		@Override public VoltageLevel[] getVoltageLevel() {return new VoltageLevel[0];} 
		@Override public void setVoltageLevel(VoltageLevel[] l) {throw new UnsupportedOperationException();} 
	}
	
	private static final class EmptyIslandList extends EmptyGroup<ElectricalIsland> implements ElectricalIslandList
	{
		@Override public boolean isEnergized(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public boolean[] isEnergized() {return new boolean[0];} 
		@Override public float getFreq(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public void setFreq(int ndx, float f) {throw new IndexOutOfBoundsException();} 
		@Override public float[] getFreq() {return new float[0];} 
		@Override public void setFreq(float[] f) {throw new UnsupportedOperationException();} 
	}
	
	private static final class EmptyOwnerList extends EmptyGroup<Owner> implements OwnerList {}
	private static final class EmptyStationList extends EmptyGroup<Station> implements StationList{}
	private static final class EmptyVoltageLevelList extends EmptyGroup<VoltageLevel> implements VoltageLevelList
	{
		@Override public float getBaseKV(int ndx)  {throw new IndexOutOfBoundsException();}
		@Override public void setBaseKV(int ndx, float k) {throw new IndexOutOfBoundsException();} 
		@Override public float[] getBaseKV() {return new float[0];} 
		@Override public void setBaseKV(float[] kv) {throw new UnsupportedOperationException();}
	}
	
	private abstract static class EmptyInServiceList<T extends InService> extends EmptyBase<T> implements InServiceList<T>
	{
		@Override public boolean isInService(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public boolean[] isInService() {return new boolean[0];}
		@Override public void setInService(int ndx, boolean s) {throw new IndexOutOfBoundsException();}
		@Override public void setInService(boolean[] s) {throw new UnsupportedOperationException();} 
	}
	
	private static abstract class  EmptyOneTermDevList<T extends OneTermDev> extends EmptyInServiceList<T> implements OneTermDevListIfc<T>
	{
		@Override public Bus getBus(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public void setBus(int ndx, Bus b) {throw new IndexOutOfBoundsException();} 
		@Override public Bus[] getBus() {return new Bus[0];} 
		@Override public void setBus(Bus[] b) {throw new UnsupportedOperationException();}
		@Override public float getP(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public void setP(int ndx, float p) {throw new IndexOutOfBoundsException();} 
		@Override public float[] getP() {return new float[0];} 
		@Override public void setP(float[] p) {throw new UnsupportedOperationException();} 
		@Override public float getQ(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public void setQ(int ndx, float q) {throw new IndexOutOfBoundsException();} 
		@Override public float[] getQ() {return new float[0];} 
		@Override public void setQ(float[] q) {throw new UnsupportedOperationException();} 
	}
	
	private static abstract class EmptyTwoTermDevList<T extends TwoTermDev> extends EmptyInServiceList<T> implements TwoTermDevListIfc<T>
	{
		@Override public Bus getFromBus(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public void setFromBus(int ndx, Bus b) {throw new IndexOutOfBoundsException();} 
		@Override public Bus[] getFromBus() {return new Bus[0];} 
		@Override public void setFromBus(Bus[] b) {throw new UnsupportedOperationException();}
		@Override public Bus getToBus(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public void setToBus(int ndx, Bus b) {throw new IndexOutOfBoundsException();} 
		@Override public Bus[] getToBus() {return new Bus[0];} 
		@Override public void setToBus(Bus[] b) {throw new UnsupportedOperationException();} 
		@Override public float getFromP(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public void setFromP(int ndx, float mw) {throw new IndexOutOfBoundsException();} 
		@Override public float[] getFromP() {return new float[0];} 
		@Override public void setFromP(float[] mw) {throw new UnsupportedOperationException();} 
		@Override public float getFromQ(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public void setFromQ(int ndx, float mvar) {throw new IndexOutOfBoundsException();} 
		@Override public float[] getFromQ() {return new float[0];} 
		@Override public void setFromQ(float[] mvar) {throw new UnsupportedOperationException();} 
		@Override public float getToP(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public void setToP(int ndx, float mw) {throw new IndexOutOfBoundsException();} 
		@Override public float[] getToP() {return new float[0];} 
		@Override public void setToP(float[] mw) {throw new UnsupportedOperationException();} 
		@Override public float getToQ(int ndx) {throw new IndexOutOfBoundsException();} 
		@Override public void setToQ(int ndx, float mvar) {throw new IndexOutOfBoundsException();} 
		@Override public float[] getToQ() {return new float[0];} 
		@Override public void setToQ(float[] mvar) {throw new UnsupportedOperationException();} 
	}
	
	private static abstract class EmptyFixedShuntList<T extends FixedShunt> extends EmptyOneTermDevList<T> implements FixedShuntListIfc<T>
	{
		@Override public float getB(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setB(int ndx, float b) {throw new IndexOutOfBoundsException();} 
		@Override public float[] getB() {return new float[0];} 
		@Override public void setB(float[] b) {throw new UnsupportedOperationException();}
	}
	
	private static final class EmptyShuntCapList extends EmptyFixedShuntList<ShuntCapacitor> implements ShuntCapList {}
	private static final class EmptyShuntReacList extends EmptyFixedShuntList<ShuntReactor> implements ShuntReacList {}
	
	private static final class EmptyGenList extends EmptyOneTermDevList<Gen> implements GenList
	{
		@Override public Type getType(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setType(int ndx, Type t) {throw new IndexOutOfBoundsException();}
		@Override public Type[] getType() {return new Type[0];}
		@Override public void setType(Type[] t) {throw new UnsupportedOperationException();}
		@Override public Mode getMode(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setMode(int ndx, Mode m) {throw new IndexOutOfBoundsException();}
		@Override public Mode[] getMode() {return new Mode[0];}
		@Override public void setMode(Mode[] m) {throw new UnsupportedOperationException();}
		@Override public float getOpMinP(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setOpMinP(int ndx, float mw) {throw new IndexOutOfBoundsException();}
		@Override public float[] getOpMinP() {return new float[0];}
		@Override public void setOpMinP(float[] mw) {throw new UnsupportedOperationException();}
		@Override public float getOpMaxP(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setOpMaxP(int ndx, float mw) {throw new IndexOutOfBoundsException();}
		@Override public float[] getOpMaxP() {return new float[0];}
		@Override public void setOpMaxP(float[] mw) {throw new UnsupportedOperationException();}
		@Override public float getMinQ(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setMinQ(int ndx, float mvar) {throw new IndexOutOfBoundsException();}
		@Override public float[] getMinQ() {return new float[0];}
		@Override public void setMinQ(float[] mvar) {throw new UnsupportedOperationException();}
		@Override public float getMaxQ(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setMaxQ(int ndx, float mvar) {throw new IndexOutOfBoundsException();}
		@Override public float[] getMaxQ() {return new float[0];}
		@Override public void setMaxQ(float[] mvar) {throw new UnsupportedOperationException();}
		@Override public float getPS(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setPS(int ndx, float mw) {throw new IndexOutOfBoundsException();}
		@Override public float[] getPS() {return new float[0];}
		@Override public void setPS(float[] mw) {throw new UnsupportedOperationException();}
		@Override public float getQS(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setQS(int ndx, float mvar) {throw new IndexOutOfBoundsException();}
		@Override public float[] getQS() {return new float[0];}
		@Override public void setQS(float[] mvar) {throw new UnsupportedOperationException();}
		@Override public boolean isRegKV(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setRegKV(int ndx, boolean reg) {throw new IndexOutOfBoundsException();}
		@Override public boolean[] isRegKV() {return new boolean[0];}
		@Override public void setRegKV(boolean[] reg) {throw new UnsupportedOperationException();}
		@Override public float getVS(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setVS(int ndx, float kv) {throw new IndexOutOfBoundsException();}
		@Override public float[] getVS() {return new float[0];}
		@Override public void setVS(float[] kv) {throw new UnsupportedOperationException();}
		@Override public void setRegBus(int ndx, Bus b) {throw new IndexOutOfBoundsException();}
		@Override public Bus getRegBus(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public Bus[] getRegBus() {return new Bus[0];}
		@Override public void setRegBus(Bus[] b) {throw new UnsupportedOperationException();}
	}
	
	private static final class EmptyLoadList extends EmptyOneTermDevList<Load> implements LoadList
	{
		@Override public float getMaxP(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setMaxP(int ndx, float mw) {throw new IndexOutOfBoundsException();}
		@Override public float[] getMaxP() {return new float[0];}
		@Override public void setMaxP(float[] mw) {throw new UnsupportedOperationException();}
		@Override public float getMaxQ(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setMaxQ(int ndx, float mvar) {throw new IndexOutOfBoundsException();}
		@Override public float[] getMaxQ()  {return new float[0];}
		@Override public void setMaxQ(float[] mvar) {throw new UnsupportedOperationException();}
	}
	
	private static final class EmptySVCList extends EmptyOneTermDevList<SVC> implements SVCList
	{
		@Override public float getMinQ(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setMinQ(int ndx, float mvar) {throw new IndexOutOfBoundsException();}
		@Override public float[] getMinQ() {return new float[0];}
		@Override public void setMinQ(float[] mvar) {throw new UnsupportedOperationException();}
		@Override public float getMaxQ(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setMaxQ(int ndx, float mvar) {throw new IndexOutOfBoundsException();}
		@Override public float[] getMaxQ()  {return new float[0];}
		@Override public void setMaxQ(float[] mvar) {throw new UnsupportedOperationException();}
		@Override public boolean isRegKV(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setRegKV(int ndx, boolean reg) {throw new IndexOutOfBoundsException();}
		@Override public boolean[] isRegKV()  {return new boolean[0];}
		@Override public void setRegKV(boolean[] reg) {throw new UnsupportedOperationException();}
		@Override public float getVS(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setVS(int ndx, float kv) {throw new IndexOutOfBoundsException();}
		@Override public float[] getVS()  {return new float[0];}
		@Override public void setVS(float[] kv) {throw new UnsupportedOperationException();}
		@Override public void setRegBus(int ndx, Bus b) {throw new IndexOutOfBoundsException();}
		@Override public Bus getRegBus(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public Bus[] getRegBus()  {return new Bus[0];}
		@Override public void setRegBus(Bus[] b) {throw new UnsupportedOperationException();}
		@Override public float getSlope(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public float[] getSlope()  {return new float[0];}
		@Override public void setSlope(int ndx, float slope) {throw new IndexOutOfBoundsException();}
		@Override public void setSlope(float[] slope) {throw new UnsupportedOperationException();}
		@Override public SVCState getOutputMode(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public SVCState[] getOutputMode()  {return new SVCState[0];}
		@Override public void setOutputMode(int ndx, SVCState m) {throw new IndexOutOfBoundsException();}
		@Override public void setOutputMode(SVCState[] m) {throw new UnsupportedOperationException();}
		@Override public float getQS(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public float[] getQS()  {return new float[0];}
		@Override public void setQS(int ndx, float mvar) {throw new IndexOutOfBoundsException();}
		@Override public void setQS(float[] mvar) {throw new UnsupportedOperationException();}
	}
	
	private static abstract class EmptyACBranchList<T extends ACBranch> extends EmptyTwoTermDevList<T> implements ACBranchListIfc<T>
	{
		@Override public float getR(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setR(int ndx, float r) {throw new IndexOutOfBoundsException();}
		@Override public float[] getR() {return new float[0];}
		@Override public void setR(float[] r) {throw new UnsupportedOperationException();}
		@Override public float getX(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setX(int ndx, float x) {throw new IndexOutOfBoundsException();}
		@Override public float[] getX() {return new float[0];}
		@Override public void setX(float[] x) {throw new UnsupportedOperationException();}
		@Override public float getFromTap(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setFromTap(int ndx, float a) {throw new IndexOutOfBoundsException();}
		@Override public float[] getFromTap() {return new float[0];}
		@Override public void setFromTap(float[] a) {throw new UnsupportedOperationException();}
		@Override public float getToTap(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setToTap(int ndx, float a) {throw new IndexOutOfBoundsException();}
		@Override public float[] getToTap() {return new float[0];}
		@Override public void setToTap(float[] a) {throw new UnsupportedOperationException();}
		@Override public float getGmag(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setGmag(int ndx, float g) {throw new IndexOutOfBoundsException();}
		@Override public float[] getGmag() {return new float[0];}
		@Override public void setGmag(float[] g) {throw new UnsupportedOperationException();}
		@Override public float getBmag(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setBmag(int ndx, float b) {throw new IndexOutOfBoundsException();}
		@Override public float[] getBmag() {return new float[0];}
		@Override public void setBmag(float[] b) {throw new UnsupportedOperationException();}
		@Override public float getFromBchg(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setFromBchg(int ndx, float b) {throw new IndexOutOfBoundsException();}
		@Override public float[] getFromBchg() {return new float[0];}
		@Override public void setFromBchg(float[] b) {throw new UnsupportedOperationException();}
		@Override public float getToBchg(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setToBchg(int ndx, float b) {throw new IndexOutOfBoundsException();}
		@Override public float[] getToBchg() {return new float[0];}
		@Override public void setToBchg(float[] b) {throw new UnsupportedOperationException();}
		@Override public float getShift(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setShift(int ndx, float sdeg) {throw new IndexOutOfBoundsException();}
		@Override public float[] getShift() {return new float[0];}
		@Override public void setShift(float[] sdeg) {throw new UnsupportedOperationException();}
		@Override public float getLTRating(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public float[] getLTRating() {return new float[0];}
		@Override public void setLTRating(int ndx, float mva) {throw new IndexOutOfBoundsException();}
		@Override public void setLTRating(float[] mva) {throw new UnsupportedOperationException();}
	}
	
	private static final class EmptyLineList extends EmptyACBranchList<Line> implements LineList {}
	private static final class EmptyPhaseShifterList extends EmptyACBranchList<PhaseShifter> implements PhaseShifterList
	{
		@Override public ControlMode getControlMode(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public ControlMode[] getControlMode() { return new ControlMode[0];}
		@Override public void setControlMode(int ndx, ControlMode m) {throw new IndexOutOfBoundsException();}
		@Override public void setControlMode(ControlMode[] m) {throw new UnsupportedOperationException();}
		@Override public boolean hasReg(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public boolean[] hasReg() {throw new UnsupportedOperationException();}
		@Override public void setReg(int ndx, boolean v) {throw new IndexOutOfBoundsException();}
		@Override public void setReg(boolean[] v) {throw new UnsupportedOperationException();}
		@Override public float getMaxAng(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setMaxAng(int ndx, float v) {throw new IndexOutOfBoundsException();}
		@Override public float[] getMaxAng() {throw new UnsupportedOperationException();}
		@Override public void setMaxAng(float[] v) {throw new IndexOutOfBoundsException();}
		@Override public float getMinAng(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setMinAng(int ndx, float v) {throw new IndexOutOfBoundsException();}
		@Override public float[] getMinAng() {throw new UnsupportedOperationException();}
		@Override public void setMinAng(float[] v) {throw new UnsupportedOperationException();}
		@Override public float getRegMaxMW(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public float[] getRegMaxMW() {throw new UnsupportedOperationException();}
		@Override public void setRegMaxMW(int ndx, float mw) {throw new IndexOutOfBoundsException();}
		@Override public void setRegMaxMW(float[] mw) {throw new UnsupportedOperationException();}
		@Override public float getRegMinMW(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setRegMinMW(int ndx, float mw) {throw new IndexOutOfBoundsException();}
		@Override public float[] getRegMinMW() {throw new UnsupportedOperationException();}
		@Override public void setRegMinMW(float[] mw) {throw new UnsupportedOperationException();}
	}
	private static final class EmptySeriesCapList extends EmptyACBranchList<SeriesCap> implements SeriesCapList {}
	private static final class EmptySeriesReacList extends EmptyACBranchList<SeriesReac> implements SeriesReacList {}
	private static final class EmptyTransformerList extends EmptyACBranchList<Transformer> implements TransformerList
	{
		@Override public boolean isRegEnabled(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setRegEnabled(int ndx, boolean enabl) {throw new IndexOutOfBoundsException();}
		@Override public boolean[] isRegEnabled() {return new boolean[0];}
		@Override public void setRegEnabled(boolean[] enabl) {throw new UnsupportedOperationException();}
		@Override public Bus getTapBus(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public Bus[] getTapBus() {throw new UnsupportedOperationException();}
		@Override public void setTapBus(int ndx, Bus s) {throw new IndexOutOfBoundsException();}
		@Override public void setTapBus(Bus[] s) {throw new UnsupportedOperationException();}
		@Override public float getMinKV(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setMinKV(int ndx, float kv) {throw new IndexOutOfBoundsException();}
		@Override public float[] getMinKV() {return new float[0];}
		@Override public void setMinKV(float[] kv) {throw new UnsupportedOperationException();}
		@Override public float getMaxKV(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setMaxKV(int ndx, float kv) {throw new IndexOutOfBoundsException();}
		@Override public float[] getMaxKV() {return new float[0];}
		@Override public void setMaxKV(float[] kv) {throw new UnsupportedOperationException();}
		@Override public Bus getRegBus(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setRegBus(int ndx, Bus b) {throw new IndexOutOfBoundsException();}
		@Override public Bus[] getRegBus() {return new Bus[0];}
		@Override public void setRegBus(Bus[] b) {throw new UnsupportedOperationException();}
		@Override public boolean hasLTC(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public boolean[] hasLTC() {return new boolean[0];}
		@Override public void setHasLTC(int ndx, boolean b) {throw new IndexOutOfBoundsException();}
		@Override public void setHasLTC(boolean[] b) {throw new UnsupportedOperationException();}
		@Override public float getFromMinTap(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setFromMinTap(int ndx, float a) {throw new IndexOutOfBoundsException();}
		@Override public float[] getFromMinTap() {return new float[0];}
		@Override public void setFromMinTap(float[] a) {throw new UnsupportedOperationException();}
		@Override public float getToMinTap(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public float[] getToMinTap() {return new float[0];}
		@Override public void setToMinTap(int ndx, float a) {throw new IndexOutOfBoundsException();}
		@Override public void setToMinTap(float[] a) {throw new UnsupportedOperationException();}
		@Override public float getFromMaxTap(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public float[] getFromMaxTap() {return new float[0];}
		@Override public void setFromMaxTap(int ndx, float a) {throw new IndexOutOfBoundsException();}
		@Override public void setFromMaxTap(float[] a) {throw new UnsupportedOperationException();}
		@Override public float getToMaxTap(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public float[] getToMaxTap() {return new float[0];}
		@Override public void setToMaxTap(int ndx, float a) {throw new IndexOutOfBoundsException();}
		@Override public void setToMaxTap(float[] a) {throw new UnsupportedOperationException();}
		@Override public float getFromStepSize(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public float[] getFromStepSize() {return new float[0];}
		@Override public void setFromStepSize(int ndx, float step) {throw new IndexOutOfBoundsException();}
		@Override public void setFromStepSize(float[] step) {throw new UnsupportedOperationException();}
		@Override public float getToStepSize(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public float[] getToStepSize() {return new float[0];}
		@Override public void setToStepSize(int ndx, float step) {throw new IndexOutOfBoundsException();}
		@Override public void setToStepSize(float[] step) {throw new UnsupportedOperationException();}
	}
	
	private static final class EmptySwitchList extends EmptyTwoTermDevList<Switch> implements SwitchList
	{
		@Override public State getState(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setState(int ndx, State state) {throw new IndexOutOfBoundsException();}
		@Override public State[] getState() {return new State[0];}
		@Override public void setState(State[] state) {throw new UnsupportedOperationException();}
		@Override public boolean isOperableUnderLoad(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setOperableUnderLoad(int ndx, boolean op) {throw new IndexOutOfBoundsException();}
		@Override public boolean[] isOperableUnderLoad() {return new boolean[0];}
		@Override public void setOperableUnderLoad(boolean[] op) {throw new UnsupportedOperationException();}
		@Override public boolean isEnabled(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setEnabled(int ndx, boolean enable) {throw new IndexOutOfBoundsException();}
		@Override public boolean[] isEnabled() {return new boolean[0];}
		@Override public void setEnabled(boolean[] enable) {throw new UnsupportedOperationException();}
		@Override public float getTransitTime(int ndx) {throw new IndexOutOfBoundsException();}
		@Override public void setTransitTime(int ndx, float t) {throw new IndexOutOfBoundsException();}
		@Override public float[] getTransitTime() {return new float[0];}
		@Override public void setTransitTime(float[] t) {throw new UnsupportedOperationException();}
	}
	
	private static final class EmptyTwoTermDCLineList extends EmptyTwoTermDevList<TwoTermDCLine> implements TwoTermDCLineList {}
	
	private static final class EmptySwitchedShuntList extends EmptyOneTermDevList<SwitchedShunt> implements SwitchedShuntList {}
	
	public static final AreaList EMPTY_AREAS = new EmptyAreaList();
	public static final BusList EMPTY_BUSES = new EmptyBusList();
	public static final ElectricalIslandList EMPTY_ISLANDS = new EmptyIslandList();
	public static final OwnerList EMPTY_OWNERS = new EmptyOwnerList();
	public static final StationList EMPTY_STATIONS = new EmptyStationList();
	public static final VoltageLevelList EMPTY_VOLTAGELEVELS = new EmptyVoltageLevelList();
	public static final LoadList EMPTY_LOADS = new EmptyLoadList();
	public static final ShuntCapList EMPTY_SHUNTCAPS = new EmptyShuntCapList();
	public static final ShuntReacList EMPTY_SHUNTREACS = new EmptyShuntReacList();
	public static final GenList EMPTY_GENS = new EmptyGenList();
	public static final SVCList EMPTY_SVCS= new EmptySVCList();
	public static final LineList EMPTY_LINES = new EmptyLineList();
	public static final PhaseShifterList EMPTY_PHASESHIFTERS =
			new EmptyPhaseShifterList();
	public static final SeriesCapList EMPTY_SERIESCAPS = new EmptySeriesCapList();
	public static final SeriesReacList EMPTY_SERIESREACS = new EmptySeriesReacList();
	public static final TransformerList EMPTY_TRANSFORMERS =
			new EmptyTransformerList();
	public static final SwitchList EMPTY_SWITCHES = new EmptySwitchList();
	public static final TwoTermDCLineList EMPTY_TWOTERMDCLINES =
			new EmptyTwoTermDCLineList();
	public static final SwitchedShuntList EMPTY_SWITCHEDSHUNTS = 
			new EmptySwitchedShuntList();
	
}
