package com.powerdata.openpa;

import com.powerdata.openpa.PAModel.ListMetaType;

public class LineListImpl extends ACBranchListImpl<Line> implements LineList
{
	public static final LineList	Empty	= new LineListImpl();
	
	float[][] _fb = IFlt(), _tb = IFlt();	

	public LineListImpl(){super();}
	
	public LineListImpl(PAModel model, int[] keys)
	{
		super(model, keys);
	}
	public LineListImpl(PAModel model, int size)
	{
		super(model, size);
	}

	@Override
	public float getFromBchg(int ndx)
	{
		return getFloat(_fb, ndx);
	}

	@Override
	public void setFromBchg(int ndx, float b)
	{
		setFloat(_fb, ndx, b);
	}

	@Override
	public float[] getFromBchg()
	{
		return getFloat(_fb);
	}

	@Override
	public void setFromBchg(float[] b)
	{
		setFloat(_fb, b);
	}

	@Override
	public float getToBchg(int ndx)
	{
		return getFloat(_tb, ndx);
	}

	@Override
	public void setToBchg(int ndx, float b)
	{
		setFloat(_tb, ndx, b);
	}
	

	@Override
	public float[] getToBchg()
	{
		return getFloat(_tb);
	}

	@Override
	public void setToBchg(float[] b)
	{
		setFloat(_tb, b);
	}

	@Override
	public Line get(int index)
	{
		return new Line(this, index);
	}

	@Override
	protected ListMetaType getMetaType()
	{
		return ListMetaType.Line;
	}

}
