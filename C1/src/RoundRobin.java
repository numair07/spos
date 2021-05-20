import java.util.*;

public class RoundRobin {

	public static void main(String[] args) {
		Scanner Sc = new Scanner(System.in);
		System.out.print("Enter The Number of Processes : \t");
		int num = Sc.nextInt();
		
		process p[] = new process[num];
		for(int i=0; i<num; i++) {
			System.out.println("For process " + (i+1));
			int at = 0;
			System.out.print("Enter BT :\t");
			int bt = Sc.nextInt();
			p[i] = new process((i+1), at, bt);
			p[i].initBT = bt;
		}
		
		Arrays.sort(p, new SortByArrival());
		
		System.out.print("Enter The Round Time : \t");
		int roundTime = Sc.nextInt();
		
		int k=0;
		int end = 0;
		System.out.println("\nP.No.\tAT\tBT\tWT\tTAT\tCT");
		
		while(true) {
			end = 0;
			for(int i=0; i<num; i++) {
				if(p[i].bt>0 && p[i].at <= k) {
					end = 1;
					if(p[i].bt > roundTime) {
						p[i].bt -= roundTime;
						k += roundTime;
					}
					else {
						k += p[i].bt;
						p[i].bt = 0;
						p[i].ct = k;
						p[i].wt = p[i].ct - p[i].initBT;
						p[i].tat = p[i].initBT + p[i].wt;
					}
				}
			}
			if(end == 0) {
				break;
			}
		}
		for(int i=0; i<num; i++) {
			p[i].display();
		}
		Sc.close();
	}

}
