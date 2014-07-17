package com.powerdata.openpa;

import com.powerdata.openpa.impl.TransformerBase;

public class PhaseShifter extends TransformerBase
{
	PhaseShifterList _list;

	public PhaseShifter(PhaseShifterList list, int ndx)
	{
		super(list, ndx);
		_list = list;
	}

}
