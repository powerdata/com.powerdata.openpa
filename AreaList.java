package com.powerdata.openpa;

import com.powerdata.openpa.impl.AreaListI;


public interface AreaList extends GroupListIfc<Area>
{
 static AreaList Empty = new AreaListI();
}
