package com.powerdata.openpa.padbc.isc;

import java.io.IOException;

import com.powerdata.openpa.padbc.BooleanAttrib;
import com.powerdata.openpa.padbc.FloatAttrib;
import com.powerdata.openpa.padbc.IntAttrib;
import com.powerdata.openpa.padbc.StringAttrib;
import com.powerdata.openpa.tools.SimpleCSV;

public class TransformerWndList extends com.powerdata.openpa.padbc.TransformerWndList<TransformerWinding>
{
	static final int I = 0;
	static final int J = 1;
	static final int K = 2;

	Equipment _eq;
	NodeList _nodes;
	SimpleCSV _xfr;
	int _size;
	
	public TransformerWndList(Equipment eq) throws IOException
	{
		_eq = eq;
		_xfr = new SimpleCSV(_eq.getDir().getPath()+"/Transformers.csv");
		_size = _xfr.getRowCount();
		_nodes = _eq.getNodes();
	}
	public void dumpHeaders()
	{
		String h[] = _xfr.getColumnNames();
		for(int i=0; i<h.length; i++)
		{
			if(i>0) System.out.print(",");
			System.out.print(h[i]);
		}
		System.out.println();		
	}
	public void dumpRow(int ndx)
	{
		for(int i=0; i<_xfr.getColCount(); i++)
		{
			if(i>0) System.out.print(",");
			System.out.print(_xfr.get(i, ndx));
		}
		System.out.println();
	}
	@Override
	public int getFromNode(int ndx)
	{
		String busid = _xfr.get(I,ndx);
		return _nodes.get(ndx).getNdx(busid);
	}
	@Override
	public int getToNode(int ndx)
	{
		String busid = _xfr.get(J,ndx);
		return _nodes.get(ndx).getNdx(busid);
	}
	@Override
	public float getR(int ndx) { return 0; }
	@Override
	public float getX(int ndx) { return 0; }
	@Override
	public float getFromBChg(int ndx) { return 0; }
	@Override
	public float getToBChg(int ndx) { return 0; }
	@Override
	public float getBmag(int ndx) { return 0; }
	@Override
	public float getFromTapRatio(int ndx) { return 0; }
	@Override
	public float getToTapRatio(int ndx) { return 0; }
	@Override
	public float getPhaseShift(int ndx) { return 0; }
	@Override
	public void updateActvPower(int ndx, float p) {}
	@Override
	public void updateReacPower(int ndx, float q) {}
	@Override
	public TransformerWinding get(int ndx) { return new TransformerWinding(ndx,this); }
	@Override
	public String getID(int ndx) { return null; }
	@Override
	public StringAttrib<TransformerWinding> mapStringAttrib(String attribname) {
		return null;
	}
	@Override
	public FloatAttrib<TransformerWinding> mapFloatAttrib(String attribname) {
		return null;
	}
	@Override
	public IntAttrib<TransformerWinding> mapIntAttrib(String attribname) {
		return null;
	}
	@Override
	public BooleanAttrib<TransformerWinding> mapBooleanAttrib(String attribname) {
		return null;
	}
	@Override
	public int size() { return _size; }
}
