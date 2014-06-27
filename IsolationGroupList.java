package com.powerdata.openpa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import com.powerdata.openpa.tools.GroupMap;

/**
 * Group buses and equipment to identify switching devices that can be used
 * to isolate the bus.
 * 
 * @author chris@powerdata.com
 *
 */
public class IsolationGroupList extends EquipLists<IsolationGroup>
{
	protected WeakReference<List<int[]>> 
			_opsw = new WeakReference<>(null),
			_nosw = new WeakReference<>(null);
	
	/**
	 * Create groups based on default mapping that breaks at all operable switches.
	 * @param model
	 */
	public IsolationGroupList(PALists model)
	{
		super(model, new BusGrpMapBldr(model)
		{
			@Override
			protected boolean incSW(Switch d)
			{
				return !d.isOperable();
			}
		}.addAll().getMap());
		createIDs();
	}
	
	/**
	 * Create groups based on provided map
	 * @param model
	 * @param busgrp
	 */
	public IsolationGroupList(PALists model, BusGrpMap busgrp)
	{
		super(model, busgrp);
		createIDs();
	}

	void createIDs()
	{
		int n = size();
		String[] v = new String[n];
		for(int i=0; i < n; ++i)
			v[i] = String.valueOf(i);
		setID(v);
		setName(v);
	}
	
	@Override
	public IsolationGroup get(int index)
	{
		return new IsolationGroup(this, index);
	}
	
	public IsolationGroup getByBus(Bus b)
	{
		return get(_bgmap.getGrp(b.getIndex()));
	}
	
	SwitchList getOpSw(int ndx, boolean op)
	{
		SwitchList list = _model.getSwitches();
		List<int[]> opsw = (op ? _opsw : _nosw).get();
		if (opsw == null)
		{
			opsw = mapOpSw(list, op);
			WeakReference<List<int[]>> nref = new WeakReference<>(opsw);
			if (op) _opsw = nref; else _nosw = nref;
		}
		return new SwitchSubList(_model, list, opsw.get(ndx));
	}

	List<int[]> mapOpSw(SwitchList list, boolean op)
	{
		int lcnt = list.size();
		int[] fmap = new int[lcnt], tmap = new int[lcnt];
		Arrays.fill(fmap, -1);
		Arrays.fill(tmap, -1);
		for (int il = 0; il < lcnt; ++il)
		{
			TwoTermDev d = list.get(il);
			int f = resolveIndex(d.getFromBus());
			int t = resolveIndex(d.getToBus());
			if (op)
			{
				if (f != t)
				{
					fmap[il] = f;
					tmap[il] = t;
				}
			}
			else
			{
				if (f == t)
				{
					fmap[il] = f;
					tmap[il] = t;
				}
			}			
		}
		return new GroupMap(fmap, tmap, _bgmap.size());

	}
	
	public SwitchList getOperableSwitches(int ndx)
	{
		return getOpSw(ndx, true);
	}
	public List<SwitchList> getOperableSwitches()
	{
		return new AbstractList<SwitchList>()
		{
			@Override
			public SwitchList get(int index)
			{
				return getOperableSwitches(index);
			}
			@Override
			public int size() {return _size;}
		};
	}

	public SwitchList getInoperableSwitches(int ndx)
	{
		return getOpSw(ndx, false);
	}
	
	public List<SwitchList> getInoperableSwitches()
	{
		return new AbstractList<SwitchList>()
		{
			@Override
			public SwitchList get(int index)
			{
				return getInoperableSwitches(index);
			}
			@Override
			public int size() {return _size;}
		};
	}
	
	
	public static void main(String[] args) throws Exception
	{
		String uri = null;
		File outdir = new File(System.getProperty("user.dir"));
		for(int i=0; i < args.length;)
		{
			String s = args[i++].toLowerCase();
			int ssx = 1;
			if (s.startsWith("--")) ++ssx;
			switch(s.substring(ssx))
			{
				case "uri":
					uri = args[i++];
					break;
				case "outdir":
					outdir = new File(args[i++]);
					break;
			}
		}
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri "
					+ "[ --outdir output_directory (deft to $CWD ]\n");
			System.exit(1);
		}

		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				new File(outdir, "isolgrp.csv"))));

		PAModel m = PflowModelBuilder.Create(uri).load();
		IsolationGroupList list = new IsolationGroupList(m); 
		BusList buses = m.getBuses();
		Bus b0 = buses.get(buses.size()-1);
		pw.format("-- Get Group By Bus for Bus %s --\n", b0);
		PrintGrp(list.getByBus(b0), pw);
		
		pw.println("-- dump all isolation groups --");
		for(IsolationGroup g : list)
		{
			PrintGrp(g, pw);
		}
		
		pw.close();
	}
	
	static void PrintGrp(IsolationGroup g, PrintWriter pw)
	{
		pw.format("%s: ", g.toString());
		for(Bus b : g.getBuses())
			pw.format("%s, ", b.toString());
		pw.print("\noperable: ");
		for(Switch b : g.getOperableSwitches())
			pw.format("%s, ", b.toString());
		pw.println();
		pw.print("INoperable: ");
		for(Switch b : g.getInoperableSwitches())
			pw.format("%s, ", b.toString());
		pw.println();
	}
}
