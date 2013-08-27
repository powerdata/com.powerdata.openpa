package com.powerdata.openpa.psse.powerflow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

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
import com.powerdata.openpa.psse.powerflow.MismatchReport.MismatchReporter;
import com.powerdata.openpa.psseraw.Psse2CSV;
import com.powerdata.openpa.psseraw.PsseHeader;
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
	public static void calcACBranchFlow(ACBranch br) throws PsseModelException
	{
		if (br.isInSvc())
		{
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
	
	public static void main(String[] args) throws Exception
	{
		File cwd = new File(System.getProperty("user.dir"));
		File csvdir = null;
		File pssefile = null;
		File outdir = cwd;
		int narg = args.length;
		for (int i = 0; i < narg;)
		{
			String a = args[i++];
			if (a.startsWith("-"))
			{
				int idx = (a.charAt(1) == '-') ? 2 : 1;
				switch (a.substring(idx))
				{
					case "psse": pssefile = new File(args[i++]); break;
					case "csvdir": csvdir = new File(args[i++]); break;
					case "outdir": outdir = new File(args[i++]); break;
				}
			}
		}

		File tmpdir = new File(System.getProperty("java.io.tmpdir"));

		if (csvdir == null)
		{
			if (pssefile==null)
			{
				pssefile = cwd.listFiles(new FilenameFilter()
				{
					@Override
					public boolean accept(File arg0, String arg1)
					{
						return arg1.endsWith(".raw");
					}
				})[0];
			}
		
			String pssefn = pssefile.getName();
			File tmpsub = new File(tmpdir, pssefn.substring(0, pssefn.length()-4));
			if (!tmpsub.exists()) tmpsub.mkdirs();
			
			Reader rpsse = new BufferedReader(new FileReader(pssefile));
			Psse2CSV p2c = new Psse2CSV(rpsse, null, tmpsub);
			
			PsseHeader hdr = p2c.getHeader();
			System.out.println("Loading File: "+pssefile);
			System.out.println("Change Code: "+hdr.getChangeCode());
			System.out.println("System Base MVA: "+hdr.getSystemBaseMVA());
			System.out.format("Case Time: %tc\n", hdr.getCaseTime());
			System.out.format("Import Time: %tc\n", hdr.getImportTime());
			System.out.println("Heading 1: "+hdr.getHeading1());
			System.out.println("Heading 2: "+hdr.getHeading2());
			System.out.println("Version: "+hdr.getVersion());
			
			p2c.process();
			rpsse.close();
			p2c.cleanup();
			csvdir = tmpsub;
		}
		
		PsseModel model = PsseModel.OpenInput("pssecsv:path="+csvdir);
		calcACBranchFlows(model.getBranches());
		calcShunts(model.getShunts());
		calcSVCs(model.getSvcs());
		calculateMismatches(model);
		new ListDumper().dump(model, outdir);
		PrintWriter mmout = new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, "mismatch.csv"))));
		new MismatchReport(model).report(new CsvMismatchReporter(mmout, model));
		mmout.close();
//		dumpBranchesToCSV(new File(outdir, "brdbg.csv"), model.getBranches());
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