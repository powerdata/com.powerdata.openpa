package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.LinkNet;

public interface GroupBuilder
{
	public LinkNet build(PsseModel model) throws PsseModelException;
}
