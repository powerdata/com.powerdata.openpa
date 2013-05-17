package com.powerdata.openpa.padbc;

public abstract class NodeList extends BaseList<Node>
{
	public abstract float getNominalKV(int ndx);

	public abstract float getVmag(int ndx);

	public abstract float getVang(int ndx);

	public abstract void updateVmag(int ndx, float vm);

	public abstract void updateVang(int ndx, float va);

	@Override
	public Node get(int ndx) {return new Node(ndx, this);}
}
