package demo.thread;

public class ThreadMain {
	public static void main(String[] args) {
		Thread tA = new ThreadA();
		Thread tB = new ThreadB();
		tA.setDaemon(true); // 设置为守护线程,注意一定要在开始之前调用
		
		tB.start();
		tA.start();
		Thread mainThread = Thread.currentThread();
		System.out.println("线程A是不是守护线程"+tA.isDaemon());
		System.out.println("线程b是不是守护线程"+tB.isDaemon());
		System.out.println("线程main是不是守护线程"+mainThread.isDaemon());
	}
}
