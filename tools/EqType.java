package com.powerdata.openpa.tools;

import java.util.Arrays;
import com.powerdata.openpa.BaseList;
import com.powerdata.openpa.ListMetaType;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.BaseObject;

public class EqType
{
	@FunctionalInterface
	private interface BaseObjSupplier
	{
		BaseObject getObj(BaseList<? extends BaseObject> list, int ndx)
				throws PAModelException;
	}
	
	static ListMetaType[] _Ltypes = ListMetaType.values();
	static BaseObjSupplier[] _Map;
	static
	{
		BaseObjSupplier s = (l, i) -> l.get(i), n = (l,i) -> null;
		_Map = new BaseObjSupplier[_Ltypes.length];
		Arrays.fill(_Map, s);
		_Map[ListMetaType.AnonymousGroup.ordinal()] = n;
		_Map[ListMetaType.Unknown.ordinal()] = n;
	}

	public static BaseObject getObject(PAModel mdl, ListMetaType type, int ndx)
			throws PAModelException
	{ 	
		return _Map[type.ordinal()].getObj(mdl.getList(type), ndx);
	}

	public static BaseObject getObject(PAModel mdl, long id) throws PAModelException
	{
		ListMetaType t = _Ltypes[(int)(id >> 32)];
		int ndx  = (int)(id & 0xFFFFFFFF);
		return getObject(mdl,t,ndx);
	}
	
	public static ListMetaType GetType(BaseObject obj)
	{
		return obj.getList().getListMeta();
	}
	
	public static long GetID(BaseObject dev)
	{
		long type = GetType(dev).ordinal();
		long ndx  = dev.getIndex();
		long id = (type << 32) | ndx;
		return id;		
	}
}
