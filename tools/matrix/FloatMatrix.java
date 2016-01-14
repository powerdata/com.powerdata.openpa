package com.powerdata.openpa.tools.matrix;

/**
 * Interface for a matrix with floating-point real values
 * @author chris@powerdata.com
 *
 */
public interface FloatMatrix
{
	/** 
	 * Return the number of rows in the matrix.
	 * @return Number of matrix rows
	 */
	int getRowCount();
	/**
	 * Return the number of columns in the matrix
	 * @return Number of matrix columns
	 */
	int getColumnCount();
	
	/**
	 * Set a value
	 * @param row location of new value
	 * @param column location of new value
	 * @param value value to store
	 */
	void setValue(int row, int column, float value);
	/**
	 * Get a value
	 * @param row location of new value
	 * @param column location of new value
	 * @return value stored at given location
	 */
	float getValue(int row, int column);
		/**
		 * Add a value in place
		 * @param row location of updated value
		 * @param column location of updated value
		 * @param value to add at given location
		 */
	void addValue(int row, int column, float value);
	/**
	 * Multiply a value in place
	 * @param row location of updated value
	 * @param column location of updated value
	 * @param value to multiply at given location
	 */
	void multValue(int row, int column, float value);
	/**
	 * subtract a value in place
	 * @param row location of updated value
	 * @param column location of updated value
	 * @param value to subtract at given location
	 */
	default void subValue(int row, int column, float value)
	{
		addValue(row, column, -value);
	}
	/**
	 * divide a value in place
	 * @param row location of updated value
	 * @param column location of updated value
	 * @param value to divide by at given location
	 */
	default void divValue(int row, int column, float value)
	{
		multValue(row, column, 1f/value);
	}
	
	static public Matrix<Float> wrap(FloatMatrix m)
	{
		return new Matrix<Float>()
		{
			@Override
			public int getRowCount() {return m.getRowCount();}
			@Override
			public int getColumnCount() {return m.getColumnCount();}
			@Override
			public void setValue(int row, int column, Float value)
			{
				m.setValue(row, column, value);
			}

			@Override
			public Float getValue(int row, int column)
			{
				return m.getValue(row, column);
			}

			@Override
			public void addValue(int row, int column, Float value)
			{
				m.addValue(row, column, value);
			}

			@Override
			public void subValue(int row, int column, Float value)
			{
				m.subValue(row, column, value);
			}
		};
	}
	
}
