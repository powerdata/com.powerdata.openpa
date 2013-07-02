package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;
import com.powerdata.openpa.tools.BaseObject;

public abstract class PsseBaseInputList<T extends BaseObject> extends BaseList<T>
{
	protected PsseInputModel _model;

	public PsseBaseInputList(PsseInputModel model) {_model = model;}
	public PsseInputModel getPsseModel() {return _model;}
	public void commit() throws PsseModelException {}
}
