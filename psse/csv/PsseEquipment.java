package com.powerdata.openpa.psse.csv;

import java.io.File;
import java.io.IOException;

import com.powerdata.openpa.psse.GeneratorList;

public class PsseEquipment implements com.powerdata.openpa.psse.Container
{
	File _dir;
	//GeneratorList _generatorList;
	//NodeList _nodeList;
	BusList _busses;
	NontransformerBranchList _branchList;
	//TransformerWndList _transformerWndList;
	
	public PsseEquipment(String dirpath)
	{
		_dir = new File(dirpath);
	}
	public File getDir() { return _dir; }
	@Override
	public String getContainerName() { return "PsseEquipment"; }
	@Override
	public BusList getBusses() throws IOException
	{
		if (_busses == null) _busses = new BusList(this);
		return _busses;
	}
	@Override
	public GeneratorList<?> getGenerators() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NontransformerBranchList getNontransformerBranches() throws IOException
	{
		if (_branchList == null) _branchList = new NontransformerBranchList(this);
		return _branchList;
	}
	/*
	@Override
	public String getContainerName() { return "CsvEquipment"; }
	@Override
	public ACLineList getACLines() { return null; }
	@Override
	public SeriesCapacitorList getSeriesCapacitors() { return null;	}
	@Override
	public SeriesReactorList getSeriesReactors() { return null;	}
	@Override
	public TransformerWndList getTransformerWindings() throws IOException
	{
		if (_transformerWndList == null) _transformerWndList = new TransformerWndList(this);
		return _transformerWndList;
	}
	@Override
	public PhaseShftWndList getPhaseShifterWindings() { return null; }
	@Override
	public GeneratorList getGenerators() throws IOException
	{
		if( _generatorList == null) _generatorList = new GeneratorList(this);
		return _generatorList;
	}
	@Override
	public LoadList getLoads() { return null; }
	@Override
	public SwitchedShuntList getSwitchedShunts() { return null;	}
	@Override
	public StaticVarCompList getStaticVarCompensators() { return null; }
	@Override
	public NodeList getNodes() throws IOException
	{
		if( _nodeList == null) _nodeList = new NodeList(this);
		return _nodeList;
	}
	@Override
	public NontransformerBranchList getBranches() throws IOException
	{
		if ( _branchList == null) _branchList = new NontransformerBranchList(this);
		return _branchList;
	}
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
	*/
	static public void main(String args[])
	{
		try
		{
			PsseEquipment eq = new PsseEquipment("testdata/db");
			//for(CsvNode n : eq.getNodes())
			//{
			//	System.out.println(n);
			//}
			//for(CsvGenerator g : eq.getGenerators())
			//{
			//	System.out.println(g);
			//}
			//for(Branch b : eq.getBranches())
			//{
			//	System.out.println(b);
			//}
			//eq.getTransformerWindings().dumpHeaders();
			//for(TransformerWinding w : eq.getTransformerWindings())
			//{
			//	w.dump();
			//}
		}
		catch (Exception e)
		{
			System.out.println("ERROR: "+e);
		}
	}
}
