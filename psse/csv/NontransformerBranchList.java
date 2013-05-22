package com.powerdata.openpa.psse.csv;

import java.io.IOException;
import java.util.HashMap;

import com.powerdata.openpa.psse.OwnershipList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.BooleanAttrib;
import com.powerdata.openpa.tools.FloatAttrib;
import com.powerdata.openpa.tools.IntAttrib;
import com.powerdata.openpa.tools.SimpleCSV;
import com.powerdata.openpa.tools.StringAttrib;

public class NontransformerBranchList extends com.powerdata.openpa.psse.NontransformerBranchList<NontransformerBranch>
{
	PsseModel _eq;
	BusList _buses;
	HashMap<String,Integer> _objIDtoNdx = new HashMap<String,Integer>();
	int _size;
	
	String _i[],_j[],_ckt[];
	float _r[],_x[],_b[],_ratea[],_rateb[],_ratec[],_gi[],_bi[],_gj[],_bj[];
	int _st[];
	float _len[];
	
	public NontransformerBranchList(PsseModel eq) throws PsseModelException
	{
		super(eq);
		try
		{
			_eq = eq;
			_buses = _eq.getBuses();
			SimpleCSV branches = new SimpleCSV(_eq.getDir().getPath()+"/Branches.csv");
			_size	= branches.getRowCount();
			_i		= branches.get("I");
			_j		= branches.get("J");
			_ckt	= branches.get("CKT");
			_r		= branches.getFloats("R");
			_x		= branches.getFloats("X");
			_b		= branches.getFloats("B");
			_ratea	= branches.getFloats("RATEA");
			_rateb	= branches.getFloats("RATEB");
			_ratec	= branches.getFloats("RATEC");
			_gi		= branches.getFloats("GI");
			_bi		= branches.getFloats("BI");
			_gj		= branches.getFloats("GJ");
			_bj		= branches.getFloats("BJ");
			_st		= branches.getInts("ST");
			_len	= branches.getFloats("LEN");
			for(int i=0; i<_size; i++) _objIDtoNdx.put(getObjectID(i),i);
		}
		catch(IOException e)
		{
			throw new PsseModelException(getClass().getName()+": "+e);
		}
	}

	@Override
	public String getI(int ndx) { return (_i != null)?_i[ndx]:""; }
	@Override
	public String getJ(int ndx) { return (_j != null)?_j[ndx]:""; }
	@Override
	public String getCKT(int ndx) { return (_ckt != null)?_ckt[ndx]:""; }
	@Override
	public float getR(int ndx) { return (_r != null)?_r[ndx]:0; }
	@Override
	public float getX(int ndx) { return (_x != null)?_x[ndx]:0; }
	@Override
	public float getB(int ndx) { return (_b != null)?_b[ndx]:0; }
	@Override
	public float getRATEA(int ndx) { return (_ratea != null)?_ratea[ndx]:0; }
	@Override
	public float getRATEB(int ndx) { return (_rateb != null)?_rateb[ndx]:0; }
	@Override
	public float getRATEC(int ndx) { return (_ratec != null)?_ratec[ndx]:0; }
	@Override
	public float getGI(int ndx) { return (_gi != null)?_gi[ndx]:0; }
	@Override
	public float getBI(int ndx) { return (_bi != null)?_bi[ndx]:0; }
	@Override
	public float getGJ(int ndx) { return (_gj != null)?_gj[ndx]:0; }
	@Override
	public float getBJ(int ndx) { return (_bj != null)?_bj[ndx]:0; }
	@Override
	public int getST(int ndx) { return (_st != null)?_st[ndx]:0; }
	@Override
	public float getLEN(int ndx) { return (_len != null)?_len[ndx]:0; }
	@Override
	public OwnershipList<?> getOwnership(int ndx) { return null; }
	@Override
	public String getObjectID(int ndx) { return _i[ndx]+":"+_j[ndx]+":"+_ckt[ndx]; }
	@Override
	public NontransformerBranch get(String objectid)
	{ 
		Integer ndx = _objIDtoNdx.get(objectid);
		return (ndx != null)?new NontransformerBranch(ndx,this):null;
	}
	@Override
	public StringAttrib<NontransformerBranch> mapStringAttrib(String attribname) { return null; }
	@Override
	public FloatAttrib<NontransformerBranch> mapFloatAttrib(String attribname) { return null; }
	@Override
	public IntAttrib<NontransformerBranch> mapIntAttrib(String attribname) { return null; }
	@Override
	public BooleanAttrib<NontransformerBranch> mapBooleanAttrib(String attribname) { return null; }
	@Override
	public NontransformerBranch get(int index) { return new NontransformerBranch(index,this); }
	@Override
	public int size() { return _size; }
}
