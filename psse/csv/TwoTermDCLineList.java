package com.powerdata.openpa.psse.csv;

import java.io.File;
import java.io.IOException;

import com.powerdata.openpa.psse.PsseModelException;
import com.powerdata.openpa.psse.util.ListDumper;
import com.powerdata.openpa.tools.LoadArray;
import com.powerdata.openpa.tools.SimpleCSV;

/**
 * TODO:  Implement metered side, and null fields in list
 */


public class TwoTermDCLineList extends com.powerdata.openpa.psse.TwoTermDCLineList
{
	protected PsseRawModel	_eq;
	protected int			_size;
	protected int[]			_dclno, _mdc, _cccitmx, _nbr, _nbi;
	protected float[]		_rdc, _setvl, _vschd, _vcmod, _rcomp, _delti,
			_dcvmin, _cccacc, _alfmn, _alfmx, _gammn, _gammx, _rcr, _xcr, _rci,
			_xci, _xcapr, _xcapi, _ebasr, _ebasi, _trr, _tapr, _tmxr, _tmnr,
			_stpr, _tri, _tapi, _tmxi, _tmni, _stpi;
	protected String[]		_ipr, _ipi, _icr, _ici, _ifr, _ifi, _itr, _iti,
			_idr, _idi;
	
	
	public TwoTermDCLineList(PsseRawModel model) throws PsseModelException
	{
		super(model);
		_eq = model;
		File dbfile = new File(model.getDir(), "TwoTerminalDCLine.csv");
		try
		{
			SimpleCSV lines = new SimpleCSV(dbfile);
			_size = lines.getRowCount();
			_dclno = lines.getInts("I");
			_mdc = LoadArray.Int(lines, "MDC", this, "getDeftMDC");
			_rdc = lines.getFloats("RDC");
			_setvl = lines.getFloats("SETVL");
			_vschd = lines.getFloats("VSCHD");
			_vcmod = LoadArray.Float(lines, "VCMOD", this, "getDeftVCMOD");
			_rcomp = LoadArray.Float(lines, "RCOMP", this, "getDeftRCOMP");
			_delti = LoadArray.Float(lines, "DELTI", this, "getDeftDELTI");
			_dcvmin = LoadArray.Float(lines, "DCVMIN", this, "getDeftDCVMIN");
			_ipr = lines.get("IPR");
			_ipi = lines.get("IPI");
			_nbr = lines.getInts("NBR");
			_nbi = lines.getInts("NBI");
			_alfmn = lines.getFloats("ALFMN");
			_alfmx = lines.getFloats("ALFMX");
			_gammn = lines.getFloats("GAMMN");
			_gammx = lines.getFloats("GAMMX");
			_rcr = LoadArray.Float(lines, "RCR", this, "getDeftRCR");
			_rci = LoadArray.Float(lines, "RCI", this, "getDeftRCI");
			_xcr = lines.getFloats("XCR");
			_xci = lines.getFloats("XCI");
			_ebasr = lines.getFloats("EBASR");
			_ebasi = lines.getFloats("EBASI");
			_icr = LoadArray.String(lines, "ICR", this, "getDeftICR");
			_ici = LoadArray.String(lines, "ICI", this, "getDeftICI");
			_ifr = LoadArray.String(lines, "IFR", this, "getDeftIFR");
			_ifi = LoadArray.String(lines, "IFI", this, "getDeftIFI");
			_itr = LoadArray.String(lines, "ITR", this, "getDeftITR");
			_iti = LoadArray.String(lines, "ITI", this, "getDeftITI");
			_idr = LoadArray.String(lines, "IDR", this, "getDeftIDR");
			_idi = LoadArray.String(lines, "IDI", this, "getDeftIDI");
			_xcapr = LoadArray.Float(lines, "XCAPR", this, "getDeftXCAPR");
			_xcapi = LoadArray.Float(lines, "XCAPI", this, "getDeftXCAPI");
			_trr = LoadArray.Float(lines, "TRR", this, "getDeftTRR");
			_tri = LoadArray.Float(lines, "TRI", this, "getDeftTRI");
			_tapr = LoadArray.Float(lines, "TAPR", this, "getDeftTAPR");
			_tapi = LoadArray.Float(lines, "TAPI", this, "getDeftTAPI");
			_tmxr = LoadArray.Float(lines, "TMXR", this, "getDeftTMXR");
			_tmxi = LoadArray.Float(lines, "TMXI", this, "getDeftTMXI");
			_tmnr = LoadArray.Float(lines, "TMNR", this, "getDeftTMNR");
			_tmni = LoadArray.Float(lines, "TMNI", this, "getDeftTMNI");
			_stpr = LoadArray.Float(lines, "STPR", this, "getDeftSTPR");
			_stpi = LoadArray.Float(lines, "STPI", this, "getDeftSTPI");
		} catch (IOException | ReflectiveOperationException | RuntimeException e)
		{
			throw new PsseModelException(e);
		}
	}
	
	@Override
	public String getObjectID(int ndx) throws PsseModelException
	{
		return String.format("2TDC-%d-%s-%s",
			getDCLineNum(ndx), getI(ndx), getJ(ndx));
	}

	@Override
	public int size() { return _size; }
	@Override
	public int getDCLineNum(int ndx) throws PsseModelException {return _dclno[ndx];}
	@Override
	public String getI(int ndx) throws PsseModelException {return _ipr[ndx];}
	@Override
	public String getJ(int ndx) throws PsseModelException {return _ipi[ndx];}
	@Override
	public int getMDC(int ndx) throws PsseModelException {return _mdc[ndx];}
	@Override
	public float getRDC(int ndx) throws PsseModelException {return _rdc[ndx];}
	@Override
	public float getSETVL(int ndx) throws PsseModelException {return _setvl[ndx];}
	@Override
	public float getVSCHD(int ndx) throws PsseModelException {return _vschd[ndx];}
	@Override
	public float getVCMOD(int ndx) throws PsseModelException {return _vcmod[ndx];}
	@Override
	public float getRCOMP(int ndx) throws PsseModelException {return _rcomp[ndx];}
	@Override
	public float getDELTI(int ndx) throws PsseModelException {return _delti[ndx];}
	@Override
	public float getDCVMIN(int ndx) throws PsseModelException {return _dcvmin[ndx];}
	@Override
	public String getIPR(int ndx) throws PsseModelException {return _ipr[ndx];}
	@Override
	public int getNBR(int ndx) throws PsseModelException {return _nbr[ndx];}
	@Override
	public float getALFMX(int ndx) throws PsseModelException {return _alfmx[ndx];}
	@Override
	public float getALFMN(int ndx) throws PsseModelException {return _alfmn[ndx];}
	@Override
	public float getRCR(int ndx) throws PsseModelException {return _rcr[ndx];}
	@Override
	public float getXCR(int ndx) throws PsseModelException {return _xcr[ndx];}
	@Override
	public float getEBASR(int ndx) throws PsseModelException {return _ebasr[ndx];}
	@Override
	public String getICR(int ndx) throws PsseModelException {return _icr[ndx];}
	@Override
	public String getIFR(int ndx) throws PsseModelException {return _ifr[ndx];}
	@Override
	public String getITR(int ndx) throws PsseModelException {return _itr[ndx];}
	@Override
	public String getIDR(int ndx) throws PsseModelException {return _idr[ndx];}
	@Override
	public float getXCAPR(int ndx) throws PsseModelException {return _xcapr[ndx];}
	@Override
	public String getIPI(int ndx) throws PsseModelException {return _ipi[ndx];}
	@Override
	public int getNBI(int ndx) throws PsseModelException {return _nbi[ndx];}
	@Override
	public float getGAMMX(int ndx) throws PsseModelException {return _gammx[ndx];}
	@Override
	public float getGAMMN(int ndx) throws PsseModelException {return _gammn[ndx];}
	@Override
	public float getRCI(int ndx) throws PsseModelException {return _rci[ndx];}
	@Override
	public float getXCI(int ndx) throws PsseModelException {return _xci[ndx];}
	@Override
	public float getEBASI(int ndx) throws PsseModelException {return _ebasi[ndx];}
	@Override
	public String getICI(int ndx) throws PsseModelException {return _ici[ndx];}
	@Override
	public String getIFI(int ndx) throws PsseModelException {return _ifi[ndx];}
	@Override
	public String getITI(int ndx) throws PsseModelException {return _iti[ndx];}
	@Override
	public String getIDI(int ndx) throws PsseModelException {return _idi[ndx];}
	@Override
	public float getXCAPI(int ndx) throws PsseModelException {return _xcapi[ndx];}

	@Override
	public float getTRR(int ndx) throws PsseModelException {return _trr[ndx];}
	@Override
	public float getTAPR(int ndx) throws PsseModelException {return _tapr[ndx];}
	@Override
	public float getTMXR(int ndx) throws PsseModelException {return _tmxr[ndx];}
	@Override
	public float getTMNR(int ndx) throws PsseModelException {return _tmnr[ndx];}
	@Override
	public float getSTPR(int ndx) throws PsseModelException {return _stpr[ndx];}
	@Override
	public float getTRI(int ndx) throws PsseModelException {return _tri[ndx];}
	@Override
	public float getTAPI(int ndx) throws PsseModelException {return _tapi[ndx];}
	@Override
	public float getTMXI(int ndx) throws PsseModelException {return _tmxi[ndx];}
	@Override
	public float getTMNI(int ndx) throws PsseModelException {return _tmni[ndx];}
	@Override
	public float getSTPI(int ndx) throws PsseModelException {return _stpi[ndx];}

	public int getDeftMDC(int ndx) throws PsseModelException {return super.getMDC(ndx);}
	public float getDeftVCMOD(int ndx) throws PsseModelException {return super.getVCMOD(ndx);}
	public float getDeftRCOMP(int ndx) throws PsseModelException {return super.getRCOMP(ndx);}
	public float getDeftDELTI(int ndx) throws PsseModelException {return super.getDELTI(ndx);}
	public float getDeftDCVMIN(int ndx) throws PsseModelException {return super.getDCVMIN(ndx);}
	public float getDeftRCR(int ndx) throws PsseModelException {return super.getRCR(ndx);}
	public float getDeftRCI(int ndx) throws PsseModelException {return super.getRCI(ndx);}
	public String getDeftICR(int ndx) throws PsseModelException {return super.getICR(ndx);}
	public String getDeftICI(int ndx) throws PsseModelException {return super.getICI(ndx);}
	public String getDeftIFR(int ndx) throws PsseModelException {return super.getIFR(ndx);}
	public String getDeftIFI(int ndx) throws PsseModelException {return super.getIFI(ndx);}
	public String getDeftITR(int ndx) throws PsseModelException {return super.getITR(ndx);}
	public String getDeftITI(int ndx) throws PsseModelException {return super.getITI(ndx);}
	public String getDeftIDR(int ndx) throws PsseModelException {return super.getIDR(ndx);}
	public String getDeftIDI(int ndx) throws PsseModelException {return super.getIDI(ndx);}
	public float getDeftXCAPR(int ndx) throws PsseModelException {return super.getXCAPR(ndx);}
	public float getDeftXCAPI(int ndx) throws PsseModelException {return super.getXCAPI(ndx);}
	public float getDeftTRR(int ndx) throws PsseModelException {return super.getTRR(ndx);}
	public float getDeftTRI(int ndx) throws PsseModelException {return super.getTRI(ndx);}
	public float getDeftTAPR(int ndx) throws PsseModelException {return super.getTAPR(ndx);}
	public float getDeftTAPI(int ndx) throws PsseModelException {return super.getTAPI(ndx);}
	public float getDeftTMXR(int ndx) throws PsseModelException {return super.getTMXR(ndx);}
	public float getDeftTMXI(int ndx) throws PsseModelException {return super.getTMXI(ndx);}
	public float getDeftTMNR(int ndx) throws PsseModelException {return super.getTMNR(ndx);}
	public float getDeftTMNI(int ndx) throws PsseModelException {return super.getTMNI(ndx);}
	public float getDeftSTPR(int ndx) throws PsseModelException {return super.getSTPR(ndx);}
	public float getDeftSTPI(int ndx) throws PsseModelException {return super.getSTPI(ndx);}
	
	public static void main(String[] args) throws Exception
	{
		String path = "/home/chris/tmp2/csv";
		com.powerdata.openpa.psse.PsseModel m = com.powerdata.openpa.psse.PsseModel
				.Open("pssecsv:path="+path);
		ListDumper ld = new ListDumper();
		ld.dump(m, new File(path));
	}

}
