package com.powerdata.openpa;

public class LineListImpl extends ACBranchListImpl<Line> implements LineList
{
	public static final LineList	Empty	= new LineListImpl();
	
	public LineListImpl(){super();}
	
	public LineListImpl(PAModel model, int[] keys)
	{
		super(model, keys);
	}
	protected LineListImpl(PAModel model, int size)
	{
		super(model, size);
	}

	@Override
	public float getFromBchg(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFromBchg(int ndx, float b)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float[] getFromBchg()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFromBchg(float[] b)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getToBchg(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setToBchg(int ndx, float b)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public float[] getToBchg()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setToBchg(float[] b)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Line get(int index)
	{
		return new Line(this, index);
	}

}
