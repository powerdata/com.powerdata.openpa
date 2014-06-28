package com.powerdata.openpa;

public class TransformerList extends TransformerBaseList<Transformer>
{

	public static final TransformerList	Empty	= new TransformerList();

	protected TransformerList() {super();}
	
	protected TransformerList(PALists model, int[] keys, int[] fbkey, int[] tbkey)
	{
		super(model, keys, fbkey, tbkey);
	}
	protected TransformerList(PALists model, int size, int[] fbkey, int[] tbkey)
	{
		super(model, size, fbkey, tbkey);
	}

	@Override
	public Transformer get(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
