package com.powerdata.openpa;

public enum ColumnMeta
{
	/*
	 * Bus
	 */
	/** Bus ID */ 										BusID,
	/** Bus Name */ 									BusNAME,
	/** Bus voltage mag KV */ 							BusVM,
	/** Bus angle DEG */ 								BusVA,
	/** Bus Freq Src Pri */ 							BusFREQSRCPRI,
	/** Bus Area */ 									BusAREA,
	/** Bus Owner */ 									BusOWNER,
	/** Bus Station */ 									BusSTATION,
	/** Bus Voltage Level */ 							BusVLEV,
	
	/*
	 * Generator
	 */
	
	/** Gen ID */ 										GenID, 
	/** Gen Name */ 									GenNAME, 
	/** Gen bus */ 										GenBUS, 
	/** Gen active power */ 							GenP, 
	/** Gen reactive power */ 							GenQ, 
	/** Gen in service */ 								GenOOS, 
	/** Gen Type */ 									GenTYPE, 
	/** Gen Mode */ 									GenMODE, 
	/** Gen min operating MW */ 						GenOPMINP, 
	/** Gen max operating MW */ 						GenOPMAXP, 
	/** Gen min MVAr capability */ 						GenMINQ, 
	/** Gen max MVAr capability */ 						GenMAXQ, 
	/** Gen MW setpoint */ 								GenPS, 
	/** Gen MVAr setpoint */ 							GenQS, 
	/** Gen AVR state */ 								GenAVR, 
	/** Gen voltage setpoint */ 						GenVS, 
	/** Gen regulated bus */ 							GenREGBUS,
	
	/*
	 * Load
	 */
	
	/** Load ID */ 										LoadID,
	/** Load Name */ 									LoadNAME,
	/** Load bus */ 									LoadBUS, 
	/** Load active power */ 							LoadP, 
	/** Load reactive power */ 							LoadQ, 
	/** Load in service */ 								LoadOOS, 
	/** Load max active power */ 						LoadPMAX,
	/** Load max reactive power */ 						LoadQMAX,
	
	/*
	 * Shunt Capacitor
	 */
	
	/** Shunt Cap ID */ 								ShcapID,
	/** Shunt Cap Name */ 								ShcapNAME,
	/** Shunt Cap bus */ 								ShcapBUS, 
	/** Shunt Cap active power */ 						ShcapP, 
	/** Shunt Cap reactive power */ 					ShcapQ, 
	/** Shunt Cap in service */ 						ShcapOOS, 
	/** Shunt Cap nominal MVAr */ 						ShcapB,
	
	/*
	 * Shunt Reactor
	 */
	
	/** Shunt Reac ID */ 								ShreacID,
	/** Shunt Reac Name */ 								ShreacNAME,
	/** Shunt Reac bus */ 								ShreacBUS, 
	/** Shunt Reac active power */ 						ShreacP, 
	/** Shunt Reac reactive power */ 					ShreacQ, 
	/** Shunt Reac in service */ 						ShreacOOS, 
	/** Shunt Reac nominal MVAr */ 						ShreacB,
	
	/*
	 * SVC
	 */

	/** SVC ID */ 										SvcID,
	/** SVC Name */ 									SvcNAME,
	/** SVC bus */ 										SvcBUS, 
	/** SVC active power */ 							SvcP, 
	/** SVC reactive power */ 							SvcQ, 
	/** SVC in service */ 								SvcOOS, 
	/** SVC MVAr setpoint */ 							SvcQS,
	/** SVC min MVAr @ Unity KV */ 						SvcQMIN,	
	/** SVC max MVAr @ Unity KV */ 						SvcQMAX,	
	/** Svc AVR state */ 								SvcAVR, 
	/** Svc voltage setpoint */ 						SvcVS,
	/** Svc Slope */									SvcSLOPE,
	/** Svc regulated bus */ 							SvcREGBUS,
	/** Svc output mode */								SvcOMODE,
	
	/*
	 * Switched Shunt
	 */
	
	/** Switched Shunt ID */ 							SwshID,
	/** Switched Shunt Name */ 							SwshNAME,
	/** Switched Shunt active power */ 					SwshP, 
	/** Switched Shunt reactive power */ 				SwshQ, 
	/** Switched Shunt in service */ 					SwshOOS, 
	/** Switched Shunt nominal MVAr */ 					SwshB,
	
	/*
	 * Area
	 */
	/** Area ID */ 										AreaID,
	/** Area Name */ 									AreaNAME,

	/*
	 * Owner
	 */
	/** Owner ID */ 									OwnerID,
	/** Owner Name */ 									OwnerNAME,

	/*
	 * Island
	 */
	/** Island ID */ 									IslandID,
	/** Island Name */ 									IslandNAME,
	/** Island Frequency */ 							IslandFREQ,
	/** Island energization status */ 					IslandEGZSTATE,

	/*
	 * Station
	 */
	/** Station ID */ 									StationID,
	/** Station Name */ 								StationNAME,

	/*
	 * Voltage Level
	 */
	/** Vlev ID */ 										VlevID,
	/** Vlev Name */ 									VlevNAME,
	/** Vlev Base KV */ 								VlevBASKV,

	/*
	 * Line
	 */
	
	/** Line ID */ 										LineID,
	/** Line Name */ 									LineNAME,
	/** Line from-side bus */ 							LineBUSFROM,
	/** Line to-side bus */ 							LineBUSTO,
	/** Line in service */ 								LineOOS, 
	/** Line from-side MW */ 							LinePFROM, 
	/** Line from-side MVAr */ 							LineQFROM, 
	/** Line to-side MW */ 								LinePTO, 
	/** Line to-side MVAr */ 							LineQTO, 
	/** Line resistance */ 								LineR, 
	/** Line reactance*/ 								LineX, 
	/** Line from-side charging B */ 					LineBFROM, 
	/** Line to-side charging B */ 						LineBTO,
	/** Line MVA long-term Rating */					LineRATLT,

	/*
	 * Series Capacitor 
	 */
	
	/** Series Cap ID */ 								SercapID,
	/** Series Cap Name */ 								SercapNAME,
	/** Series Cap from-side bus */ 					SercapBUSFROM,
	/** Series Cap to-side bus */ 						SercapBUSTO,
	/** Series Cap in service */ 						SercapOOS, 
	/** Series Cap from-side MW */ 						SercapPFROM, 
	/** Series Cap from-side MVAr */ 					SercapQFROM, 
	/** Series Cap to-side MW */ 						SercapPTO, 
	/** Series Cap to-side MVAr */ 						SercapQTO, 
	/** Series Cap resistance */ 						SercapR, 
	/** Series Cap reactance*/ 							SercapX, 
	/** Series Cap MVA long-term Rating */				SercapRATLT,

	/*
	 * Series Reactor 
	 */
	
	/** Series Reac ID */ 								SerreacID,
	/** Series Reac Name */ 							SerreacNAME,
	/** Series Reac from-side bus */ 					SerreacBUSFROM,
	/** Series Reac to-side bus */ 						SerreacBUSTO,
	/** Series Reac in service */ 						SerreacOOS, 
	/** Series Reac from-side MW */ 					SerreacPFROM, 
	/** Series Reac from-side MVAr */ 					SerreacQFROM, 
	/** Series Reac to-side MW */ 						SerreacPTO, 
	/** Series Reac to-side MVAr */ 					SerreacQTO, 
	/** Series Reac resistance */ 						SerreacR, 
	/** Series Reac reactance*/ 						SerreacX, 
	/** Series Reac MVA long-term Rating */				SerreacRATLT,

	/*
	 * Phase Shifter 
	 */
	
	/** Phase Shifter ID */ 							PhashID,
	/** Phase Shifter Name */ 							PhashNAME,
	/** Phase Shifter from-side bus */ 					PhashBUSFROM,
	/** Phase Shifter to-side bus */ 					PhashBUSTO,
	/** Phase Shifter in service */ 					PhashOOS, 
	/** Phase Shifter from-side MW */ 					PhashPFROM, 
	/** Phase Shifter from-side MVAr */ 				PhashQFROM, 
	/** Phase Shifter to-side MW */ 					PhashPTO, 
	/** Phase Shifter to-side MVAr */ 					PhashQTO, 
	/** Phase Shifter resistance */ 					PhashR, 
	/** Phase Shifter reactance*/ 						PhashX, 
	/** Phase Shifter magnetizing conductance */ 		PhashGMAG, 
	/** Phase Shifter magnetizing susceptance */ 		PhashBMAG,
	/** Phase Shifter angle  */ 						PhashANG,
	/** Phase Shifter from-side off-nom turns ratio  */ PhashTAPFROM,
	/** Phase Shifter from-side off-nom turns ratio  */ PhashTAPTO,
	/** Phase Shifter control mode */					PhashCTRLMODE,
	/** Phase Shifter MVA long-term Rating */			PhashRATLT,
	
	/*
	 * Transformer 
	 */
	
	/** Transformer ID */ 								TfmrID,
	/** Transformer Name */ 							TfmrNAME,
	/** Transformer from-side bus */ 					TfmrBUSFROM,
	/** Transformer to-side bus */ 						TfmrBUSTO,
	/** Transformer in service */ 						TfmrOOS, 
	/** Transformer from-side MW */ 					TfmrPFROM, 
	/** Transformer from-side MVAr */ 					TfmrQFROM, 
	/** Transformer to-side MW */ 						TfmrPTO, 
	/** Transformer to-side MVAr */ 					TfmrQTO, 
	/** Transformer resistance */ 						TfmrR, 
	/** Transformer reactance*/ 						TfmrX, 
	/** Transformer magnetizing conductance */ 			TfmrGMAG, 
	/** Transformer magnetizing susceptance */ 			TfmrBMAG,
	/** Transformer angle  */ 							TfmrANG,
	/** Transformer from-side off-nom turns ratio  */ 	TfmrTAPFROM,
	/** Transformer from-side off-nom turns ratio  */	TfmrTAPTO,
	/** Transformer MVA long-term Rating */				TfmrRATLT,
	/** Transformer Minimum regulating KV */			TfmrMINREGKV,
	/** Transformer Maximum regulating KV */			TfmrMAXREGKV,
	/** Transformer Regulated Bus */					TfmrREGBUS,
	/** Transformer Tap Side */							TfmrREGSIDE,
	/** Transformer voltage regulation enabled */		TfmrREGENAB,
	/** transformer can regulate voltage */				TfmrHASREG,
	/** transformer min tap ratio from-side*/			TfmrMNTPFROM,
	/** transformer max tap ratio from-side*/			TfmrMXTPFROM,
	/** transformer min tap ratio to-side*/				TfmrMNTPTO,
	/** transformer max tap ratio to-side*/				TfmrMXTPTO,
	/** transformer step size from-side */				TfmrSTEPFROM,
	/** transformer step size to-side */				TfmrSTEPTO,
	
	
	/*
	 * Switch 
	 */
	
	/** Switch ID */ 									SwID,
	/** Switch Name */ 									SwNAME,
	/** Switch from-side bus */ 						SwBUSFROM,
	/** Switch to-side bus */ 							SwBUSTO,
	/** Switch in service */ 							SwOOS, 
	/** Switch from-side MW */ 							SwPFROM, 
	/** Switch from-side MVAr */ 						SwQFROM, 
	/** Switch to-side MW */ 							SwPTO, 
	/** Switch to-side MVAr */ 							SwQTO, 
	/** Switch STATE */ 								SwSTATE,
	/** Switch Operable under load */ 					SwOPLD,
	/** Switch Enabled */ 								SwENAB,

	/*
	 * Two-Terminal DC Line 
	 */
	
	/** Two-Term DC Line ID */ 							T2dcID,
	/** Two-Term DC Line Name */ 						T2dcNAME,
	/** Two-Term DC Line from-side bus */ 				T2dcBUSFROM,
	/** Two-Term DC Line to-side bus */ 				T2dcBUSTO,
	/** Two-Term DC Line in service */ 					T2dcOOS, 
	/** Two-Term DC Line from-side MW */ 				T2dcPFROM, 
	/** Two-Term DC Line from-side MVAr */ 				T2dcQFROM, 
	/** Two-Term DC Line to-side MW */ 					T2dcPTO, 
	/** Two-Term DC Line to-side MVAr */ 				T2dcQTO, 

;
}
