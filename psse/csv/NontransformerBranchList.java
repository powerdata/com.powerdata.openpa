package com.powerdata.openpa.psse.csv;

import java.util.HashMap;

import com.powerdata.openpa.psse.OwnershipList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.BooleanAttrib;
import com.powerdata.openpa.tools.FloatAttrib;
import com.powerdata.openpa.tools.IntAttrib;
import com.powerdata.openpa.tools.LoadArray;
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
			_ckt	= LoadArray.String(branches,"CKT",this,"getDeftCKT");
			_r		= branches.getFloats("R");
			_x		= branches.getFloats("X");
			_b		= LoadArray.Float(branches,"B",this,"getDeftB");
			_ratea	= LoadArray.Float(branches,"RATEA",this,"getDeftRATEA");
			_rateb	= LoadArray.Float(branches,"RATEB",this,"getDeftRATEB");
			_ratec	= LoadArray.Float(branches,"RATEC",this,"getDeftRATEC");
			_gi		= LoadArray.Float(branches,"GI",this,"getDeftGI");
			_bi		= LoadArray.Float(branches,"BI",this,"getDeftBI");
			_gj		= LoadArray.Float(branches,"GJ",this,"getDeftGJ");
			_bj		= LoadArray.Float(branches,"BJ",this,"getDeftBJ");
			_st		= LoadArray.Int(branches,"ST",this,"getDeftST");
			_len	= LoadArray.Float(branches,"LEN",this,"getDeftLEN");
			for(int i=0; i<_size; i++) _objIDtoNdx.put(getObjectID(i),i);
		}
		catch(Exception e)
		{
			throw new PsseModelException(getClass().getName()+": "+e);
		}
	}

	@Override
	public String getI(int ndx) { return _i[ndx]; }
	@Override
	public String getJ(int ndx) { return _j[ndx]; }
	@Override
	public String getCKT(int ndx) { return _ckt[ndx]; }
	@Override
	public float getR(int ndx) { return _r[ndx]; }
	@Override
	public float getX(int ndx) { return _x[ndx]; }
	@Override
	public float getB(int ndx) { return _b[ndx]; }
	@Override
	public float getRATEA(int ndx) { return _ratea[ndx]; }
	@Override
	public float getRATEB(int ndx) { return _rateb[ndx]; }
	@Override
	public float getRATEC(int ndx) { return _ratec[ndx]; }
	@Override
	public float getGI(int ndx) { return _gi[ndx]; }
	@Override
	public float getBI(int ndx) { return _bi[ndx]; }
	@Override
	public float getGJ(int ndx) { return _gj[ndx]; }
	@Override
	public float getBJ(int ndx) { return _bj[ndx]; }
	@Override
	public int getST(int ndx) { return _st[ndx]; }
	@Override
	public float getLEN(int ndx) { return _len[ndx]; }
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
