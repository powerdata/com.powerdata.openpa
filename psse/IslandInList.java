package com.powerdata.openpa.psse;


public abstract class IslandInList extends PsseBaseInputList<IslandIn>
{
	public IslandInList(PsseInputModel model)
	{
		super(model);
	}

	/** Get a Transformer by it's index. */
	@Override
	public IslandIn get(int ndx) { return new IslandIn(ndx,this); }
	/** Get a Transformer by it's ID. */
	@Override
	public IslandIn get(String id) { return super.get(id); }

}
