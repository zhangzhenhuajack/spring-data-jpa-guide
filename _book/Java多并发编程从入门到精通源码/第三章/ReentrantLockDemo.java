package demo;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
	public static void main(String[] args) {
		final Countx ct = new Countx();
		for (int i = 0; i < 2; i++) {
			new Thread() {
				@Override
				public void run() {
					ct.get();
				}
			}.start();
		}
		for (int i = 0; i < 2; i++) {
			new Thread() {
				@Override
				public void run() {
					ct.put();
				}
			}.start();
		}
	}
}

class Countx {
	final ReentrantLock lock = new ReentrantLock();

	public void get() {
		// final ReentrantLock lock = new ReentrantLock();
		try {
			lock.lock();// 加锁
			System.out.println(Thread.currentThread().getName() + "get begin");
			Thread.sleep(1000L);// 模仿干活
			System.out.println(Thread.currentThread().getName() + "get end");
			lock.unlock(); // 解锁
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void put() {
		// final ReentrantLock lock = new ReentrantLock();
		try {
			lock.lock();// 加锁
			System.out.println(Thread.currentThread().getName() + "put begin");
			Thread.sleep(1000L);// 模仿干活
			System.out.println(Thread.currentThread().getName() + "put end");
			lock.unlock(); // 解锁
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}