package com.powerdata.openpa.tools;

/**
 * Contain delta-network impedance and provide conversion to star-network
 * representation
 * 
 * @author chris@powerdata.com
 * 
 */

public class DeltaNetwork
{
	private Complex	_z12;
	private Complex	_z23;
	private Complex	_z31;

	public DeltaNetwork(Complex z12, Complex z23, Complex z31)
	{
		_z12 = z12;
		_z23 = z23;
		_z31 = z31;
	}

	public StarNetwork star()
	{
		return new StarNetwork(
			(_z12.add(_z31).sub(_z23)).div(2F),
			(_z12.add(_z23).sub(_z31)).div(2F),
			(_z23.add(_z31).sub(_z12)).div(2F));
	}
	
	public Complex getZ12() {return _z12;}
	public Complex getZ23() {return _z23;}
	public Complex getZ31() {return _z31;}
}
