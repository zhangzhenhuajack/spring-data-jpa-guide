	package demo.thread;
	import java.util.concurrent.Callable;
	//implements Callable接口，实现call()方法；
	//可有有返回结果，注意这次是要覆盖call方法；
	public class ThreadC implements Callable<String> {
		@Override
		public String call() throws Exception {
			try {
				// TODO Auto-generated method stub
				//模拟做事情执行了500毫秒；
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("这是线程 B");
			return "thread B";
		}
	}
