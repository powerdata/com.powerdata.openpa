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

public class SummaryMismatchReporter implements MismatchReporter
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
			if (type != null) this.type = type.clone();
		}
	}
	ArrayList<MMInfo> mmlist = new ArrayList<>();
	private File _dir;
	int _iter = 0;
	BusList _buses;
	
	public SummaryMismatchReporter(File dir)
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
				String type = (mm.type == null) ? "" : mm.type[b].toString();  
				mmdbg.format(",%s,%f,%f,%f,%f", type,
					mm.va[b], mm.vm[b],
					mm.pmm[b],
					mm.qmm[b]);
			}
			mmdbg.println();
		}
		
		
		mmdbg.close();

	}


	@Override
	public SummaryMismatchReporter reportBegin(BusList buses)
	{
		_buses = buses;
		mmlist.clear();
		++_iter;
		return this;
	}


	@Override
	public void reportMismatch(float[] pmm, float[] qmm, 
			float[] vm, float[] va, BusType[] type)
	{
		mmlist.add(new MMInfo(vm, va, pmm, qmm, type));
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

	@Override
	public boolean reportLast()
	{
		return false;
	}
	
}
