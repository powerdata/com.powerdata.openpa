package com.powerdata.openpa;

public class BusSubList extends BusList
{
	int[] _ndx;
	BusList _src;
	
	public BusSubList(PALists model, BusList src, int[] srcndx)
	{
		super(model, src.getKeys(srcndx));
		_ndx = srcndx;
		_src = src;
	}

	int map(int ndx) {return _ndx[ndx];}
	
	@Override
	public float getBaseKV(int ndx)
	{
		return _src.getBaseKV(map(ndx));
	}

	@Override
	public float[] getBaseKV()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBaseKV(int ndx, float kv)
	{
		_src.setBaseKV(map(ndx), kv);
	}

	@Override
	public void setBaseKV(float[] kv)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public float getVM(int ndx)
	{
		return _src.getVM(map(ndx));
	}

	@Override
	public float[] getVM()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setVM(int ndx, float vm)
	{
		_src.setVM(map(ndx), vm);
	}

	@Override
	public void setVM(float[] vm)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public float getVA(int ndx)
	{
		return _src.getVA(map(ndx));
	}

	@Override
	public float[] getVA()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setVA(int ndx, float va)
	{
		_src.setVA(map(ndx), va);
	}

	@Override
	public void setVA(float[] va)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public BusList getBuses(int ndx)
	{
		return _src.getBuses(map(ndx));
	}

	@Override
	public SwitchList getSwitches(int ndx)
	{
		return _src.getSwitches(map(ndx));
	}

	@Override
	public LineList getLines(int ndx)
	{
		return _src.getLines(map(ndx));
	}

	@Override
	public SeriesReacList getSeriesReactors(int ndx)
	{
		return _src.getSeriesReactors(map(ndx));
	}

	@Override
	public SeriesCapList getSeriesCapacitors(int ndx)
	{
		return _src.getSeriesCapacitors(map(ndx));
	}

	@Override
	public TransformerList getTransformers(int ndx)
	{
		return _src.getTransformers(map(ndx));
	}

	@Override
	public PhaseShifterList getPhaseShifters(int ndx)
	{
		return _src.getPhaseShifters(map(ndx));
	}

	@Override
	public TwoTermDCLineList getTwoTermDCLines(int ndx)
	{
		return _src.getTwoTermDCLines(map(ndx));
	}

	@Override
	public GenList getGenerators(int ndx)
	{
		return _src.getGenerators(map(ndx));
	}

	@Override
	public LoadList getLoads(int ndx)
	{
		return _src.getLoads(map(ndx));
	}

	@Override
	public ShuntReacList getShuntReactors(int ndx)
	{
		return _src.getShuntReactors(map(ndx));
	}

	@Override
	public ShuntCapList getShuntCapacitors(int ndx)
	{
		return _src.getShuntCapacitors(map(ndx));
	}

	@Override
	public SwitchedShuntList getSwitchedShunts(int ndx)
	{
		return _src.getSwitchedShunts(map(ndx));
	}

	@Override
	public SVCList getSVCs(int ndx)
	{
		return _src.getSVCs(map(ndx));
	}

	@Override
	public String getID(int ndx)
	{
		return _src.getID(map(ndx));
	}

	@Override
	public void setID(int ndx, String id)
	{
		_src.setID(map(ndx), id);
	}

	@Override
	public String[] getID()
	{
		return _src.getSubListIDs(_ndx);
	}

	@Override
	public void setID(String[] id)
	{
		_src.setSubListIDs(id, _ndx);
	}

	@Override
	public String getName(int ndx)
	{
		return _src.getName(map(ndx));
	}

	@Override
	public void setName(int ndx, String name)
	{
		_src.setName(map(ndx), name);
	}

	@Override
	public String[] getName()
	{
		return _src.getSubListNames(_ndx);
	}

	@Override
	public void setName(String[] name)
	{
		_src.setSubListNames(name, _ndx);
	}

}
