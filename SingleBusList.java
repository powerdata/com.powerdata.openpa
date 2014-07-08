package com.powerdata.openpa;

import java.util.regex.Pattern;

import com.powerdata.openpa.Switch.State;

/**
 * Provide a single-bus view (closed switches removed)
 * 
 * @author chris@powerdata.com
 *
 */
public class SingleBusList extends GroupListI<Bus> implements BusList
{
	BusList _buses;
	
	public SingleBusList(PAModel model)
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
	public String getName(int ndx)
	{
		if(_name[RW] == null) createNames();
		return super.getName(ndx);
	}
	
	

	@Override
	public String getID(int ndx)
	{
		if (_id[RW] == null) createIDs();
		return super.getID(ndx);
	}

	protected void createNames()
	{
		String[] nm = new String[_size];
		for(int i=0; i < _size; ++i)
		{
			String s = getStation(i).getName();
			int score = -1;
			Bus sel = null;
			for(Bus b : getBuses(i))
			{
				int ts = scoreBus(b);
				if (ts > score)
				{
					score = ts;
					sel = b;
				}
			}
			StringBuilder ns = new StringBuilder();
			String bn = sel.getName();
			if (!bn.contains(s))
			{
				ns.append(s);
				ns.append(' ');
			}
			ns.append(bn);
			nm[i] = ns.toString();
		}
		setName(nm);
	}
	
	protected void createIDs()
	{
		String[] id = new String[_size];
		for(int i=0; i < _size; ++i)
			id[i] = String.valueOf(getKey(i));
		setID(id);
	}

	static final Pattern[] _Scores = new Pattern[] 
	{
		null,
		Pattern.compile("^[a-zA-Z ]+$"),
		Pattern.compile("^[^0-9]+.*$"),
	};
	
	protected int scoreBus(Bus b)
	{
		int score = 0;
		String bn = b.getName();
		
		for(int i=1; i < _Scores.length; ++i)
		{
			if(_Scores[i].matcher(bn).matches())
				score = i;
		}
		return score;
	}

	@Override
	public String[] getName()
	{
		if(_name[RW] == null) createNames();
		return super.getName();
	}

	@Override
	public float getVM(int ndx)
	{
		return getBuses(ndx).getVM(0);
	}

	@Override
	public void setVM(int ndx, float vm)
	{
		for (Bus b : getBuses(ndx))
			b.setVM(vm);
	}

	@Override
	public float[] getVM()
	{
		float[] rv = new float[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = getVM(i);
		return rv;
	}

	@Override
	public void setVM(float[] vm)
	{
		for(int i=0; i < _size; ++i)
			setVM(i, vm[i]);
	}

	@Override
	public float getVA(int ndx)
	{
		return getBuses(ndx).getVA(0);
	}

	@Override
	public void setVA(int ndx, float va)
	{
		for(Bus b : getBuses(ndx))
			b.setVA(va);
	}

	@Override
	public float[] getVA()
	{
		float[] rv = new float[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = getVA(i);
		return rv;
	}

	@Override
	public void setVA(float[] va)
	{
		for(int i=0; i < _size; ++i)
			setVA(i, va[i]);
	}

	@Override
	public int getFrequencySourcePriority(int ndx)
	{
		return getBuses(ndx).getFrequencySourcePriority(0);
	}

	@Override
	public void setFrequencySourcePriority(int ndx, int fsp)
	{
		for(Bus b: getBuses(ndx))
			b.setFrequencySourcePriority(fsp);
	}

	@Override
	public int[] getFrequencySourcePriority()
	{
		int[] rv = new int[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = getFrequencySourcePriority(i);
		return rv;
	}

	@Override
	public void setFrequencySourcePriority(int[] fsp)
	{
		for(int i=0; i < _size; ++i)
			setFrequencySourcePriority(i, fsp[i]);
	}

	@Override
	public Island getIsland(int ndx)
	{
		return getBuses(ndx).getIsland(0);
	}

	@Override
	public Area getArea(int ndx)
	{
		return getBuses(ndx).getArea(0);
	}

	@Override
	public void setArea(int ndx, Area a)
	{
		for(Bus b : getBuses(ndx))
			b.setArea(a);
	}

	@Override
	public Area[] getArea()
	{
		Area[] rv = new Area[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = getArea(i);
		return rv;
	}

	@Override
	public void setArea(Area[] a)
	{
		for(int i=0; i < _size; ++i)
			setArea(i, a[i]);
	}

	@Override
	public Station getStation(int ndx)
	{
		return getBuses(ndx).getStation(0);
	}

	@Override
	public void setStation(int ndx, Station s)
	{
		for(Bus b : getBuses(ndx))
			b.setStation(s);
	}

	@Override
	public Station[] getStation()
	{
		Station[] rv = new Station[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = getStation(i);
		return rv;
	}

	@Override
	public void setStation(Station[] s)
	{
		for(int i=0; i < _size; ++i)
			setStation(i, s[i]);
	}

	@Override
	public Owner getOwner(int ndx)
	{
		return getBuses(ndx).getOwner(0);
	}

	@Override
	public void setOwner(int ndx, Owner o)
	{
		for(Bus b : getBuses(ndx))
			b.setOwner(o);
	}

	@Override
	public Owner[] getOwner()
	{
		Owner[] rv = new Owner[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = getOwner(i);
		return rv;
	}

	@Override
	public void setOwner(Owner[] o)
	{
		for(int i=0; i < _size; ++i)
			setOwner(i, o[i]);
	}

	@Override
	public VoltageLevel getVoltageLevel(int ndx)
	{
		return getBuses(ndx).getVoltageLevel(0);
	}

	@Override
	public void setVoltageLevel(int ndx, VoltageLevel l)
	{
		for(Bus b : getBuses(ndx))
			b.setVoltageLevel(l);
	}

	@Override
	public VoltageLevel[] getVoltageLevel()
	{
		VoltageLevel[] rv = new VoltageLevel[_size];
		for(int i=0; i < _size; ++i)
			rv[i] = getVoltageLevel(i);
		return rv;
	}

	@Override
	public void setVoltageLevel(VoltageLevel[] l)
	{
		for (int i=0; i < _size; ++i)
			setVoltageLevel(i, l[i]);
	}

	@Override
	public Bus get(int index)
	{
		return new Bus(this, index);
	}
	
	
	
}
