package com.powerdata.openpa.psse.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.powerdata.openpa.psse.Area;
import com.powerdata.openpa.psse.Bus;
import com.powerdata.openpa.psse.Gen;
import com.powerdata.openpa.psse.Limits;
import com.powerdata.openpa.psse.Line;
import com.powerdata.openpa.psse.LineMeterEnd;
import com.powerdata.openpa.psse.Load;
import com.powerdata.openpa.psse.Owner;
import com.powerdata.openpa.psse.PhaseShifter;
import com.powerdata.openpa.psse.PsseModel;
import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.Shunt;
import com.powerdata.openpa.psse.ShuntList;
import com.powerdata.openpa.psse.SwitchedShunt;
import com.powerdata.openpa.psse.SwitchedShunt.SwitchedShuntBlock;
import com.powerdata.openpa.psse.Transformer;
import com.powerdata.openpa.psse.TwoTermDCLine;
import com.powerdata.openpa.psse.Zone;
import com.powerdata.openpa.psse.conversions.PhaseShiftTapLimits;
import com.powerdata.openpa.psse.conversions.PwrCtrlBand;
import com.powerdata.openpa.psse.conversions.RatioTapLimits;
import com.powerdata.openpa.psse.conversions.VoltageControlBand;

/**
 * Export a version 30 PSS/e file
 * 
 * @author chris@powerdata.com
 *
 */
public class Psse30Export
{
	protected static final SimpleDateFormat TS = 
		new SimpleDateFormat("EEE, MMM dd yyyy  HH:mm");
	protected PsseModel _m;
	protected PrintWriter _o;
	public Psse30Export(PsseModel model, PrintWriter out)
	{
		_m = model;
		_o = out;
	}

	public void expHdr()
	{
		_o.format("0, %6.2f / PSS/E-30.2   %s\n", _m.getSBASE(),
				TS.format(new Date()));
		_o.println();
		_o.println();
	}
	
	public void expBus(Bus b) throws PsseModelException
	{
		_o.format("%d, '%12s', %f, %d, %f, %f, %d, %d, %f, %f, %d\n",
				b.getI(), b.getNAME(), b.getBASKV(), b.getIDE(), b.getGL(), b.getBL(), b.getAREA(),
				b.getZONE(), b.getVMpu(), b.getVA(), b.getOWNER());
	}

	public void expLoad(Load l) throws PsseModelException
	{
		_o.format("%s, '%2s', %d, %d, %d, %f, %f, 0.0, 0.0, 0.0, 0.0, %d\n", 
				l.getI(), l.getID(), l.getSTATUS(), l.getAREA(),
				l.getZONE(), l.getP(), l.getQ(), l.getOWNER());
	}

	public void expGen(Gen g) throws PsseModelException
	{
		//TODO:  handle ownership
		_o.format("%s, '%2s', %f, %f, %f, %f, %f, %s, %f, %f, %f, %f, %f, %f, %d, %f, %f, %f\n",
			g.getI(), g.getID(), g.getP(), g.getQ(), g.getQT(), g.getQB(), 
			g.getVS(), g.getIREG(), g.getMBASE(), g.getZR(), g.getZX(),
			g.getRT(), g.getXT(), g.getGTAP(), g.getSTAT(), g.getRMPCT(), g.getPT(), g.getPB());
	}
	
	public void expLine(Line l) throws PsseModelException
	{
		//TODO:  handle ownership
		_o.format("%s, %c%s, '%2s', %f, %f, %f, %f, %f, %f, %f, %f, %f, %f, %d, %f\n",
			l.getI(), (l.getMeteredEnd() == LineMeterEnd.To?'-':' '), l.getJ(), l.getCKT(), l.getR(), l.getX(), l.getB(), l.getRATEA(),
			l.getRATEB(), l.getRATEC(), l.getGI(), l.getBI(), l.getGJ(), l.getBJ(), l.getST(), l.getLEN());
	}
	
	public void expTransformer(Transformer t) throws PsseModelException
	{
		//TODO: implement ownership
		_o.format("%d, %d, 0, '%2s', 1, 1, 1, %f, %f, %d, '%12s', %d\n",
			t.getFromBus().getI(), t.getToBus().getI(),t.getCKT(),
			t.getGmag(), t.getBmag(), t.getNMETR(), t.getNAME(), t.getSTAT());
		_o.format("%f, %f, 100.0\n", t.getR(), t.getX());
		Limits rm = RatioTapLimits.getLimits(t, 1);
		Limits vm = VoltageControlBand.getLimits(t, 1);
		_o.format("%f, 0.0, %f, %f, %f, %f, %d, %s, %f, %f, %f, %f, %d, %d, %f, %f\n",
			t.getFromTap(), t.getANG1(), t.getRATA1(), t.getRATB1(), t.getRATC1(),
			t.getCOD1(), t.getCONT1(), rm.getMax(), rm.getMin(), vm.getMax(),
			vm.getMin(), t.getNTP1(), t.getTAB1(), t.getCR1(), t.getCX1());
		_o.format("%f, 0.0\n", t.getToTap());
	}
	
	public void expPhaseShifter(PhaseShifter p) throws PsseModelException
	{
		//TODO: implement ownership
		_o.format("%s, %s, 0, '%2s', 1, 1, 1, %f, %f, %d, '%12s', %d\n",
				p.getFromBus().getI(), p.getToBus().getI(),p.getCKT(),
			p.getGmag(), p.getBmag(), p.getNMETR(), p.getNAME(), p.getSTAT());
		_o.format("%f, %f, 100.0\n", p.getR(), p.getX());
		Limits rm = PhaseShiftTapLimits.getLimits(p, 1);

		_o.format("%f, 0.0, %f, %f, %f, %f, %d, '', %f, %f, %f, %f, %d, %d, %f, %f\n",
			p.getFromTap(), p.getANG1(), p.getRATA1(), p.getRATB1(), p.getRATC1(),
			p.getCOD1(), rm.getMax(), rm.getMin(), p.getVMA1(), p.getVMI1(),
			p.getNTP1(), p.getTAB1(), p.getCR1(), p.getCX1());
		_o.format("%f, 0.0\n", p.getToTap());
	}
	
	public void expArea(Area a) throws PsseModelException
	{
		_o.format("%d, %s, %f, %f, '%8s'\n",
			a.getI(), a.getISW(), a.getPDES(), a.getPTOL(), a.getARNAME());
	}
	
	public void expTwoTermDCLine(TwoTermDCLine l) throws PsseModelException
	{
		//TODO:  implement this
	}
	
	public void expSwShunt(SwitchedShunt s) throws PsseModelException
	{
		_o.format("%s %d %f, %f, %f, %f, %s, %f", 
			s.getI(), s.getMODSW(), s.getVSWHI(), s.getVSWLO(),
			s.getSWREM(), s.getRMPCT(), s.getRMIDNT(), s.getBINIT());
		for(SwitchedShuntBlock b : s.getBlocks())
		{
			_o.format(", %d, %f", b.getN(), b.getB());
		}
		_o.println();
	}
	
	public void expZone(Zone z) throws PsseModelException
	{
		_o.format("%d,  %s\n", z.getKey(), z.getObjectName());
	}
	
	public void expOwner(Owner o) throws PsseModelException
	{
		_o.format("%d, %s\n", o.getKey(), o.getObjectName());
	}
	
	public void export() throws PsseModelException
	{
		expHdr();
		for(Bus b : _m.getBuses()) expBus(b); endrec("bus", "load");
		for(Load l : _m.getLoads()) expLoad(l); endrec("load", "gen");
		for(Gen g : _m.getGenerators()) expGen(g); endrec("gen", "line");
		for(Line l : _m.getLines()) expLine(l); endrec("line", "transformer");
		for(Transformer t : _m.getTransformers()) expTransformer(t);
		for(PhaseShifter p : _m.getPhaseShifters()) expPhaseShifter(p); endrec("transformer", "area");
		for(Area a : _m.getAreas()) expArea(a); endrec("area", "2-term dc line");
		for(TwoTermDCLine l : _m.getTwoTermDCLines()) expTwoTermDCLine(l); endrec("2-term dc line", "vsc");
		endrec("vsc", "switched shunt"); // vsc
		for(SwitchedShunt s : _m.getSwitchedShunts()) expSwShunt(s); endrec("switched shunt", "impedance correction");
		endrec("impedance correction", "multi-term dc line"); // impedance correction
		endrec("multi-term dc line", "multi-section line group"); // multi-terminal dcline
		endrec("multi-section line group", "zone"); // multi-section line group
		for(Zone z : _m.getZones()) expZone(z); endrec("zone", "inter-area transfer");
		endrec("inter-area transfer", "owner"); // inter-area transfer
		for(Owner o : _m.getOwners()) expOwner(o); endrec("owner", "facts");
		endrec("facts", "end"); //facts

	}
	
	public void endrec(String oldnm, String newnm) throws PsseModelException
	{
		_o.format("0 / end of %s, begin %s\n", oldnm, newnm);
		_o.flush();
	}
	
	public static void main(String[] args) throws Exception
	{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/tmp/test30/test30.raw")));
		PsseModel m = PsseModel.Open("pssecsv:path=/home/chris/Documents/testmodels/private/serc");
		Psse30Export exp = new Psse30Export(m, out);
		exp.export();
		out.close();
	}
}
