	package demo.thread;
	
	public class ThreadMain {
	
		public static void main(String[] args) {
			
			ThreadB threadb = new ThreadB();
			new Thread(threadb).start();//注意启动方式有点不一样；
			System.out.println("这是主线程；");
		}
	}
