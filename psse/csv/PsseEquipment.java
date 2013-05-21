package com.powerdata.openpa.psse.csv;

import java.io.File;
import java.io.IOException;

public class PsseEquipment extends com.powerdata.openpa.psse.PsseModel
{
	/** root of the directory where the csv files are stored */
	File _dir;
	
	GeneratorList _generatorList;
	BusList _buses;
	NontransformerBranchList _branchList;
	TransformerList _transformerList;
	
	public PsseEquipment(String dirpath)
	{
		_dir = new File(dirpath);
	}
	public File getDir() { return _dir; }
	@Override
	public String getContainerName() { return "PsseEquipment"; }
	@Override
	public BusList getBuses() throws IOException
	{
		if (_buses == null) _buses = new BusList(this);
		return _buses;
	}
	@Override
	public GeneratorList getGenerators() throws IOException
	{
		if (_generatorList == null) _generatorList = new GeneratorList(this);
		return _generatorList;
	}
	@Override
	public NontransformerBranchList getNontransformerBranches() throws IOException
	{
		if (_branchList == null) _branchList = new NontransformerBranchList(this);
		return _branchList;
	}
	@Override
	public TransformerList getTransformers() throws IOException
	{
		if (_transformerList == null) _transformerList = new TransformerList(this);
		return _transformerList;
	}
	static public void main(String args[])
	{
		try
		{
			PsseEquipment eq = new PsseEquipment("testdata/db");
			for(Bus b : eq.getBuses())
			{
				System.out.println(b);
			}
			//for(Generator g : eq.getGenerators())
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
