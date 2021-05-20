import java.util.Comparator;

public class process {
	public int at;
	public int bt;
	public int tat;
	public int ct;
	public int wt;
	public int processNo;
	public int initBT;
	public int status;
	public int pri;
	
	public process(int processNo, int at, int bt) {
		this.processNo = processNo;
		this.at = at;
		this.bt = bt;
	}
	
	public process(int processNo, int at, int bt, int pri) {
		this.processNo = processNo;
		this.at = at;
		this.bt = bt;
		this.pri = pri;
	}
	
	public void display() {
		System.out.println(processNo + "\t" + at + "\t" + bt + "\t" + wt + "\t" + tat + "\t" + ct);
	}
	
	public void display2() {
		System.out.println(processNo + "\t" + pri + "\t" + bt + "\t" + wt + "\t" + tat + "\t" + ct);
	}
	
	public void display3() {
		System.out.println(processNo + "\t" + pri + "\t" + at + "\t" + initBT + "\t" + wt + "\t" + tat + "\t" + ct);
	}
	
}

class SortByPriority implements Comparator<process> {

	@Override
	public int compare(process p1, process p2) {
		
		return p1.pri-p2.pri;
	}
	
}

class SortByArrival implements Comparator<process> {

	@Override
	public int compare(process p1, process p2) {
		
		return p1.at-p2.at;
	}
	
}