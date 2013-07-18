package com.powerdata.openpa.psse.csv;

import java.io.File;

import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.Gen;
import com.powerdata.openpa.psse.Line;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.tools.QueryString;

public class PsseModel extends com.powerdata.openpa.psse.PsseModel
{
	/** root of the directory where the csv files are stored */
	File _dir;
	
	GenList _generatorList;
	BusList _buses;
	LineList _branchList;
//	TransformerList _transformerList;
	
	public PsseModel(String parms) throws PsseModelException
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
	public BusList getBuses() throws PsseModelException
	{
		if (_buses == null) _buses = new BusList(this);
		return _buses;
	}
	@Override
	public GenList getGenerators() throws PsseModelException
	{
		if (_generatorList == null) _generatorList = new GenList(this);
		return _generatorList;
	}
	@Override
	public LineList getLines() throws PsseModelException
	{
		if (_branchList == null) _branchList = new LineList(this);
		return _branchList;
	}
//	@Override
//	public TransformerList getTransformers() throws PsseModelException
//	{
//		if (_transformerList == null) _transformerList = new TransformerList(this);
//		return _transformerList;
//	}
	
	static public void main(String args[])
	{
		try
		{
			PsseModel eq = new PsseModel("path=/tmp/frcc/");
			for(Bus b : eq.getBuses())
			{
				System.out.println(b);
			}
			for(Gen g : eq.getGenerators())
			{
				System.out.println(g);
			}
			for(Line b : eq.getLines())
			{
				System.out.println(b);
			}
//			for(Transformer t : eq.getTransformers())
//			{
//				System.out.println(t);
//			}
		}
		catch (Exception e)
		{
			System.out.println("ERROR: "+e);
		}
	}
}
