package demo.thread;

public class Count {
	public int num = 0;
	public void add() {
		try {
			Thread.sleep(5l);//模仿用户干活
		} catch (InterruptedException e) {
		}
		num += 1;
		System.out.println(Thread.currentThread().getName() + "-" + num);
	}
}