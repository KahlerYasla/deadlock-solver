package intro1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class sync {
	public static void main(String [] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		ReadWriteLock RW = new ReadWriteLock();
		
		
		executorService.execute(new Writer(1,RW));
		executorService.execute(new Writer(2,RW));
		executorService.execute(new Writer(3,RW));
		executorService.execute(new Writer(4,RW));
		
		executorService.execute(new Reader(1,RW));
		executorService.execute(new Reader(2,RW));
		executorService.execute(new Reader(3,RW));
		executorService.execute(new Reader(4,RW));	
	}

}

class ReadWriteLock{
	
	private int readerCount=0;
	private Semaphore mutex=new Semaphore(1);
	private Semaphore S=new Semaphore(1);
	
	
	
	public void readLock(int rNum) {
		
		try {mutex.acquire();}
		catch(InterruptedException e) {}
		
		readerCount++;
		
		if(readerCount==1){
		{try{S.acquire();}catch(InterruptedException e) {}}}
		
		System.out.println("reader "+rNum+" is reading. Reader count="+readerCount);
		mutex.release();
		
	}
	
	
	
	public void writeLock(int wNum) {
		
		try {S.acquire();}
		catch(InterruptedException e) {}
		
		System.out.println("writer "+wNum+" is now writing");
		
	}
	
	
	
	public void readUnLock(int rNum) {
		
		try {mutex.acquire();}
		catch(InterruptedException e) {}
		readerCount--;
		if(readerCount==0) {S.release();}
		
		System.out.println("reader "+rNum+" is done reading. Reader count="+readerCount);
		mutex.release();
		
	}
	
	
	
	public void writeUnLock(int wNum) {
		
		System.out.println("writer "+wNum+" is done now writing");
		S.release();
		
	}

}

class SleepUtilities{
	 
	 public static void nap() {nap(NAP_TIME);}
	 public static void nap(int duration) {
		 int sleeptime=(int)(NAP_TIME*Math.random());
		 try {Thread.sleep(sleeptime*1000);}
		 catch(InterruptedException e) {}
		 }
	
	 private static int NAP_TIME=5;
}
  
  



class Writer implements Runnable
{
   private ReadWriteLock RW_lock;
   private int writerNum;
   

    public Writer(int writerNum,ReadWriteLock rw) {
    	RW_lock = rw;
    	this.writerNum=writerNum;
   }

    public void run() {
      while (true){
    	  SleepUtilities.nap();
    	  System.out.println("writer "+writerNum+" wants to write");
    	  
    	  RW_lock.writeLock(writerNum);
    	  
    	  SleepUtilities.nap();
    	
    	  RW_lock.writeUnLock(writerNum);
       
      }
   }


}



class Reader implements Runnable
{
   private ReadWriteLock RW_lock;
   private int readerNum;
   

   public Reader(int readerNum,ReadWriteLock rw) {
    	RW_lock = rw;
    	this.readerNum=readerNum;
   }
    public void run() {
      while (true){ 
    	  SleepUtilities.nap();
    	  System.out.println("reader "+readerNum+" wants to read");
    	  
    	  RW_lock.readLock(readerNum);
    	  
    	  SleepUtilities.nap();
    	  
    	  RW_lock.readUnLock(readerNum);
       
      }
   }


    
}