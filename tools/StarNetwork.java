package com.powerdata.openpa.tools;

/**
 * Contain star-network impedance and provide conversion to delta-network
 * representation
 * 
 * @author chris@powerdata.com
 * 
 */

public class StarNetwork
{
	private Complex	_z1;
	private Complex	_z2;
	private Complex	_z3;

	public StarNetwork(Complex z1, Complex z2, Complex z3)
	{
		_z1 = z1;
		_z2 = z2;
		_z3 = z3;
	}

	public DeltaNetwork delta()
	{
		return new DeltaNetwork(
			_z1.add(_z2),
			_z2.add(_z3),
			_z3.add(_z1));
	}
	
	public Complex getZ1() {return _z1;}
	public Complex getZ2() {return _z2;}
	public Complex getZ3() {return _z3;}
	
}
