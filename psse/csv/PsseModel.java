package com.powerdata.openpa.psse.csv;

import java.io.File;
import java.lang.reflect.Method;

import com.powerdata.openpa.psse.AreaInterchangeList;
import com.powerdata.openpa.psse.ImpCorrTblList;
import com.powerdata.openpa.psse.LoadList;
import com.powerdata.openpa.psse.OwnerList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.ZoneList;

public class PsseModel extends com.powerdata.openpa.psse.PsseModel
{
	/** root of the directory where the csv files are stored */
	File _dir;
	
	GeneratorList _generatorList;
	BusList _buses;
	NontransformerBranchList _branchList;
	TransformerList _transformerList;
	
	public PsseModel(String dirpath)
	{
		_dir = new File(dirpath);
	}
	public File getDir() { return _dir; }
	@Override
	public String getContainerName() { return "PsseEquipment"; }
	@Override
	public BusList getBuses() throws PsseModelException
	{
		if (_buses == null) _buses = new BusList(this);
		return _buses;
	}
	@Override
	public GeneratorList getGenerators() throws PsseModelException
	{
		if (_generatorList == null) _generatorList = new GeneratorList(this);
		return _generatorList;
	}
	@Override
	public NontransformerBranchList getNontransformerBranches() throws PsseModelException
	{
		if (_branchList == null) _branchList = new NontransformerBranchList(this);
		return _branchList;
	}
	@Override
	public TransformerList getTransformers() throws PsseModelException
	{
		if (_transformerList == null) _transformerList = new TransformerList(this);
		return _transformerList;
	}
	@Override
	public ImpCorrTblList<?> getImpCorrTables() throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public LoadList<?> getLoads() throws PsseModelException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public OwnerList<?> getOwners() throws PsseModelException { return null; }
	@Override
	public AreaInterchangeList<?> getAreas() throws PsseModelException { return null; }
	@Override
	public ZoneList<?> getZones() throws PsseModelException { return null; }
	static public void main(String args[])
	{
		try
		{
			PsseModel eq = new PsseModel("testdata/db");
			for(Bus b : eq.getBuses())
			{
				System.out.println(b);
			}
			//for(Generator g : eq.getGenerators())
			//{
			//	System.out.println(g);
			//}
			//for(NontransformerBranch b : eq.getNontransformerBranches())
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
