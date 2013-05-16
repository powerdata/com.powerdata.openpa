package com.powerdata.openpa.padbc;

import java.util.AbstractList;

public abstract class NodeList extends AbstractList<Node> implements PowerAppsDBList
{
	public abstract float getNominalKV(int ndx);

	public abstract float getVmag(int ndx);

	public abstract float getVang(int ndx);

	public abstract void updateVmag(int ndx, float vm);

	public abstract void updateVang(int ndx, float va);

	@Override
	public Node get(int ndx) {return new Node(ndx, this);}
}
