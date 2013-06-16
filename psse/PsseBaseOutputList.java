package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;
import com.powerdata.openpa.tools.BaseObject;


public abstract class PsseBaseOutputList<T extends BaseObject> extends BaseList<T>
{
	protected PsseOutputModel _model;

	public PsseBaseOutputList(PsseOutputModel model) {_model = model;}
	public PsseOutputModel getPsseModel() {return _model;}

}
