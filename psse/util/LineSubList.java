package com.powerdata.openpa.psse.util;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.Line;
import com.powerdata.openpa.psse.LineList;
import com.powerdata.openpa.psse.LineMeterEnd;
import com.powerdata.openpa.psse.OwnershipList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.Complex;

public class LineSubList extends LineList
{
	LineList _base;
	int[] _ndxs;
	boolean _indexed = false;
	
	public LineSubList() {super();}
	public LineSubList(LineList lines, int[] ndxs) throws PsseModelException
	{
		super(lines.getPsseModel());
		_base = lines;
		_ndxs = ndxs;
	}

	
	@Override
	public Line get(String id)
	{
		if (!_indexed)
		{
			_indexed = true;
			try
			{
				reindex();
			} catch (PsseModelException e)
			{
				e.printStackTrace();
			}
		}
		return super.get(id);
	}

	protected int map(int ndx) {return _ndxs[ndx];}

	@Override
	public String getFullName(int ndx) throws PsseModelException {return _base.getFullName(map(ndx));}
	@Override
	public String getDebugName(int ndx) throws PsseModelException {return _base.getDebugName(map(ndx));}
	@Override
	public String getI(int ndx) throws PsseModelException {return _base.getI(map(ndx));}
	@Override
	public String getJ(int ndx) throws PsseModelException {return _base.getJ(map(ndx));}
	@Override
	public float getX(int ndx) throws PsseModelException  {return _base.getX(map(ndx));}
	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _base.getObjectID(map(ndx));}
	@Override
	public int size() {return _ndxs.length;}
	@Override
	public Bus getFromBus(int ndx) throws PsseModelException {return _base.getFromBus(map(ndx));}
	@Override
	public Bus getToBus(int ndx) throws PsseModelException {return _base.getToBus(map(ndx));}
	@Override
	public LineMeterEnd getMeteredEnd(int ndx) throws PsseModelException {return _base.getMeteredEnd(map(ndx));}
	@Override
	public boolean isInSvc(int ndx) throws PsseModelException {return _base.isInSvc(map(ndx));}
	@Override
	public Complex getZ(int ndx) throws PsseModelException {return _base.getZ(map(ndx));}
	@Override
	public Complex getY(int ndx) throws PsseModelException {return _base.getY(map(ndx));}
	@Override
	public String getObjectName(int ndx) throws PsseModelException {return _base.getObjectName(map(ndx));}
	@Override
	public String getCKT(int ndx) throws PsseModelException {return _base.getCKT(map(ndx));}
	@Override
	public float getR(int ndx) throws PsseModelException {return _base.getR(map(ndx));}
	@Override
	public float getRATEA(int ndx) throws PsseModelException {return _base.getRATEA(map(ndx));}
	@Override
	public float getRATEB(int ndx) throws PsseModelException {return _base.getRATEB(map(ndx));}
	@Override
	public float getRATEC(int ndx) throws PsseModelException {return _base.getRATEC(map(ndx));}
	@Override
	public float getGI(int ndx) throws PsseModelException {return _base.getGI(map(ndx));}
	@Override
	public float getBI(int ndx) throws PsseModelException {return _base.getBI(map(ndx));}
	@Override
	public float getGJ(int ndx) throws PsseModelException {return _base.getGJ(map(ndx));}
	@Override
	public float getBJ(int ndx) throws PsseModelException {return _base.getBJ(map(ndx));}
	@Override
	public int getST(int ndx) throws PsseModelException {return _base.getST(map(ndx));}
	@Override
	public float getLEN(int ndx) throws PsseModelException {return _base.getLEN(map(ndx));}
	@Override
	public float getFromBchg(int ndx) throws PsseModelException {return _base.getFromBchg(map(ndx));}
	@Override
	public float getToBchg(int ndx) throws PsseModelException {return _base.getToBchg(map(ndx));}
	@Override
	public float getMVA(int ndx) throws PsseModelException {return _base.getMVA(map(ndx));}
	@Override
	public float getMVAPercent(int ndx) throws PsseModelException {return _base.getMVAPercent(map(ndx));}
	@Override
	public OwnershipList getOwnership(int ndx) throws PsseModelException {return _base.getOwnership(map(ndx));}
	@Override
	public void commit() throws PsseModelException {_base.commit();}
	@Override
	public int getRootIndex(int ndx) {return _base.getRootIndex(map(ndx));}
	@Override
	public void setInSvc(int ndx, boolean state) throws PsseModelException {_base.setInSvc(map(ndx), state);}
	@Override
	public long getKey(int ndx) throws PsseModelException {return _base.getKey(map(ndx));}
}
