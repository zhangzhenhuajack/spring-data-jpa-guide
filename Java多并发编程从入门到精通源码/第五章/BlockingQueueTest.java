package demo.thread;

import java.util.Iterator;
import java.util.Vector;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
 * 现有的程序代码模拟产生了16个日志对象，并且需要运行16秒才能打印完这些日志，
 * 请在程序中增加4个线程去调用parseLog()方法来分头打印这16个日志对象，
 * 程序只需要运行4秒即可打印完这些日志对象。
 */
public class BlockingQueueTest {
	public static void main(String[] args) throws Exception {
		// 新建一个等待队列
		final BlockingQueue<String> bq = new ArrayBlockingQueue<String>(16);
		// 四个线程
		for (int i = 0; i < 4; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							String log = (String) bq.take();
							parseLog(log);
						} catch (Exception e) {
						}
					}
				}
			}).start();
		}
		for (int i = 0; i < 16; i++) {
			String log = (i + 1) + " -->  ";
			bq.put(log); // 将数据存到队列里！
		}
	}

	// parseLog方法内部的代码不能改动
	public static void parseLog(String log) {
		System.out.println(log + System.currentTimeMillis());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}