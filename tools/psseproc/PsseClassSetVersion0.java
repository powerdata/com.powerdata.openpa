package com.powerdata.openpa.tools.psseproc;

/**
 * We have some documentation from some point before version 30. It has been
 * used to import Version 29 files, but nothing else earlier was tested. The raw
 * file documentation we have does not specify the version.
 * 
 * @author chris@powerdata.com
 * 
 */

public class PsseClassSetVersion0 extends PsseClassSet
{
	public static final PsseClass Bus = new PsseClass("Bus", new PsseField[]
	{
		new PsseField("I", PsseFieldType.Integer),
		new PsseField("NAME", PsseFieldType.String),
		new PsseField("BASKV", PsseFieldType.Float),
		new PsseField("IDE", PsseFieldType.Integer),
		new PsseField("GL", PsseFieldType.Float),
		new PsseField("BL", PsseFieldType.Float),
		new PsseField("AREA", PsseFieldType.Integer),
		new PsseField("ZONE", PsseFieldType.Integer),
		new PsseField("VM", PsseFieldType.Float),
		new PsseField("VA", PsseFieldType.Float),
		new PsseField("OWNER", PsseFieldType.Integer)
	});
	
	public static final PsseClass Load = new PsseClass("Load", new PsseField[]
	{
		 new PsseField("I", PsseFieldType.String),
		 new PsseField("ID", PsseFieldType.String),
		 new PsseField("STATUS", PsseFieldType.Integer),
		 new PsseField("AREA", PsseFieldType.Integer),
		 new PsseField("ZONE", PsseFieldType.Integer),
		 new PsseField("PL", PsseFieldType.Float),
		 new PsseField("QL", PsseFieldType.Float),
		 new PsseField("IP", PsseFieldType.Float),
		 new PsseField("IQ", PsseFieldType.Float),
		 new PsseField("YP", PsseFieldType.Float),
		 new PsseField("YQ", PsseFieldType.Float),
		 new PsseField("OWNER", PsseFieldType.Integer)
	});
	
	public static final PsseClass Generator = new PsseClass("Generator", new PsseField[]
	{
		 new PsseField("I", PsseFieldType.String),
		 new PsseField("ID", PsseFieldType.String),
		 new PsseField("PG", PsseFieldType.Float),
		 new PsseField("QG", PsseFieldType.Float),
		 new PsseField("QT", PsseFieldType.Float),
		 new PsseField("QB", PsseFieldType.Float),
		 new PsseField("VS", PsseFieldType.Float),
		 new PsseField("IREG", PsseFieldType.String),
		 new PsseField("MBASE", PsseFieldType.Float),
		 new PsseField("ZR", PsseFieldType.Float),
		 new PsseField("ZX", PsseFieldType.Float),
		 new PsseField("RT", PsseFieldType.Float),
		 new PsseField("XT", PsseFieldType.Float),
		 new PsseField("GTAP", PsseFieldType.Float),
		 new PsseField("STAT", PsseFieldType.Integer),
		 new PsseField("RMPCT", PsseFieldType.Float),
		 new PsseField("PT", PsseFieldType.Float),
		 new PsseField("PB", PsseFieldType.Float)
	}, "Ownership", new PsseField[]
	{
		new PsseField("O", PsseFieldType.Integer),
		new PsseField("F", PsseFieldType.Float)
	});
	
	public static final PsseClass NontransformerBranch = 
		new PsseClass("NontransformerBranch", new PsseField[]
	{
		 new PsseField("I", PsseFieldType.String),
		 new PsseField("J", PsseFieldType.String),
		 new PsseField("CKT", PsseFieldType.String),
		 new PsseField("R", PsseFieldType.Float),
		 new PsseField("X", PsseFieldType.Float),
		 new PsseField("B", PsseFieldType.Float),
		 new PsseField("RATEA", PsseFieldType.Float),
		 new PsseField("RATEB", PsseFieldType.Float),
		 new PsseField("RATEC", PsseFieldType.Float),
		 new PsseField("GI", PsseFieldType.Float),
		 new PsseField("BI", PsseFieldType.Float),
		 new PsseField("GJ", PsseFieldType.Float),
		 new PsseField("BJ", PsseFieldType.Float),
		 new PsseField("ST", PsseFieldType.Integer),
		 new PsseField("LEN", PsseFieldType.Float)
	}, "Ownership", new PsseField[]
	{
		new PsseField("O", PsseFieldType.Integer),
		new PsseField("F", PsseFieldType.Float)
	});

	public static final PsseClass Transformer = new PsseClassTransformer(new PsseField[][]
	{		
		{
			 new PsseField("I", PsseFieldType.String),
			 new PsseField("J", PsseFieldType.String),
			 new PsseField("K", PsseFieldType.String),
			 new PsseField("CKT", PsseFieldType.String),
			 new PsseField("CW", PsseFieldType.Integer),
			 new PsseField("CZ", PsseFieldType.Integer),
			 new PsseField("CM", PsseFieldType.Integer),
			 new PsseField("MAG1", PsseFieldType.Float),
			 new PsseField("MAG2", PsseFieldType.Float),
			 new PsseField("NMETR", PsseFieldType.Integer),
			 new PsseField("NAME", PsseFieldType.String),
			 new PsseField("STAT", PsseFieldType.Integer)
		},
		{
			 new PsseField("R1-2", PsseFieldType.Float),
			 new PsseField("X1-2", PsseFieldType.Float),
			 new PsseField("SBASE1-2", PsseFieldType.Float),
			 new PsseField("R2-3", PsseFieldType.Float),
			 new PsseField("X2-3", PsseFieldType.Float),
			 new PsseField("SBASE2-3", PsseFieldType.Float),
			 new PsseField("R3-1", PsseFieldType.Float),
			 new PsseField("X3-1", PsseFieldType.Float),
			 new PsseField("SBASE3-1", PsseFieldType.Float),
			 new PsseField("VMSTAR", PsseFieldType.Float),
			 new PsseField("ANSTAR", PsseFieldType.Float)
		},
		{
			 new PsseField("WINDV1", PsseFieldType.Float),
			 new PsseField("NOMV1", PsseFieldType.Float),
			 new PsseField("ANG1", PsseFieldType.Float),
			 new PsseField("RATA1", PsseFieldType.Float),
			 new PsseField("RATB1", PsseFieldType.Float),
			 new PsseField("RATC1", PsseFieldType.Float),
			 new PsseField("COD", PsseFieldType.Integer),
			 new PsseField("CONT", PsseFieldType.String),
			 new PsseField("RMA", PsseFieldType.Float),
			 new PsseField("RMI", PsseFieldType.Float),
			 new PsseField("VMA", PsseFieldType.Float),
			 new PsseField("VMI", PsseFieldType.Float),
			 new PsseField("NTP", PsseFieldType.Integer),
			 new PsseField("TAB", PsseFieldType.Integer),
			 new PsseField("CR", PsseFieldType.Float),
			 new PsseField("CX", PsseFieldType.Float)
		},
		{
			 new PsseField("WINDV2", PsseFieldType.Float),
			 new PsseField("NOMV2", PsseFieldType.Float),
			 new PsseField("ANG2", PsseFieldType.Float),
			 new PsseField("RATA2", PsseFieldType.Float),
			 new PsseField("RATB2", PsseFieldType.Float),
			 new PsseField("RATC2", PsseFieldType.Float)
		},
		{
			 new PsseField("WINDV3", PsseFieldType.Float),
			 new PsseField("NOMV3", PsseFieldType.Float),
			 new PsseField("ANG3", PsseFieldType.Float),
			 new PsseField("RATA3", PsseFieldType.Float),
			 new PsseField("RATB3", PsseFieldType.Float),
			 new PsseField("RATC3", PsseFieldType.Float)
		}
	}, "Ownership", new PsseField[]
	{
		new PsseField("O", PsseFieldType.Integer),
		new PsseField("F", PsseFieldType.Float)
	});


	public static final PsseClass AreaInterchange =
			new PsseClass("AreaInterchange", new PsseField[]
	{
		 new PsseField("I", PsseFieldType.Integer),
		 new PsseField("ISW", PsseFieldType.String),
		 new PsseField("PDES", PsseFieldType.Float),
		 new PsseField("PTOL", PsseFieldType.Float),
		 new PsseField("ARNAME", PsseFieldType.String)
	});
	
	public static final PsseClass TwoTerminalDCLine = 
		new PsseMultiLineClass("TwoTerminalDCLine", new PsseField[][]
	{
		{
			 new PsseField("I", PsseFieldType.Integer),
			 new PsseField("MDC", PsseFieldType.Integer),
			 new PsseField("RDC", PsseFieldType.Float),
			 new PsseField("SETVL", PsseFieldType.Float),
			 new PsseField("VSCHD", PsseFieldType.Float),
			 new PsseField("VCMOD", PsseFieldType.Float),
			 new PsseField("RCOMP", PsseFieldType.Float),
			 new PsseField("DELTI", PsseFieldType.Float),
			 new PsseField("METER", PsseFieldType.String),
			 new PsseField("DCVMIN", PsseFieldType.Float),
			 new PsseField("CCCITMX", PsseFieldType.Integer),
			 new PsseField("CCCACC", PsseFieldType.Float)
		},
		{
			 new PsseField("IPR", PsseFieldType.String),
			 new PsseField("NBR", PsseFieldType.Integer),
			 new PsseField("ALFMX", PsseFieldType.Float),
			 new PsseField("ALFMN", PsseFieldType.Float),
			 new PsseField("RCR", PsseFieldType.Float),
			 new PsseField("XCR", PsseFieldType.Float),
			 new PsseField("EBASR", PsseFieldType.Float),
			 new PsseField("TRR", PsseFieldType.Float),
			 new PsseField("TAPR", PsseFieldType.Float),
			 new PsseField("TMXR", PsseFieldType.Float),
			 new PsseField("TMNR", PsseFieldType.Float),
			 new PsseField("STPR", PsseFieldType.Float),
			 new PsseField("ICR", PsseFieldType.String),
			 new PsseField("IFR", PsseFieldType.String),
			 new PsseField("ITR", PsseFieldType.String),
			 new PsseField("IDR", PsseFieldType.String),
			 new PsseField("XCAPR", PsseFieldType.Float)
		},
		{
			 new PsseField("IPI", PsseFieldType.String),
			 new PsseField("NBI", PsseFieldType.Integer),
			 new PsseField("GAMMX", PsseFieldType.Float),
			 new PsseField("GAMMN", PsseFieldType.Float),
			 new PsseField("RCI", PsseFieldType.Float),
			 new PsseField("XCI", PsseFieldType.Float),
			 new PsseField("EBASI", PsseFieldType.Float),
			 new PsseField("TRI", PsseFieldType.Float),
			 new PsseField("TAPI", PsseFieldType.Float),
			 new PsseField("TMXI", PsseFieldType.Float),
			 new PsseField("TMNI", PsseFieldType.Float),
			 new PsseField("STPI", PsseFieldType.Float),
			 new PsseField("ICI", PsseFieldType.String),
			 new PsseField("IFI", PsseFieldType.String),
			 new PsseField("ITI", PsseFieldType.String),
			 new PsseField("IDI", PsseFieldType.String),
			 new PsseField("XCAPI", PsseFieldType.Float)
		}
	});
	
	public static final PsseClass VoltageSourceConverterDCLine =
		new PsseMultiLineClass("VoltageSourceConverterDCLine", new PsseField[][]
	{
		{
			 new PsseField("NAME", PsseFieldType.String),
			 new PsseField("MDC", PsseFieldType.Integer),
			 new PsseField("RDC", PsseFieldType.Float)
		},
		{
			 new PsseField("IBUS1", PsseFieldType.String),
			 new PsseField("TYPE1", PsseFieldType.Integer),
			 new PsseField("MODE1", PsseFieldType.Integer),
			 new PsseField("DCSET1", PsseFieldType.Float),
			 new PsseField("ACSET1", PsseFieldType.Float),
			 new PsseField("ALOSS1", PsseFieldType.Float),
			 new PsseField("BLOSS1", PsseFieldType.Float),
			 new PsseField("MINLOSS1", PsseFieldType.Float),
			 new PsseField("SMAX1", PsseFieldType.Float),
			 new PsseField("IMAX1", PsseFieldType.Float),
			 new PsseField("PWF1", PsseFieldType.Float),
			 new PsseField("MAXQ1", PsseFieldType.Float),
			 new PsseField("MINQ1", PsseFieldType.Float),
			 new PsseField("REMOT1", PsseFieldType.String),
			 new PsseField("RMPCT1", PsseFieldType.Float)
		},
		{
			 new PsseField("IBUS2", PsseFieldType.String),
			 new PsseField("TYPE2", PsseFieldType.Integer),
			 new PsseField("MODE2", PsseFieldType.Integer),
			 new PsseField("DCSET2", PsseFieldType.Float),
			 new PsseField("ACSET2", PsseFieldType.Float),
			 new PsseField("ALOSS2", PsseFieldType.Float),
			 new PsseField("BLOSS2", PsseFieldType.Float),
			 new PsseField("MINLOSS2", PsseFieldType.Float),
			 new PsseField("SMAX2", PsseFieldType.Float),
			 new PsseField("IMAX2", PsseFieldType.Float),
			 new PsseField("PWF2", PsseFieldType.Float),
			 new PsseField("MAXQ2", PsseFieldType.Float),
			 new PsseField("MINQ2", PsseFieldType.Float),
			 new PsseField("REMOT2", PsseFieldType.String),
			 new PsseField("RMPCT2", PsseFieldType.Float)
		}
	}, "Ownership", new PsseField[]
	{
		new PsseField("O", PsseFieldType.Integer),
		new PsseField("F", PsseFieldType.Float)
	});


	public static final PsseClass SwitchedShunt =
		new PsseClass("SwitchedShunt", new PsseField[]
	{
		 new PsseField("I", PsseFieldType.String),
		 new PsseField("MODSW", PsseFieldType.Integer),
		 new PsseField("VSWHI", PsseFieldType.Float),
		 new PsseField("VSWLO", PsseFieldType.Float),
		 new PsseField("SWREM", PsseFieldType.String),
		 new PsseField("RMIDNT", PsseFieldType.String),
		 new PsseField("BINIT", PsseFieldType.Float)
	}, "SwitchedShuntSegment",
	new PsseField[]
	{
		new PsseField("N", PsseFieldType.Integer),
		new PsseField("B", PsseFieldType.Float)
	}); 

	public static final PsseClass TransformerImpedanceCorrectionTable =
		new PsseClass("TransformerImpedanceCorrectionTable", new PsseField[]
	{
		 new PsseField("I", PsseFieldType.Integer)
	}, "ImpedanceCorrectionDetail",
	new PsseField[]
	{
		new PsseField("T", PsseFieldType.Float),
		new PsseField("F", PsseFieldType.Float)
	}); 

	public static final PsseClass MultiTermACConv =
			new PsseClass("MultiTermACConv", new PsseField[]
	{
		new PsseField("IB", PsseFieldType.String),
		new PsseField("N", PsseFieldType.Integer),
		new PsseField("ANGMX", PsseFieldType.Float),
		new PsseField("ANGMN", PsseFieldType.Float),
		new PsseField("RC", PsseFieldType.Float),
		new PsseField("XC", PsseFieldType.Float),
		new PsseField("EBAS", PsseFieldType.Float),
		new PsseField("TR", PsseFieldType.Float),
		new PsseField("TAP", PsseFieldType.Float),
		new PsseField("TAPMX", PsseFieldType.Float),
		new PsseField("TAPMN", PsseFieldType.Float),
		new PsseField("TSTP", PsseFieldType.Float),
		new PsseField("SETVL", PsseFieldType.Float),
		new PsseField("DCPF", PsseFieldType.Float),
		new PsseField("MARG", PsseFieldType.Float),
		new PsseField("CNVCOD", PsseFieldType.Integer)
		
	});
	
	public static final PsseClass MultiTermDCBus = 
		new PsseClass("MultiTermDCBus", new PsseField[]
	{
		new PsseField("IDC", PsseFieldType.Integer),
		new PsseField("IB", PsseFieldType.String),
		new PsseField("IA", PsseFieldType.Integer),
		new PsseField("ZONE", PsseFieldType.Integer),
		new PsseField("NAME", PsseFieldType.String),
		new PsseField("IDC2", PsseFieldType.Integer),
		new PsseField("RGRND", PsseFieldType.Float),
		new PsseField("OWNER", PsseFieldType.Integer)
		
	});
	
	public static final PsseClass MultiTermDCLink = new PsseClass("MultiTermDCLink", new PsseField[]
	{
		new PsseField("IDC", PsseFieldType.Integer),
		new PsseField("JDC", PsseFieldType.Integer),
		new PsseField("DCCKT", PsseFieldType.String),
		new PsseField("RDC", PsseFieldType.Float),
		new PsseField("LDC", PsseFieldType.Float)
	});
	
	public static final PsseClass MultiTerminalDCLine =
		new MultiTerminalDCLine(new PsseField[] 
	{
		 new PsseField("I", PsseFieldType.Integer),
		 new PsseField("NCONV", PsseFieldType.Integer),
		 new PsseField("NDCBS", PsseFieldType.Integer),
		 new PsseField("NDCLN", PsseFieldType.Integer),
		 new PsseField("MDC", PsseFieldType.Integer),
		 new PsseField("VCONV", PsseFieldType.String),
		 new PsseField("VCMOD", PsseFieldType.Float),
		 new PsseField("VCONVN", PsseFieldType.String)
	}, MultiTermACConv, MultiTermDCBus, MultiTermDCLink);
	
	public static final PsseClass MultiSectionLine = 
		new PsseClass("MultiSectionLine", new PsseField[]
	{
		new PsseField("I", PsseFieldType.String),
		new PsseField("J", PsseFieldType.String),
		new PsseField("ID", PsseFieldType.String)
	}, "MSLineInternalBus",
	new PsseField[]
	{
		new PsseField("DUM", PsseFieldType.String)
	});	
	
	public static final PsseClass Zone = 
			new PsseClass("Zone", new PsseField[]
	{
		new PsseField("I", PsseFieldType.Integer),
		new PsseField("ZONAME", PsseFieldType.String)
	});

	public static final PsseClass InterAreaTransfer = 
			new PsseClass("InterAreaTransfer", new PsseField[]
	{
		new PsseField("ARFROM", PsseFieldType.Integer),
		new PsseField("ARTO", PsseFieldType.Integer),
		new PsseField("TRID", PsseFieldType.String),
		new PsseField("PTRAN", PsseFieldType.Float)
	});
	
	public static final PsseClass Owner = 
			new PsseClass("Owner", new PsseField[]
	{
		new PsseField("I", PsseFieldType.Integer),
		new PsseField("OWNAME", PsseFieldType.String)
	});

	public static final PsseClass FACTSDevice = 
			new PsseClass("FACTSDevice", new PsseField[]
	{
		new PsseField("N", PsseFieldType.Integer),
		new PsseField("I", PsseFieldType.String),
		new PsseField("J", PsseFieldType.String),
		new PsseField("MODE", PsseFieldType.Integer),
		new PsseField("PDES", PsseFieldType.Float),
		new PsseField("QDES", PsseFieldType.Float),
		new PsseField("VSET", PsseFieldType.Float),
		new PsseField("SHMX", PsseFieldType.Float),
		new PsseField("TRMX", PsseFieldType.Float),
		new PsseField("VTMN", PsseFieldType.Float),
		new PsseField("VTMX", PsseFieldType.Float),
		new PsseField("VSMX", PsseFieldType.Float),
		new PsseField("IMX", PsseFieldType.Float),
		new PsseField("LINX", PsseFieldType.Float),
		new PsseField("OWNER", PsseFieldType.Integer),
		new PsseField("SET1", PsseFieldType.Float),
		new PsseField("SET2", PsseFieldType.Float),
		new PsseField("VSREF", PsseFieldType.Integer)
		
	});
	
	private static final PsseClass[] ClassList = new PsseClass[]
	{
		Bus, Load, Generator, NontransformerBranch, Transformer, AreaInterchange,
		TwoTerminalDCLine, VoltageSourceConverterDCLine, SwitchedShunt,
		TransformerImpedanceCorrectionTable, MultiTerminalDCLine, MultiSectionLine,
		Zone, InterAreaTransfer, Owner, FACTSDevice
	};
	
	private static final int VersionMajor = 0;
	
	@Override
	public PsseClass[] getClasses()
	{
		return ClassList.clone();
	}

	@Override
	public int getVersionMajor()
	{
		return VersionMajor;
	}

}
