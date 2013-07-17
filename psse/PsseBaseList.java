package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.BaseList;
import com.powerdata.openpa.tools.BaseObject;

public abstract class PsseBaseList<T extends BaseObject> extends BaseList<T>
{
	protected PsseModel _model;

	protected PsseBaseList() {}
	public PsseBaseList(PsseModel model) {_model = model;}
	public PsseModel getPsseModel() {return _model;}
	public void commit() throws PsseModelException {}
}
