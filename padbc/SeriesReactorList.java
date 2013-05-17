package com.powerdata.openpa.padbc;

public abstract class SeriesReactorList extends BaseList<SeriesReactor>
{
	public abstract int getFromNode(int ndx);
	public abstract int getToNode(int ndx);
	public abstract float getR(int ndx);
	public abstract float getX(int ndx);
	public abstract void updateActvPower(int ndx, float p);
	public abstract void updateReacPower(int ndx, float q);
	
//	public abstract void updateMW(int ndx, float p);
//	public abstract void updateMVAr(int ndx, float q);

	@Override
	public SeriesReactor get(int ndx) {return new SeriesReactor(ndx, this);}

}
