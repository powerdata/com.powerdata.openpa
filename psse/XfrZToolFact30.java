package com.powerdata.openpa.psse;

public class XfrZToolFact30 extends XfrZToolFactory
{
	private static final XfrZTools[] _ToolSet = new XfrZTools[] {
		null, new XfrZTools30PUSysBase(), new XfrZTools30PUWndBase(), new XfrZTools30LossWndBase() 
	};
	
	@Override
	public XfrZTools get(int cod) {return _ToolSet[cod];}
}
