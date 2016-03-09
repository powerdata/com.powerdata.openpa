package com.powerdata.openpa;

public enum ColumnMeta
{
	/*
	 * Bus
	 */
	/** Bus ID */ 										BusID(ListMetaType.Bus),
	/** Bus Name */ 									BusNAME(ListMetaType.Bus),
	/** Bus voltage mag KV */ 							BusVM(ListMetaType.Bus),
	/** Bus angle DEG */ 								BusVA(ListMetaType.Bus),
	/** Bus Freq Src Pri */ 							BusFREQSRCPRI(ListMetaType.Bus),
	/** Bus Area */ 									BusAREA(ListMetaType.Bus),
	/** Bus Owner */ 									BusOWNER(ListMetaType.Bus),
	/** Bus Station */ 									BusSTATION(ListMetaType.Bus),
	/** Bus Voltage Level */ 							BusVLEV(ListMetaType.Bus),
	
	/*
	 * Generator
	 */
	
	/** Gen ID */ 										GenID(ListMetaType.Gen), 
	/** Gen Name */ 									GenNAME(ListMetaType.Gen), 
	/** Gen bus */ 										GenBUS(ListMetaType.Gen), 
	/** Gen active power */ 							GenP(ListMetaType.Gen), 
	/** Gen reactive power */ 							GenQ(ListMetaType.Gen), 
	/** Gen in service */ 								GenINSVC(ListMetaType.Gen), 
	/** Gen Type */ 									GenTYPE(ListMetaType.Gen), 
	/** Gen Mode */ 									GenMODE(ListMetaType.Gen), 
	/** Gen min operating MW */ 						GenOPMINP(ListMetaType.Gen), 
	/** Gen max operating MW */ 						GenOPMAXP(ListMetaType.Gen), 
	/** Gen min MVAr capability */ 						GenMINQ(ListMetaType.Gen), 
	/** Gen max MVAr capability */ 						GenMAXQ(ListMetaType.Gen), 
	/** Gen MW setpoint */ 								GenPS(ListMetaType.Gen), 
	/** Gen MVAr setpoint */ 							GenQS(ListMetaType.Gen), 
	/** Gen AVR state */ 								GenAVR(ListMetaType.Gen), 
	/** Gen voltage setpoint */ 						GenVS(ListMetaType.Gen), 
	/** Gen regulated bus */ 							GenREGBUS(ListMetaType.Gen),
	/** Gen Ramp Rate */								GenRAMP(ListMetaType.Gen),
	
	/*
	 * Load
	 */
	
	/** Load ID */ 										LoadID(ListMetaType.Load),
	/** Load Name */ 									LoadNAME(ListMetaType.Load),
	/** Load bus */ 									LoadBUS(ListMetaType.Load), 
	/** Load active power */ 							LoadP(ListMetaType.Load), 
	/** Load reactive power */ 							LoadQ(ListMetaType.Load), 
	/** Load in service */ 								LoadINSVC(ListMetaType.Load), 
	/** Load max active power */ 						LoadPMAX(ListMetaType.Load),
	/** Load max reactive power */ 						LoadQMAX(ListMetaType.Load),
	
	/*
	 * Shunt Capacitor
	 */
	
	/** Shunt Cap ID */ 								ShcapID(ListMetaType.ShuntCap),
	/** Shunt Cap Name */ 								ShcapNAME(ListMetaType.ShuntCap),
	/** Shunt Cap bus */ 								ShcapBUS(ListMetaType.ShuntCap), 
	/** Shunt Cap active power */ 						ShcapP(ListMetaType.ShuntCap), 
	/** Shunt Cap reactive power */ 					ShcapQ(ListMetaType.ShuntCap), 
	/** Shunt Cap in service */ 						ShcapINSVC(ListMetaType.ShuntCap), 
	/** Shunt Cap nominal MVAr */ 						ShcapB(ListMetaType.ShuntCap),
	
	/*
	 * Shunt Reactor
	 */
	
	/** Shunt Reac ID */ 								ShreacID(ListMetaType.ShuntReac),
	/** Shunt Reac Name */ 								ShreacNAME(ListMetaType.ShuntReac),
	/** Shunt Reac bus */ 								ShreacBUS(ListMetaType.ShuntReac), 
	/** Shunt Reac active power */ 						ShreacP(ListMetaType.ShuntReac), 
	/** Shunt Reac reactive power */ 					ShreacQ(ListMetaType.ShuntReac), 
	/** Shunt Reac in service */ 						ShreacINSVC(ListMetaType.ShuntReac), 
	/** Shunt Reac nominal MVAr */ 						ShreacB(ListMetaType.ShuntReac),
	
	/*
	 * SVC
	 */

	/** SVC ID */ 										SvcID(ListMetaType.SVC),
	/** SVC Name */ 									SvcNAME(ListMetaType.SVC),
	/** SVC bus */ 										SvcBUS(ListMetaType.SVC), 
	/** SVC active power */ 							SvcP(ListMetaType.SVC), 
	/** SVC reactive power */ 							SvcQ(ListMetaType.SVC), 
	/** SVC in service */ 								SvcINSVC(ListMetaType.SVC), 
	/** SVC MVAr setpoint */ 							SvcQS(ListMetaType.SVC),
	/** SVC min MVAr @ Unity KV */ 						SvcQMIN(ListMetaType.SVC),	
	/** SVC max MVAr @ Unity KV */ 						SvcQMAX(ListMetaType.SVC),	
	/** Svc AVR state */ 								SvcAVR(ListMetaType.SVC), 
	/** Svc voltage setpoint */ 						SvcVS(ListMetaType.SVC),
	/** Svc Slope */									SvcSLOPE(ListMetaType.SVC),
	/** Svc regulated bus */ 							SvcREGBUS(ListMetaType.SVC),
	/** Svc output mode */								SvcOMODE(ListMetaType.SVC),
	
	/*
	 * Switched Shunt
	 */
	
	/** Switched Shunt ID */ 							SwshID(ListMetaType.SwitchedShunt),
	/** Switched Shunt Name */ 							SwshNAME(ListMetaType.SwitchedShunt),
	/** Switched Shunt active power */ 					SwshP(ListMetaType.SwitchedShunt), 
	/** Switched Shunt reactive power */ 				SwshQ(ListMetaType.SwitchedShunt), 
	/** Switched Shunt in service */ 					SwshOOS(ListMetaType.SwitchedShunt), 
	/** Switched Shunt nominal MVAr */ 					SwshB(ListMetaType.SwitchedShunt),
	
	/*
	 * Area
	 */
	/** Area ID */ 										AreaID(ListMetaType.Area),
	/** Area Name */ 									AreaNAME(ListMetaType.Area),

	/*
	 * Owner
	 */
	/** Owner ID */ 									OwnerID(ListMetaType.Owner),
	/** Owner Name */ 									OwnerNAME(ListMetaType.Owner),

	/*
	 * Island
	 */
	/** Island ID */ 									IslandID(ListMetaType.Island),
	/** Island Name */ 									IslandNAME(ListMetaType.Island),
	/** Island Frequency */ 							IslandFREQ(ListMetaType.Island),
	/** Island energization status */ 					IslandEGZSTATE(ListMetaType.Island),

	/*
	 * Station
	 */
	/** Station ID */ 									StationID(ListMetaType.Station),
	/** Station Name */ 								StationNAME(ListMetaType.Station),

	/*
	 * Voltage Level
	 */
	/** Vlev ID */ 										VlevID(ListMetaType.VoltageLevel),
	/** Vlev Name */ 									VlevNAME(ListMetaType.VoltageLevel),
	/** Vlev Base KV */ 								VlevBASKV(ListMetaType.VoltageLevel),

	/*
	 * Line
	 */
	
	/** Line ID */ 										LineID(ListMetaType.Line),
	/** Line Name */ 									LineNAME(ListMetaType.Line),
	/** Line from-side bus */ 							LineBUSFROM(ListMetaType.Line),
	/** Line to-side bus */ 							LineBUSTO(ListMetaType.Line),
	/** Line in service */ 								LineINSVC(ListMetaType.Line), 
	/** Line from-side MW */ 							LinePFROM(ListMetaType.Line), 
	/** Line from-side MVAr */ 							LineQFROM(ListMetaType.Line), 
	/** Line to-side MW */ 								LinePTO(ListMetaType.Line), 
	/** Line to-side MVAr */ 							LineQTO(ListMetaType.Line), 
	/** Line resistance */ 								LineR(ListMetaType.Line), 
	/** Line reactance*/ 								LineX(ListMetaType.Line), 
	/** Line from-side charging B */ 					LineBFROM(ListMetaType.Line), 
	/** Line to-side charging B */ 						LineBTO(ListMetaType.Line),
	/** Line MVA long-term Rating */					LineRATLT(ListMetaType.Line),

	/*
	 * Series Capacitor 
	 */
	
	/** Series Cap ID */ 								SercapID(ListMetaType.SeriesCap),
	/** Series Cap Name */ 								SercapNAME(ListMetaType.SeriesCap),
	/** Series Cap from-side bus */ 					SercapBUSFROM(ListMetaType.SeriesCap),
	/** Series Cap to-side bus */ 						SercapBUSTO(ListMetaType.SeriesCap),
	/** Series Cap in service */ 						SercapINSVC(ListMetaType.SeriesCap), 
	/** Series Cap from-side MW */ 						SercapPFROM(ListMetaType.SeriesCap), 
	/** Series Cap from-side MVAr */ 					SercapQFROM(ListMetaType.SeriesCap), 
	/** Series Cap to-side MW */ 						SercapPTO(ListMetaType.SeriesCap), 
	/** Series Cap to-side MVAr */ 						SercapQTO(ListMetaType.SeriesCap), 
	/** Series Cap resistance */ 						SercapR(ListMetaType.SeriesCap), 
	/** Series Cap reactance*/ 							SercapX(ListMetaType.SeriesCap), 
	/** Series Cap MVA long-term Rating */				SercapRATLT(ListMetaType.SeriesCap),

	/*
	 * Series Reactor 
	 */
	
	/** Series Reac ID */ 								SerreacID(ListMetaType.SeriesReac),
	/** Series Reac Name */ 							SerreacNAME(ListMetaType.SeriesReac),
	/** Series Reac from-side bus */ 					SerreacBUSFROM(ListMetaType.SeriesReac),
	/** Series Reac to-side bus */ 						SerreacBUSTO(ListMetaType.SeriesReac),
	/** Series Reac in service */ 						SerreacINSVC(ListMetaType.SeriesReac), 
	/** Series Reac from-side MW */ 					SerreacPFROM(ListMetaType.SeriesReac), 
	/** Series Reac from-side MVAr */ 					SerreacQFROM(ListMetaType.SeriesReac), 
	/** Series Reac to-side MW */ 						SerreacPTO(ListMetaType.SeriesReac), 
	/** Series Reac to-side MVAr */ 					SerreacQTO(ListMetaType.SeriesReac), 
	/** Series Reac resistance */ 						SerreacR(ListMetaType.SeriesReac), 
	/** Series Reac reactance*/ 						SerreacX(ListMetaType.SeriesReac), 
	/** Series Reac MVA long-term Rating */				SerreacRATLT(ListMetaType.SeriesReac),

	/*
	 * Phase Shifter 
	 */
	
	/** Phase Shifter ID */ 							PhashID(ListMetaType.PhaseShifter),
	/** Phase Shifter Name */ 							PhashNAME(ListMetaType.PhaseShifter),
	/** Phase Shifter from-side bus */ 					PhashBUSFROM(ListMetaType.PhaseShifter),
	/** Phase Shifter to-side bus */ 					PhashBUSTO(ListMetaType.PhaseShifter),
	/** Phase Shifter in service */ 					PhashINSVC(ListMetaType.PhaseShifter), 
	/** Phase Shifter from-side MW */ 					PhashPFROM(ListMetaType.PhaseShifter), 
	/** Phase Shifter from-side MVAr */ 				PhashQFROM(ListMetaType.PhaseShifter), 
	/** Phase Shifter to-side MW */ 					PhashPTO(ListMetaType.PhaseShifter), 
	/** Phase Shifter to-side MVAr */ 					PhashQTO(ListMetaType.PhaseShifter), 
	/** Phase Shifter resistance */ 					PhashR(ListMetaType.PhaseShifter), 
	/** Phase Shifter reactance*/ 						PhashX(ListMetaType.PhaseShifter), 
	/** Phase Shifter magnetizing conductance */ 		PhashGMAG(ListMetaType.PhaseShifter), 
	/** Phase Shifter magnetizing susceptance */ 		PhashBMAG(ListMetaType.PhaseShifter),
	/** Phase Shifter angle  */ 						PhashANG(ListMetaType.PhaseShifter),
	/** Phase Shifter from-side off-nom turns ratio  */ PhashTAPFROM(ListMetaType.PhaseShifter),
	/** Phase Shifter to-side off-nom turns ratio  */   PhashTAPTO(ListMetaType.PhaseShifter),
	/** Phase Shifter control mode */					PhashCTRLMODE(ListMetaType.PhaseShifter),
	/** Phase Shifter MVA long-term Rating */			PhashRATLT(ListMetaType.PhaseShifter),
	/** Phase Shifter can regulate MW */				PhashHASREG(ListMetaType.PhaseShifter),
	/** Phase Shifter maximum angle (DEG) */			PhashMXANG(ListMetaType.PhaseShifter),
	/** Phase Shifter minimum angle (DEG) */			PhashMNANG(ListMetaType.PhaseShifter),
	/** Phase Shifter maximum active power (MW) */		PhashMXMW(ListMetaType.PhaseShifter),
	/** Phase Shifter minimum active power (MW) */		PhashMNMW(ListMetaType.PhaseShifter),
	
	/*
	 * Transformer 
	 */
	
	/** Transformer ID */ 								TfmrID(ListMetaType.Transformer),
	/** Transformer Name */ 							TfmrNAME(ListMetaType.Transformer),
	/** Transformer from-side bus */ 					TfmrBUSFROM(ListMetaType.Transformer),
	/** Transformer to-side bus */ 						TfmrBUSTO(ListMetaType.Transformer),
	/** Transformer in service */ 						TfmrINSVC(ListMetaType.Transformer), 
	/** Transformer from-side MW */ 					TfmrPFROM(ListMetaType.Transformer), 
	/** Transformer from-side MVAr */ 					TfmrQFROM(ListMetaType.Transformer), 
	/** Transformer to-side MW */ 						TfmrPTO(ListMetaType.Transformer), 
	/** Transformer to-side MVAr */ 					TfmrQTO(ListMetaType.Transformer), 
	/** Transformer resistance */ 						TfmrR(ListMetaType.Transformer), 
	/** Transformer reactance*/ 						TfmrX(ListMetaType.Transformer), 
	/** Transformer magnetizing conductance */ 			TfmrGMAG(ListMetaType.Transformer), 
	/** Transformer magnetizing susceptance */ 			TfmrBMAG(ListMetaType.Transformer),
	/** Transformer angle  */ 							TfmrANG(ListMetaType.Transformer),
	/** Transformer from-side off-nom turns ratio  */ 	TfmrTAPFROM(ListMetaType.Transformer),
	/** Transformer from-side off-nom turns ratio  */	TfmrTAPTO(ListMetaType.Transformer),
	/** Transformer MVA long-term Rating */				TfmrRATLT(ListMetaType.Transformer),
	/** Transformer Minimum regulating KV */			TfmrMINREGKV(ListMetaType.Transformer),
	/** Transformer Maximum regulating KV */			TfmrMAXREGKV(ListMetaType.Transformer),
	/** Transformer Regulated Bus */					TfmrREGBUS(ListMetaType.Transformer),
	/** Transformer Tap Side */							TfmrTAPBUS(ListMetaType.Transformer),
	/** Transformer voltage regulation enabled */		TfmrREGENAB(ListMetaType.Transformer),
	/** transformer can regulate voltage */				TfmrHASREG(ListMetaType.Transformer),
	/** transformer min tap ratio from-side*/			TfmrMNTPFROM(ListMetaType.Transformer),
	/** transformer max tap ratio from-side*/			TfmrMXTPFROM(ListMetaType.Transformer),
	/** transformer min tap ratio to-side*/				TfmrMNTPTO(ListMetaType.Transformer),
	/** transformer max tap ratio to-side*/				TfmrMXTPTO(ListMetaType.Transformer),
	/** transformer step size from-side */				TfmrSTEPFROM(ListMetaType.Transformer),
	/** transformer step size to-side */				TfmrSTEPTO(ListMetaType.Transformer),
	
	
	/*
	 * Switch 
	 */
	
	/** Switch ID */ 									SwID(ListMetaType.Switch),
	/** Switch Name */ 									SwNAME(ListMetaType.Switch),
	/** Switch from-side bus */ 						SwBUSFROM(ListMetaType.Switch),
	/** Switch to-side bus */ 							SwBUSTO(ListMetaType.Switch),
	/** Switch in service */ 							SwINSVC(ListMetaType.Switch), 
	/** Switch from-side MW */ 							SwPFROM(ListMetaType.Switch), 
	/** Switch from-side MVAr */ 						SwQFROM(ListMetaType.Switch), 
	/** Switch to-side MW */ 							SwPTO(ListMetaType.Switch), 
	/** Switch to-side MVAr */ 							SwQTO(ListMetaType.Switch), 
	/** Switch STATE */ 								SwSTATE(ListMetaType.Switch),
	/** Switch Operable under load */ 					SwOPLD(ListMetaType.Switch),
	/** Switch Enabled */ 								SwENAB(ListMetaType.Switch),
	/** Switch transit time (sec) */					SwTRTIME(ListMetaType.Switch),

	/*
	 * Two-Terminal DC Line 
	 */
	
	/** Two-Term DC Line ID */ 							T2dcID(ListMetaType.TwoTermDCLine),
	/** Two-Term DC Line Name */ 						T2dcNAME(ListMetaType.TwoTermDCLine),
	/** Two-Term DC Line from-side bus */ 				T2dcBUSFROM(ListMetaType.TwoTermDCLine),
	/** Two-Term DC Line to-side bus */ 				T2dcBUSTO(ListMetaType.TwoTermDCLine),
	/** Two-Term DC Line in service */ 					T2dcINSVC(ListMetaType.TwoTermDCLine), 
	/** Two-Term DC Line from-side MW */ 				T2dcPFROM(ListMetaType.TwoTermDCLine), 
	/** Two-Term DC Line from-side MVAr */ 				T2dcQFROM(ListMetaType.TwoTermDCLine), 
	/** Two-Term DC Line to-side MW */ 					T2dcPTO(ListMetaType.TwoTermDCLine), 
	/** Two-Term DC Line to-side MVAr */ 				T2dcQTO(ListMetaType.TwoTermDCLine),
	
	/** Steam Turbine ID */								SteamTurbineID(ListMetaType.SteamTurbine), 
	/** Steam Turbine Name */							SteamTurbineNAME(ListMetaType.SteamTurbine), 
	/** Steam Supply */									SteamTurbineSteamSupply(ListMetaType.SteamTurbine), 

;
	
	ColumnMeta(ListMetaType list) {_list = list;}
	ListMetaType _list;
	public ListMetaType getListType() {return _list;}
}
