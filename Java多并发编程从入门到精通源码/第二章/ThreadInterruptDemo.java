package demo;

public class ThreadInterruptDemo implements Runnable {
	public static void main(String[] args) throws Exception {
		Thread thread = new Thread(new ThreadInterruptDemo(), "InterruptDemo Thread");
		System.out.println("Starting thread...");
		thread.start();
		Thread.sleep(3000);
		System.out.println("Interrupting thread...");
		thread.interrupt();
		System.out.println("线程是否中断：" + thread.isInterrupted());
		Thread.sleep(3000);
		System.out.println("Stopping application...");
	}

	@Override
	public void run() {
		boolean stop = false;
		while (!stop) {
			System.out.println("My Thread is running...");

			long time = System.currentTimeMillis();
			while ((System.currentTimeMillis() - time < 1000)) {
				// 让该循环持续一段时间，使上面的话打印次数少点
			}
			// if (Thread.currentThread().isInterrupted()) {
			// // 需要线程本身去处理一下它的终止状态
			// break;
			// }
			try {
				Thread.sleep(3L);
				if (Thread.currentThread().interrupted()) {
					System.out.println("tttttt");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
		System.out.println("My Thread exiting under request...");
	}
}