package com.powerdata.openpa.psse.csv;

import java.io.File;

import com.powerdata.openpa.psse.AreaInList;
import com.powerdata.openpa.psse.BusIn;
import com.powerdata.openpa.psse.GenIn;
import com.powerdata.openpa.psse.ImpCorrTblInList;
import com.powerdata.openpa.psse.IslandInList;
import com.powerdata.openpa.psse.LoadInList;
import com.powerdata.openpa.psse.LineIn;
import com.powerdata.openpa.psse.OwnerInList;
import com.powerdata.openpa.psse.PhaseShifterInList;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.SwitchPsseList;
import com.powerdata.openpa.psse.SwitchedShuntInList;
import com.powerdata.openpa.psse.TransformerIn;
import com.powerdata.openpa.psse.ZoneInList;
import com.powerdata.openpa.tools.QueryString;

public class PsseInputModel extends com.powerdata.openpa.psse.PsseInputModel
{
	/** root of the directory where the csv files are stored */
	File _dir;
	
	GenInList _generatorList;
	BusInList _buses;
	LineInList _branchList;
	TransformerInList _transformerList;
	
	public PsseInputModel(String parms) throws PsseModelException
	{
		QueryString q = new QueryString(parms);
		if (!q.containsKey("path"))
		{
			throw new PsseModelException("com.powerdata.openpa.psse.csv.PsseInputModel Missing path= in uri.");
		}
		_dir = new File(q.get("path")[0]);
	}
	public File getDir() { return _dir; }
	@Override
	public BusInList getBuses() throws PsseModelException
	{
		if (_buses == null) _buses = new BusInList(this);
		return _buses;
	}
	@Override
	public GenInList getGenerators() throws PsseModelException
	{
		if (_generatorList == null) _generatorList = new GenInList(this);
		return _generatorList;
	}
	@Override
	public LineInList getLines() throws PsseModelException
	{
		if (_branchList == null) _branchList = new LineInList(this);
		return _branchList;
	}
	@Override
	public TransformerInList getTransformers() throws PsseModelException
	{
		if (_transformerList == null) _transformerList = new TransformerInList(this);
		return _transformerList;
	}
	@Override
	public LoadInList getLoads() throws PsseModelException {return null;} //TODO:
	@Override
	public OwnerInList getOwners() throws PsseModelException { return null; } //TODO:
	@Override
	public AreaInList getAreas() throws PsseModelException { return null; } //TODO:
	@Override
	public ZoneInList getZones() throws PsseModelException { return null; } //TODO:
	@Override
	public float getSBASE() {return getDeftSBASE();}
	@Override
	public SwitchedShuntInList getSwitchedShunts() throws PsseModelException {return null;} //TODO:
	@Override
	public ImpCorrTblInList getImpCorrTables() throws PsseModelException {return null;} //TODO:
	@Override
	public SwitchPsseList getSwitches() throws PsseModelException {return null;} //TODO:
	
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
			for(LineIn b : eq.getLines())
			{
				System.out.println(b);
			}
			for(TransformerIn t : eq.getTransformers())
			{
				System.out.println(t);
			}
		}
		catch (Exception e)
		{
			System.out.println("ERROR: "+e);
		}
	}
	@Override
	public PhaseShifterInList getPhaseShifters() throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IslandInList getIslands() throws PsseModelException
	{
		// TODO Auto-generated method stub
		return null;
	}
}
