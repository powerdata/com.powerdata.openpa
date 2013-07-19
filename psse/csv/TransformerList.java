package com.powerdata.openpa.psse.csv;


import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.ComplexList;

public class TransformerList extends com.powerdata.openpa.psse.TransformerList
{
	int _size;

	/* line 1 */
	String[] _ckt, _name;
	int[] _i, _j, _cw, _cm, _nmetr, _stat;
	float[] _mag1, _mag2;
	ComplexList _z;
	
	public TransformerList(PsseModel model, TransformerRawList rlist,
			TransformerPrep prep) throws PsseModelException 
	{
		super(model);
		_size = prep.size();
		
	}
	
	@Override
	public Bus getFromBus(int ndx) throws PsseModelException {return _buses.get(_i[ndx]);}
	@Override
	public Bus getToBus(int ndx) throws PsseModelException {return _buses.get(_j[ndx]);}

	@Override
	public String getI(int ndx) throws PsseModelException {return _buses.get(_i[ndx]).getObjectID();}
	@Override
	public String getJ(int ndx) throws PsseModelException {return _buses.get(_j[ndx]).getObjectID();}

	
	@Override
	public float getX1_2(int ndx) throws PsseModelException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {return _size;}
}
