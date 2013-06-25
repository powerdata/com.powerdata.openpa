package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BooleanAttrib;
import com.powerdata.openpa.tools.FloatAttrib;
import com.powerdata.openpa.tools.IntAttrib;
import com.powerdata.openpa.tools.StringAttrib;

public class BranchInList extends PsseBaseInputList<BranchIn>
{
	public BranchInList(PsseInputModel model)
	{
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getObjectID(int ndx)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringAttrib<BranchIn> mapStringAttrib(String attribname)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FloatAttrib<BranchIn> mapFloatAttrib(String attribname)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntAttrib<BranchIn> mapIntAttrib(String attribname)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BooleanAttrib<BranchIn> mapBooleanAttrib(String attribname)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BranchIn get(int arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public BusIn getFrBus(int ndx)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public BusIn getToBus(int ndx)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public float getR(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public float getX(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public float getFrB(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public float getToB(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public float getFrTapRatio(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public float getToTapRatio(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public float getPhaseShift(int ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}

abstract class BranchView
{
	protected int _ofs;
	protected int _lastndx=-1;
	protected int _lastofs = -1;

	public BranchView (int offset) {_ofs = offset;}
	
	public int tfndx(int ndx) {return ndx-_ofs;}
	
	public abstract BusIn getFrBus(int ndx) throws PsseModelException;
	public abstract BusIn getToBus(int ndx) throws PsseModelException;
	public abstract float getR(int ndx) throws PsseModelException;
	public abstract float getX(int ndx) throws PsseModelException;
	public abstract float getFrB(int ndx) throws PsseModelException;
	public abstract float getToB(int ndx) throws PsseModelException;
	public abstract float getFrTapRatio(int ndx) throws PsseModelException;
	public abstract float getToTapRatio(int ndx) throws PsseModelException;
	public abstract float getPhaseShift(int ndx) throws PsseModelException;
}

class LineView extends BranchView
{
	protected LineInList	_lines;
	
	public LineView(int offset, LineInList lines)
	{
		super(offset);
		_lines = lines;
	}

	@Override
	public BusIn getFrBus(int ndx) throws PsseModelException {return _lines.getFromBus(tfndx(ndx));}
	@Override
	public BusIn getToBus(int ndx) throws PsseModelException {return _lines.getToBus(tfndx(ndx));}
	@Override
	public float getR(int ndx) throws PsseModelException {return _lines.getR(tfndx(ndx));}
	@Override
	public float getX(int ndx) throws PsseModelException {return _lines.getX(tfndx(ndx));}
	@Override
	public float getFrB(int ndx) throws PsseModelException {return _lines.getFromBch(tfndx(ndx));}
	@Override
	public float getToB(int ndx) throws PsseModelException {return _lines.getToBch(tfndx(ndx));}
	@Override
	public float getFrTapRatio(int ndx) {return 1F;}
	@Override
	public float getToTapRatio(int ndx) {return 1F;}
	@Override
	public float getPhaseShift(int ndx) {return 0F;}
}

