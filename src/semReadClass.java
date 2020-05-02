import java.util.LinkedList;
import java.util.Queue;

public class semReadClass {
	private static boolean semRead = true;
	private static Queue<Process> waitQueue = new LinkedList<>();
	
	public static void semReadWait(Process p) {
		if(!semReadClass.getSemaphore()) {
			semReadClass.addToWaitQueue(p);
		}
		semRead=false;
	}
	public static void semReadPost() {
		semRead=true;
		putToReadyQueue();
	}
	
	public static boolean getSemaphore() {
		return semRead;
	}
	
	public static void addToWaitQueue(Process p) {
//		System.err.println("Process " + p.processID + "inside read semaphore");
		p.setProcessState(p, ProcessState.Waiting);
		waitQueue.add(p);
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
