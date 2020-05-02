import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import jdk.nashorn.api.tree.ForInLoopTree;


public class OperatingSystem {
	

	public static Queue<Process> readyQueue;

	public static String readFile(String name,Process p) {
		
		semReadClass.semReadWait(p);
		String Data="";
		File file = new File(name);
		try {
		Scanner scan = new Scanner(file);
		while (scan.hasNextLine())
		{
			Data+= scan.nextLine()+"\n";
		}
		scan.close();
	 } catch (FileNotFoundException e) {
		System.out.println(e.getMessage());
		semReadClass.semReadPost();
		
	 }
	 	semReadClass.semReadPost();
	 	
		return Data;
	}
	
	public static void writefile(String name, String data,Process p) {
		
		semWriteClass.semWriteWait(p);
		try
		{
			BufferedWriter BW = new BufferedWriter(new FileWriter(name));
			BW.write(data);
			BW.close();
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			semWriteClass.semWritePost();
			
		}
		semWriteClass.semWritePost();
	}
	
	
	public static void printText(String text,Process p) {
		
		semPrintClass.semPrintWait(p);
		
		System.out.println(text);
		
		
		semPrintClass.semPrintPost();
		
	}
	
	
	public static String TakeInput(Process p) {
		
		semTakeClass.semTakeWait(p);
		Scanner in= new Scanner(System.in);
		String data = in.nextLine();
		semTakeClass.semTakePost();
		return data;
	}
	
	private static void createProcess(int processID){
		Process p = new Process(processID);
//		ProcessTable.add(p);
		Process.setProcessState(p,ProcessState.Running);
		p.start();
	}
	
	public static void addProcess(int  processID) {
		Process p = new Process(processID);
		Process.setProcessState(p, ProcessState.Ready);
		readyQueue.add(p);
	}
	
	private static void runProcesses() throws InterruptedException {
		while(!readyQueue.isEmpty()) {
			Process p = readyQueue.poll();
//			System.out.println(p.processID+" " + p.status);
//			if(p.processID == 3) {
//				System.err.println("Process 3 : " + semPrintClass.getSemaphore());
//			}
			p.status = ProcessState.Running;

			if(p.start) {
				p.start();
//				System.err.println("inside if : " + p.status);
			}
			else {
				p.resume();
			}
//			System.err.println("before loop : Process " + p.processID + " " + p.status);

			while(p.status == ProcessState.Running) {
//				Thread.sleep(100);
				
//				System.err.println("inside loop");
			}
//			System.err.println("after loop : Process " + p.processID + " " + p.status);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
   		readyQueue = new LinkedList<>();
   		
   		addProcess(3);
   		addProcess(2);
   		addProcess(4);
   		addProcess(1);
   		addProcess(5);
   		
//   		createProcess(3);
//   		createProcess(4);

   		
   		runProcesses();
	}
}
