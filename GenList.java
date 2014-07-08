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

	float getOpMinP(int ndx);
	
	void setOpMinP(int ndx, float mw);
	
	float[] getOpMinP();
	
	void setOpMinP(float[] mw);

	float getOpMaxP(int ndx);

	void setOpMaxP(int ndx, float mw);

	float[] getOpMaxP();
	
	void setOpMaxP(float[] mw);

	float getMinQ(int ndx);

	void setMinQ(int ndx, float mvar);
	
	float[] getMinQ();
	
	void setMinQ(float[] mvar);

	float getMaxQ(int ndx);

	void setMaxQ(int ndx, float mvar);

	float[] getMaxQ();
	
	void setMaxQ(float[] mvar);

	float getPS(int ndx);

	void setPS(int ndx, float mw);
	
	float[] getPS();
	
	void setPS(float[] mw);

	float getQS(int ndx);

	void setQS(int ndx, float mvar);
	
	float[] getQS();
	
	void setQS(float[] mvar);
}
