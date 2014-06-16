package com.powerdata.openpa;

public class LineList extends ACBranchList<Line>
{
	public static final LineList	Empty	= new LineList();

	protected LineList(){super();}
	
	protected LineList(PALists model, int[] keys, int[] fbkey, int[] tbkey)
	{
		super(model, keys, fbkey, tbkey);
	}

	@Override
	public Line get(int index)
	{
		return new Line(this, index);
	}
}
