package com.powerdata.openpa;

import com.powerdata.openpa.Gen.Mode;
import com.powerdata.openpa.Gen.Type;

public interface GenList extends OneTermDevList<Gen>
{

	static final GenList Empty = new GenListI();

	Type getType(int ndx);
	
	void setType(int ndx, Type t);

	Type[] getType();
	
	void setType(Type[] t);
	
	Mode getMode(int ndx);

	void setMode(int ndx, Mode m);
	
	Mode[] getMode();
	
	void setMode(Mode[] m);

}
