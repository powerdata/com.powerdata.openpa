package com.powerdata.openpa.tools.matrix;

/**
 * A simple array-backed matrix with floating-point values
 * 
 * @author chris@powerdata.com
 *
 */
public class FloatArrayMatrix implements FloatMatrix
{
	float[][] _m;
	
	public FloatArrayMatrix(int nrow, int ncol)
	{
		_m = new float[nrow][ncol];
	}
	
	public FloatArrayMatrix()
	{
		
	}
	
	public void setSize(int nrow, int ncol)
	{
		_m = new float[nrow][ncol];
	}
	
	@Override
	public int getRowCount()
	{
		return _m.length;
	}

	@Override
	public int getColumnCount()
	{
		return _m[0].length;
	}

	@Override
	public void setValue(int row, int column, float value)
	{
		_m[row][column] = value;
	}

	@Override
	public float getValue(int row, int column)
	{
		return _m[row][column];
	}

	@Override
	public void addValue(int row, int column, float value)
	{
		_m[row][column] += value;
	}

	@Override
	public void multValue(int row, int column, float value)
	{
		_m[row][column] *= value;
	}
}
