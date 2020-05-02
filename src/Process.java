//import java.util.concurrent.Semaphore;


public class Process extends Thread {
	
	
	public volatile boolean start = true;
	public int processID;
    volatile ProcessState status=ProcessState.New;	

	
	public Process(int m) {
		processID = m;
	}
	@Override
	public void run() {
		this.start= false;
//		System.err.println("inside run : " + processID + " " + status);
		switch(processID)
		{
		case 1:process1();break;
		case 2:process2();break;
		case 3:process3();break;
		case 4:process4();break;
		case 5:process5();break;
		case -1 : processSpecial1();
		}

	}
	
	private void processSpecial1() {
		OperatingSystem.TakeInput(this);
	}
	
	private void process1() {
		
		OperatingSystem.printText("Enter File Name: ",this);
		OperatingSystem.printText(OperatingSystem.readFile(OperatingSystem.TakeInput(this),this),this);
		
		setProcessState(this,ProcessState.Terminated);
	}
	
	private void process2() {
		
		OperatingSystem.printText("Enter File Name: ",this);
		String filename= OperatingSystem.TakeInput(this);
		OperatingSystem.printText("Enter Data: ",this);
		String data= OperatingSystem.TakeInput(this);
		OperatingSystem.writefile(filename,data,this);
		setProcessState(this,ProcessState.Terminated);
		
	}
	private void process3() {
		int x=0;
		while (x<301)
		{ 
			OperatingSystem.printText(x+"\n",this);
			x++;
		}
		setProcessState(this,ProcessState.Terminated);
		}
	
	private void process4() {
	
		int x=500;
		while (x<1001)
		{
			OperatingSystem.printText(x+"\n",this);
			x++;
		}	
		setProcessState(this,ProcessState.Terminated);
		}
	private void process5() {
		
		OperatingSystem.printText("Enter LowerBound: ",this);
		String lower= OperatingSystem.TakeInput(this);
		OperatingSystem.printText("Enter UpperBound: ",this);
		String upper= OperatingSystem.TakeInput(this);
		int lowernbr=Integer.parseInt(lower);
		int uppernbr=Integer.parseInt(upper);
		String data="";
		
		while (lowernbr<=uppernbr)
		{
			data+=lowernbr++ +"\n";
		}	
		OperatingSystem.writefile("P5.txt", data,this);
		setProcessState(this,ProcessState.Terminated);
	}
    
	public static void setProcessState(Process p, ProcessState s) {
		 p.status=s;
		 if (s == ProcessState.Terminated)
		 {
//			 OperatingSystem.ProcessTable.remove(OperatingSystem.ProcessTable.indexOf(p));
		 }
	}
	 
	public static ProcessState getProcessState(Process p) {
		 return p.status;
	}
	 @Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Process	: " + processID + ". Status : " + status + ". First : " + start;
	}
}
