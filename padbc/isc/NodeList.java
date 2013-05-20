package com.powerdata.openpa.padbc.isc;

import java.io.IOException;
import java.util.HashMap;

import com.powerdata.openpa.tools.BooleanAttrib;
import com.powerdata.openpa.tools.FloatAttrib;
import com.powerdata.openpa.tools.IntAttrib;
import com.powerdata.openpa.tools.SimpleCSV;
import com.powerdata.openpa.tools.StringAttrib;

public class NodeList extends com.powerdata.openpa.padbc.NodeList<Node>
{
	static final int FLAG = 0;
	static final int I = 1;
	static final int NAME = 2;
	static final int BASEKV = 3;
	static final int IDE = 4;
	static final int AREA = 5;
	static final int ZONE = 6;
	static final int OWNER = 7;
	static final int VM = 8;
	static final int VA = 9;
	
	Equipment _eq;
	HashMap<String,Integer> _idToNdx = new HashMap<String,Integer>();
	SimpleCSV _nodes;
	int _size = 0;
	
	public NodeList(Equipment eq) throws IOException
	{
		_eq = eq;
		_nodes = new SimpleCSV(_eq.getDir().getPath()+"/Buses.csv");
		_size = _nodes.getRowCount();
		for(int i=0; i<_size; i++) _idToNdx.put(_nodes.get(I, i), i);
	}
	public int getFlag(int ndx) { return Integer.parseInt(_nodes.get(FLAG,ndx)); }
	public int getNdx(String id)
	{
		Integer ndx = _idToNdx.get(id);
		return (ndx != null)?ndx:-1;
	}
	public String getName(int ndx) { return _nodes.get(NAME,ndx); }
	public String getIDE(int ndx) { return _nodes.get(IDE,ndx); }
	public String getArea(int ndx) { return _nodes.get(AREA, ndx); }
	public String getZone(int ndx) { return _nodes.get(ZONE, ndx); }
	public String getOwner(int ndx) { return _nodes.get(OWNER, ndx); }
	@Override
	public String getID(int ndx) { return _nodes.get(I,ndx); }
	@Override
	public float getNominalKV(int ndx) { return Float.parseFloat(_nodes.get(BASEKV, ndx)); }
	@Override
	public float getVmag(int ndx) { return Float.parseFloat(_nodes.get(VM, ndx)); }
	@Override
	public float getVang(int ndx) { return Float.parseFloat(_nodes.get(VA, ndx)); }
	@Override
	public void updateVmag(int ndx, float vm) { _nodes.set(VM, ndx, String.valueOf(vm)); }
	@Override
	public void updateVang(int ndx, float va) { _nodes.set(VA, ndx, String.valueOf(va)); }
	@Override
	public StringAttrib<Node> mapStringAttrib(String attribname)
	{
		return null;
	}
	@Override
	public FloatAttrib<Node> mapFloatAttrib(String attribname)
	{
		return null;
	}
	@Override
	public IntAttrib<Node> mapIntAttrib(String attribname)
	{
		return null;
	}
	@Override
	public BooleanAttrib<Node> mapBooleanAttrib(String attribname)
	{
		return null;
	}
	@Override
	public int size() { return _size; }
	@Override
	public Node get(int ndx) { return new Node(ndx,this); }
	@Override
	public int getIndex(String id)
	{
		// TODO Auto-generated method stub
		return 0;
	}
}
