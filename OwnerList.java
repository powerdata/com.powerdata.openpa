package com.powerdata.openpa;

import com.powerdata.openpa.impl.OwnerListI;


public interface OwnerList extends GroupListIfc<Owner>
{

	static OwnerListI Empty = new OwnerListI();

}
