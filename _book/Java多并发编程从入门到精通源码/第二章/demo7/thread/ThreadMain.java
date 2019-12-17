package demo.thread;
public class ThreadMain {
	public static void main(String[] args) {
		ThreadB task = new ThreadB();
		Thread thread = new Thread(task);
		thread.setUncaughtExceptionHandler(new ExceptionHandlerThreadB());
		thread.start();
	}
}
