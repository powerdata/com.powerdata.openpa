package com.powerdata.openpa;

public class PhaseShifterList extends ACBranchList<PhaseShifter>
{

	public static final PhaseShifterList	Empty	= new PhaseShifterList();

	protected PhaseShifterList(PALists model, int[] keys, int[] fbkey, int[] tbkey)
	{
		super(model, keys, fbkey, tbkey);
	}
	protected PhaseShifterList(PALists model, int size, int[] fbkey, int[] tbkey)
	{
		super(model, size, fbkey, tbkey);
	}

	protected PhaseShifterList() {super();}

	@Override
	public PhaseShifter get(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
