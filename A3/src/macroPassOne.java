import java.util.*;
import java.io.*;

public class macroPassOne {
	
	public static void main(String[] args) {
	
		BufferedReader bin;		
		FileReader fin;

		BufferedWriter bw;
		BufferedWriter bw2;
		
		FileWriter fw;
		FileWriter fw2;
		
		FileWriter fout;
		BufferedWriter bout;
		
		
		try {
			
			String inputCode = "C:\\Users\\Numair Shaikh\\Desktop\\SEM6\\SPOSL\\A3\\INPUT.txt";
			fin = new FileReader(inputCode);
			bin = new BufferedReader(fin);
			
			String outputMdt = "C:\\Users\\Numair Shaikh\\Desktop\\SEM6\\SPOSL\\A3\\MDT.txt";
			fw = new FileWriter(outputMdt);
			bw = new BufferedWriter(fw);
			
			String outputMnt = "C:\\Users\\Numair Shaikh\\Desktop\\SEM6\\SPOSL\\A3\\MNT.txt";
			fw2 = new FileWriter(outputMnt);
			bw2 = new BufferedWriter(fw2);
			
			String output = "C:\\Users\\Numair Shaikh\\Desktop\\SEM6\\SPOSL\\A3\\OUPUT.txt";
			fout = new FileWriter(output);
			bout = new BufferedWriter(fout);
			
			
			int mdtptr = 1;
			
			Hashtable<String, String> kpdtab = new Hashtable<String, String>(); int kpdtptr = 1;
			Hashtable<String, Integer> pntab = null; int pnptr = 1;
			
			Hashtable<String, String> is = new Hashtable<String, String>();
			is.put("STOP", "00");
			is.put("ADD", "01");
			is.put("SUB", "02");
			is.put("MULT", "03");
			is.put("MOVER", "04");
			is.put("MOVEM", "05");
			is.put("COMP", "06");
			is.put("BC", "07");
			is.put("DIV", "08");
			is.put("READ", "09");
			is.put("PRINT", "10");
			
			int isMacro = 0; 
			String currMacroName = "";
			
			int pp = 0;
			int kp = 0;
			
			int mntIndex = 1;
			
			int currmdtptr = mdtptr;
			int currkpdtptr = kpdtptr;
			
			System.out.println("\n------------------------\tMDT\t------------------------\n");
			
			String readInput;
			while((readInput = bin.readLine()) != null) {
				String words[] = readInput.split("\t");
				if(words[0].length() != 0) {
					if(words[0].equals("MACRO")) {
						pntab = new Hashtable<String, Integer>(); 
						isMacro = 1;
						pp=0;
						kp=0;
						currmdtptr = mdtptr;
						currkpdtptr = kpdtptr;
					}
					else if (words[0].equals("MEND")) {
						isMacro = 0;
						System.out.println(Integer.toString(mdtptr) + "\t" + words[0] + "\t");
						bw.write(Integer.toString(mdtptr) + "\t" + words[0] + "\t");
						mdtptr++;
						System.out.println();
						bw.write("\n");
						bw.write("\n");
						bw2.write(Integer.toString(mntIndex) + "\t" + currMacroName + "\t" + Integer.toString(pp) + "\t" + Integer.toString(kp) + "\t" + Integer.toString(currmdtptr) + "\t" + Integer.toString(currkpdtptr) + "\n");
						mntIndex++;
						pnptr=1;
						System.out.println("------------------------\tPNTAB\t------------------------");

						System.out.println("#");
						
						Set<String> keys2 = pntab.keySet();
				        for(String key: keys2){
				            System.out.println(key + "\t" + pntab.get(key));
				        }
				        
				        System.out.println("\n");
						
						continue;
					}
					else {
						currMacroName = words[0];
					}
				}
				
				if(isMacro == 1) {
					if(words.length > 1) {
						if(words[0].equals(currMacroName)) {
							words[1] = words[1].replaceAll("\\s", "");
							words[1] = words[1].replaceAll("&", "");
							String params[] = words[1].split(",");
							for(int i=0; i<params.length; i++) {
								if(params[i].length() == 1) {
									pp++;
									pntab.put(params[i], pnptr);
									pnptr ++;
								}
								else {
									String kps[] = params[i].split("=");
									if(kps.length == 1) {
										kp++;
										pntab.put(kps[0], pnptr);
										pnptr++;
										kpdtab.put(kps[0], "-");
										kpdtptr++;
									}else {
										kp++;
										pntab.put(kps[0], pnptr);
										pnptr++;
										kpdtab.put(kps[0], kps[1]);
										kpdtptr++;
									}
								}
							}
						}
						if(is.containsKey(words[1])) {
							words[2] = words[2].replace("&", "");
							words[2] = words[2].replace(",", "");
							System.out.print(Integer.toString(mdtptr) + "\t" + words[1] + "\t");
							bw.write(Integer.toString(mdtptr) + "\t" + words[1] + "\t");
							String sepWords[] = words[2].split("\\s");
							if(pntab.containsKey(sepWords[0])) {
								System.out.print("P" + ", " + Integer.toString(pntab.get(sepWords[0])) + "\t");
								bw.write("P" + "\t" + Integer.toString(pntab.get(sepWords[0])) + "\t");
								if(sepWords.length > 1) {
									if(pntab.containsKey(sepWords[1])) {
										System.out.print("P" + ", " + Integer.toString(pntab.get(sepWords[1])) + "\t");
										bw.write("P" + "\t" + Integer.toString(pntab.get(sepWords[1])) + "\t");
									}
									else {
										System.out.print(sepWords[1] + "\t");
										bw.write(sepWords[1] + "\t");
									}
								}
							}
							mdtptr++;
							System.out.println();
							bw.write("\n");
						}
					}
				}
				else {
					bout.write(readInput + "\n");
				}
			}
			
			
			System.out.println("\n------------------------\tKPDTAB\t------------------------\n");

			System.out.println("#");
			
			Set<String> keys = kpdtab.keySet();
	        for(String key: keys){
	            System.out.println(key + "\t" + kpdtab.get(key));
	        }
	        
			bw.close();
			bw2.close();
			bout.close();
			
		}catch (Exception E) {
			E.printStackTrace();
		}

	}

}
