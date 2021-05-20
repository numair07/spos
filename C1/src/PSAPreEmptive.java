import java.util.*;

public class PSAPreEmptive {

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
			System.out.println("Enter the Priority");
			int pri = Sc.nextInt();
			p[i] = new process((i+1), at, bt, pri);
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
			int maxPri = 0;
			process maxprocess = null;
			process temp = null;
			for(int k=0; k<v.size(); k++) {
				temp = v.get(k);
				if(maxPri == 0 || (maxPri < temp.pri && temp.bt!=0)) {
					maxPri = temp.pri;
					maxprocess = temp;
				}
			}
			if(maxprocess != null) {
				maxprocess.bt--;
				System.out.print("P"+ maxprocess.processNo + " - ");
				if(maxprocess.bt == 0) {
					maxprocess.status = 1;
					maxprocess.wt = i -  maxprocess.initBT - maxprocess.at;
					maxprocess.tat = maxprocess.wt + maxprocess.initBT;
					maxprocess.ct = i;
				}
			}
			if(checkAllStatuses(p, n) == 1) {
				break;
			}
			i++;
		}
		
		System.out.println("\n\nP.No.\tPRI\tAT\tBT\tWT\tTAT\tCT");
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
		
		System.out.println("\nAverage waiting time : " + String.format("%.2f", avgWT));
		System.out.println("Average turn aroung time : " + String.format("%.2f", avgTAT));
		
		Sc.close();
		
	}

}
