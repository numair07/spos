import java.util.*;
import java.io.*;

public class passOneAssembler {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		
		BufferedReader br = null;
		FileReader fr = null;

		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			
			String inputfilename = "C:\\Users\\Numair Shaikh\\Desktop\\SEM6\\SPOSL\\A1\\INPUT.asm";
			fr = new FileReader(inputfilename);
			br = new BufferedReader(fr);

			String OUTPUTFILENAME = "C:\\Users\\Numair Shaikh\\Desktop\\SEM6\\SPOSL\\A1\\IC.txt";
			fw = new FileWriter(OUTPUTFILENAME);
			bw = new BufferedWriter(fw);
			
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

			Hashtable<String, String> dl = new Hashtable<String, String>();
			dl.put("DC", "01");
			dl.put("DS", "02");

			Hashtable<String, String> ad = new Hashtable<String, String>();

			ad.put("START", "01");
			ad.put("END", "02");
			ad.put("ORIGIN", "03");
			ad.put("EQU", "04");
			ad.put("LTORG", "05");

			Hashtable<String, String> symtab = new Hashtable<String, String>();
			Hashtable<String, String> littab = new Hashtable<String, String>();
			
			String sCurrentLine;
			int locptr = 0;
			int litptr = 0;
			int symptr = 0;
			int pooltabptr = 0;

			sCurrentLine = br.readLine();
			
			ArrayList<Integer> pooltab=new ArrayList<Integer>();

			String s1 = sCurrentLine.split(" ")[1];
			if (s1.equals("START")) {
				bw.write("AD \t 01 \t");
				String s2 = sCurrentLine.split(" ")[2];
				bw.write("C \t" + s2 + "\n");
				locptr = Integer.parseInt(s2);
			}

			while ((sCurrentLine = br.readLine()) != null) {
				int lc_check = 0;
				String type = null;

				int flag2 = 0;
				int flag3 = 0;
				
				String s = sCurrentLine.split(" |\\,")[0];
				
//				for (Map.Entry m : symtab.entrySet()) {
//					if (s.equals(m.getKey())) {
//						m.setValue(locptr);
//						flag2 = 1;
//					}
//				}
				
				String checkS = sCurrentLine.split(" |\\,")[1];

				
				if (checkS.equals("ORIGIN")) {
					String s2 = sCurrentLine.split(" |\\,")[2];
					if(symtab.containsKey(s2.split("\\+")[0])) {
						
						String s2Sym = s2.split("\\+")[0];
						String symPtr = symtab.get(s2Sym);
						String s2ptr = s2.split("\\+")[1];
						locptr = Integer.parseInt(symPtr) + Integer.parseInt(s2ptr);
						flag3 = 1;
						lc_check = 1;
					}
				}
				
				if(symtab.containsKey(s) && flag3==0) {
					String isPtrPresent = symtab.get(s);
					if(isPtrPresent.equals("")) {
						symtab.put(s, String.valueOf(locptr));
						flag2 = 1;
					}
					else {
						System.out.println("Error, symbol already present in SYMTAB");
					}
				}
				
				if (s.length() != 0 && flag2 == 0) {
					symtab.put(s, String.valueOf(locptr));
					symptr++;
				}

				int isOpcode = 0;
				
				s = sCurrentLine.split(" |\\,")[1];

//				for (Map.Entry m : is.entrySet()) {
//					if (s.equals(m.getKey())) {
//						bw.write("IS\t" + m.getValue() + "\t");
//						type = "is";
//						isOpcode = 1;
//					}
//				}
//				
				if(is.containsKey(s)) {
					bw.write(locptr + "\t" + "(IS\t" + is.get(s) + ")\t");
					type = "is";
					isOpcode = 1;
				}
				
//				for (Map.Entry m : ad.entrySet()) {
//					if (s.equals(m.getKey())) {
//						bw.write("AD\t" + m.getValue() + "\t");
//						type = "ad";
//						isOpcode = 1;
//					}
//				}
//				
				if(ad.containsKey(s)) {
					bw.write(locptr + "\t" + "(AD\t" + ad.get(s) + ")\t");
					type = "ad";
					isOpcode = 1;
				}
				
				
//				for (Map.Entry m : dl.entrySet()) {
//					if (s.equals(m.getKey())) {
//						bw.write("DL\t" + m.getValue() + "\t");
//						type = "dl";
//						isOpcode = 1;
//					}
//				}
//				
				
				if(dl.containsKey(s)) {
					bw.write(locptr + "\t" + "(DL\t" + dl.get(s) + ")\t");
					type = "dl";
					isOpcode = 1;
				}
				
				
				
				if (s.equals("LTORG")) {
					pooltab.add(pooltabptr);
					for (Map.Entry m : littab.entrySet()) {
						//if (m.getValue() == "") {
							m.setValue(locptr);
							locptr++;
							pooltabptr++;
							lc_check = 1;
							isOpcode = 1;
						//}
					}
				}
				
				if (s.equals("END")) {
					pooltab.add(pooltabptr);
					for (Map.Entry m : littab.entrySet()) {
						if (m.getValue() == "") {
							m.setValue(locptr);
							locptr++;
							lc_check = 1;
						}
					}
				}
				
				
				if(s.equals("EQU")){
					if(symtab.containsKey("EQU")) {
						System.out.println("Already Present");
					}
					else
						symtab.put("EQU", String.valueOf(locptr));
				}
				
				
				if (sCurrentLine.split(" |\\,").length > 2) {
					s = sCurrentLine.split(" |\\,")[2];
					String checkS2 = sCurrentLine.split(" |\\,")[1];

					if (s.equals("AREG")) {
						bw.write("RG\t01\t");
						isOpcode = 1;
					} else if (s.equals("BREG")) {
						bw.write("RG\t02\t");
						isOpcode = 1;
					} else if (s.equals("CREG")) {
						bw.write("RG\t03\t");
						isOpcode = 1;
					} else if (s.equals("DREG")) {
						bw.write("RG\t04\t");
						isOpcode = 1;
					} else if (type == "dl") {
						bw.write("C\t" + s + "\t");
					} else {
						if(checkS2.equals("ORIGIN")) {
							
						}
						else {
							symtab.put(s, "");
						}
					}
				}
				
				
				if (sCurrentLine.split(" |\\,").length > 3) {		
					
					s = sCurrentLine.split(" |\\,")[3];			

					if (s.contains("=")) {
						littab.put(s, "");
						bw.write("L\t" + litptr + "\t");
						isOpcode = 1;
						litptr++;
					} else {
						symtab.put(s, "");
						bw.write("S\t" + symptr + "\t");		
						symptr++;
						
					}
				}

				bw.write("\n");

				if (lc_check == 0)
					locptr++;
			}
			
			System.out.println();

			System.out.println("SYMBOL TABLE : ");
			
			String f1 = "C:\\Users\\Numair Shaikh\\Desktop\\SEM6\\SPOSL\\A1\\SYMTAB.txt";
			FileWriter fw1 = new FileWriter(f1);
			BufferedWriter bw1 = new BufferedWriter(fw1);
			for (Map.Entry m : symtab.entrySet()) {
				bw1.write(m.getKey() + "\t" + m.getValue()+"\n");				
				System.out.println(m.getKey() + " " + m.getValue());
			}

			System.out.println("\nLITERAL TABLE : ");
			String f2 = "C:\\Users\\Numair Shaikh\\Desktop\\SEM6\\SPOSL\\A1\\LITTAB.txt";
			FileWriter fw2 = new FileWriter(f2);
			BufferedWriter bw2 = new BufferedWriter(fw2);
			for (Map.Entry m : littab.entrySet()) {
				bw2.write(m.getKey() + "\t" + m.getValue()+"\n");
				System.out.println(m.getKey() + " " + m.getValue());
			}
			
			System.out.println("\nPOOLTAB : ");
			
			String f3 = "C:\\Users\\Numair Shaikh\\Desktop\\SEM6\\SPOSL\\A1\\POOLTAB.txt";
			FileWriter fw3 = new FileWriter(f3);
			BufferedWriter bw3 = new BufferedWriter(fw3);
			for (Integer item : pooltab) {  
				bw3.write(item+"\n");
			    System.out.println(item);
			}

			bw.close();
			bw1.close();
			bw2.close();
			bw3.close();


		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
