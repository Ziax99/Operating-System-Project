import java.util.LinkedList;
import java.util.Queue;

public class semWriteClass {
	private static boolean semWrite = true;
	private static Queue<Process> waitQueue = new LinkedList<>();
	
	public static void semWriteWait(Process p) {
		if(!semWriteClass.getSemaphore()) {
			semWriteClass.addToWaitQueue(p);
		}
		semWrite = false;
	}
	public static void semWritePost() {
		semWrite = true;
		putToReadyQueue();
	}
	
	public static boolean getSemaphore() {
		return semWrite;
	}
	
	public static void addToWaitQueue(Process p) {
//		System.err.println("Process " + p.processID + "inside write semaphore");
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
