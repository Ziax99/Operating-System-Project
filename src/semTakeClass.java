import java.util.LinkedList;
import java.util.Queue;

public class semTakeClass {
	private static boolean semTake = true;
	private static Queue<Process> waitQueue = new LinkedList<>();
	
	public static void semTakeWait(Process p) {
//		System.err.println(semTake);
		if(!semTakeClass.getSemaphore()) {
			semTakeClass.addToWaitQueue(p);
		}
		semTake = false;
	}
	public static void semTakePost() {
		semTake = true;
		putToReadyQueue();
	}
	
	public static boolean getSemaphore() {
		return semTake;
	}
	
	public static void addToWaitQueue(Process p) {
//		System.err.println("Process " + p.processID + "inside take semaphore");
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
