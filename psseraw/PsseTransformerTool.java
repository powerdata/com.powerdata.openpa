package com.powerdata.openpa.psseraw;

import java.io.PrintWriter;
import java.util.function.Predicate;
import com.powerdata.openpa.psseraw.PsseRepository.BusInfo;
import com.powerdata.openpa.psseraw.PsseRepository.CaseFormat;
import com.powerdata.openpa.psseraw.PsseRepository.PsmFormat;
import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.DeltaNetwork;
import com.powerdata.openpa.tools.PAMath;
import com.powerdata.openpa.tools.StarNetwork;
//import com.powerdata.openpa.tools.StarNetwork;
import gnu.trove.map.TObjectIntMap;

/**
 * TODO:  Test CW 2 & 3 
 */
public class PsseTransformerTool extends PsseEquipment 
{
	static final String _TxRecFmt = "\"%s\",\"%s\",\"%s\",2\n";

	/** TODO:  this should come from PSS/e */
	float _sbase = 100f;
	
	static float filterZeroFloat(String v, float deft)
	{
		float f = nullEmpty.test(v) ? Float.parseFloat(v) : 0f;
		return (f == 0f) ? deft : f;
	}
	
	static int filterZeroInt(String v, int deft)
	{
		return nullEmptyZero.test(v) ? Integer.parseInt(v) : deft;
	}
	
	
	
	/** make sure that the field is both non-null and non-empty */
	private static Predicate<String> nullEmpty = i -> i != null && !i.isEmpty();
	/** make sure that the field data is non-null, non-empty, and not equal to 0 */
	private static Predicate<String> nullEmptyZero = nullEmpty.and(i -> !i.equals("0"));
	
	@FunctionalInterface
	/** Interface (function) to convert winding ratio and limits */
	interface WindingConvFunction
	{
		/**
		 * apply the conversion
		 * 
		 * @param v
		 *            read-in string from raw PSS/e data
		 * @param base
		 *            relative voltage for default. For example, a winding
		 *            defaults to 1.0 p.u., where the max tap might default to
		 *            1.1f
		 * @return return converted value
		 */
		float apply(String v, float base);
		static WindingConvFunction useDefault = (v,b) -> filterZeroFloat(v,b);
	}
	
	@FunctionalInterface
	/** Interface (function) to determine voltage control band per-unit */
	interface VoltageBandConvFunction
	{
		/**
		 * apply the conversion
		 * 
		 * @param v
		 *            read-in string from raw PSS/e data
		 * @param base
		 *            relative voltage for default. For example, voltage control
		 *            minimum defaults to 0.9, and max to 1.1
		 * @return converted value
		 */
		float apply(String v, float base);
		static VoltageBandConvFunction useDefault = (v,b) -> filterZeroFloat(v,b);
	}
	
	/** Process differenet impedance input units */
	interface ZTool
	{
		Complex conv2W(String[] rec);
		DeltaNetwork conv3W(String[] rec);
		default Complex getZ(String r, String x) 
		{
			return new Complex(getFloat(r, 0f), Float.parseFloat(x));
		}
	}
	
	/** process differenc magnetizing admittance units */
	@FunctionalInterface
	interface MagTool
	{
		Complex conv(String m1, String m2);
		/**
		 * Load the raw values of MAG1 and MAG2. Note that using a Complex
		 * variable here is for convenience, and that it really doesn't apply
		 * for PSS/e input codes
		 */
		static Complex loadYMag(String mag1, String mag2)
		{
			return new Complex(getFloat(mag1, 0f), getFloat(mag2, 0f));
		}
	}
	
	class WndDataMap
	{
		final int i;
		final int r, x, sbase;
		final int windv, nomv, ang;
		final int rata, ratb, ratc;
		final int cod, cont;
		final int rma, rmi;
		final int vma, vmi;
		final int ntp, tab, cr, cx;
		
		/**
		 * Map the field positions within the record arrays
		 * @param fm Map by field name to record index
		 * @param fn from-side node id field name
		 * @param bw string containing between-windings identifier "12", "23", "31"
		 * @param wno integer containing winding number 1-3
		 */
		protected WndDataMap(TObjectIntMap<String> fm, String fn, 
			String bw, int wno)
		{
			i = fm.get(fn);
			r = fm.get("r"+bw);
			x = fm.get("x"+bw);
			sbase = fm.get("sbase"+bw);
			windv = fm.get("windv"+wno);
			nomv = fm.get("nomv"+wno);
			ang = fm.get("ang"+wno);
			rata = fm.get("rata"+wno);
			ratb = fm.get("ratb"+wno);
			ratc = fm.get("ratc"+wno);
			cod = fm.get("cod"+wno);
			cont = fm.get("cont"+wno);
			rma = fm.get("rma"+wno);
			rmi = fm.get("rmi"+wno);
			vma = fm.get("vma"+wno);
			vmi = fm.get("vmi"+wno);
			ntp = fm.get("ntp"+wno);
			tab = fm.get("tab"+wno);
			cr = fm.get("cr"+wno);
			cx = fm.get("cx"+wno);
			
		}
	}

	class Wnd1DataMap extends WndDataMap
	{
		Wnd1DataMap(TObjectIntMap<String> fm)
		{
			super(fm, "i", "1-2", 1);
		}
	}
	
	class Wnd2DataMap extends WndDataMap
	{
		Wnd2DataMap(TObjectIntMap<String> fm)
		{
			super(fm, "j", "2-3", 2);
		}
	}
	
	class Wnd3DataMap extends WndDataMap
	{
		Wnd3DataMap(TObjectIntMap<String> fm)
		{
			super(fm, "k", "3-1", 3);
		}
	}
	
	enum Type {Winding2, Winding3, PhaseShifter};
	private class TxData
	{
		class WndData
		{
			String fbusid, tbusid;
			float busbaskv;
			float wndneukv;
			float wndtap;
			float ang;
			Complex z;
			float sbase;
			@SuppressWarnings("unused")
			float rata, ratb, ratc;
			int ntp;
			float rma, rmi;
			/** has regulation */
			boolean hasreg;
			/** enable regulation */
			boolean enareg = true;
			/** COD (absolute value, the meaning of the - sign is enareg */
			int cod;
			/** controlled bus (CONT) */
			String contbus;
			/** tap adjustment bus (derived from sign of CONT */
			String tapbus;
			WndDataMap _map;
			/** voltage control band max limit */
			float vma;
			/** voltage control band min limit */
			float vmi;
			/** load drop compensation impedance only used for COD = 1 */
			@SuppressWarnings("unused")
			Complex zldc;
			/** winding unique id */
			int wid;
			/** in service */
			boolean insvc;
			
			String getID() {return "";}

			public float getBmag() {return 0f;}
			public float getGmag() {return 0f;}
			public String getName() {return "";}
			void setupLimits(String[] rec) {}
			void updateToNode(String tn) {}
			void updateZ(Complex z) {}
			void setupControl(String[] rec) {}
			

		}
		class WndDataElem extends WndData
		{

			WndDataElem(String[] rec, WndDataMap map, int wno)
			{
				wid = wno;
				_map = map;
				fbusid = PsseBusTool.mkId(rec[map.i]);
//				tbusid = PsseBusTool.mkId(rec[to.i]);
				BusInfo bi = _rep.getBusInfo(fbusid);
				busbaskv = bi.getBaskv();
				wndneukv = filterZeroFloat(rec[map.nomv], busbaskv);
				wndtap = WndConv[cw].apply(rec[map.windv], 1f);
				ang = getFloat(rec[map.ang], 0f);
				sbase = filterZeroFloat(rec[map.sbase], _sbase);
				rata = getFloat(rec[map.rata], 0f);
				ratb = getFloat(rec[map.ratb], 0f);
				ratc = getFloat(rec[map.ratc], 0f);
				ntp = getInt(rec[map.ntp], 33);
				setupCOD(rec[map.cod]);
				setupControlBand(rec);
				zldc = new Complex(getFloat(rec[_map.cr]), getFloat(rec[_map.cx], 0));
				int ii = getInt(rec[_stat], 1);
				insvc = ii != 0 && ii != ((wno==1)?4:wno); 
			}
			
			@Override
			String getID()
			{
				return String.format("%s-wnd",id);
			}
			
			/** setup control parameters, depends on knowing our to-side bus */
			@Override
			void setupControl(String[] rec)
			{
				contbus = rec[_map.cont];
				boolean hasNegSign = contbus.startsWith("-");
				if (hasNegSign) contbus = contbus.substring(1);
				hasreg = (!contbus.isEmpty()
						&& !contbus.equals("0")
						&& cod != 0 && type != Type.PhaseShifter) || cod == 3;
				tapbus = fbusid;
				if (hasreg)
				{
					tapbus = (contbus.equals(fbusid) || contbus.equals(tbusid)) ? fbusid
							: (hasNegSign ? tbusid : fbusid);
				}
				else
				{
					enareg = false;
				}
			}
			
			void setupControlBand(String[] rec)
			{
				VoltageBandConvFunction c = ControlConv[cod];
				/*
				 * for some codes, namely 2, and 3 which specify controlling
				 * flows and not voltages, no default exists and so they are
				 * ignored.
				 */
				vma = c.apply(rec[_map.vma], (type == Type.PhaseShifter) ? 180f : 1.1f);
				vmi = c.apply(rec[_map.vmi], (type == Type.PhaseShifter) ? -180f : 0.9f);
			}
			
			void setupCOD(String scod)
			{
				cod = getInt(scod, 0);
				
				if (cod <= 0)
				{
					enareg = false;
					cod = Math.abs(cod);
				}
			}
			
			@Override
			void setupLimits(String[] rec)
			{
				setupTapLimits(rec);
			}
			
			void setupTapLimits(String[] rec)
			{
				WindingConvFunction w = WndConv[cw];
				String srma = rec[_map.rma], srmi = rec[_map.rmi];
				float max = 1.1f, min = 0.9f;
				if (type == Type.PhaseShifter)
				{
					if (cod == 3)
					{
						rma = filterZeroFloat(srma, 180f);
						rmi = filterZeroFloat(srmi, -180f);
					}
					else
					{
						rma = 180f;
						rmi = -180f;
					}
				}
				else if (cod == 1 || cod == 2)
				{
					rma = w.apply(srma, max);
					rmi = w.apply(srmi, min);
				}
				else
				{
					rma = max;
					rmi = min;
				}
			}
		
			@Override
			void updateZ(Complex z) {this.z = z;}
			@Override
			void updateToNode(String tn) {tbusid = tn;}

			/** Winding Conversions */
			WindingConvFunction[] WndConv = 
			{
				/* ignore CW = 0, doesn't exist */
				null,
				/* CW = 1, off-nominal turns ratio p.u. of bus base */
				WindingConvFunction.useDefault,
				/* CW = 2, turns ratio using actual winding voltage */
				(v,b) ->
				{
					float bkv = busbaskv * b;
					return filterZeroFloat(v, bkv) / busbaskv;
				},
				/* CW = 3, off-nominal turns ratio p.u. of winding base */
				(v,b) -> filterZeroFloat(v,b) * (wndneukv/busbaskv)
			};

			@Override
			public String getName()
			{
				return String.format("%s[%d]", name, wid);
			}

			@Override
			public float getBmag()
			{
				return (wid == 1) ? ymag.im() : 0f;
			}
			
			@Override
			public float getGmag()
			{
				return (wid == 1) ? ymag.re() : 0f;
			}
			
		}
		WndData[] wdata = new WndData[4];
		Type type;
		/** magnetizing admittance */
		Complex ymag;
		/** Winding I/O code */
		int cw;
		/** impedance I/O code */
		int cz;
		/** magnetizing susceptance i/o code */
		int cm;
		/** for 3-winding, star node vmag */
		float vmstar;
		/** for 3-winding, star node vang */
		float anstar;
		/** created ID */
		String id;
		/** created Name */
		String name;
		/** circuit ID */
		String ckt;
		/** save the name and ID */
		String orgid, orgname;
		
		/** Array of impedance tools ... look up directly using CZ */
		ZTool[] ZConv = 
		{
			null,
			new ZTool()
			{
				/* CZ 1 impedance on system base*/
				@Override
				public Complex conv2W(String[] rec)
				{
					WndDataMap m = _wmap[1];
					return PAMath.rebaseZ100(getZ(rec[m.r], rec[m.x]), _sbase);
				}
				@Override
				public DeltaNetwork conv3W(String[] rec) 
				{
					return new DeltaNetwork(
						conv2W(rec), 
						PAMath.rebaseZ100(getZ(rec[_wmap[2].r], rec[_wmap[2].x]), _sbase),
						PAMath.rebaseZ100(getZ(rec[_wmap[3].r], rec[_wmap[3].x]), _sbase));
				}
			},
			new ZTool()
			{
				/* CZ 2 impedance on provided base*/
				@Override
				public Complex conv2W(String[] rec) 
				{
					WndDataMap m1 = _wmap[1];
					return PAMath.rebaseZ100(
						getZ(rec[m1.r], rec[m1.x]),
						filterZeroFloat(rec[m1.sbase], _sbase));
				}

				@Override
				public DeltaNetwork conv3W(String[] rec)
				{
					WndDataMap m2 = _wmap[2], m3 = _wmap[3];
					return new DeltaNetwork(
						conv2W(rec), 
						PAMath.rebaseZ100(
							getZ(rec[m2.r], rec[m2.x]),
							filterZeroFloat(rec[m2.sbase], _sbase)),
						PAMath.rebaseZ100(
							getZ(rec[m3.r], rec[m3.x]),
							filterZeroFloat(rec[m3.sbase], _sbase)));
				}
			},
			new ZTool()
			{
				/* CZ 3 impedance in watts and magnitude */
				/* Note that the complex input isn't technically accurate, but convenient packaging */
				Complex cvt(Complex z, float sbase)
				{
					float r = z.re(), x = z.im();
					/* change W to MW and then per-unit on sbase */
					r /= (1e+6f*sbase);
					/* compute reactance, then change the whole thing to 100MVA */
					return PAMath.rebaseZ100(new Complex(r, 
						(float) Math.sqrt(x * x - r * r)), sbase);
				}
				@Override
				public Complex conv2W(String[] rec)
				{
					WndDataMap m1 = _wmap[1];
					return cvt(getZ(rec[m1.r], rec[m1.x]), 
						filterZeroFloat(rec[m1.sbase], _sbase));
				}

				@Override
				public DeltaNetwork conv3W(String[] rec) 
				{
					WndDataMap m2 = _wmap[2], m3 = _wmap[3];
					return new DeltaNetwork(
						conv2W(rec),
						cvt(getZ(rec[m2.r], rec[m2.x]), 
							filterZeroFloat(rec[m2.sbase], _sbase)),
						cvt(getZ(rec[m3.r], rec[m3.x]), 
							filterZeroFloat(rec[m3.sbase], _sbase)));
				}
			}
		};
		
		/** Array of magnetizing susceptance conversion tools */
		MagTool[] MagConv =
		{
			/* ignore CM = 0 */
			null,
			/* CM = 1, already per-unit on system base */
			(m1,m2) -> PAMath.rebaseZ100(MagTool.loadYMag(m1,m2), _sbase),
			/* CM = 2, in watts & impedance magnitude */
			(m1,m2) -> 
			{
				Complex raw = MagTool.loadYMag(m1, m2);
				WndData w = wdata[1];
				float vratio = w.busbaskv / w.wndneukv;
				float vrsq = vratio * vratio;
				float ghe = raw.re() / 1e+08F * vrsq;
				float ymabs = raw.im() * (w.sbase / 100F) * vrsq;
				return new Complex(ghe, -((float) Math.sqrt(ymabs * ymabs - ghe * ghe)));
			}
		};


		TxData(String[] rec)
		{
			cw = filterZeroInt(rec[_cw], 1);
			cz = filterZeroInt(rec[_cz], 1);
			cm = filterZeroInt(rec[_cm], 1);
			
			String ir = rec[_i];
			String jr = rec[_j];
			String kr = rec[_k];
			
			type = nullEmptyZero.test(kr) ? Type.Winding3 : Type.Winding2;
			
			String i = PsseBusTool.mkId(ir), j = PsseBusTool.mkId(jr), k = null;
			ckt = rec[_ckt];
			
			WndData empty = new WndData();
			wdata[0] = empty;
			wdata[1] = new WndDataElem(rec, _wmap[1], 1);
			wdata[2] = new WndDataElem(rec, _wmap[2], 2);
			wdata[3] = empty;
			ZTool zt = ZConv[cz];
			if(type == Type.Winding3)
			{
				k = PsseBusTool.mkId(kr);
				wdata[3] = new WndDataElem(rec, _wmap[3], 3);
				StarNetwork s = zt.conv3W(rec).star();
				wdata[1].updateZ(s.getZ1());
				wdata[2].updateZ(s.getZ2());
				wdata[3].updateZ(s.getZ3());
				id = String.format("tx3-%s-%s-%s-%s", i, j, k, ckt);
				String ndid = id+"-star";

				for(WndData w : wdata) w.updateToNode(ndid);
			}
			else
			{
				WndData w1 = wdata[1], w2 = wdata[2];
				w1.updateZ(zt.conv2W(rec));
				w1.updateToNode(PsseBusTool.mkId(rec[_j]));
				id = String.format("tx2-%s-%s-%s", i, j, ckt);

				int codeCOD1 = w1.cod;
				if (codeCOD1 == 3 || 
					(codeCOD1 == 0 && w1.wndneukv == w2.wndneukv))
				{
					type = Type.PhaseShifter;
				}
			}

			name = validateName(rec, i, j, k);
			
			/* Setup remaining items that we couldn't do during winding construction */
			for(WndData w : wdata)
			{
				w.setupLimits(rec);
				w.setupControl(rec);
			}
			
			ymag = MagConv[cm].conv(rec[_mag1], rec[_mag2]);
			vmstar = getFloat(rec[_vmstar], 1f);
			anstar = getFloat(rec[_anstar], 0f);
		}
		
		String validateName(String[] rec, String i, String j, String k)
		{
			boolean create = false;
			String name = rec[_name];
			if (nullEmpty.test(name))
				create = true;
			else
			{
				boolean noletnum = true;
				for(char c : name.toCharArray())
				{
					if (c < 0x20 || c > 0x7e)
					{
						create = true;
						break;
					}
					if (noletnum && Character.isLetterOrDigit(c))
						noletnum = false;
				}
				if (noletnum) create = true;
			}
			if (create)
			{
				String in = _rep.getBusName(i), jn = _rep.getBusName(j);

				StringBuilder sb = new StringBuilder(String.format("%s-%s", in, jn));
				if (type == Type.Winding3)
				{
					sb.append('-');
					sb.append(_rep.getBusName(k));
				}
				name = sb.toString();
			}
			return name;

		}

		/** voltage control band conversions, indexed by COD */
		VoltageBandConvFunction[] ControlConv = 
		{
			/*  COD 0 not used, but allow in case data exists */
			VoltageBandConvFunction.useDefault,
			/* COD1, voltage in per-unit of controlled bus */
			VoltageBandConvFunction.useDefault,
			/* COD2, reactive power flow at winding one (no default allowed )*/
			(v,b) -> Float.parseFloat(v),
			/* COD3, active power flow (no default allowed) */
			(v,b) -> Float.parseFloat(v),
			/* COD4 , not used for COD4, but allow in case data exists */
			VoltageBandConvFunction.useDefault
		};


		public void updateSuffix(String suffix)
		{
			if (orgid == null)
			{
				orgid = id;
				orgname = name;
			}
			id = orgid + suffix;
			name = orgname + suffix;
		}
		

	}	

	private final int _i;
	private final int _j;
	private final int _k;
	private final int _ckt;
	private final int _cw;
	private final int _cz;
	private final int _cm;
	private final int _mag1;
	private final int _mag2;
	@SuppressWarnings("unused")
	private final int _nmetr;
	private final int _name;
	private final int _stat;
	@SuppressWarnings("unused")
	private final int _o1;
	@SuppressWarnings("unused")
	private final int _f1;
	@SuppressWarnings("unused")
	private final int _o2;
	@SuppressWarnings("unused")
	private final int _f2;
	@SuppressWarnings("unused")
	private final int _o3;
	@SuppressWarnings("unused")
	private final int _f3;
	@SuppressWarnings("unused")
	private final int _o4;
	@SuppressWarnings("unused")
	private final int _f4;
	private final int _vmstar;
	private final int _anstar;
	
	/** winding attribute maps */
	private final WndDataMap _wmap[] = new WndDataMap[4];
	
	public PsseTransformerTool(PsseClass pc, PsseRepository rep)
	{
		super(rep);
		TObjectIntMap<String> fldMap = PsseEquipment.buildMap(pc.getLines());
		_i = fldMap.get("i");
		_j = fldMap.get("j");
		_k = fldMap.get("k");
		_ckt = fldMap.get("ckt");
		_cw = fldMap.get("cw");
		_cz = fldMap.get("cz");
		_cm = fldMap.get("cm");
		_mag1 = fldMap.get("mag1");
		_mag2 = fldMap.get("mag2");
		_nmetr = fldMap.get("nmetr");
		_name = fldMap.get("name");
		_stat = fldMap.get("stat");
		_o1 = fldMap.get("o1");
		_f1 = fldMap.get("f1");
		_o2 = fldMap.get("o2");
		_f2 = fldMap.get("f2");
		_o3 = fldMap.get("o3");
		_f3 = fldMap.get("f3");
		_o4 = fldMap.get("o4");
		_f4 = fldMap.get("f4");
		_vmstar = fldMap.get("vmstar");
		_anstar = fldMap.get("anstar");
		
		_wmap[1] = new Wnd1DataMap(fldMap);
		_wmap[2] = new Wnd2DataMap(fldMap);
		_wmap[3] = new Wnd3DataMap(fldMap);
	}

	@Override
	public void writeRecord(PsseClass pclass, String[] record)
		throws PsseProcException
	{
		TxData txd = new TxData(record);
		switch(txd.type)
		{
			case PhaseShifter:
				writePhaseShifterRecord(txd);
				break;
			case Winding3:
				writeFakeNode(txd);
				txd.updateSuffix("_2");
				writeTransformerRecord(txd,2);
				txd.updateSuffix("_3");
				writeTransformerRecord(txd,3);
				txd.updateSuffix("_1");
			case Winding2:
				writeTransformerRecord(txd, 1);
				
		}
	}
	
	void writeFakeNode(TxData txd)
	{
		PrintWriter m = _rep.findWriter(PsmFormat.Node);
		PrintWriter c = _rep.findWriter(CaseFormat.Node);
		TxData.WndData w1 = txd.wdata[1];
		BusInfo bi = _rep.getBusInfo(w1.fbusid);
		m.format("\"%s\",\"%s\",1,\"%s\",\"%s\",true\n", 
			w1.tbusid, txd.name, bi.getOwner(), bi.getArea());
		c.format("\"%s\",%f,%f\n", w1.tbusid, txd.anstar, txd.vmstar);
	}

	/** write all the records relevant to a transformer */
	void writeTransformerRecord(TxData txd, int i)
	{
		writeTransformer(txd);
		writeWinding(txd, i);
		writeRatioTap(txd.wdata[i]);
		TxData.WndData w2 = txd.wdata[2];
		if (i ==1 && w2.wndneukv != w2.busbaskv)
		{
			writeRatioTap(w2);
		}
	}

	void writeRatioTap(TxData.WndData w) 
	{
		PrintWriter m = _rep.findWriter(PsmFormat.RatioTapChanger);
		PrintWriter c = _rep.findWriter(CaseFormat.RatioTapChanger);
		String wid = w.getID();
		String tapid = String.format("%s-tap%d", wid, w.wid);

		m.format("\"%s\",\"%s\",\"%s\",\"%s\",%f,%f,%f,%d,\"%s\"",
			tapid,
			w.getName(),
			wid,
			w.tapbus,
			w.wndneukv,
			w.rmi,
			w.rma,
			w.ntp,
			String.valueOf(w.hasreg));
		if (w.hasreg)
		{
			float kv = _rep.getBusInfo(w.contbus).getBaskv();
			m.format(",%f,%f,\"%s\"",
				w.vmi * kv,
				w.vma * kv,
				w.contbus);
		}
		m.println();
		
		c.format("\"%s\",\"%s\",%f\n",
			tapid,
			String.valueOf(w.enareg),
			w.wndtap);
	}

	/** write just the transformer entry */
	void writeTransformer(TxData txd)
	{
		PrintWriter m = _rep.findWriter(PsmFormat.Transformer);
		m.format(_TxRecFmt,
			txd.id, txd.name, txd.ckt);
	}
	
	/** write a winding record */
	void writeWinding(TxData txd, int wnd)
	{
		TxData.WndData w = txd.wdata[wnd];
		String xfid = txd.id;
		PrintWriter m = _rep.findWriter(PsmFormat.TransformerWinding);
		Complex z = w.z;
		m.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%f,%f,%f,%f,%f,%f\n",
			w.getID(), w.getName(), xfid, w.fbusid, w.tbusid, 
			z.re(), z.im(), w.getGmag(), w.getBmag(), 
			(txd.type == Type.PhaseShifter)?0f:w.ang, w.rata);
		_rep.findWriter(CaseFormat.TransformerWinding).format(
			"\"%s\",%s\n", w.getID(),
			String.valueOf(w.insvc));
	}

	void writePhaseShifterRecord(TxData txd)
	{
		writeTransformer(txd);
		writeWinding(txd,1);
		writePhaseTap(txd.wdata[1]);
		TxData.WndData w2 = txd.wdata[2];
		if (w2.wndneukv != w2.busbaskv)
		{
			writePhaseTap(w2);
		}
	}

	private void writePhaseTap(TxData.WndData w)
	{
		PrintWriter m = _rep.findWriter(PsmFormat.PhaseTapChanger);
		PrintWriter c = _rep.findWriter(CaseFormat.PhaseTapChanger);
		String wid = w.getID();
		String tapid = String.format("%s-tap%d", wid, w.wid);
		m.format("\"%s\",\"%s\",\"%s\",\"%s\",%f,%f,%f,%d,\"%s\"",
			tapid,
			w.getName(),
			wid,
			w.tapbus,
			w.wndneukv,
			w.rmi,
			w.rma,
			w.ntp,
			String.valueOf(w.hasreg));
		
		if (w.hasreg)
		{
			m.format(",%f,%f",
				w.vmi, w.vma);
		}
		m.println();
		
		c.format("\"%s\",\"%s\",%f\n", tapid, String.valueOf(w.enareg), w.ang);
	}

	
}
