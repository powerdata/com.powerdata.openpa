package com.powerdata.openpa.psse;

public abstract class TransformerList<T extends Transformer> 
	extends PsseBaseList<T>
{
	public TransformerList(PsseModel model) {super(model);}

	protected float rmDefault(int ndx, char mm, int wnd, float defval, int abscod)
			throws PsseModelException
	{
		int cw = getCW(ndx);
		if (cw == 2 && (abscod ==1 || abscod == 2))
		{
			throw new PsseModelException("No default allowed for RM"
					+ mm + wnd + " when |COD| is 1 or 2 and CW is 2");
		}
		else if (abscod == 3)
		{
			throw new PsseModelException("No default allowed for RM" + mm
					+ wnd + " when |COD| is 3");
		}
		return defval;
	}		
		
	protected float vmDefault(int ndx, char mm, int wnd, float defval,
			int abscod) throws PsseModelException
	{
		if (abscod == 2 || abscod == 3)
		{
			throw new PsseModelException("No default allowed for VM" + mm + wnd
					+ " when |COD| is 2 or 3");
		}
		return defval;
	}

	protected float ratioLim(int ndx, int abscod, Bus bus, float val)
			throws PsseModelException
	{
		if (abscod == 1 || abscod == 2)
		{
			return (getCW(ndx) == 2) ? val / getBus1(ndx).getBASKV() : val;
		}
		else if (abscod == 0 || abscod == 4)
		{
			return val;
		}
		else
		{
			throw new PsseModelException(
					"No valid ratio limits for Transformer " + getObjectID(ndx)
							+ " (index " + ndx + ")");
		}
	}

	protected float shiftLim(int ndx, int rcod, float val)
			throws PsseModelException
	{
		if (Math.abs(rcod) != 3)
		{
			throw new PsseModelException(
					"No valid phase shift limits for Transformer "
							+ getObjectID(ndx) + " (index " + ndx + ")");
		}
		return val;
	}

	protected float voltLim(int ndx, int abscod, float val)
			throws PsseModelException
	{
		if (abscod == 2 || abscod == 3)
		{
			throw new PsseModelException(
					"No valid controlled bus voltage limits for Transformer "
							+ getObjectID(ndx) + " (index " + ndx + ")");
		}
		return val;
	}

	protected float reacPwrLim(int ndx, int abscod, float val)
			throws PsseModelException
	{
		if (abscod != 2)
		{
			throw new PsseModelException(
					"No valid reactive power limits for Transformer "
							+ getObjectID(ndx) + " (index " + ndx + ")");
		}
		return val;
	}

	protected float actvPwrLim(int ndx, int abscod, float val)
			throws PsseModelException
	{
		if (abscod != 3)
		{
			throw new PsseModelException(
					"No valid active power limits for Transformer "
							+ getObjectID(ndx) + " (index " + ndx + ")");
		}
		return val;
	}	
	
	/* Convenience Methods */
	
	public Bus getBus1(int ndx) throws PsseModelException {return _model.getBus(getI(ndx));}
	public Bus getBus2(int ndx) throws PsseModelException {return _model.getBus(getJ(ndx));}
	public Bus getBus3(int ndx) throws PsseModelException {return _model.getBus(getK(ndx));}
	public float getMagCondPerUnit(int ndx) {return 0;}
	public float getMagSuscPerUnit(int ndx) {return 0;}
	public float getNoLoadLoss(int ndx) {return 0;}
	public float getExcitingCurrent(int ndx) {return 0;}
	public TransformerStatus getInitTransformerStat(int ndx)
	{
		return TransformerStatus.fromCode(getSTAT(ndx));
	}
	public float getResistance1_2(int ndx) {return 0;}
	public float getReactance1_2(int ndx)  {return 0;}
	public float getResistance2_3(int ndx) {return 0;}
	public float getReactance2_3(int ndx) {return 0;}
	public float getResistance3_1(int ndx) {return 0;}
	public float getReactance3_1(int ndx) {return 0;}
	public float getWnd1Ratio(int ndx) throws PsseModelException
	{
		float wv1 = getWINDV1(ndx);
		return (getCW(ndx)==2)?wv1/getBus1(ndx).getBASKV():wv1;
	}
	public float getWnd1NomKV(int ndx) throws PsseModelException
	{
		float nv1 = getNOMV1(ndx);
		return (nv1==0F)?getBus1(ndx).getBASKV():nv1;
	}
	public TransformerCtrlMode getCtrlMode1(int ndx) {return TransformerCtrlMode.fromCode(Math.abs(getCOD1(ndx)));}
	public boolean enableAutoCtrl1(int ndx) {return getCOD1(ndx) > 0;}
	public Bus getCtrlBus1(int ndx) throws PsseModelException
	{
		String cod = getCONT1(ndx);
		return _model.getBus((cod.charAt(0)=='-')?cod.substring(1):cod);
	}
	public boolean ctrlOnWnd1(int ndx) {return getCONT1(ndx).charAt(0) == '-';}
	public float getMaxRatio1(int ndx) throws PsseModelException {return ratioLim(ndx, getCOD1(ndx), getBus1(ndx), getRMA1(ndx));}
	public float getMinRatio1(int ndx) throws PsseModelException {return ratioLim(ndx, getCOD1(ndx), getBus1(ndx), getRMI1(ndx));}
	public float getMaxShift1(int ndx) throws PsseModelException {return shiftLim(ndx, getCOD1(ndx), getRMA1(ndx));}
	public float getMinShift1(int ndx) throws PsseModelException {return shiftLim(ndx, getCOD1(ndx), getRMI1(ndx));}
	public float getMaxVolt1(int ndx) throws PsseModelException {return voltLim(ndx, getCOD1(ndx), getVMA1(ndx));}   
	public float getMinVolt1(int ndx) throws PsseModelException  {return voltLim(ndx, getCOD1(ndx), getVMI1(ndx));}
	public float getMaxReacPwr1(int ndx) throws PsseModelException {return reacPwrLim(ndx, getCOD1(ndx), getVMA1(ndx));}
	public float getMinReacPwr1(int ndx) throws PsseModelException {return reacPwrLim(ndx, getCOD1(ndx), getVMI1(ndx));}
	public float getMaxActvPwr1(int ndx) throws PsseModelException {return actvPwrLim(ndx, getCOD1(ndx), getVMA1(ndx));}
	public float getMinActvPwr1(int ndx) throws PsseModelException {return actvPwrLim(ndx, getCOD1(ndx), getVMA1(ndx));}
	public ImpCorrTbl getImpCorrTbl1(int ndx) throws PsseModelException {return _model.getImpCorrTables().get(String.valueOf(getTAB1(ndx)));}

	public float getWnd2Ratio(int ndx) throws PsseModelException
	{
		float wv2 = getWINDV2(ndx);
		return (getCW(ndx)==2)?wv2/getBus2(ndx).getBASKV():wv2;
	}
	public float getWnd2NomKV(int ndx) throws PsseModelException
	{
		float nv = getNOMV2(ndx);
		return (nv==0F)?getBus2(ndx).getBASKV():nv;
	}
	public TransformerCtrlMode getCtrlMode2(int ndx) {return TransformerCtrlMode.fromCode(Math.abs(getCOD2(ndx)));}
	public boolean enableAutoCtrl2(int ndx) {return getCOD2(ndx) > 0;}
	public Bus getCtrlBus2(int ndx) throws PsseModelException
	{
		String cod = getCONT2(ndx);
		return _model.getBus((cod.charAt(0)=='-')?cod.substring(1):cod);
	}
	public boolean ctrlOnWnd2(int ndx) {return getCONT2(ndx).charAt(0) == '-';}
	public float getMaxRatio2(int ndx) throws PsseModelException {return ratioLim(ndx, getCOD2(ndx), getBus2(ndx), getRMA2(ndx));}
	public float getMinRatio2(int ndx) throws PsseModelException {return ratioLim(ndx, getCOD2(ndx), getBus2(ndx), getRMI2(ndx));}
	public float getMaxShift2(int ndx) throws PsseModelException {return shiftLim(ndx, getCOD2(ndx), getRMA2(ndx));}
	public float getMinShift2(int ndx) throws PsseModelException {return shiftLim(ndx, getCOD2(ndx), getRMI2(ndx));}
	public float getMaxVolt2(int ndx) throws PsseModelException  {return voltLim(ndx, getCOD2(ndx), getVMA2(ndx));}   
	public float getMinVolt2(int ndx) throws PsseModelException  {return voltLim(ndx, getCOD2(ndx), getVMI2(ndx));}
	public float getMaxReacPwr2(int ndx) throws PsseModelException {return reacPwrLim(ndx, getCOD2(ndx), getVMA2(ndx));}
	public float getMinReacPwr2(int ndx) throws PsseModelException {return reacPwrLim(ndx, getCOD2(ndx), getVMI2(ndx));}
	public float getMaxActvPwr2(int ndx) throws PsseModelException {return actvPwrLim(ndx, getCOD2(ndx), getVMA2(ndx));}
	public float getMinActvPwr2(int ndx) throws PsseModelException {return actvPwrLim(ndx, getCOD2(ndx), getVMA2(ndx));}
	public ImpCorrTbl getImpCorrTbl2(int ndx) throws PsseModelException {return _model.getImpCorrTables().get(String.valueOf(getTAB2(ndx)));}
	
	public float getWnd3Ratio(int ndx) throws PsseModelException
	{
		float wv = getWINDV3(ndx);
		return (getCW(ndx)==2)?wv/getBus3(ndx).getBASKV():wv;
	}
	public float getWnd3NomKV(int ndx) throws PsseModelException
	{
		float nv = getNOMV3(ndx);
		return (nv==0F)?getBus3(ndx).getBASKV():nv;
	}
	public TransformerCtrlMode getCtrlMode3(int ndx) {return TransformerCtrlMode.fromCode(Math.abs(getCOD3(ndx)));}
	public boolean enableAutoCtrl3(int ndx) {return getCOD3(ndx) > 0;}
	public Bus getCtrlBus3(int ndx) throws PsseModelException
	{
		String cod = getCONT3(ndx);
		return _model.getBus((cod.charAt(0)=='-')?cod.substring(1):cod);
	}
	public boolean ctrlOnWnd3(int ndx) {return getCONT3(ndx).charAt(0) == '-';}
	public float getMaxRatio3(int ndx) throws PsseModelException {return ratioLim(ndx, getCOD3(ndx), getBus3(ndx), getRMA3(ndx));}
	public float getMinRatio3(int ndx) throws PsseModelException {return ratioLim(ndx, getCOD3(ndx), getBus3(ndx), getRMI3(ndx));}
	public float getMaxShift3(int ndx) throws PsseModelException {return shiftLim(ndx, getCOD3(ndx), getRMA3(ndx));}
	public float getMinShift3(int ndx) throws PsseModelException {return shiftLim(ndx, getCOD3(ndx), getRMI3(ndx));}
	public float getMaxVolt3(int ndx) throws PsseModelException  {return voltLim(ndx, getCOD3(ndx), getVMA3(ndx));}   
	public float getMinVolt3(int ndx) throws PsseModelException  {return voltLim(ndx, getCOD3(ndx), getVMI3(ndx));}
	public float getMaxReacPwr3(int ndx) throws PsseModelException {return reacPwrLim(ndx, getCOD3(ndx), getVMA3(ndx));}
	public float getMinReacPwr3(int ndx) throws PsseModelException {return reacPwrLim(ndx, getCOD3(ndx), getVMI3(ndx));}
	public float getMaxActvPwr3(int ndx) throws PsseModelException {return actvPwrLim(ndx, getCOD3(ndx), getVMA3(ndx));}
	public float getMinActvPwr3(int ndx) throws PsseModelException {return actvPwrLim(ndx, getCOD3(ndx), getVMA3(ndx));}
	public ImpCorrTbl getImpCorrTbl3(int ndx) throws PsseModelException {return _model.getImpCorrTables().get(String.valueOf(getTAB3(ndx)));}

	
	/* Raw methods */
	
	public abstract String getI(int ndx);
	public abstract String getJ(int ndx);
	public String getK(int ndx) {return "0";}
	public String getCKT(int ndx) {return "1";}
	public int getCW(int ndx) {return 1;}
	public int getCZ(int ndx) {return 1;}
	public int getCM(int ndx) {return 1;}
	public float getMAG1(int ndx) {return 0;}
	public float getMAG2(int ndx)  {return 0;}
	public int getNMETR(int ndx) {return 2;}
	public String getNAME(int ndx) {return "";}
	public int getSTAT(int ndx) {return 1;}
	public float getR1_2(int ndx) {return 0F;}
	public abstract float getX1_2(int ndx);
	public float getSBASE1_2(int ndx) {return _model.getSBASE();}
	public float getR2_3(int ndx) {return 0F;}
	public abstract float getX2_3(int ndx);
	public float getSBASE2_3(int ndx) {return _model.getSBASE();}
	public float getR3_1(int ndx) {return 0F;}
	public abstract float getX3_1(int ndx);
	public float getSBASE3_1(int ndx) {return _model.getSBASE();}
	public float getVMSTAR(int ndx) {return 1F;}
	public float getANSTAR(int ndx) {return 0F;}
	public float getWINDV1(int ndx) throws PsseModelException {return (getCW(ndx)==2)?getBus1(ndx).getBASKV():1F;}
	public float getNOMV1(int ndx)  {return 0F;}
	public float getANG1(int ndx)   {return 0F;}
	public float getRATA1(int ndx) {return 0F;}
	public float getRATB1(int ndx) {return 0F;}
	public float getRATC1(int ndx) {return 0F;}
	public int getCOD1(int ndx) {return 0;}
	public String getCONT1(int ndx) {return "0";}
	public float getRMA1(int ndx) throws PsseModelException {return rmDefault(ndx, 'A', 1, 1.1F, Math.abs(getCOD1(ndx)));}
	public float getRMI1(int ndx) throws PsseModelException {return rmDefault(ndx, 'I', 1, 0.9F, Math.abs(getCOD1(ndx)));}
	public float getVMA1(int ndx) throws PsseModelException {return vmDefault(ndx, 'A', 1, 1.1F, Math.abs(getCOD1(ndx)));}
	public float getVMI1(int ndx) throws PsseModelException {return vmDefault(ndx, 'I', 1, 0.9F, Math.abs(getCOD1(ndx)));}
	public int getNTP1(int ndx) {return 33;}
	public int getTAB1(int ndx) {return 0;}
	public float getCR1(int ndx) {return 0F;}
	public float getCX1(int ndx) {return 0F;}

	public float getWINDV2(int ndx) throws PsseModelException {return (getCW(ndx)==2)?getBus2(ndx).getBASKV():1F;}
	public float getNOMV2(int ndx)  {return 0F;}
	public float getANG2(int ndx)   {return 0F;}
	public float getRATA2(int ndx) {return 0F;}
	public float getRATB2(int ndx) {return 0F;}
	public float getRATC2(int ndx) {return 0F;}
	public int getCOD2(int ndx) {return 0;}
	public String getCONT2(int ndx) {return "0";}
	public float getRMA2(int ndx) throws PsseModelException {return rmDefault(ndx, 'A', 1, 1.1F, Math.abs(getCOD2(ndx)));}
	public float getRMI2(int ndx) throws PsseModelException {return rmDefault(ndx, 'I', 1, 0.9F, Math.abs(getCOD2(ndx)));}
	public float getVMA2(int ndx) throws PsseModelException {return vmDefault(ndx, 'A', 1, 1.1F, Math.abs(getCOD2(ndx)));}
	public float getVMI2(int ndx) throws PsseModelException {return vmDefault(ndx, 'I', 1, 0.9F, Math.abs(getCOD2(ndx)));}
	public int getNTP2(int ndx) {return 33;}
	public int getTAB2(int ndx) {return 0;}
	public float getCR2(int ndx) {return 0F;}
	public float getCX2(int ndx) {return 0F;}

	// TODO:  Do we need any special checking if it's 2-winding(and hence none of these are valid...or line 4 for that matter)
	public float getWINDV3(int ndx) throws PsseModelException {return (getCW(ndx)==2)?getBus3(ndx).getBASKV():1F;}
	public float getNOMV3(int ndx)  {return 0F;}
	public float getANG3(int ndx)   {return 0F;}
	public float getRATA3(int ndx) {return 0F;}
	public float getRATB3(int ndx) {return 0F;}
	public float getRATC3(int ndx) {return 0F;}
	public int getCOD3(int ndx) {return 0;}
	public String getCONT3(int ndx) {return "0";}
	public float getRMA3(int ndx) throws PsseModelException {return rmDefault(ndx, 'A', 1, 1.1F, Math.abs(getCOD3(ndx)));}
	public float getRMI3(int ndx) throws PsseModelException {return rmDefault(ndx, 'I', 1, 0.9F, Math.abs(getCOD3(ndx)));}
	public float getVMA3(int ndx) throws PsseModelException {return vmDefault(ndx, 'A', 1, 1.1F, Math.abs(getCOD3(ndx)));}
	public float getVMI3(int ndx) throws PsseModelException {return vmDefault(ndx, 'I', 1, 0.9F, Math.abs(getCOD3(ndx)));}
	public int getNTP3(int ndx) {return 33;}
	public int getTAB3(int ndx) {return 0;}
	public float getCR3(int ndx) {return 0F;}
	public float getCX3(int ndx) {return 0F;}

	public abstract OwnershipList<?> getOwnership(int ndx);

}	
