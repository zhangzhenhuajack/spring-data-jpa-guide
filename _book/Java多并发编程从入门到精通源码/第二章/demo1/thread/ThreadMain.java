/**
 * 
 */
package demo.thread;

/**
 * @author jack
 *
 */
public class ThreadMain {

	public static void main(String[] args) {
		
		ThreadA threada = new ThreadA();
		threada.start();
		System.out.println("这是主线程；");
	}

}
