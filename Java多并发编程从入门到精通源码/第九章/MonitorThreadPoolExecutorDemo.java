package demo.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MonitorThreadPoolExecutorDemo {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Thread.sleep(500L);// 方便测试
		ExecutorService executor = new MonitorThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
		for (int i = 0; i < 3; i++) {
			Runnable runnable = new Runnable() {
				public void run() {
					try {
						Thread.sleep(100L);
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
class MonitorThreadPoolExecutor extends ThreadPoolExecutor {
	public MonitorThreadPoolExecutor(int arg0, int arg1, long arg2, TimeUnit arg3, BlockingQueue<Runnable> arg4) {
		super(arg0, arg1, arg2, arg3, arg4);
	}
	protected void beforeExecute(Thread paramThread, Runnable paramRunnable) {
		System.out.println("work_task before:" + paramThread.getName());
	}
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		System.out.println("work_task after worker thread is :" + r);
		/*if (t == null && r instanceof Future<?>) {
			try {
				Object result = ((Future<?>) r).get();
				System.out.println(result);
			} catch (CancellationException ce) {
				t = ce;
			} catch (ExecutionException ee) {
				t = ee.getCause();
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt(); // ignore/reset
			}
		}
		if (t != null)
			System.out.println(t);*/
	}
	protected void terminated() {
		System.out.println("terminated getCorePoolSize:" + this.getCorePoolSize() + "；getPoolSize:" + this.getPoolSize() + "；getTaskCount:" + this.getTaskCount() + "；getCompletedTaskCount:"
				+ this.getCompletedTaskCount() + "；getLargestPoolSize:" + this.getLargestPoolSize() + "；getActiveCount:" + this.getActiveCount());
		System.out.println("ThreadPoolExecutor terminated:");
	}
}
