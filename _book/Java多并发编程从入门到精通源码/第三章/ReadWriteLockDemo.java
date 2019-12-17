package demo.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
	public static void main(String[] args) {
		final Count ct = new Count();
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

class Count {
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	public void get() {
		rwl.readLock().lock();// 上读锁，其他线程只能读不能写，具有高并发性
		try {
			System.out.println(Thread.currentThread().getName() + " read start.");
			Thread.sleep(1000L);// 模拟干活
			System.out.println(Thread.currentThread().getName() + "read end.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			rwl.readLock().unlock(); // 释放写锁，最好放在finnaly里面
		}
	}

	public void put() {
		rwl.writeLock().lock();// 上写锁，具有阻塞性
		try {
			System.out.println(Thread.currentThread().getName() + " write start.");
			Thread.sleep(1000L);// 模拟干活
			System.out.println(Thread.currentThread().getName() + "write end.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			rwl.writeLock().unlock(); // 释放写锁，最好放在finnaly里面
		}
	}

	private final Map<String, Object> map = new HashMap<String, Object>();// 假设这里面存了数据缓存
	private final ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

	public Object readWrite(String id) {
		Object value = null;
		rwlock.readLock().lock();// 首先开启读锁，从缓存中去取
		try {
			value = map.get(id);
			if (value == null) { // 如果缓存中没有释放读锁，上写锁
				rwlock.readLock().unlock();
				rwlock.writeLock().lock();
				try {
					if (value == null) {
						value = "aaa"; // 此时可以去数据库中查找，这里简单的模拟一下
					}
				} finally {
					rwlock.writeLock().unlock(); // 释放写锁
				}
				rwlock.readLock().lock(); // 然后再上读锁
			}
		} finally {
			rwlock.readLock().unlock(); // 最后释放读锁
		}
		return value;
	}
}
