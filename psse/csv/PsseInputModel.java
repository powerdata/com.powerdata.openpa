package com.powerdata.openpa.psse.csv;

import java.io.File;

import com.powerdata.openpa.psse.AreaList;
import com.powerdata.openpa.psse.BusIn;
import com.powerdata.openpa.psse.GenIn;
import com.powerdata.openpa.psse.ImpCorrTblList;
import com.powerdata.openpa.psse.LoadList;
import com.powerdata.openpa.psse.Line;
import com.powerdata.openpa.psse.OwnerList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.SwitchedShuntList;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.psse.ZoneList;

public class PsseInputModel extends com.powerdata.openpa.psse.PsseInputModel
{
	/** root of the directory where the csv files are stored */
	File _dir;
	
	GeneratorList _generatorList;
	BusInList _buses;
	LineList _branchList;
	TransformerList _transformerList;
	
	public PsseInputModel(String dirpath)
	{
		_dir = new File(dirpath);
	}
	public File getDir() { return _dir; }
	@Override
	public BusInList getBuses() throws PsseModelException
	{
		if (_buses == null) _buses = new BusInList(this);
		return _buses;
	}
	@Override
	public GeneratorList getGenerators() throws PsseModelException
	{
		if (_generatorList == null) _generatorList = new GeneratorList(this);
		return _generatorList;
	}
	@Override
	public LineList getLines() throws PsseModelException
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
	public LoadList getLoads() throws PsseModelException {return null;} //TODO:
	@Override
	public OwnerList getOwners() throws PsseModelException { return null; } //TODO:
	@Override
	public AreaList getAreas() throws PsseModelException { return null; } //TODO:
	@Override
	public ZoneList getZones() throws PsseModelException { return null; } //TODO:
	@Override
	public float getSBASE() {return getDeftSBASE();}
	@Override
	public SwitchedShuntList getSwitchedShunts() throws PsseModelException {return null;} //TODO:
	@Override
	public ImpCorrTblList getImpCorrTables() throws PsseModelException {return null;} //TODO:
	
	static public void main(String args[])
	{
		try
		{
			PsseInputModel eq = new PsseInputModel("/tmp/caiso/");
			for(BusIn b : eq.getBuses())
			{
				System.out.println(b);
			}
			for(GenIn g : eq.getGenerators())
			{
				System.out.println(g);
			}
			for(Line b : eq.getLines())
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
