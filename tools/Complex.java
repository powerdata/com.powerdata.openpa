package com.powerdata.openpa.tools;

/**
 * Complex number in cartesian form
 * 
 * @author chris@powerdata.com
 * 
 */
public class Complex
{
	private float _re;
	private float _im;
	
	public static final Complex Zero = new Complex(0,0);

	public Complex(float re, float im)
	{
		_re = re;
		_im = im;
	}

	public float re() {return _re;}
	public float im() {return _im;}
	
	public Complex inv()
	{
		float den = _re*_re+_im*_im;
		return new Complex(_re/den, _im/-den);
	}

	/** Conjugate a complex number in cartesian coordinates. */
	public Complex conjg() { return new Complex(_re, _im*-1); } 
	/** Calculate absolute value of a complex number */
	public float abs() {return (float) Math.sqrt(_re*_re+_im*_im);}
	/** add two complex numbers */
	public Complex add(Complex v) {return new Complex(_re+v._re, _im+v._im);}
	/** add two complex numbers */
	public Complex add(float re2, float im2) {return new Complex(_re+re2, _im+im2);}
	/** subtract two complex numbers */
	public Complex sub(Complex v) {return new Complex(_re-v._re,_im -v._im);}
	/** subtract two complex numbers */
	public Complex sub(float re2, float im2) {return new Complex(_re - re2, _im - im2);}
	/** multiply the complex number by a scalar */
	public Complex mult(float scalar) {return new Complex(_re*scalar, _im*scalar);}
	/** multiply two complex numbers */
	public Complex mult(Complex v) {return mult(v._re, v._im);}
	/** multiply two complex numbers */
	public Complex mult(float re2, float im2)
	{
		return new Complex(_re*re2-_im*im2, _im*re2+_re*im2);
	}
	/** divide the complex number by a scalar */
	public Complex div(float scalar) {return new Complex(_re/scalar, _im/scalar);}
	/** divide two complex numbers */
	public Complex div(Complex divisor) {return div(divisor._re, divisor._im);}
	/** divide two complex numbers */
	public Complex div(float divre, float divim)
	{
		float den = divre * divre + divim * divim;
		return new Complex((_re * divre + _im * divim) / den,
			(_im * divre - _re * divim) / den);
	}

	/** convert to polar coordinates */
	public PComplex polar() {return new PComplex(abs(), (float)Math.atan2(_im, _re));}
	
	@Override
	final public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		sb.append(_re);
		sb.append(',');
		sb.append(_im);
		sb.append(')');
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj)
	{
		Complex o = (Complex) obj;
		return (_re == o._re && _im == o._im);
	}
	
}
