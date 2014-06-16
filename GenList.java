package com.powerdata.openpa;

import com.powerdata.openpa.Gen.Mode;
import com.powerdata.openpa.Gen.Type;

public class GenList extends OneTermDevList<Gen>
{
	public static final GenList	Empty	= new GenList();

	protected GenList() {super();}
	
	protected GenList(PALists model, int[] keys)
	{
		super(model, keys);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Gen get(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Type getType(int ndx) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void setType(int ndx, Type t)
	{
		// TODO Auto-generated method stub
		
	}

	public Mode getMode(int ndx) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void setMode(int ndx, Mode m) 
	{
		// TODO Auto-generated method stub
		
	}

}
