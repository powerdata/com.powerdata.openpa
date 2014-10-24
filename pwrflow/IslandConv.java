package com.powerdata.openpa.pwrflow;

import com.powerdata.openpa.BusList;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.Island;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.Load;

public abstract class IslandConv
{
	int _iter = -1;
	Island _island;
	int _worstp=-1, _worstq=-1, _bxlowv=-1, _bxhiv=-1, _bxfail=-1;
	float _wpmm=0f, _wqmm=0f, _genmw, _ldmw,
		_hv=1f, _lv=1f;
	boolean _pconv=false, _qconv=false, _fail=false, _lvfail=false, _hvfail=false;
	BusList _buses;
	
	protected IslandConv(Island island, BusList buses) throws PAModelException
	{
		_island = island;
		_buses = buses;
	}
	
	public boolean pConv() {return _pconv;}
	public boolean qConv() {return _qconv;}
	public boolean fail() {return _fail;}
	public int getWorstP() {return _worstp;}
	public int getWorstQ() {return _worstq;}
	public float getWorstPmm() {return _wpmm;}
	public float getWorstQmm() {return _wqmm;}
	public float getGenMW() {return _genmw;}
	public float getLoadMW() {return _ldmw;}
	public int lvBus() {return _bxlowv;}
	public int hvBus() {return _bxhiv;}
	public float lowestV() {return _lv;}
	public float highestV() {return _hv;}
	public boolean lvFail() {return _lvfail;}
	public boolean hvFail() {return _hvfail;}
	
	public Island getIsland() {return _island;}

	@Override
	public String toString()
	{
		try
		{
			String freason = "";
			if (_fail) freason = "[FAIL]";
			else if (_lvfail) freason = "[VOLTAGE COLLAPSE]";
			else if (_hvfail) freason = "[HIGH VOLTAGE]";
			String istr = String.format("%sIsland %d %d iters", freason, _island.getIndex(), _iter);
			StringBuilder vstr = new StringBuilder();
			if (!_fail)
			{
				vstr.append(String.format(", low vlt %f @ %s", _lv,
					_buses.getName(_bxlowv)));
				vstr.append(String.format(", high vlt %f @ %s", _hv,
					_buses.getName(_bxhiv)));
			}
			String fstr = "";
			if (_fail) fstr = String.format(", Blew up at %s", _buses.getName(_bxfail));
			
			String conv = "";
			if (!_fail && !_lvfail && !_hvfail)
			{
				conv = String.format(", pconv=%s, qconv=%s, worstp=%f MW @ %s, worstq=%f MVAr @ %s",
					String.valueOf(_pconv), String.valueOf(_qconv),
					_wpmm*100f, _buses.getName(_worstp),
					_wqmm*100f, _buses.getName(_worstq));
			}
			return new StringBuilder(freason).append(istr)
					.append(conv).append(vstr).append(fstr).toString();
			
		}
		catch(PAModelException e)
		{
			return "err: "+e;
		}
	}

	/** complete reporting 
	 * @throws PAModelException */
	public void complete() throws PAModelException
	{
		_genmw = 0f;
		_ldmw = 0f;
		if (!_fail && _pconv && _qconv)
		{
			for (Gen g : _island.getGenerators()	)
				if (!g.isOutOfSvc()) _genmw += g.getP();
			for (Load l : _island.getLoads())
				if (!l.isOutOfSvc()) _ldmw += l.getP();
		}
	}

	
	abstract public void test(float[] pmm, float[] qmm);

}
