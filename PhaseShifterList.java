package com.powerdata.openpa;

public class PhaseShifterList extends TransformerBaseList<PhaseShifter>
{
	public static final PhaseShifterList	Empty	= new PhaseShifterList();

	float[] _shift, _shifto;
	
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
	/** get phase shift through branch in Degrees */
	public float getShift(int ndx)
	{
		return _shift[ndx];
	}
	/** get phase shift through branch in Degrees */
	public float[] getShift()
	{
		return _shift;
	}
	/** set phase shift through branch in Degrees */
	public void setShift(int ndx, float sdeg)
	{
		if (_shifto == null && _shift != null)
			_shifto = _shift.clone();
		_shift[ndx] = sdeg;
	}
	/** set phase shift through branch in Degrees */
	public void setShift(float[] sdeg)
	{
		if (_shift != sdeg)
		{
			if (_shifto == null)
				_shifto = _shift;
			_shift = sdeg;
		}
	}
	/** shift access for "sublist" subclasses to use */
	protected float[] getSubListShift(int[] slndx)
	{
		int n = size();
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = getShift(slndx[i]);
		return rv;
	}
	/** shift access for "sublist" subclasses to use */
	protected void setSubListShift(float[] shift, int[] slndx)
	{
		int n = size();
		for(int i=0; i < n; ++i)
			setShift(slndx[i], shift[i]);
	}

}
