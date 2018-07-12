	package demo.thread;
	//implements Runnable接口，实现run()方法；
	//有点，java里面可以有多个接口，解决extends的缺点
	public class ThreadB implements Runnable {

		@Override
		public void run() {
			try {
				// TODO Auto-generated method stub
				//模拟做事情执行了500毫秒；
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("这是线程 B");
		}
		
		
	}
