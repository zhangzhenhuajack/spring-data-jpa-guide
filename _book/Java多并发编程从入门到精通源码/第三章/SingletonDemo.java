package demo;

import java.util.concurrent.locks.ReentrantLock;

/*
 class Singleton {
 private static Singleton instance;
 private static ReentrantLock lock = new ReentrantLock();

 private Singleton() {
 }

 public static Singleton getInstance() {
 if (instance == null) {
 lock.lock();
 if (instance == null) {
 instance = new Singleton();
 }
 lock.unlock();
 }
 return instance;
 }
 }
 */
class Singleton {
	private static Singleton instance;
	private static ReentrantLock lock = new ReentrantLock();

	private Singleton() {
	}

	public static Singleton getInstance() {
		if (instance == null) {
			lock.lock();
			if (instance == null) {
				instance = new Singleton();
			}
			lock.unlock();
		}
		return instance;
	}
}
