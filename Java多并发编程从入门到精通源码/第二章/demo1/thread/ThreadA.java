package demo.thread;

/**
 * 第一种创建线程的方式直接 extends Thread 覆盖run()方法即可；
 */
public class ThreadA extends Thread {
	
	@Override
	public void run() {
		super.run();
		
		try {
			// TODO Auto-generated method stub
			//模拟做事情执行了500毫秒；
			Thread.sleep(500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("这是线程 A");
	}
}
