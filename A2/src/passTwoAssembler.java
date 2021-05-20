import java.util.*;
import java.io.*;

public class passTwoAssembler {

	public static void main(String[] args) {
		
		BufferedReader bic;
		BufferedReader bst;
		BufferedReader blt;
		
		FileReader fic;
		FileReader fst;
		FileReader flt;
		
		BufferedWriter bw;
		FileWriter fw;
		
		try {
			String inputIntermediateCode = "C:\\Users\\Numair Shaikh\\Desktop\\SEM6\\SPOSL\\A2\\IC.txt";
			String inputSymbolTable = "C:\\Users\\Numair Shaikh\\Desktop\\SEM6\\SPOSL\\A2\\SYMTAB.txt";
			String inputLiteralTable = "C:\\Users\\Numair Shaikh\\Desktop\\SEM6\\SPOSL\\A2\\LITTAB.txt";
			
			fic = new FileReader(inputIntermediateCode);
			fst = new FileReader(inputSymbolTable);
			flt = new FileReader(inputLiteralTable);
			
			bic = new BufferedReader(fic);
			bst = new BufferedReader(fst);
			blt = new BufferedReader(flt);
			
			String outputMachineCode = "C:\\Users\\Numair Shaikh\\Desktop\\SEM6\\SPOSL\\A2\\MacCode.txt";
			
			fw = new FileWriter(outputMachineCode);
			bw = new BufferedWriter(fw);
			
			Hashtable<Integer, String> symtab = new Hashtable<Integer, String>();
			Hashtable<Integer, String> littab = new Hashtable<Integer, String>();

			String symtabString = "";
			int symtabIndex = 0;
			while((symtabString = bst.readLine()) != null) {
				String words[] = symtabString.split("\t");
				symtab.put(symtabIndex, words[1]);
				symtabIndex++;
			}
			
			String littabString = "";
			int littabIndex = 0;
			while((littabString = blt.readLine()) != null) {
				String words[] = littabString.split("\t");
				littab.put(littabIndex, words[1]);
				littabIndex++;
			}
			
			int loc_ctr = 0;
			
			System.out.println("Machine Code : ");
			
			String currentLine = "";
			
			int isWritten = 0;
			
			while((currentLine = bic.readLine()) != null) {
				
				isWritten = 0;
				String words[] = currentLine.split("\t");
				
				if(words[1].equals("IS")) {
					loc_ctr++;
					bw.write(Integer.toString(loc_ctr) + "\t\t");
					System.out.print(Integer.toString(loc_ctr) + "\t\t");
					
					System.out.print(words[2] + "\t");
					bw.write(words[2] + "\t");
					
					if(words[3].equals("RG")) {
						System.out.print(words[4] + "\t");
						bw.write(words[4] + "\t");
					}
					else if(words[3].equals("S")) {
						int index = Integer.parseInt(words[4]);
						String ptr = symtab.get(index);
						System.out.print("00" + "\t" + ptr);
						bw.write("00" + "\t" + ptr);
					}
					else if(words[3].equals("L")) {
						int index = Integer.parseInt(words[4]);
						String ptr = littab.get(index);
						System.out.print("00" + "\t" + ptr);
						bw.write("00" + "\t" + ptr);
					}
					
					if(words.length > 5) {
						if(words[5].equals("S")) {
							int index = Integer.parseInt(words[6]);
							String ptr = symtab.get(index);
							System.out.print(ptr);
							bw.write(ptr);
						}
						else if(words[5].equals("L")) {
							int index = Integer.parseInt(words[6]);
							String ptr = littab.get(index);
							System.out.print(ptr);
							bw.write(ptr);
						}
					}
					
					isWritten = 1;
				}
				else if(words[0].equals("AD") || words[1].equals("AD")) {
					
					if(words[1].equals("01")) {
						//START
						loc_ctr = Integer.parseInt(words[3]);
					}
					else {
						loc_ctr+=1;
						bw.write(Integer.toString(loc_ctr) + "\t\t");
						System.out.print(Integer.toString(loc_ctr) + "\t\t");
						
						System.out.print("00" + "\t" + "00" + "\t");
						bw.write("00" + "\t" + "00" + "\t");
						if(words[2].equals("05")) {
							System.out.print("00" + words[6]);
							bw.write("00" + words[6]);
						}
						isWritten = 1;
					}
					
				}
				else if(words[1].equals("S")) {
					if(words[3].equals("IS")) {
						loc_ctr++;
						bw.write(Integer.toString(loc_ctr) + "\t\t");
						System.out.print(Integer.toString(loc_ctr) + "\t\t");
						
						System.out.print(words[4] + "\t");
						bw.write(words[4] + "\t");
						if(words[5].equals("RG")) {
							System.out.print(words[6] + "\t");
							bw.write(words[6] + "\t");
						}
						
						if(words[7].equals("S")) {
							int index = Integer.parseInt(words[8]);
							String ptr = symtab.get(index);
							System.out.print(ptr);
							bw.write(ptr);
						}
						else if(words[7].equals("L")) {
							int index = Integer.parseInt(words[8]);
							String ptr = littab.get(index);
							System.out.print(ptr);
							bw.write(ptr);
						}
						
						isWritten = 1;
					}
					
				}
				
				System.out.println();
				if(isWritten == 1) {
					bw.write("\n");
				}
			}
			
			bw.close();
			
		}catch (Exception E) {
			E.printStackTrace();
		}

	}

}
