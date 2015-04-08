package com.powerdata.openpa.psseraw;

import java.util.List;

import gnu.trove.map.TObjectIntMap;

public class Psse3WdgTool implements PsseEquipment 
{
	protected static TObjectIntMap<String> _fldMap;
	protected static TObjectIntMap<String> _nodeMap;
	
	protected PsseTransformerTool _tfmr1;
	protected PsseTransformerTool _tfmr2;
	protected PsseTransformerTool _tfmr3;
	protected PsseBusTool _node;
	protected String[] _baseRecord;
	
	public Psse3WdgTool(List<PsseField[]> lines, String[] record) 
	{
		if(_fldMap == null) _fldMap = PsseEquipment.buildMap(lines);
		if(_baseRecord == null) _baseRecord = createBaseRecord(record);

		String[] rec1 = makeRec1(record);
		String[] rec2 = makeRec2(record);
		String[] rec3 = makeRec3(record);
		
		_node = new PsseBusTool(makeNodeRec(record));
		
		//Create records for each winding
	}

	private String[] makeRec1(String[] record)
	{
		String[] rec		 		= _baseRecord.clone();
		rec[_fldMap.get("i")] 		= record[_fldMap.get("i")];//node1
		rec[_fldMap.get("r1-2")] 	= record[_fldMap.get("r1-2")];//r
		rec[_fldMap.get("x1-2")] 	= record[_fldMap.get("x1-2")];//x
		rec[_fldMap.get("rata1")] 	= record[_fldMap.get("rata1")];//normoplimit
		rec[_fldMap.get("windv1")]	= record[_fldMap.get("windv1")];//ratio high
		return rec;

	}
	
	private String[] makeRec2(String[] record)
	{
		String[] rec 				= _baseRecord.clone();
		rec[_fldMap.get("i")] 		= record[_fldMap.get("j")];//node1
		rec[_fldMap.get("r1-2")] 	= record[_fldMap.get("r2-3")];//r
		rec[_fldMap.get("x1-2")] 	= record[_fldMap.get("x2-3")];//x
		rec[_fldMap.get("rata1")] 	= record[_fldMap.get("rata2")];//normoplimit
		rec[_fldMap.get("windv1")]	= record[_fldMap.get("windv2")];//ratio high
		return rec;
	}
	
	private String[] makeRec3(String[] record)
	{
		String[] rec 				= _baseRecord.clone();
		rec[_fldMap.get("i")] 		= record[_fldMap.get("k")];//node1
		rec[_fldMap.get("r1-2")] 	= record[_fldMap.get("r3-1")];//r
		rec[_fldMap.get("x1-2")] 	= record[_fldMap.get("x3-1")];//x
		rec[_fldMap.get("rata1")] 	= record[_fldMap.get("rata3")];//normoplimit
		rec[_fldMap.get("windv1")]	= record[_fldMap.get("windv3")];//ratio high
		return rec;
	}
	
	private String[] makeNodeRec(String[] record)
	{
		if(_nodeMap == null) _nodeMap = PsseBusTool.getMap();
		
		String [] rec = new String[_nodeMap.size()];
		
		rec[_nodeMap.get("i")] = record[_fldMap.get("i")]+"_"
								+record[_fldMap.get("j")]+"_"
								+record[_fldMap.get("k")];
		
		rec[_nodeMap.get("name")] = record[_fldMap.get("name")];
		rec[_nodeMap.get("baskv")] = "1";
		rec[_nodeMap.get("owner")] = record[_fldMap.get("o1")];
		return rec;
	}
	
	private String[] createBaseRecord(String[] record)
	{
		 String[] base = new String[record.length];
		 
		 base[_fldMap.get("name")] 		= record[_fldMap.get("name")];
		 base[_fldMap.get("j")] 		= record[_fldMap.get("i")]+"_"
										+record[_fldMap.get("j")]+"_"
										+record[_fldMap.get("k")];
		 float bmag 					= Float.parseFloat(record[_fldMap.get("mag2")]) / 3f;
		 base[_fldMap.get("mag2")]		= ""+bmag;
		 base[_fldMap.get("windv2")]	= "1.0";
		 return base;
	}
	
	@Override
	public String toCsv(String type) { return null; }
	public static TObjectIntMap<String> getMap() { return _fldMap; }
	public PsseBusTool getNode() { return _node; }
	public PsseTransformerTool getTfmr1() { return _tfmr1; }
	public PsseTransformerTool getTfmr2() { return _tfmr2; }
	public PsseTransformerTool getTfmr3() { return _tfmr3; }
}
