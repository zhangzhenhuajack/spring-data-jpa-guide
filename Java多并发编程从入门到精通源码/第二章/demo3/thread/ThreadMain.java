	package demo.thread;
	import java.util.concurrent.ExecutionException;
	import java.util.concurrent.FutureTask;
	import java.util.concurrent.locks.Lock;
	import java.util.concurrent.locks.ReentrantLock;

	public class ThreadDemo {
		public void updateUser(UserInfo userInfo){
			Lock lock = new ReentrantLock();
			lock.lock();//加锁
			try {
				。。。。//共享数据操作
			} finally {
				lock.unlock();//释放锁，一定要释放
			}
		}
		public  void updateUser(UserInfo userInfo){
			synchronized(this) {

			}
		}


		public static void main(String[] args) {
			ThreadC threadc = new ThreadC();
			//FutureTask 后续会讲到，先知道有怎么个实现方式
			FutureTask<String> faeature = new FutureTask<String>(threadc);
			new Thread(faeature).start();//注意启动方式有点不一样；
			System.out.println("这是主线程；begin！");
			//注意细细体会这个，只有主线程get了，主线程才会继续往下面执行
			try {
				System.out.println("得到的返回结果是："+faeature.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			System.out.println("这是主线程；end！");
		}
	}
