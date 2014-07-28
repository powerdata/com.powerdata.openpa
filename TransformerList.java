package com.powerdata.openpa;

import com.powerdata.openpa.impl.TransformerListI;

public interface TransformerList extends ACBranchListIfc<Transformer>
{

	static final TransformerList Empty = new TransformerListI();
	
}
