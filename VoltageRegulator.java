package com.powerdata.openpa;

public interface VoltageRegulator
{
	Bus getRegBus();
	void setRegBus(Bus b);
	float getVS();
	void setVS(float vs);
}
