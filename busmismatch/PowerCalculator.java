package com.powerdata.openpa.busmismatch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import com.powerdata.openpa.tools.PAMath;
import com.powerdata.openpa.tools.PComplex;

public class PowerCalculator
{
	public static void calcACBranchFlows(ACBranchList branches)
			throws PsseModelException
	{
		for (ACBranch br : branches)
			calcACBranchFlow(br);
	}
	public static void calcACBranchFlow2(ACBranch br) throws PsseModelException
	{
		if (br.isInSvc())
		{
			if (br.getObjectID().equals("PS-18002-18001-1"))
			{
				int xxx = 5;
			}
			Bus fbus = br.getFromBus(), tbus = br.getToBus();
			PComplex fv = fbus.getVoltage(), tv = tbus.getVoltage();
			float fvm2 = fv.r() * fv.r(), tvm2 = tv.r() * tv.r(), ftvm = fv.r() * tv.r();
			Complex y = br.getY();
			float falph = fv.theta() - tv.theta() - br.getPhaseShift();
			float talph = tv.theta() - fv.theta() + br.getPhaseShift();
			float fcalph = (float) Math.cos(falph);
			float tcalph = (float) Math.cos(talph);
			float fsalph = (float) Math.sin(falph);
			float tsalph = (float) Math.sin(talph);
			float g = y.re(), b = y.im();
			
			float fgcs = g * fcalph;
			float fbcs = b * fcalph;
			float fgsn = g * fsalph;
			float fbsn = b * fsalph;
			float tgcs = g * tcalph;
			float tbcs = b * tcalph;
			float tgsn = g * tsalph;
			float tbsn = b * tsalph;
			
			float faij = fgsn - fbcs;
			float fbij = fgcs + fbsn;
			float taij = tgsn - tbcs;
			float tbij = tgcs + tbsn;
			
			float fs = br.getFromYcm().im();
			float ts = br.getToYcm().im();
			
			float pp = (g * fvm2) - (fbij * ftvm);
			float qp = (-(fs+b) * fvm2) - (faij * ftvm);
			
			float pq = (g * tvm2) - (tbij * ftvm);
			float qq = (-(ts+b) * tvm2) - (taij * ftvm);
			
			br.setRTFromS(new Complex(pp, qp));
			br.setRTToS(new Complex(pq, qq));
			
		}
	}
	public static void calcACBranchFlow(ACBranch br) throws PsseModelException
	{
//		float xc = 0.00005f;
//		float rc = 0.00001f;
		if (br.isInSvc())
		{
//			Complex z = br.getZ();
//			float x = z.im(), r = z.re();
//			if (Math.abs(x) < xc) x = Math.signum(x) * xc;
//			if (r < rc) r = rc;
//			Complex y = new Complex(r, x).inv();
			
//			Bus fbus = br.getFromBus();
//			Bus tbus = br.getToBus();
//			String objid = br.getObjectName();
//			
//			
			PComplex fv = br.getFromBus().getVoltage();
			PComplex tv = br.getToBus().getVoltage();

			float shift = fv.theta() - tv.theta() - br.getPhaseShift();

			float tvmpq = fv.r() * tv.r() / (br.getFromTap() * br.getToTap());
			float tvmp2 = fv.r() * fv.r() / (br.getFromTap() * br.getFromTap());
			float tvmq2 = tv.r() * tv.r() / (br.getToTap() * br.getToTap());

			float ctvmpq = tvmpq * (float) Math.cos(shift);
			float stvmpq = tvmpq * (float) Math.sin(shift);

			Complex y = br.getY();
			float gcos = ctvmpq * y.re();
			float bcos = ctvmpq * y.im();
			float gsin = stvmpq * y.re();
			float bsin = stvmpq * y.im();

			
			Complex froms = new Complex(-gcos - bsin + tvmp2 * y.re(), -gsin
					+ bcos - tvmp2 * (y.im() + br.getFromYcm().im())).mult(-1f); 
			Complex tos = new Complex(-gcos + bsin + tvmq2 * y.re(), gsin + bcos
					- tvmq2 * (y.im() + br.getToYcm().im())).mult(-1f); 
			
			
//			float fvt = fv.theta(), tvt = tv.theta();
//			float phase = br.getPhaseShift();
//			float pqshift = fvt - tvt - phase;
//			float qpshift = tvt - fvt + phase;
//			
//			float vmpq = fv.r() * tv.r();
//			float vmp2 = fv.r() * fv.r();
//			float vmq2 = tv.r() * tv.r();
//			
//			float a = br.getFromTap() / br.getToTap();
//			
//			float pqcos = (vmp2/a - vmpq * (float) Math.cos(pqshift))/a;
//			float qpcos = (vmq2*a - vmpq * (float) Math.cos(qpshift))/a;
//			float tsin = vmpq/a;
//			float pqsin = tsin * (float) Math.sin(pqshift);
//			float qpsin = tsin * (float) Math.sin(qpshift);

//			_ppresult[i] = pqcos * gg - pqsin * bb;
//			_pqresult[i] = qpcos * gg - qpsin * bb;
//			_qpresult[i] = -1F * pqsin * gg - pqcos * bb - _bpch[i] * vmp2;
//			_qqresult[i] = -1F * qpsin * gg - qpcos * bb - _bqch[i] * vmq2;
	
//			Complex y = br.getY();
//			float g = y.re(), b = y.im();
//			float fbcm = br.getFromYcm().im();
//			float tbcm = br.getToYcm().im();
//			
//			Complex froms = new Complex(pqcos * g - pqsin * b, -pqsin * g - pqcos * b - fbcm * vmp2).mult(-1f);
//			Complex tos = new Complex(qpcos * g - qpsin * b, -qpsin * g - qpcos * b - tbcm * vmq2).mult(-1f);
			
			br.setRTFromS(froms);
			br.setRTToS(tos);
		}
		else
		{
			br.setRTFromS(Complex.Zero);
			br.setRTToS(Complex.Zero);
		}
	}
	
	public static void dumpBranchesToCSV(File csv, ACBranchList branches)
			throws IOException, PsseModelException
	{
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(csv)));
		pw.println("I,J,ObjectID,ObjectName,R,X,G,B,Bcm,FromVM,FromVA,ToVM,ToVA,Shift,a,pp,qp,pq,qq");
		for(ACBranch branch: branches)
		{
			if (branch.isInSvc())
			{
				Bus frbus = branch.getFromBus();
				Bus tobus = branch.getToBus();
				Complex z = branch.getZ();
				Complex y = branch.getY();
				Complex ycm = branch.getFromYcm().add(branch.getToYcm());
				PComplex frv = frbus.getVoltage(),
						 tov = tobus.getVoltage();
				Complex frs = branch.getRTFromS(),
						tos = branch.getRTToS();
				
				pw.format("\"%s\",\"%s\",\"%s\",\"%s\",%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f\n",
						frbus.getObjectID(),
						tobus.getObjectID(),
						branch.getObjectID(),
						branch.getObjectName(),
						z.re(), z.im(),
						y.re(),y.im(), ycm.im(),
						frv.r(), PAMath.rad2deg(frv.theta()),
						tov.r(), PAMath.rad2deg(tov.theta()),
						branch.getPhaseShift(),
						branch.getFromTap(),
						frs.re(), frs.im(),
						tos.re(), tos.im());
			}
		}
		
		pw.close();
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
			if (l.isInSvc())
				mm.assignadd(l.getBus().getIndex(), l.getRTS());
		}
		for(int i=0; i < ngen; ++i)
		{
			Gen g = genlist.get(i);
			String oid = g.getBus().getObjectID(); 
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
		shunt.setRTS(new Complex(0f, shunt.getY().im()*vm*vm));
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
		PsseModel model = PsseModel.OpenInput("pssecsv:path=/tmp/caiso");
		File outdir = new File("/tmp/caiso/out");
		calcACBranchFlows(model.getBranches());
		calcShunts(model.getShunts());
		calcSVCs(model.getSvcs());
		calculateMismatches(model);
		new ListDumper().dump(model, outdir);
		PrintWriter mmout = new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, "mismatch.csv"))));
		new MismatchReport(model).report(new CsvMismatchReporter(mmout, model));
		mmout.close();
		dumpBranchesToCSV(new File(outdir, "brdbg.csv"), model.getBranches());
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
		out.println("BusID,BusName,Pmm,Qmm,MaxMM,DevID,DevName,Pdev,Qdev");
	}
	@Override
	public void report(int bus, int[] acbranches, int[] onetermdevs) throws PsseModelException
	{
		Bus b = _buses.get(bus);
		if (!b.isEnergized()) return;
		Complex mm = b.getRTMismatch().mult(100f);
		float mmm = Math.max(Math.abs(mm.re()), Math.abs(mm.im()));
		
		String btmp = String.format("\"%s\",\"%s\",%f,%f,%f,",
				b.getObjectID(), b.getObjectName(), mm.re(), mm.im(), mmm);
				
		for(int acbranch : acbranches)
		{
			ACBranch acb = _acbr.get(acbranch);
			int fbus = acb.getFromBus().getIndex();
			Complex s = ((fbus == bus) ? acb.getRTFromS() : acb.getRTToS()).mult(100f);
			_out.print(btmp);
			_out.format("\"%s\",\"%s\",%f,%f\n",
					acb.getObjectID(), acb.getObjectName(), s.re(), s.im());
		}
		
		for(int otdx : onetermdevs)
		{
			OneTermDev otd = _otdevs.get(otdx);
			Complex s = otd.getRTS().mult(100f);
			_out.print(btmp);
			_out.format("\"%s\",\"%s\",%f,%f\n",
					otd.getObjectID(), otd.getObjectName(), s.re(), s.im());
		}
	}
	
}