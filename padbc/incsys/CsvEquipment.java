package com.powerdata.openpa.padbc.incsys;

import java.io.File;
import java.io.IOException;

import com.powerdata.openpa.padbc.ACLineList;
import com.powerdata.openpa.padbc.AreaList;
import com.powerdata.openpa.padbc.BranchList;
import com.powerdata.openpa.padbc.Container;
import com.powerdata.openpa.padbc.GeneratorList;
import com.powerdata.openpa.padbc.LoadList;
import com.powerdata.openpa.padbc.Node;
import com.powerdata.openpa.padbc.NodeList;
import com.powerdata.openpa.padbc.OwnerList;
import com.powerdata.openpa.padbc.PhaseShftWndList;
import com.powerdata.openpa.padbc.SeriesCapacitorList;
import com.powerdata.openpa.padbc.SeriesReactorList;
import com.powerdata.openpa.padbc.StaticVarCompList;
import com.powerdata.openpa.padbc.StationList;
import com.powerdata.openpa.padbc.SwitchedShuntList;
import com.powerdata.openpa.padbc.TopNodeList;
import com.powerdata.openpa.padbc.TransformerWndList;
import com.powerdata.openpa.padbc.VoltageLevelList;

public class CsvEquipment implements Container
{
	File _dir;
	public CsvEquipment(String dirpath)
	{
		_dir = new File(dirpath);
	}
	@Override
	public String getContainerName() { return "CsvEquipment"; }
	@Override
	public ACLineList getACLines() { return null; }
	@Override
	public SeriesCapacitorList getSeriesCapacitors() { return null;	}
	@Override
	public SeriesReactorList getSeriesReactors() { return null;	}
	@Override
	public TransformerWndList getTransformerWindings() { return null; }
	@Override
	public PhaseShftWndList getPhaseShifterWindings() { return null; }
	@Override
	public GeneratorList getGenerators() { return null; }
	@Override
	public LoadList getLoads() { return null; }
	@Override
	public SwitchedShuntList getSwitchedShunts() { return null;	}
	@Override
	public StaticVarCompList getStaticVarCompensators() { return null; }
	@Override
	public CsvNodeList getNodes() throws IOException { return new CsvNodeList(_dir); }
	@Override
	public BranchList getBranches() { return null; }
	@Override
	public AreaList getAreas() { return null; }
	@Override
	public OwnerList getOwners() { return null;	}
	@Override
	public StationList getStations() { return null;	}
	@Override
	public VoltageLevelList getVoltageLevels() { return null; }
	@Override
	public TopNodeList getTopNodes() { return null;	}
	static public void main(String args[])
	{
		try
		{
			CsvEquipment eq = new CsvEquipment("testdata/db");
			CsvNodeList nl = eq.getNodes();
			System.out.println("Node count: "+nl.size());
			for(CsvNode n : nl)
			{
				System.out.println(n);
			}
		}
		catch (IOException e)
		{
			System.out.println("ERROR: "+e);
		}
	}
}
