package com.powerdata.openpa;

import com.powerdata.openpa.impl.TransformerListI;

public interface TransformerList extends TransformerBaseList<Transformer>
{

	static final TransformerList Empty = new TransformerListI();
	
}
