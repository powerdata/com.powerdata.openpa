package com.powerdata.openpa.pwrflow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.tools.PAMath;

public class PFMismatchDbg
{
	static class MMInfo
	{
		float[] vm, va, pmm, qmm;
		BusType[] type;
		MMInfo(float[] vm, float[] va, float[] pmm, float[] qmm, BusType[] type)
		{
			this.vm = vm;
			this.va = va;
			this.pmm = pmm;
			this.qmm = qmm;
			this.type = type;
		}
	}
	ArrayList<MMInfo> mmlist = new ArrayList<>();
	private File _dir;
	FDPFCore _pf;
	
	public PFMismatchDbg(File dir)
	{
		_dir = dir;
	}
	
	class PF extends FDPFCore
	{
		public PF(PAModel m) throws PAModelException
		{
			super(m);
		}

		@Override
		protected void mismatchHook(float[] vm, float[] va, BusType[] type, float[] pmm, float[] qmm)
		{
			mmlist.add(new MMInfo(vm.clone(), va.clone(), pmm.clone(), qmm.clone(), type));
		}
	}
	
	public FDPFCore getPF(PAModel m) throws PAModelException
	{
		FDPFCore rv = new PF(m);
		_pf = rv;
		return rv;
	}

	public void write() throws IOException, PAModelException
	{
		if (!_dir.exists()) _dir.mkdirs();
		PrintWriter mmdbg = new PrintWriter(new BufferedWriter(
			new FileWriter(new File(_dir, "mismatch.csv"))));
		mmdbg.print("Bus,Island");
		for(int i=0; i < mmlist.size(); ++i)
			mmdbg.format(",'T %d','VA %d','VM %d','P %d','Q %d'", i, i, i, i, i);
		mmdbg.println();
		
		for(int b=0; b < _pf._buses.size(); ++b)
		{
			mmdbg.format("'%s',%d", _pf.getBuses().getName(b), _pf.getBuses().getIsland(b).getIndex());
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
	
}
