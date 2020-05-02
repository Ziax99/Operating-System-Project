import java.util.LinkedList;
import java.util.Queue;

public class semPrintClass {
	private static boolean semPrint = true;
	private static Queue<Process> waitQueue = new LinkedList<>();
	
	public static void semPrintWait(Process p) {
//		System.err.println(semPrint);
		if(!semPrintClass.getSemaphore()) {
			semPrintClass.addToWaitQueue(p);
		}
		semPrint = false;
	}
	public static void semPrintPost() {
		semPrint = true;
		putToReadyQueue();
	}
	
	public static boolean getSemaphore() {
		return semPrint;
	}
	
	public static void addToWaitQueue(Process p) {
//		System.err.println("Process " + p.processID + " inside wait print semaphore");
		p.setProcessState(p, ProcessState.Waiting);
		waitQueue.add(p);
//		System.err.println(waitQueue.size());
		p.suspend();
	}
	
	public static void putToReadyQueue() {
		if(!waitQueue.isEmpty()) {
			Process p = waitQueue.poll();
			Process.setProcessState(p, ProcessState.Ready);
			OperatingSystem.readyQueue.add(p);
//			System.err.println("Process " + p.processID + " released");
//			p.resume();
		}
	}
	
}
