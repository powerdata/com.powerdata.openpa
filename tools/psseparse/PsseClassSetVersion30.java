package com.powerdata.openpa.tools.psseparse;

public class PsseClassSetVersion30 extends PsseClassSetVersion0
{
	private static final int VersionMajor = 30;

	public static final PsseClass Transformer30 = new PsseClassTransformer(new PsseField[][]
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
			 new PsseField("COD1", PsseFieldType.Integer),
			 new PsseField("CONT1", PsseFieldType.String),
			 new PsseField("RMA1", PsseFieldType.Float),
			 new PsseField("RMI1", PsseFieldType.Float),
			 new PsseField("VMA1", PsseFieldType.Float),
			 new PsseField("VMI1", PsseFieldType.Float),
			 new PsseField("NTP1", PsseFieldType.Integer),
			 new PsseField("TAB1", PsseFieldType.Integer),
			 new PsseField("CR1", PsseFieldType.Float),
			 new PsseField("CX1", PsseFieldType.Float)
		},
		{
			 new PsseField("WINDV2", PsseFieldType.Float),
			 new PsseField("NOMV2", PsseFieldType.Float),
			 new PsseField("ANG2", PsseFieldType.Float),
			 new PsseField("RATA2", PsseFieldType.Float),
			 new PsseField("RATB2", PsseFieldType.Float),
			 new PsseField("RATC2", PsseFieldType.Float),
			 new PsseField("COD2", PsseFieldType.Integer),
			 new PsseField("CONT2", PsseFieldType.String),
			 new PsseField("RMA2", PsseFieldType.Float),
			 new PsseField("RMI2", PsseFieldType.Float),
			 new PsseField("VMA2", PsseFieldType.Float),
			 new PsseField("VMI2", PsseFieldType.Float),
			 new PsseField("NTP2", PsseFieldType.Integer),
			 new PsseField("TAB2", PsseFieldType.Integer),
			 new PsseField("CR2", PsseFieldType.Float),
			 new PsseField("CX2", PsseFieldType.Float)
		},
		{
			 new PsseField("WINDV3", PsseFieldType.Float),
			 new PsseField("NOMV3", PsseFieldType.Float),
			 new PsseField("ANG3", PsseFieldType.Float),
			 new PsseField("RATA3", PsseFieldType.Float),
			 new PsseField("RATB3", PsseFieldType.Float),
			 new PsseField("RATC3", PsseFieldType.Float),
			 new PsseField("COD3", PsseFieldType.Integer),
			 new PsseField("CONT3", PsseFieldType.String),
			 new PsseField("RMA3", PsseFieldType.Float),
			 new PsseField("RMI3", PsseFieldType.Float),
			 new PsseField("VMA3", PsseFieldType.Float),
			 new PsseField("VMI3", PsseFieldType.Float),
			 new PsseField("NTP3", PsseFieldType.Integer),
			 new PsseField("TAB3", PsseFieldType.Integer),
			 new PsseField("CR3", PsseFieldType.Float),
			 new PsseField("CX3", PsseFieldType.Float)
		}
	}, "Ownership", new PsseField[]
	{
		new PsseField("O", PsseFieldType.Integer),
		new PsseField("F", PsseFieldType.Float)
	});

	public static final PsseClass SwitchedShunt30 =
			new PsseClass("SwitchedShunt", new PsseField[]
		{
			 new PsseField("I", PsseFieldType.String),
			 new PsseField("MODSW", PsseFieldType.Integer),
			 new PsseField("VSWHI", PsseFieldType.Float),
			 new PsseField("VSWLO", PsseFieldType.Float),
			 new PsseField("SWREM", PsseFieldType.String),
			 new PsseField("RMPCT", PsseFieldType.Float),
			 new PsseField("RMIDNT", PsseFieldType.String),
			 new PsseField("BINIT", PsseFieldType.Float)
		}, "SwitchedShuntSegment",
		new PsseField[]
		{
			new PsseField("N", PsseFieldType.Integer),
			new PsseField("B", PsseFieldType.Float)
		}); 

	public static final PsseClass FACTSDevice30 = 
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
		new PsseField("RMPCT", PsseFieldType.Float),
		new PsseField("OWNER", PsseFieldType.Integer),
		new PsseField("SET1", PsseFieldType.Float),
		new PsseField("SET2", PsseFieldType.Float),
		new PsseField("VSREF", PsseFieldType.Integer)
		
	});
	

	@Override
	public PsseClass[] getClasses()
	{
		PsseClass[] rv = super.getClasses();
		rv[4] = Transformer30;
		rv[8] = SwitchedShunt30;
		rv[15] = FACTSDevice30;
		return rv;
	}

	@Override
	public int getVersionMajor()
	{
		return VersionMajor;
	}

}
