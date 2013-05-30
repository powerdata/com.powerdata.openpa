package com.powerdata.openpa.psse.csv;

import java.io.File;

import com.powerdata.openpa.psse.AreaList;
import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.Generator;
import com.powerdata.openpa.psse.ImpCorrTblList;
import com.powerdata.openpa.psse.LoadList;
import com.powerdata.openpa.psse.Line;
import com.powerdata.openpa.psse.OwnerList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.SwitchedShuntList;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.psse.ZoneList;

public class PsseModel extends com.powerdata.openpa.psse.PsseModel
{
	/** root of the directory where the csv files are stored */
	File _dir;
	
	GeneratorList _generatorList;
	BusList _buses;
	LineList _branchList;
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
	public LineList getNontransformerBranches() throws PsseModelException
	{
		if (_branchList == null) _branchList = new LineList(this);
		return _branchList;
	}
	@Override
	public TransformerList getTransformers() throws PsseModelException
	{
		if (_transformerList == null) _transformerList = new TransformerList(this);
		return _transformerList;
	}
	@Override
	public ImpCorrTblList getImpCorrTables() throws PsseModelException
	{
		return null;
	}
	@Override
	public LoadList getLoads() throws PsseModelException
	{
		return null;
	}
	@Override
	public OwnerList getOwners() throws PsseModelException { return null; }
	@Override
	public AreaList getAreas() throws PsseModelException { return null; }
	@Override
	public ZoneList getZones() throws PsseModelException { return null; }
	@Override
	public float getSBASE() {return getDeftSBASE();}
	@Override
	public SwitchedShuntList getSwitchedShunts() throws PsseModelException
	{
		return null;
	}
	static public void main(String args[])
	{
		try
		{
			PsseModel eq = new PsseModel("/tmp/caiso/");
			for(Bus b : eq.getBuses())
			{
				System.out.println(b);
			}
			for(Generator g : eq.getGenerators())
			{
				System.out.println(g);
			}
			for(Line b : eq.getNontransformerBranches())
			{
				System.out.println(b);
			}
			for(Transformer t : eq.getTransformers())
			{
				System.out.println(t);
			}
		}
		catch (Exception e)
		{
			System.out.println("ERROR: "+e);
		}
	}	
}
