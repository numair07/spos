import java.util.*;


public class SJF {

	public static int checkAllStatuses(process p[], int n) {
		for(int i=0; i<n; i++) {
			if(p[i].status==0) {
				return 0;
			}
		}
		return 1;
	}
	
	public static void main(String[] args) {
		
		Vector <process> v;
		
		Scanner Sc = new Scanner(System.in);
		System.out.print("Enter the number of processes : \t");
		int n = Sc.nextInt();
		process p[] = new process[n];
		for(int i=0; i<n; i++) {
			System.out.println("For process " + (i+1) + " enter arrival time (AT) & burst time (BT)");
			int at = Sc.nextInt();
			int bt = Sc.nextInt();
			p[i] = new process((i+1), at, bt);
			p[i].initBT = bt;
		}
		
		System.out.println();
		
		Arrays.sort(p, new SortByArrival());
		
		int i=1;
		
		while(true) {
			v = new Vector<process>();
			for (int j=0; j<n; j++) {
				if(p[j].at<i && p[j].bt!=0) {
					if(p[j].at == i) {
					}
					v.add(p[j]);
				}
			}
			int minBT = 0 ;
			process minprocess = null;
			process temp = null;
			for(int k=0; k<v.size(); k++) {
				temp = v.get(k);
				if(minBT == 0 || (minBT > temp.bt && temp.bt!=0)) {
					minBT = temp.bt;
					minprocess = temp;
				}
			}
			if(minprocess != null) {
				minprocess.bt--;
				System.out.print("P"+ minprocess.processNo + " - ");
				if(minprocess.bt == 0) {
					minprocess.status = 1;
					minprocess.wt = i -  minprocess.initBT - minprocess.at;
					minprocess.tat = minprocess.wt + minprocess.initBT;
					minprocess.ct = i;
				}
			}
			if(checkAllStatuses(p, n) == 1) {
				break;
			}
			i++;
		}
		
		System.out.println("\n\nP.No.\tAT\tBT\tWT\tTAT\tCT");
		for (int i1=0; i1<n; i1++) {
			p[i1].display3();
		}
		
		double avgWT=0;
		double avgTAT=0;
		
		for(int j=0; j<n; j++) {
			avgWT += p[j].wt;
			avgTAT += p[j].tat;
		}
		
		avgWT /= n;
		avgTAT /= n;
		
		System.out.println("\nAverage waiting time : " + avgWT);
		System.out.println("Average turn aroung time : " + avgTAT);
		
		Sc.close();
		
	}

}
