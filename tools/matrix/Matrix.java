package com.powerdata.openpa.tools.matrix;

import java.io.PrintWriter;
import com.powerdata.openpa.PAModelException;

/**
 * Matrix with value of any object
 * @author chris@powerdata.com
 *
 */
public interface Matrix<T>
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
	void setValue(int row, int column, T value);
	/**
	 * Get a value
	 * @param row location of new value
	 * @param column location of new value
	 * @return value stored at given location
	 */
	T getValue(int row, int column);
	/**
	 * Add a value in place
	 * @param row location of updated value
	 * @param column location of updated value
	 * @param value to add at given location
	 */
	void addValue(int row, int column, T value);
	/**
	 * subtract a value in place
	 * @param row location of updated value
	 * @param column location of updated value
	 * @param value to subtract at given location
	 */
	void subValue(int row, int column, T value);

	default void dump(PrintWriter pw, MatrixDebug<T> dbg) throws PAModelException
	{
		int nc = getColumnCount(), nr = getRowCount();
		for(int i=0; i < nc; ++i)
		{
			pw.print(',');
			pw.print(dbg.getColumnID(i));
		}
		pw.println();
		for(int ir=0; ir < nr; ++ir)
		{
			pw.print(dbg.getRowID(ir));
			pw.print(',');
			for(int ic=0; ic < nc; ++ic)
			{
				pw.format("\"%s\",", getValue(ir, ic));
			}
			pw.println();
		}
	}

	

}
