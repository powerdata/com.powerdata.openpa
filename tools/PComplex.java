package com.powerdata.openpa.tools;

/**
 * Complex number in polar form
 * 
 * @author chris@powerdata.com
 *
 */

public class PComplex
{
	private float _r;
	private float _theta;
	
	public static final PComplex Zero = new PComplex(0,0);

	public PComplex(float r, float theta)
	{
		_r = r;
		_theta = theta;
	}

	public float r() {return _r;}
	public float theta() {return _theta;}
	
	/** multiply two complex numbers */
	public PComplex mult(PComplex v) {return new PComplex(_r*v.r(), _theta+v.theta());}
	/** multiply two complex numbers */
	public PComplex mult(float r, float theta) {return new PComplex(_r*r, _theta + theta);}
	/** multiply a complex number by a scalar */
	public PComplex mult(float scalar) {return new PComplex(_r * scalar, _theta);}

	/** multiply two complex numbers */
	public PComplex div(PComplex v) {return new PComplex(_r/v.r(), _theta-v.theta());}
	/** multiply two complex numbers */
	public PComplex div(float r, float theta) {return new PComplex(_r/r, _theta - theta);}
	/** multiply a complex number by a scalar */
	public PComplex div(float scalar) {return new PComplex(_r / scalar, _theta);}
	
	/** invert the complex number */
	public PComplex inv() {return new PComplex(1F/_r, -_theta);}
	/** return the absolute value of the complex number */
	public float abs() {return _r;}
	/** return the complex conjugate */
	public PComplex conjg() {return new PComplex(_r, -_theta);}
	
	/** convert to cartesian coordinates */
	public Complex cartesian()
	{
		return new Complex((float) (_r * Math.cos(_theta)),
				(float) (_r * Math.sin(_theta)));
	}
	
	@Override
	final public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		sb.append(_r);
		sb.append(',');
		sb.append(_theta);
		sb.append(')');
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj)
	{
		PComplex o = (PComplex) obj;
		return (_r == o._r && _theta == o._theta);
	}
	

}


