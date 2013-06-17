package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BooleanAttrib;
import com.powerdata.openpa.tools.FloatAttrib;
import com.powerdata.openpa.tools.IntAttrib;
import com.powerdata.openpa.tools.StringAttrib;

public class IslandInList extends PsseBaseInputList<IslandIn>
{
	public IslandInList(PsseInputModel model)
	{
		super(model);
		
		
	}

	@Override
	public String getObjectID(int ndx) {return String.valueOf(ndx);}

	@Override
	public StringAttrib<IslandIn> mapStringAttrib(String attribname) {return null;}
	@Override
	public FloatAttrib<IslandIn> mapFloatAttrib(String attribname) {return null;}
	@Override
	public IntAttrib<IslandIn> mapIntAttrib(String attribname) {return null;}
	@Override
	public BooleanAttrib<IslandIn> mapBooleanAttrib(String attribname) {return null;}
	@Override
	public IslandIn get(int arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
