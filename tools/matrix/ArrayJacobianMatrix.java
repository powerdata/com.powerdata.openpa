package com.powerdata.openpa.tools.matrix;

public class ArrayJacobianMatrix implements JacobianMatrix
{
	int _nrow, _ncol;
	JacobianArrayList[] _m;
	
	public ArrayJacobianMatrix(int nrow, int ncol)
	{
		_nrow = nrow;
		_ncol = ncol;
		_m = new JacobianArrayList[ncol];
		for(int i=0; i < ncol; ++i)
			_m[i] = new JacobianArrayList(nrow);
	}
	
	@Override
	public int getRowCount()
	{
		return _nrow;
	}

	@Override
	public int getColumnCount()
	{
		return _ncol;
	}

	@Override
	public void setValue(int row, int column, JacobianElement value)
	{
		_m[column].replace(row, value);
	}

	@Override
	public JacobianElement getValue(int row, int column)
	{
		return _m[column].get(row);
	}

	@Override
	public void addValue(int row, int column, JacobianElement value)
	{
		_m[column].inc(row, value);
	}

	@Override
	public void subValue(int row, int column, JacobianElement value)
	{
		_m[column].dec(row, value);
	}
	
}
