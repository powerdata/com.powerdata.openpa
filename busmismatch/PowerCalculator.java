package com.powerdata.openpa.busmismatch;

import java.io.File;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.ACBranchList;
import com.powerdata.openpa.psse.ListDumper;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.SVC;
import com.powerdata.openpa.psse.Shunt;
import com.powerdata.openpa.psse.ShuntList;
import com.powerdata.openpa.psse.SvcList;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PComplex;

public class PowerCalculator
{
	public static void calcACBranchFlows(ACBranchList branches) throws PsseModelException
	{
		for(ACBranch br : branches) calcACBranchFlow(br);
	}
	
	public static void calcACBranchFlow(ACBranch br) throws PsseModelException
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
		
		br.setRTFromS(new Complex(-gcos - bsin + tvmp2 * y.re(),
				-gsin + bcos - tvmp2 * (y.im() + br.getFromYcm().im())));
		br.setRTToS(new Complex(-gcos + bsin + tvmq2 * y.re(),
				gsin + bcos - tvmq2 * (y.im() + br.getToYcm().im())));
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
		new ListDumper().dump(model, outdir);
	}

}
