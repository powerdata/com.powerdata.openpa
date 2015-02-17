package com.powerdata.openpa.pwrflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import com.powerdata.openpa.Bus;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.pwrflow.FDPowerFlow.PFMismatchReporter;
import com.powerdata.openpa.tools.PAMath;

public class PFMismatchDbg implements PFMismatchReporter
{
	static class MMInfo
	{
		float[] vm, va, pmm, qmm;
		BusType[] type;
		MMInfo(float[] vm, float[] va, float[] pmm, float[] qmm, BusType[] type)
		{
			this.vm = vm.clone();
			this.va = va.clone();
			this.pmm = pmm.clone();
			this.qmm = qmm.clone();
			this.type = type.clone();
		}
	}
	ArrayList<MMInfo> mmlist = new ArrayList<>();
	private File _dir;
	int _iter = 0;
	BusList _buses;
	
	public PFMismatchDbg(File dir)
	{
		_dir = dir;
	}
	
	
	public void write() throws IOException, PAModelException
	{
		if (!_dir.exists()) _dir.mkdirs();
		PrintWriter mmdbg = new PrintWriter(new BufferedWriter(
			new FileWriter(new File(_dir, String.format("mismatch-%d.csv", _iter)))));
		mmdbg.print("Bus,Island");
		for(int i=0; i < mmlist.size(); ++i)
			mmdbg.format(",'T %d','VA %d','VM %d','P %d','Q %d'", i, i, i, i, i);
		mmdbg.println();
		
		for(int b=0; b < _buses.size(); ++b)
		{
			Bus bus = _buses.get(b);
			mmdbg.format("'%s',%d", bus.getName(), bus.getIsland().getIndex());
			for(int i=0; i < mmlist.size(); ++i)
			{
				MMInfo mm = mmlist.get(i);
				mmdbg.format(",%s,%f,%f,%f,%f", mm.type[b].toString(),
					PAMath.rad2deg(mm.va[b]), mm.vm[b],
					PAMath.pu2mva(mm.pmm[b], 100f),
					PAMath.pu2mva(mm.qmm[b], 100f));
			}
			mmdbg.println();
		}
		
		
		mmdbg.close();

	}


	@Override
	public void reportBegin(BusList buses)
	{
		_buses = buses;
		mmlist.clear();
		++_iter;
	}


	@Override
	public void reportMismatch(Mismatch pmm, Mismatch qmm, 
			float[] vm, float[] va, BusType[] type)
	{
		mmlist.add(new MMInfo(vm, va, pmm.get(), qmm.get(), type));
	}

	@Override
	public void reportEnd()
	{
		try
		{
			write();
		}
		catch (IOException | PAModelException e)
		{
			e.printStackTrace();
		}
	}
	
}
