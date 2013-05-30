package com.powerdata.openpa.pssecvt;

public class PsseField
{
	private String _name;
	private PsseFieldType _type;

	public PsseField(String name, PsseFieldType type)
	{
		_name = name;
		_type = type;
	}
	
	public String getName() {return _name;}
	public PsseFieldType getType() {return _type;}

	@Override
	public String toString()
	{
		return String.format("%s [%s]", getName(), getType());
	}
	
	
}
