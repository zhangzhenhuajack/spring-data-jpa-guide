package demo.thread;
public class ThreadMain {
	public static void main(String[] args) {
		Count count = new Count();
		ThreadA task = new ThreadA(count);
		task.setName("线程A");
		task.start();
		ThreadB taskB = new ThreadB(count);
		taskB.setName("线程B");
		taskB.start();
	}
}
