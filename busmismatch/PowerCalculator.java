package com.powerdata.openpa.busmismatch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import com.powerdata.openpa.busmismatch.MismatchReport.MismatchReporter;
import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.ACBranchList;
import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.BusList;
import com.powerdata.openpa.psse.Gen;
import com.powerdata.openpa.psse.GenList;
import com.powerdata.openpa.psse.ListDumper;
import com.powerdata.openpa.psse.Load;
import com.powerdata.openpa.psse.LoadList;
import com.powerdata.openpa.psse.OneTermDev;
import com.powerdata.openpa.psse.OneTermDevList;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.SVC;
import com.powerdata.openpa.psse.Shunt;
import com.powerdata.openpa.psse.ShuntList;
import com.powerdata.openpa.psse.SvcList;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.ComplexList;
import com.powerdata.openpa.tools.PComplex;

public class PowerCalculator
{
	public static void calcACBranchFlows(ACBranchList branches)
			throws PsseModelException
	{
		for (ACBranch br : branches)
			calcACBranchFlow(br);
	}

	public static void calcACBranchFlow(ACBranch br) throws PsseModelException
	{
		if (br.isInSvc())
		{
			Complex y = br.getY();
			PComplex fv = br.getFromBus().getVoltage();
			PComplex tv = br.getToBus().getVoltage();

			float shift = fv.theta() - tv.theta() - br.getPhaseShift();

			float tvmpq = fv.r() * tv.r() / (br.getFromTap() * br.getToTap());
			float tvmp2 = fv.r() * fv.r() / (br.getFromTap() * br.getFromTap());
			float tvmq2 = tv.r() * tv.r() / (br.getToTap() * br.getToTap());

			float ctvmpq = tvmpq * (float) Math.cos(shift);
			float stvmpq = tvmpq * (float) Math.sin(shift);

			float gcos = ctvmpq * y.re();
			float bcos = ctvmpq * y.im();
			float gsin = stvmpq * y.re();
			float bsin = stvmpq * y.im();

			br.setRTFromS(new Complex(-gcos - bsin + tvmp2 * y.re(), -gsin
					+ bcos - tvmp2 * (y.im() + br.getFromYcm().im())).mult(-1f));
			br.setRTToS(new Complex(-gcos + bsin + tvmq2 * y.re(), gsin + bcos
					- tvmq2 * (y.im() + br.getToYcm().im())).mult(-1f));
		}
		else
		{
			br.setRTFromS(Complex.Zero);
			br.setRTToS(Complex.Zero);
		}
	}
	
	public static void calculateMismatches(PsseModel model) throws PsseModelException
	{
		BusList blist = model.getBuses();
		ComplexList mm = new ComplexList(blist.size(), true);
		ACBranchList brlist = model.getBranches();
		LoadList ldlist = model.getLoads();
		GenList genlist = model.getGenerators();
		ShuntList shuntlist = model.getShunts();
		SvcList svclist = model.getSvcs();
		
		int nbus = blist.size();
		int nacbranch = brlist.size();
		int nload = ldlist.size();
		int ngen = genlist.size();
		int nshunt = shuntlist.size();
		int nsvc = svclist.size();
		
		for (int i=0; i < nacbranch; ++i)
		{
			ACBranch br = brlist.get(i);
			if (br.isInSvc())
			{
				int fb = br.getFromBus().getIndex(), tb = br.getToBus()
						.getIndex();
				mm.assignadd(fb, br.getRTFromS());
				mm.assignadd(tb, br.getRTToS());
			}
		}
		
		for(int i=0; i < nload; ++i)
		{
			Load l = ldlist.get(i);
			Bus b = l.getBus();
			if (l.isInSvc())
				mm.assignadd(l.getBus().getIndex(), l.getRTS());
		}
		for(int i=0; i < ngen; ++i)
		{
			Gen g = genlist.get(i);
			if (g.isInSvc())
				mm.assignadd(g.getBus().getIndex(), g.getRTS());
		}
		for(int i=0; i < nshunt; ++i)
		{
			Shunt s = shuntlist.get(i);
			if (s.isSwitchedOn())
				mm.assignadd(s.getBus().getIndex(), s.getRTS());
		}
		
		for(int i=0; i < nsvc; ++i)
		{
			SVC s = svclist.get(i);
			mm.assignadd(s.getBus().getIndex(), s.getRTS());
		}
		
		for(int i=0; i < nbus; ++i)
		{
			blist.get(i).setRTMismatch(mm.get(i));
		}
		
	}

	public static void calcShunts(ShuntList shunts) throws PsseModelException
	{
		for(Shunt s : shunts) calcShunt(s);
	}
	
	public static void calcShunt(Shunt shunt) throws PsseModelException
	{
		float vm = shunt.getBus().getVoltage().r();
		shunt.setRTS(new Complex(0f, shunt.getCaseY().im()*vm*vm));
	}

	public static void calcSVC(SVC svc) throws PsseModelException
	{
		float vm = svc.getBus().getVoltage().r();
		svc.setRTS(new Complex(0f, svc.getRTY().im()*vm*vm));
	}

	public static void calcSVCs(SvcList svcs) throws PsseModelException
	{
		for(SVC s : svcs) calcSVC(s);
	}
	
	public long test(PsseModel model) throws PsseModelException
	{
		ACBranchList bl = model.getBranches();
		long ts = System.currentTimeMillis();
		calcACBranchFlows(bl);
		long tdiff = System.currentTimeMillis()-ts;
//		System.out.format("Calculated %d AC branches in %d ms.\n", bl.size(), tdiff);
		return tdiff;
	}
	
	public static void main2(String[] args) throws Exception
	{
		PsseModel model = PsseModel.OpenInput("pssecsv:path=/tmp/caiso");
		File outdir = new File("/tmp/caisoout");
//		PsseModel model = PsseModel.OpenInput("pssecsv:path=/tmp/frcc");
//		File outdir = new File("/tmp/frccout");
		PowerCalculator pc = new PowerCalculator();
		long tsum = 0;
		int count = 1000;
		for (int i=0; i < 100; ++i) pc.test(model);
		for(int i=100; i < count; ++i) tsum += pc.test(model);
		System.out.format("Count: %d Average: %f ms\n", model.getBranches().size(), tsum / 900.0);
		new ListDumper().dump(model, outdir);
	}


	public static void main(String[] args) throws Exception
	{
		PsseModel model = PsseModel.OpenInput("pssecsv:path=/tmp/frcc");
		File outdir = new File("/tmp/frccout");
		calcACBranchFlows(model.getBranches());
		calcShunts(model.getShunts());
		calcSVCs(model.getSvcs());
		calculateMismatches(model);
		new ListDumper().dump(model, outdir);
		PrintWriter mmout = new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, "mismatch.csv"))));
		new MismatchReport(model).report(new CsvMismatchReporter(mmout, model));
		mmout.close();
	}
	
}

class CsvMismatchReporter implements MismatchReporter
{
	PrintWriter _out;
	BusList _buses;
	OneTermDevList _otdevs;
	ACBranchList _acbr;
	
	public CsvMismatchReporter(PrintWriter out, PsseModel model) throws PsseModelException
	{
		_buses = model.getBuses();
		_otdevs = model.getOneTermDevs();
		_acbr = model.getBranches();
		_out = out;
		out.println("BusID,BusName,Pmm,Qmm,DevID,DevName,Pdev,Qdev");
	}
	@Override
	public void report(int bus, int[] acbranches, int[] onetermdevs) throws PsseModelException
	{
		Bus b = _buses.get(bus);
		Complex mm = b.getRTMismatch();
		
		String btmp = String.format("\"%s\",\"%s\",%f,%f,",
				b.getObjectID(), b.getObjectName(), mm.re(), mm.im());
				
		for(int acbranch : acbranches)
		{
			ACBranch acb = _acbr.get(acbranch);
			int fbus = acb.getFromBus().getIndex();
			Complex s = (fbus == bus) ? acb.getRTFromS() : acb.getRTToS();
			_out.print(btmp);
			_out.format("\"%s\",\"%s\",%f,%f\n",
					acb.getObjectID(), acb.getObjectName(), s.re(), s.im());
		}
		
		for(int otdx : onetermdevs)
		{
			OneTermDev otd = _otdevs.get(otdx);
			Complex s = otd.getRTS();
			_out.print(btmp);
			_out.format("\"%s\",\"%s\",%f,%f\n",
					otd.getObjectID(), otd.getObjectName(), s.re(), s.im());
		}
	}
	
}