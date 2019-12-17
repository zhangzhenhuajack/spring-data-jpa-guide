package demo.thread;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsDemo {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
//		ExecutorService executor = Executors.newSingleThreadExecutor();
		Thread.sleep(10000L);//方便监控工具能捕获到
//		ExecutorService executor = Executors.newCachedThreadPool();
		ExecutorService executor = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 20; i++) {
			final int no = i;
			Runnable runnable = new Runnable() {
				public void run() {
					try {
						System.out.println("into" + no);
						Thread.sleep(1000L);
						System.out.println("end" + no);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			executor.execute(runnable);
		}
		executor.shutdown();
		System.out.println("Thread Main End!");
	}
}
