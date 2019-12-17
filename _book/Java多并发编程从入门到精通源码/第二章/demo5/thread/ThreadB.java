package demo.thread;

public class ThreadB extends Thread{
	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println("线程B第" + i + "次执行！");
			try {
				Thread.sleep(7);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
