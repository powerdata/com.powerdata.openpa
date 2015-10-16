package com.powerdata.openpa.impl;

import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.Line;
import com.powerdata.openpa.LineList;
import com.powerdata.openpa.PAModelException;

public class LineListI extends ACBranchListI<Line> implements LineList
{
	static final ACBranchEnum _PFld = new ACBranchEnum()
	{
		@Override
		public ColumnMeta fbus()
		{
			return ColumnMeta.LineBUSFROM;
		}
		@Override
		public ColumnMeta tbus()
		{
			return ColumnMeta.LineBUSTO;
		}
		@Override
		public ColumnMeta insvc()
		{
			return ColumnMeta.LineINSVC;
		}
		@Override
		public ColumnMeta fp()
		{
			return ColumnMeta.LinePFROM;
		}
		@Override
		public ColumnMeta fq()
		{
			return ColumnMeta.LineQFROM;
		}
		@Override
		public ColumnMeta tp()
		{
			return ColumnMeta.LinePTO;
		}
		@Override
		public ColumnMeta tq()
		{
			return ColumnMeta.LineQTO;
		}
		@Override
		public ColumnMeta id()
		{
			return ColumnMeta.LineID;
		}
		@Override
		public ColumnMeta name()
		{
			return ColumnMeta.LineNAME;
		}
		@Override
		public ColumnMeta r()
		{
			return ColumnMeta.LineR;
		}
		@Override
		public ColumnMeta x()
		{
			return ColumnMeta.LineX;
		}
		@Override
		public ColumnMeta ratLT()
		{
			return ColumnMeta.LineRATLT;
		}
	};
	FloatData _fb = new FloatData(ColumnMeta.LineBFROM), _tb = new FloatData(
			ColumnMeta.LineBTO); 
	public LineListI()
	{
		super();
	}
	public LineListI(PAModelI model, int[] keys) throws PAModelException
	{
		super(model, keys, _PFld);
	}
	public LineListI(PAModelI model, int size) throws PAModelException
	{
		super(model, size, _PFld);
	}
	@Override
	public float getFromBchg(int ndx) throws PAModelException
	{
		return _fb.get(ndx);
	}
	@Override
	public void setFromBchg(int ndx, float b) throws PAModelException
	{
		_fb.set(ndx, b);
	}
	@Override
	public float[] getFromBchg() throws PAModelException
	{
		return _fb.get();
	}
	@Override
	public void setFromBchg(float[] b) throws PAModelException
	{
		_fb.set(b);
	}
	@Override
	public float getToBchg(int ndx) throws PAModelException
	{
		return _tb.get(ndx);
	}
	@Override
	public void setToBchg(int ndx, float b) throws PAModelException
	{
		_tb.set(ndx, b);
	}
	@Override
	public float[] getToBchg() throws PAModelException
	{
		return _tb.get();
	}
	@Override
	public void setToBchg(float[] b) throws PAModelException
	{
		_tb.set(b);
	}
	@Override
	public Line get(int index)
	{
		return new Line(this, index);
	}
}
