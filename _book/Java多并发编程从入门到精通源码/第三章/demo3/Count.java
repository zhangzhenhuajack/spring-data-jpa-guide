package demo.thread;
public class Count {
	private byte[] lock = new byte[1];
	public int num = 0;
	public synchronized void methodA() {
		try {
			Thread.sleep(5l);// 模仿用户干活
		} catch (InterruptedException e) {
		}
		num += 1;
		System.out.println(Thread.currentThread().getName() + "-" + num);
	}
	public void methodB() {
		synchronized (lock) {//注意这个锁的对象不一样
			try {
				Thread.sleep(5l);// 模仿用户干活
			} catch (InterruptedException e) {
			}
			num += 1;
			System.out.println(Thread.currentThread().getName() + "-" + num);
		}
	}
}