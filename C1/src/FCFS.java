import java.util.*;


public class FCFS {

	public static void main(String[] args) {
		Scanner Sc = new Scanner(System.in);
		System.out.print("Enter the number of processes : \t");
		int n = Sc.nextInt();
		process p[] = new process[n];
		for(int i=0; i<n; i++) {
			System.out.println("For process " + (i+1));
			System.out.print("Enter AT :\t");
			int at = Sc.nextInt();
			System.out.print("Enter BT :\t");
			int bt = Sc.nextInt();
			p[i] = new process((i+1), at, bt);
			p[i].initBT = bt;
		}
		
		Arrays.sort(p, new SortByArrival());
		
		int sum=0;
		
		for(int i=0;i<n;i++)
		{
			p[i].wt = sum - p[i].at;
			p[i].tat = p[i].wt + p[i].bt;
			sum += p[i].bt;
			p[i].ct = sum;
		}
		
		//dispay
		System.out.println("\nP.No.\tAT\tBT\tWT\tTAT\tCT");
		for (int i=0; i<n; i++) {
			p[i].display();
		}
		
		double avgwt=0;
		double avgtat=0;
		
		for(int i=0; i<n; i++) {
			avgwt += p[i].wt;
			avgtat += p[i].tat;
		}
		
		avgwt /= n;
		avgtat /= n;
		
		System.out.println("\nAverage waiting time : " + avgwt);
		System.out.println("Average turn aroung time : " + avgtat);
		
		Sc.close();
		
		System.out.println("\nGantt Chart : ");
		
		for(int i=0; i<((n*16) + 8); i++) {
			System.out.print("-");
		}
		
		System.out.println();
		
		System.out.print("\t");
		
		for(int i=0; i<n; i++) {
			System.out.print("P" + p[i].processNo + "\t|\t");
		}
		
		System.out.println();
		
		for(int i=0; i<((n*16) + 8); i++) {
			System.out.print("-");
		}
		
		System.out.println();
		
		for(int i=0; i<n; i++) {
			System.out.print(p[i].at + p[i].wt + "\t\t");
		}
		System.out.print(p[n-1].ct);
		
		System.out.println();
		
		for(int i=0; i<((n*16) + 8); i++) {
			System.out.print("-");
		}
		
	}

}
