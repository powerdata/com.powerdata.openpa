package com.powerdata.openpa.tools.psmfmt;

import java.util.function.IntFunction;


public class SwitchTypeOPA extends Export
{
	static class Rec
	{
		String name;
		boolean operable;
		Rec(String name, boolean op)
		{
			this.name = name;
			operable = op;
		}
	}
	
	Rec[] _reclist = new Rec[] 
	{
		new Rec("Type_Breaker", true),
		new Rec("Type_Disconnect", false)
	};
	
	@Override
	protected int getCount()
	{
		return _reclist.length;
	}

	@Override
	protected FmtInfo[] getFmtInfo()
	{
		SwitchType[] cols = SwitchType.values();
		int nc = cols.length;
		FmtInfo[] rv = new FmtInfo[nc];
		IntFunction<String> qnm = i -> String.format("\"%s\"", _reclist[i].name);
		IntFunction<String> op = i -> String.valueOf(_reclist[i].operable);
		rv[SwitchType.ID.ordinal()] = new FmtInfo(SwitchType.ID.toString(), qnm);
		rv[SwitchType.Name.ordinal()] = new FmtInfo(SwitchType.Name.toString(), qnm);
		rv[SwitchType.OpenUnderLoad.ordinal()] = new FmtInfo(SwitchType.OpenUnderLoad.toString(), op);
		rv[SwitchType.CloseUnderLoad.ordinal()] = new FmtInfo(SwitchType.CloseUnderLoad.toString(), op);
		return rv;
	}
	
	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.SwitchType.toString();
	}

}
