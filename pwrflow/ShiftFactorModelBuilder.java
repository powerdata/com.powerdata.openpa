package com.powerdata.openpa.pwrflow;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import com.powerdata.openpa.Bus;
import com.powerdata.openpa.CloneModelBuilder;
import com.powerdata.openpa.ColumnMeta;
import com.powerdata.openpa.Gen;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.impl.GenListI;
import com.powerdata.openpa.impl.LoadListI;

public class ShiftFactorModelBuilder extends CloneModelBuilder
{
	int _source, _sink;
	
	public ShiftFactorModelBuilder(PAModel srcmdl, Bus source, Bus sink)
	{
		super(srcmdl, _localCols);
		_source = source.getIndex();
		_sink = sink.getIndex();
	}


	@Override
	protected void loadPrep()
	{
		super.loadPrep();
		
		/* replace entries to return our fabricated data */
		DataLoader<String[]> gname = () -> new String[] {"SOURCE"};
		DataLoader<float[]> fzero = () -> new float[] {0f};
		DataLoader<float[]> f200 = () -> new float[] {200f};
		DataLoader<boolean[]> insvc = () -> new boolean[] {true};
		DataLoader<int[]> src = () -> new int[] {_source};
		DataLoader<int[]> sink = () -> new int[] {_sink};
		
		
		_col.put(ColumnMeta.GenID, gname);
		_col.put(ColumnMeta.GenNAME, gname);
		_col.put(ColumnMeta.GenBUS, src);
		_col.put(ColumnMeta.GenP, fzero);
		_col.put(ColumnMeta.GenQ, fzero);
		_col.put(ColumnMeta.GenINSVC, insvc); 
		_col.put(ColumnMeta.GenTYPE, () -> new Gen.Type[] {Gen.Type.Thermal});
		_col.put(ColumnMeta.GenMODE, () -> new Gen.Mode[] {Gen.Mode.MAN});
		_col.put(ColumnMeta.GenOPMINP, fzero);
		_col.put(ColumnMeta.GenOPMAXP, f200);
		_col.put(ColumnMeta.GenMINQ, () -> new float[] {-100f});
		_col.put(ColumnMeta.GenMAXQ, f200);
		_col.put(ColumnMeta.GenPS, () -> new float[] {100f});
		_col.put(ColumnMeta.GenQS, fzero);
		_col.put(ColumnMeta.GenAVR, () -> new boolean[] {true});
		_col.put(ColumnMeta.GenVS, () -> new float[] {1f});
		_col.put(ColumnMeta.GenREGBUS, src);
	
		DataLoader<String[]> lname = () -> new String[] {"SINK"};
		DataLoader<float[]> pld = () -> new float[]{-100f};
		DataLoader<float[]> qld = () -> new float[]{-10f};
		
		_col.put(ColumnMeta.LoadID, lname);
		_col.put(ColumnMeta.LoadNAME, lname);
		_col.put(ColumnMeta.LoadBUS, sink);
		_col.put(ColumnMeta.LoadP, pld);
		_col.put(ColumnMeta.LoadQ, qld);
		_col.put(ColumnMeta.LoadINSVC, insvc);
		_col.put(ColumnMeta.LoadPMAX, pld);
		_col.put(ColumnMeta.LoadQMAX, qld);

	}

	@Override
	protected LoadListI loadLoads() throws PAModelException
	{
		return new LoadListI(_m, 1);
	}

	@Override
	protected GenListI loadGens() throws PAModelException
	{
		return new GenListI(_m, 1);
	}

	static Set<ColumnMeta> _localCols = EnumSet.copyOf(Arrays.asList(new ColumnMeta[]
	{
		ColumnMeta.BusVM,
		ColumnMeta.BusVA,
		
		ColumnMeta.ShcapQ,
		ColumnMeta.ShreacQ,
		
		ColumnMeta.SvcQ,
		
		ColumnMeta.LinePFROM,
		ColumnMeta.LinePTO,
		ColumnMeta.LineQFROM,
		ColumnMeta.LineQTO,
		
		ColumnMeta.SercapPFROM,
		ColumnMeta.SercapPTO,
		ColumnMeta.SercapQFROM,
		ColumnMeta.SercapQTO,
			
		ColumnMeta.SerreacPFROM,
		ColumnMeta.SerreacPTO,
		ColumnMeta.SerreacQFROM,
		ColumnMeta.SerreacQTO,
		
		ColumnMeta.PhashPFROM,
		ColumnMeta.PhashPTO,
		ColumnMeta.PhashQFROM,
		ColumnMeta.PhashQTO,
		
		ColumnMeta.TfmrPFROM,
		ColumnMeta.TfmrPTO,
		ColumnMeta.TfmrQFROM,
		ColumnMeta.TfmrQTO,
	}));


}

