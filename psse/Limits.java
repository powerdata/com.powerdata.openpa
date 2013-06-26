package com.powerdata.openpa.psse;

public class Limits
{
	private float _min;
	private float _max;
	
	public Limits(float min, float max)
	{
		_min = min;
		_max = max;
	}

	public float getMin() {return _min;}
	public float getMax() {return _max;}
}
