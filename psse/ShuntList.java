package com.powerdata.openpa.psse;

public abstract class ShuntList extends PsseBaseList<Shunt>
{
	public static final ShuntList Empty = new ShuntList()
	{
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	
	protected ShuntList(){super();}
	public ShuntList(PsseModel model) {super(model);}

	/** Get a Switch by it's index. */
	@Override
	public Shunt get(int ndx) { return new Shunt(ndx,this); }
	/** Get an SwitchIn by it's ID. */
	@Override
	public Shunt get(String id) { return super.get(id); }
	public Bus getBus(int ndx) throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}
	public float getNomB(int _ndx)
	{
		// TODO Auto-generated method stub
		return 0;
	}


}
