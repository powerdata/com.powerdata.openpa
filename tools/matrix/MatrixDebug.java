package com.powerdata.openpa.tools.matrix;

import com.powerdata.openpa.PAModelException;

//import java.io.PrintWriter;

public interface MatrixDebug<T>
{
//	protected Matrix<T> _matrix;
//	public MatrixDebug(Matrix<T> matrix) {_matrix = matrix;}
//	public void setMatrix(Matrix<T> matrix) {_matrix = matrix;}
	
//	public void dump(PrintWriter pw)
//	{
//		int ncol = _matrix.getColumnCount(), nrow = _matrix.getRowCount();
//		for(int ic=0; ic < ncol; ++ic)
//		{
//			pw.print(',');
//			pw.format("\"%s\"", getColumnID(ic));
//		}
//		
//		for(int ir=0; ir < nrow; ++ir)
//		{
//			pw.format("\"%s\"", getRowID(ir));
//			for(int ic=0; ic < ncol; ++ic)
//			{
//				pw.print(',');
//				pw.format("\"%s\"", _matrix.getValue(ir, ic).toString());
//			}
//		}
//		
//	}
//
	abstract public String getRowID(int ir) throws PAModelException;

	abstract public String getColumnID(int col) throws PAModelException;
	
}
