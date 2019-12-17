package demo.thread;
import java.util.concurrent.DelayQueue;
public class DelayQueueTest {
	public static void main(String[] args) throws Exception {
		// 新建一个等待队列
		final DelayQueue<Student> bq = new DelayQueue<Student>();
		for (int i = 0; i < 5; i++) {
			Student student = new Student("学生"+i,Math.round((Math.random()*10+i)));
			bq.put(student); // 将数据存到队列里！
		}
		//获取但不移除此队列的头部；如果此队列为空，则返回 null。
		System.out.println(bq.peek().getName());
		//获取并移除此队列的头部，在可从此队列获得到期延迟的元素，或者到达指定的等待时间之前一直等待（如有必要）。
		//poll(long timeout, TimeUnit unit) 大家可以试一试这个方法
	}
}