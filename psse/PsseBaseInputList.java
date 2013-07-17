package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;
import com.powerdata.openpa.tools.BaseObject;

public abstract class PsseBaseInputList<T extends BaseObject> extends BaseList<T>
{
	protected PsseModel _model;

	protected PsseBaseInputList() {}
	public PsseBaseInputList(PsseModel model) {_model = model;}
	public PsseModel getPsseModel() {return _model;}
	public void commit() throws PsseModelException {}
}
