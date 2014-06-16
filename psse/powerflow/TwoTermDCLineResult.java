package com.powerdata.openpa.psse.powerflow;

import com.powerdata.openpa.tools.AbstractBaseObject;
import com.powerdata.openpa.psse.PsseModelException;

public class TwoTermDCLineResult extends AbstractBaseObject
{
	TwoTermDCLineResultList _rlist;
	
	public TwoTermDCLineResult(TwoTermDCLineResultList list, int ndx)
	{
		super(list, ndx);
		_rlist = list;
	}
	
	public float getTapR() throws PsseModelException {return _rlist.getTapR(_ndx);}
	public float getTapI() throws PsseModelException {return _rlist.getTapI(_ndx);}
	public float getAlpha() throws PsseModelException {return _rlist.getAlpha(_ndx);}
	public float getGamma() throws PsseModelException {return _rlist.getGamma(_ndx);}
	public float getMWR() throws PsseModelException {return _rlist.getMWR(_ndx);}
	public float getMWI() throws PsseModelException {return _rlist.getMWI(_ndx);}
	public float getMVArR() throws PsseModelException {return _rlist.getMVArR(_ndx);}
	public float getMVArI() throws PsseModelException {return _rlist.getMVArI(_ndx);}

	@Override
	public String toString()
	{
		String v = "<err>";
		try
		{
			v =  String.format("%s: alpha=%f, rtap=%f, rmw=%f, rmvar=%f, gamma=%f, itap=%f, imw=%f, imvar=%f\n",
					getObjectName(), getAlpha(), getTapR(), getMWR(), getMVArR(),
					getGamma(), getTapI(), getMWI(), getMVArI());
		} catch (PsseModelException e)
		{
			e.printStackTrace();
		}
		return v;
	}
	
	
}
