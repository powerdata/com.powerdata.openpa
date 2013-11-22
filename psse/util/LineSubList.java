package com.powerdata.openpa.psse.util;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.LineList;
import com.powerdata.openpa.psse.LineMeterEnd;
import com.powerdata.openpa.psse.OwnershipList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.Complex;

public class LineSubList extends LineList
{
	LineList _lines;
	int[] _ndxs;
	
	public LineSubList() {super();}
	public LineSubList(LineList lines, int[] ndxs) throws PsseModelException
	{
		super(lines.getPsseModel());
		_lines = lines;
		_ndxs = ndxs;
		reindex();
	}

	@Override
	public String getI(int ndx) throws PsseModelException {return _lines.getI(_ndxs[ndx]);}
	@Override
	public String getJ(int ndx) throws PsseModelException {return _lines.getJ(_ndxs[ndx]);}
	@Override
	public float getX(int ndx) throws PsseModelException  {return _lines.getX(_ndxs[ndx]);}
	@Override
	public String getObjectID(int ndx) throws PsseModelException {return _lines.getObjectID(_ndxs[ndx]);}
	@Override
	public int size() {return _ndxs.length;}
	@Override
	public Bus getFromBus(int ndx) throws PsseModelException {return _lines.getFromBus(_ndxs[ndx]);}
	@Override
	public Bus getToBus(int ndx) throws PsseModelException {return _lines.getToBus(_ndxs[ndx]);}
	@Override
	public LineMeterEnd getMeteredEnd(int ndx) throws PsseModelException {return _lines.getMeteredEnd(_ndxs[ndx]);}
	@Override
	public boolean isInSvc(int ndx) throws PsseModelException {return _lines.isInSvc(_ndxs[ndx]);}
	@Override
	public Complex getZ(int ndx) throws PsseModelException {return _lines.getZ(_ndxs[ndx]);}
	@Override
	public Complex getY(int ndx) throws PsseModelException {return _lines.getY(_ndxs[ndx]);}
	@Override
	public String getObjectName(int ndx) throws PsseModelException {return _lines.getObjectName(_ndxs[ndx]);}
	@Override
	public String getCKT(int ndx) throws PsseModelException {return _lines.getCKT(_ndxs[ndx]);}
	@Override
	public float getR(int ndx) throws PsseModelException {return _lines.getR(_ndxs[ndx]);}
	@Override
	public float getB(int ndx) throws PsseModelException {return _lines.getB(_ndxs[ndx]);}
	@Override
	public float getRATEA(int ndx) throws PsseModelException {return _lines.getRATEA(_ndxs[ndx]);}
	@Override
	public float getRATEB(int ndx) throws PsseModelException {return _lines.getRATEB(_ndxs[ndx]);}
	@Override
	public float getRATEC(int ndx) throws PsseModelException {return _lines.getRATEC(_ndxs[ndx]);}
	@Override
	public float getGI(int ndx) throws PsseModelException {return _lines.getGI(_ndxs[ndx]);}
	@Override
	public float getBI(int ndx) throws PsseModelException {return _lines.getBI(_ndxs[ndx]);}
	@Override
	public float getGJ(int ndx) throws PsseModelException {return _lines.getGJ(_ndxs[ndx]);}
	@Override
	public float getBJ(int ndx) throws PsseModelException {return _lines.getBJ(_ndxs[ndx]);}
	@Override
	public int getST(int ndx) throws PsseModelException {return _lines.getST(_ndxs[ndx]);}
	@Override
	public float getLEN(int ndx) throws PsseModelException {return _lines.getLEN(_ndxs[ndx]);}
	@Override
	public float getFromBchg(int ndx) throws PsseModelException {return _lines.getFromBchg(_ndxs[ndx]);}
	@Override
	public float getToBchg(int ndx) throws PsseModelException {return _lines.getToBchg(_ndxs[ndx]);}
	@Override
	public float getMVA(int ndx) throws PsseModelException {return _lines.getMVA(_ndxs[ndx]);}
	@Override
	public float getMVAPercent(int ndx) throws PsseModelException {return _lines.getMVAPercent(_ndxs[ndx]);}
	@Override
	public OwnershipList getOwnership(int ndx) throws PsseModelException {return _lines.getOwnership(_ndxs[ndx]);}
	@Override
	public void commit() throws PsseModelException {_lines.commit();}
	@Override
	public int getRootIndex(int ndx) {return _lines.getRootIndex(_ndxs[ndx]);}
}
