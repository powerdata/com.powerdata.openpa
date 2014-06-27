package com.powerdata.openpa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class AreaList extends EquipLists<Area>
{
	public static final AreaList Empty = new AreaList();

	public AreaList() {super();}

	public AreaList(PALists model, int[] busref, int narea)
	{
		super(model, null);
		setupMap(busref, narea);
	}

	public AreaList(PALists model, int[] keys, int[] busref)
	{
		super(model, keys, null);
		setupMap(busref, keys.length);
	}

	void setupMap(int[] busref, int ngrp)
	{
		_bgmap = new BasicBusGrpMap(getIndexes(busref), ngrp);
	}

	@Override
	public Area get(int index)
	{
		return new Area(this, index);
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
				new File(outdir, "areagrps.csv"))));

		PAModel m = PflowModelBuilder.Create(uri).load();
		for(Area a : m.getAreas())
			PrintArea(pw, a);
		pw.close();
	}

	static void PrintArea(PrintWriter pw, Area a)
	{
		pw.format("Area %s\n", a.getName());
		pw.println("\tBuses:");
		for(Bus b : a.getBuses())
			pw.format("\t\t%s\n", b);
		pw.println("\tLines:");
		for(Line l : a.getLines())
			pw.format("\t\t%s\n", l);
	}

	@Override
	protected Area[] newarray(int size)
	{
		return new Area[size];
	}

}
