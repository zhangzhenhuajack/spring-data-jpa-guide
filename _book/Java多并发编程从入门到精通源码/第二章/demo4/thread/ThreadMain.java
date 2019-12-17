	package demo.thread;
	
	public class ThreadMain {
	
		public static void main(String[] args) {
			
			ThreadB threadb = new ThreadB();
			for(int i=0;i<5;i++) {
				new Thread(threadb,"线程名称：("+i+")").start();
			}
			//返回对当前正在执行的线程对象的引用。此处获得咱们的主线程
			Thread threadMain = Thread.currentThread() ;
			
			System.out.println("这是主线程；");
			System.out.println("返回当前线程的线程组中活动线程的数目："+Thread.activeCount());
			System.out.println("主线程的名称："+threadMain.getName());
			System.out.println("返回该线程的标识符："+threadMain.getId());
			System.out.println("返回线程的优先级:"+threadMain.getPriority());
			System.out.println("返回该线程的状态:"+threadMain.getState());
			System.out.println("返回该线程所属的线程组:"+threadMain.getThreadGroup());
			System.out.println("测试该线程是否为守护线程:"+threadMain.isAlive());
			
			
			try {
				Thread.sleep(10000L);//休息10s以便我们的监控工具能监控到
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
