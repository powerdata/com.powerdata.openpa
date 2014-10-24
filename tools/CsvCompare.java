package com.powerdata.openpa.tools;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
		boolean equalData;
		int fileOffset;
		TObjectIntMap<String> newFileMap;
		
//		for(int i=0; i < args.length;)
//		{
//			String s = args[i++].toLowerCase();
//			int ssx = 1;
//			if (s.startsWith("--")) ++ssx;
//			switch(s.substring(ssx))
//			{
//				case "dirorig":
//					System.out.println("dirOrig = "+args[i++]);
//					dirOrig = new File(args[i++]);
//					break;
//				case "dirnew":
//					System.out.println("dirNew = "+args[i++]);
//					dirNew = new File(args[i++]);
//					break;
//				case "outfile":
//					System.out.println("outFile = "+args[i++]);
//					outFile = new File(args[i++]);
//					break;
//				default:
//					System.out.println("Unknown ("+i+"/"+args.length+") | "+s.substring(ssx)+" : "+args[i++]);
//			}
//		}
//		if (dirNew == null || dirOrig == null || outFile == null)
//		{
//			System.err.format("Usage: --dirOrig Original_CSVs --dirNew New_CSVs --outFile File_Name");
//			System.exit(1);
//		}
		
		//Setup Files
		dirOrig = new File("/home/derek/holding/isone");
		dirNew = new File("/home/derek/holding/isone/PsmFmtExport");
//		outFile = new File("/home/derek/holding/CascadiaResults.txt");
		
		FileWriter fw;
		fw = new FileWriter(new File("/home/derek/holding/isoneResults.txt"));
		
		filesOrig = dirOrig.listFiles();
		filesNew = dirNew.listFiles();
		
		fw.write("Comparing "+dirOrig.getName()+" to "+dirNew.getName());
		
		//Create a map of the new file names
		newFileMap = new TObjectIntHashMap<>(filesNew.length, 0.75f, -1);
		for(int i = 0; i < filesNew.length; ++i)
		{
			newFileMap.put(filesNew[i].getName(), i);
		}
		
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
	
		fw.close(); 
		System.out.println("Comparison complete. Check file for results");
	}

}
