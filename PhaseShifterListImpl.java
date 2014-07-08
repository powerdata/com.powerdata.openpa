package com.powerdata.openpa;

public class PhaseShifterListImpl extends TransformerBaseListImpl<PhaseShifter> implements PhaseShifterList
{
	public static final PhaseShifterList	Empty	= new PhaseShifterListImpl();

	public PhaseShifterListImpl(PAModel model, int[] keys)
	{
		super(model, keys);
	}
	public PhaseShifterListImpl(PAModel model, int size)
	{
		super(model, size);
	}

	public PhaseShifterListImpl() {super();}

	@Override
	public PhaseShifter get(int index)
	{
		return new PhaseShifter(this, index);
	}
	@Override
	public float getShift(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setShift(int ndx, float sdeg)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public float[] getShift()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setShift(float[] sdeg)
	{
		// TODO Auto-generated method stub
		
	}
}
