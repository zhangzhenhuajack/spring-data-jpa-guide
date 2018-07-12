	package demo.thread;
	public class ThreadB implements Runnable {
		@Override
		public void run() {
			try {
				// TODO Auto-generated method stub
				//模拟做事情执行了100秒；//以便一会咱们的监控工具监控到啊！
				Thread.sleep(100000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Thread curThread = Thread.currentThread() ;
			String curThreadName = curThread.getName();
			System.out.println("这是线程的名称:"+curThread.getName());
			System.out.println("返回当前线程"+curThreadName+"的线程组中活动线程的数目："+Thread.activeCount());
			System.out.println("返回该线程"+curThreadName+"的标识符："+curThread.getId());
			System.out.println("返回线程"+curThreadName+"的优先级:"+curThread.getPriority());
			System.out.println("返回该线程"+curThreadName+"的状态:"+curThread.getState());
			System.out.println("返回该线程"+curThreadName+"所属的线程组:"+curThread.getThreadGroup());
			System.out.println("测试线程"+curThreadName+"是否处于活动状态:"+curThread.isAlive());
			System.out.println("测试线程"+curThreadName+"是否测试该线程是否为守护线程:"+curThread.isDaemon());
		}
		
		
	}
