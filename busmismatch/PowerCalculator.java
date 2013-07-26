package com.powerdata.openpa.busmismatch;

import java.io.File;

import com.powerdata.openpa.psse.ACBranch;
import com.powerdata.openpa.psse.ACBranchList;
import com.powerdata.openpa.psse.ListDumper;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
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
	
	public static void main(String[] args) throws Exception
	{
		PsseModel model = PsseModel.OpenInput("pssecsv:path=/tmp/frcc");
		File outdir = new File("/tmp/frccout");
		calcACBranchFlows(model.getBranches());
		new ListDumper().dump(model, outdir);
	}
}
