package com.powerdata.openpa.tools;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvCompare 
{
	
	public static void main(String...args) throws IOException
	{
		File dirOrig = null;
		File dirNew = null;
		File outFile = null;
		File[] filesOrig;
		File[] filesNew;
		String results = "";
		List<Integer> errorRows = new ArrayList<>();
		SimpleCSV origCSV = null;
		SimpleCSV newCSV = null;
		String[] origCols;
		String[] newData;
		String[] origData;
		boolean thorough = false;
		boolean equalData;
		int fileOffset;
		TObjectIntMap<String> newFileMap;
		
		int narg = args.length;
		for (int i = 0; i < narg;)
		{
			String a = args[i++];
			if (a.startsWith("-"))
			{
				int idx = (a.charAt(1) == '-') ? 2 : 1;
				switch (a.substring(idx).toLowerCase())
				{
					case "do":
					case "dirorig":
						dirOrig = new File(args[i++]);
						System.out.println("[main] dirOrig = "+dirOrig.getName());
						break;
					case "dn":
					case "dirnew":
						dirNew = new File(args[i++]);
						System.out.println("[main] dirNew = "+dirNew.getName());
						break;
					case "o":
					case "outfile":
						outFile = new File(args[i++]);
						System.out.println("[main] outFile = "+outFile.getName());
						break;
					case "t":
						//Thorough
						thorough = true;
					default:
						System.out.println("parameter " + a + " not understood");
				}
			}
		}
		
		FileWriter fw;
		fw = new FileWriter(outFile);
		
		filesOrig = dirOrig.listFiles();
		filesNew = dirNew.listFiles();
		
		fw.write("Comparing "+dirOrig.getName()+" to "+dirNew.getName());
		
		//Create a map of the new file names
		newFileMap = new TObjectIntHashMap<>(filesNew.length, 0.75f, -1);
		for(int i = 0; i < filesNew.length; ++i)
		{
			newFileMap.put(filesNew[i].getName(), i);
		}
		if(thorough)
		{
			for(int i = 0; i < filesNew.length; ++i)
			{
				File nf = filesNew[i];
				File of = filesOrig[newFileMap.get(nf.getName())];
				compareFiles(of,nf,fw);
			}
		}
		else
		{
			//Cycle through original files
			for(int i = 0; i < filesOrig.length; ++i)
			{
				if(filesOrig[i].getName().endsWith(".csv"))
				{
					System.out.println("\n=====================\n("+i+"/"+filesOrig.length+")File = "+filesOrig[i].getName());
					fw.append("\n\n=====================\nFile = "+filesOrig[i].getName());
					fileOffset = newFileMap.get(filesOrig[i].getName());
					if(fileOffset != -1)
					{
						fw.append("\nFile Exists = true");
						
						//CSV exists for both original and new CSV sets
						//Now to compare the data
						try 
						{
							origCSV = new SimpleCSV(filesOrig[i]);
							newCSV = new SimpleCSV(filesNew[fileOffset]);
						} 
						catch (IOException e) {
							e.printStackTrace();
						}
						
						origCols = origCSV.getColumnNames();
						
						//Compare row counts
						if(origCSV.getRowCount() == newCSV.getRowCount())
						{
							fw.append("\nRow Counts: Same");
						}
						else
						{
							fw.append("\nRow Counts: "+origCSV.getRowCount()+" vs "+newCSV.getRowCount());
						}
						
						//Compare column titles
						for(int j = 0; j < origCols.length; ++j)
						{
							origData = origCSV.get(origCols[j]);
							newData = newCSV.get(origCols[j]);
							
							System.out.println("Comparing column \""+origCols[j]+"\"");
							
							if(newData == null) 
							{
								fw.append("\nColumn \""+origCols[j]+"\" does not exist in the new CSVs");
							}
							else
							{
								equalData = true;
								errorRows.clear();
								
								for(int k = 0; k < origData.length; ++k)
								{
		//							System.out.println("k = "+k+" | newData.length = "+newData.length);
									if(k >= newData.length)
									{
										equalData = false;
										fw.append("\nColumn \""+origCols[j]+"\" invalid value in row "+k);
									}
									else if(!newData[k].equals(origData[k]))
									{
										equalData = false;
										errorRows.add((k+2));
		//								fw.append(results += "\n Column \""+origCols[j]+"\" value difference in row "+k+": "+origData[k]+" vs "+newData[k]);
									}
								}
								
								fw.append("\nColumn \""+origCols[j]+"\" data equal: "+equalData);
								if(!equalData && errorRows.size() < 10)
								{
									fw.append("\nRows with errors: "+errorRows);
								}
							}
						}
						
					}
					else
					{
						fw.append("\nFile Exists = FALSE");
					}
				}
			}
		}
	
		fw.close(); 
		System.out.println("Comparison complete. Check file for results");
	}
	
	private static void compareFiles(File nf, File of, FileWriter fw) throws IOException
	{
		
		fw.append("\n\n=========================");
		
		fw.append("\nFile = "+of.getName());
		fw.append("\nFile Exists = "+(nf.exists()));
		
		SimpleCSV newCsv = new SimpleCSV(nf);
		SimpleCSV oldCsv = new SimpleCSV(of);
		int nCol = oldCsv.getColCount();
		int oldRow = oldCsv.getRowCount();
		int newRow = newCsv.getRowCount();
		int nRow;
		fw.append("\nRow Count Equal = ");

		if(oldRow != newRow)
		{
			fw.append("false");
			nRow = (oldRow < newRow)?oldRow:newRow;
		}
		else
		{
			fw.append("true");
			nRow = newRow;
		}
		
		String[] colNames = newCsv.getColumnNames();
		
		//Make a map from the ID column of the original file
		TObjectIntMap<String> idMap = createIdMap(of);
		TObjectIntMap<String> colMap = createColMap(of);
		
		//Compare the values of each col
		for(int i = 0; i < nCol; ++i)
		{
			boolean isEqual = true;
			List<Integer> invData = new ArrayList<>();
			
			for(int j = 0; j < nRow; ++j)
			{
				String oldData = oldCsv.get(i,j);
				String newData = newCsv.get(colMap.get(colNames[i]), idMap.get(oldCsv.get(0)[j]));
				if(!oldData.equals(newData))
				{
					isEqual = false;
					invData.add(j);
				}
			}
			
			fw.append("\nColumn \""+colNames[i]+"\" = ");
			if(isEqual)
			{
				fw.append("true");
			}
			else
			{
				fw.append("false");
				if(invData.size() > 10)
				{
					fw.append("\nError count = "+invData.size());
				}
				else
				{
					fw.append("\nError rows = "+invData);
				}
			}
		}
	}
	
	private static TObjectIntMap<String> createIdMap(File f) throws IOException
	{
		SimpleCSV csv = new SimpleCSV(f);
		int n = csv.getRowCount();
		String[] ids = csv.get("ID");
		TObjectIntMap<String> idMap = new TObjectIntHashMap<>(n);
		System.out.println("\n===============\n"+f.getName());
		if(ids == null) return idMap;
//		System.out.println("ID[0] = "+ids[0]);
		
		for(int i = 0; i < n; ++i)
		{
//			System.out.println("["+f.getName()+"] "+i+"/"+n);
			idMap.put(ids[i], i);
		}
		
		return idMap;
	}
	
	private static TObjectIntMap<String> createColMap(File f) throws IOException
	{
		SimpleCSV csv = new SimpleCSV(f);
		String[] colNames = csv.getColumnNames();
		int n = colNames.length;
		TObjectIntMap<String> colMap = new TObjectIntHashMap<>(n);
		
		for(int i = 0; i < n; ++i)
		{
			colMap.put(colNames[i], i);
		}
		
		return colMap;
	}
}
