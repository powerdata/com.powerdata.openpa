package com.powerdata.openpa;

import com.powerdata.openpa.Switch.State;

/**
 * Provide a single-bus view (closed switches removed) for study purposes
 * 
 * @author chris@powerdata.com
 *
 */
public class SingleBusList extends BusListIfc
{
	BusList _buses;
	
	public SingleBusList(PALists model)
	{
		super(model, new BusGrpMapBldr(model)
		{
			@Override
			protected boolean incSW(Switch d)
			{
				return d.getState() == State.Closed;
			}
		}.addSwitches().getMap());

		_buses = model.getBuses();
	}
	
	@Override
	public int getAREA(int ndx)
	{
		return 0;
	}

	@Override
	public float getBaseKV(int ndx)
	{
		return _buses.getBaseKV(_bgmap.getTokens()[ndx]);
	}

	@Override
	public void setBaseKV(int ndx, float kv)
	{
		for(Bus b : getBuses(ndx))
			b.setBaseKV(kv);
	}

	@Override
	public float[] getBaseKV()
	{
		int[] tok = _bgmap.getTokens();
		int n = tok.length;
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = _buses.getBaseKV(tok[i]);
		return rv;
	}
	
	@Override
	public void setBaseKV(float[] kv)
	{
		
		int n = kv.length;
		for(int i=0; i < n; ++i)
		{
			for(int b : _bgmap.map().get(i))
				_buses.setBaseKV(b, kv[i]);
		}
	}
	
	@Override
	public float getVM(int ndx)
	{
		return _buses.getVM(_bgmap.getTokens()[ndx]);
	}

	@Override
	public void setVM(int ndx, float vm)
	{
		for(Bus b : getBuses(ndx))
			b.setVM(vm);
	}

	@Override
	public float[] getVM()
	{
		int[] tok = _bgmap.getTokens();
		int n = tok.length;
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = _buses.getVM(tok[i]);
		return rv;
	}
	
	@Override
	public void setVM(float[] vm)
	{
		
		int n = vm.length;
		for(int i=0; i < n; ++i)
		{
			for(int b : _bgmap.map().get(i))
				_buses.setVM(b, vm[i]);
		}
	}
	

	@Override
	public float getVA(int ndx)
	{
		return _buses.getVA(_bgmap.getTokens()[ndx]);
	}

	@Override
	public void setVA(int ndx, float va)
	{
		for(Bus b : getBuses(ndx))
			b.setVA(va);
	}

	@Override
	public Bus get(int index)
	{
		return new Bus(this, index);
	}

	@Override
	public float[] getVA()
	{
		int[] tok = _bgmap.getTokens();
		int n = tok.length;
		float[] rv = new float[n];
		for(int i=0; i < n; ++i)
			rv[i] = _buses.getVA(tok[i]);
		return rv;
	}
	
	@Override
	public void setVA(float[] va)
	{
		int n = va.length;
		for(int i=0; i < n; ++i)
		{
			for(int b : _bgmap.map().get(i))
				_buses.setVA(b, va[i]);
		}
	}

	@Override
	public String getID(int ndx)
	{
		return _buses.getID(_bgmap.getTokens()[ndx]);
	}

	@Override
	public void setID(int ndx, String id)
	{
		_buses.setID(_bgmap.getTokens()[ndx], id);
	}

	@Override
	public String[] getID()
	{
		int[] tok = _bgmap.getTokens();
		int n = tok.length;
		String[] rv = new String[n];
		for(int i=0; i < n; ++i)
			rv[i] = _buses.getID(tok[i]);
		return rv;
	}

	@Override
	public void setID(String[] id)
	{
		int n = id.length;
		for(int i=0; i < n; ++i)
		{
			for(int b : _bgmap.map().get(i))
				_buses.setID(b, id[i]);
		}
	}

	@Override
	public String getName(int ndx)
	{
		return _buses.getName(_bgmap.getTokens()[ndx]);
	}

	@Override
	public void setName(int ndx, String name)
	{
		_buses.setName(_bgmap.getTokens()[ndx], name);
	}

	@Override
	public String[] getName()
	{
		int[] tok = _bgmap.getTokens();
		int n = tok.length;
		String[] rv = new String[n];
		for(int i=0; i < n; ++i)
			rv[i] = _buses.getName(tok[i]);
		return rv;
	}

	@Override
	public void setName(String[] name)
	{
		int n = name.length;
		for(int i=0; i < n; ++i)
		{
			for(int b : _bgmap.map().get(i))
				_buses.setName(b, name[i]);
		}
	}
	@Override
	public SwitchList isolate(int ndx)
	{
		return null;
	}
	
}
