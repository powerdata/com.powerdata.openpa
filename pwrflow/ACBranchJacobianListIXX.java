package com.powerdata.openpa.pwrflow;

import java.io.File;
import java.util.Arrays;
import com.powerdata.openpa.ACBranch;
import com.powerdata.openpa.ACBranchList;
import com.powerdata.openpa.ACBranchListIfc;
import com.powerdata.openpa.BusList;
import com.powerdata.openpa.BusRefIndex;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.PflowModelBuilder;
import com.powerdata.openpa.pwrflow.ACBranchJacobianListXX.ACBranchJacobianXX;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PAMath;

public class ACBranchJacobianListIXX extends ACBranchExtListI<ACBranchJacobianXX> implements ACBranchJacobianListXX
{
	float[] _dfpdfm, _dfpdtm, _dfpdfa, _dfpdta; 
	float[] _dfqdfm, _dfqdtm, _dfqdfa, _dfqdta; 
	float[] _dtpdfm, _dtpdtm, _dtpdfa, _dtpdta; 
	float[] _dtqdfm, _dtqdtm, _dtqdfa, _dtqdta;
	float[][] _results;

	BusRefIndex.TwoTerm _bus;
	
	public ACBranchJacobianListIXX(ACBranchListIfc<? extends ACBranch> branches, BusRefIndex bri)
			throws PAModelException
	{
		super(branches, bri);
		prep();
	}

	public ACBranchJacobianListIXX(ACBranchExtList<? extends ACBranchExt> copy) throws PAModelException
	{
		super(copy);
		prep();
	}

	
	private void prep() throws PAModelException
	{
		int n = _list.size();
		_dfpdfm = new float[n];
		_dfpdtm = new float[n];
		_dfpdfa = new float[n];
		_dfpdta = new float[n];
		_dfqdfm = new float[n];
		_dfqdtm = new float[n];
		_dfqdfa = new float[n];
		_dfqdta = new float[n];
		_dtpdfm = new float[n];
		_dtpdtm = new float[n];
		_dtpdfa = new float[n];
		_dtpdta = new float[n];
		_dtqdfm = new float[n];
		_dtqdtm = new float[n];
		_dtqdfa = new float[n];
		_dtqdta = new float[n];
		_bus = _bri.get2TBus(_list);
		_results = new float[][] { _dfpdfm, _dfpdtm, _dfpdfa, _dfpdta, _dfqdfm,
				_dfqdtm, _dfqdfa, _dfqdta, _dtpdfm, _dtpdtm, _dtpdfa, _dtpdta,
				_dtqdfm, _dtqdtm, _dtqdfa, _dtqdta };
	}

	@Override
	public ACBranchJacobianXX get(int index)
	{
		return new ACBranchJacobianXX(this, index);
	}
	
	

	@Override
	public void calc(float[] vm, float[] va) throws PAModelException
	{
		for(float[] a : _results) Arrays.fill(a, 0f);
		int[] fb = _bus.getFromBus(), tb = _bus.getToBus();
		int n = size();
		float[] lshift = _list.getShift();
		float[] ftap = _list.getFromTap(), ttap = _list.getToTap();
		float[] bmag = _list.getBmag();
		float[] fbch = _list.getFromBchg(), tbch = _list.getToBchg();
		for(int i=0; i < n; ++i)
		{
			Complex y = _y.get(i);
			float b = y.im(), g = y.re();
			int f = fb[i], t = tb[i];
			float fvm = vm[f], tvm = vm[t], fva = va[f], tva = va[t];
			float shift = fva - tva - lshift[i];
			float ft = ftap[i], tt = ttap[i], ft2 = ft*ft, tt2 = tt*tt;
			float tprod = 1f/(ft*tt);
			float w = (fvm*tvm)/(ft*tt);
			float dwdvf = tvm * tprod;
			float dwdvt = fvm * tprod;
			float wg = w * g, wb = w * b;
			float cos = (float) Math.cos(shift);
			float sin = (float) Math.sin(shift);
			float gcos = cos * g, gsin = sin * g;
			float bcos = b * cos, bsin = b * sin;
			float bbmag = b + bmag[i];
			float wbcos = wb * cos, wgsin = wg * sin;
			float wgcos = wg * cos, wbsin = wb * sin;
			
			/* From-side active power */
			float tdfpdv = gcos + bsin;
			_dfpdfm[i] = dwdvf * tdfpdv - 2f * g * fvm / ft2;
			_dfpdtm[i] = dwdvt * tdfpdv;
			_dfpdfa[i] = wbcos - wgsin;
			_dfpdta[i] = -_dfpdfa[i]; 
			
			/* from-side reactive power */
			float tdfqv = gsin - bcos;
			_dfqdfm[i] = dwdvf * tdfqv + 2f * fvm * (bbmag + fbch[i]) / ft2;
			_dfqdtm[i] = dwdvt * tdfqv;
			_dfqdfa[i] = wgcos + wbsin;
			_dfqdta[i] = -_dfqdfa[i];
			
			/* to-side active power */
			float tdtpdv = gcos - bsin;
			_dtpdfm[i] = dwdvf * tdtpdv;
			_dtpdtm[i] = dwdvt * tdtpdv - 2f * g * tvm / tt2;
			_dtpdfa[i] = -wgsin - wbcos;
			_dtpdta[i] = -_dtpdfa[i];
			
			/* to-side reactive power */
			float tdtqdv = gsin + bcos;
			_dtqdfm[i] = -dwdvf * tdtqdv;
			_dtqdtm[i] = -dwdvt * tdtqdv + 2f * tvm * (bbmag + tbch[i]) / tt2;
			_dtqdfa[i] = -wgcos + wbsin;
			_dtqdta[i] = -_dtqdfa[i];
			
		}
	}

	@Override
	public float getdFPdFVm(int ndx)
	{
		return _dfpdfm[ndx];
	}

	@Override
	public float[] getdFPdFVm()
	{
		return _dfpdfm;
	}

	@Override
	public float getdFPdTVm(int ndx)
	{
		return _dfpdtm[ndx];
	}

	@Override
	public float[] getdFPdTVm()
	{
		return _dfpdtm;
	}

	@Override
	public float getdFPdFVa(int ndx)
	{
		return _dfpdfa[ndx];
	}

	@Override
	public float[] getdFPdFVa()
	{
		return _dfpdfa;
	}

	@Override
	public float getdFPdTVa(int ndx)
	{
		return _dfpdta[ndx];
	}

	@Override
	public float[] getdFPdTVa()
	{
		return _dfpdta;
	}

	@Override
	public float getdFQdFVm(int ndx)
	{
		return _dfqdfm[ndx];
	}

	@Override
	public float[] getdFQdFVm()
	{
		return _dfqdfm;
	}

	@Override
	public float getdFQdTVm(int ndx)
	{
		return _dfqdtm[ndx];
	}

	@Override
	public float[] getdFQdTVm()
	{
		return _dfqdtm;
	}

	@Override
	public float getdFQdFVa(int ndx)
	{
		return _dfqdfa[ndx];
	}

	@Override
	public float[] getdFQdFVa()
	{
		return _dfqdfa;
	}

	@Override
	public float getdFQdTVa(int ndx)
	{
		return _dfqdta[ndx];
	}

	@Override
	public float[] getdFQdTVa()
	{
		return _dfqdta;
	}

	@Override
	public float getdTPdFVm(int ndx)
	{
		return _dtpdfm[ndx];
	}

	@Override
	public float[] getdTPdFVm()
	{
		return _dtpdfm;
	}

	@Override
	public float getdTPdTVm(int ndx)
	{
		return _dtpdtm[ndx];
	}

	@Override
	public float[] getdTPdTVm()
	{
		return _dtpdtm;
	}

	@Override
	public float getdTPdFVa(int ndx)
	{
		return _dtpdfa[ndx];
	}

	@Override
	public float[] getdTPdFVa()
	{
		return _dtpdfa;
	}

	@Override
	public float getdTPdTVa(int ndx)
	{
		return _dtpdta[ndx];
	}

	@Override
	public float[] getdTPdTVa()
	{
		return _dtpdta;
	}

	@Override
	public float getdTQdFVm(int ndx)
	{
		return _dtqdfm[ndx];
	}

	@Override
	public float[] getdTQdFVm()
	{
		return _dtqdfm;
	}

	@Override
	public float getdTQdTVm(int ndx)
	{
		return _dtqdtm[ndx];
	}

	@Override
	public float[] getdTQdTVm()
	{
		return _dtqdtm;
	}

	@Override
	public float getdTQdFVa(int ndx)
	{
		return _dtqdfa[ndx];
	}

	@Override
	public float[] getdTQdFVa()
	{
		return _dtqdfa;
	}

	@Override
	public float getdTQdTVa(int ndx)
	{
		return _dtqdta[ndx];
	}

	@Override
	public float[] getdTQdTVa()
	{
		return _dtqdta;
	}
	
	public static void main(String... args) throws Exception
	{
		String uri = null;
		File poutdir = new File(System.getProperty("user.dir"));
		for (int i = 0; i < args.length;)
		{
			String s = args[i++].toLowerCase();
			int ssx = 1;
			if (s.startsWith("--")) ++ssx;
			switch (s.substring(ssx))
			{
				case "uri":
					uri = args[i++];
					break;
				case "outdir":
					poutdir = new File(args[i++]);
					break;
			}
		}
		if (uri == null)
		{
			System.err.format("Usage: -uri model_uri "
					+ "[ --outdir output_directory (deft to $CWD ]\n");
			System.exit(1);
		}
		final File outdir = poutdir;
		if (!outdir.exists()) outdir.mkdirs();
		PflowModelBuilder bldr = PflowModelBuilder.Create(uri);
		bldr.enableFlatVoltage(false);
		bldr.setLeastX(0.0001f);
		bldr.setUnitRegOverride(false);
		// bldr.enableRCorrection(true);
		PAModel m = bldr.load();
		BusRefIndex bri = BusRefIndex.CreateFromSingleBuses(m);
		BusList sbus = bri.getBuses();
		float[] vmpu = PAMath.vmpu(sbus);
		float[] varad = PAMath.deg2rad(sbus.getVA());
		ListDumper ld = new ListDumper();
		for(ACBranchList b : m.getACBranches())
		{
			ACBranchJacobianListXX l = new ACBranchJacobianListIXX(b, bri);
			l.calc(vmpu, varad);
			File nfile = new File(outdir, b.getListMeta().toString()+".csv");
			ld.dumpList(nfile, l);
		}
	}
}
