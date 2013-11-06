package com.powerdata.openpa.psse.util;

import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.tools.LinkNet;

public interface GroupBuilder
{
	public LinkNet build(PsseModel model) throws PsseModelException;
}
