package com.powerdata.openpa.psseraw;

public class PsseClassSetVersion33 extends PsseClassSetVersion30
{
	private static final int		VersionMajor	= 33;
	public static final PsseClass		Bus33					= new PsseClass("Bus");
	public static final PsseClass		FixedShunt33			= new PsseClass("FixedShunt");

	@Override
	public int getVersionMajor()
	{
		return VersionMajor;
	}

	static
	{
		Bus33.addLine(new PsseField[] {
				new PsseField("I", PsseFieldType.Integer),
				new PsseField("NAME", PsseFieldType.String),
				new PsseField("BASKV", PsseFieldType.Float),
				new PsseField("IDE", PsseFieldType.Integer),
				new PsseField("AREA", PsseFieldType.Integer),
				new PsseField("ZONE", PsseFieldType.Integer),
				new PsseField("OWNER", PsseFieldType.Integer),
				new PsseField("VM", PsseFieldType.Float),
				new PsseField("VA", PsseFieldType.Float) });

		/**
		 * TODO: The STAT field is just a guess, verify once docs become
		 * available
		 */
		FixedShunt33.addLine(new PsseField[] {
			new PsseField("I", PsseFieldType.Integer),
			new PsseField("ID", PsseFieldType.String),
			new PsseField("STAT", PsseFieldType.Integer),
			new PsseField("G", PsseFieldType.Float),
			new PsseField("B", PsseFieldType.Float) });
	}

}
