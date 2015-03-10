package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.pd3.Column;
import com.powerdata.pd3.PDDB;
import com.powerdata.pd3.PDDBException;
import com.powerdata.pd3.View;

public class CasePhaseTapChangerOPAExtended extends CasePhaseTapChangerOPA 
{
	Column _tapCol;

	public CasePhaseTapChangerOPAExtended(PAModel m, PDDB db) throws PAModelException, PDDBException 
	{
		super(m);
		
		View phaseView = db.openView("view:group=pd3openpa&name=PhaseShifter&ctx=Ots&inputctx=OtsControl", null);
		_tapCol = phaseView.col("Tap");
		
		assign(CasePhaseTapChanger.TapPosition, i -> tapPosition(_list.getKey(i)));
	}
	
	String tapPosition(int key)
	{
		String pos = "";
		
		try { pos = _tapCol.getString(key); } 
		catch (PDDBException e)  { e.printStackTrace(); }
		
		return pos;
	}
}
