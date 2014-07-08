package com.powerdata.openpa;

import com.powerdata.openpa.Gen.Mode;
import com.powerdata.openpa.Gen.Type;

public class GenListI extends OneTermDevListI<Gen> implements GenList
{
	public static final GenList	Empty	= new GenListI();

	protected GenListI() {super();}
	
	protected GenListI(PAModel model, int[] keys)
	{
		super(model, keys);
	}
	protected GenListI(PAModel model, int size)
	{
		super(model, size);
	}

	@Override
	public Type getType(int ndx)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setType(int ndx, Type t)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Type[] getType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setType(Type[] t)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Mode getMode(int ndx)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMode(int ndx, Mode m)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Mode[] getMode()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMode(Mode[] m)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Gen get(int index)
	{
		return new Gen(this, index);
	}

}
