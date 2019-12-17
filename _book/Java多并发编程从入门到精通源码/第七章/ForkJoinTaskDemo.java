package demo.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTaskDemo {
	public static void main(String[] args) throws InterruptedException, ExecutionException{
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		CountTask task = new CountTask(1,10);
		Future<Integer> result = forkJoinPool.submit(task);
		System.out.println("最终的结果：" + result.get());
		System.out.println("Thread Main End!");
	}
}
class CountTask extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 3336021432713606929L;
	private static int splitSize =2;
	private int start;
	private int end;
	public CountTask(int start,int end) {
        this.start = start;
        this.end = end;
	}
	@Override
	protected Integer compute() {
		int sum=0;
		
		//如果任务已经不需要再拆分了就开始计算
		boolean canCompute=(end-start)<=splitSize;
		if(canCompute) {
			for(int i=start;i<=end;i++) {
				sum=sum+i;
			}
		} else {
			//拆分成两个子任务
			int middle = (start+end)/2;
			CountTask fistTask = new CountTask(start,middle);
			CountTask secondTask = new CountTask(middle+1,end);
			fistTask.fork();//开始执行
			secondTask.fork();//
			//获得第一个子任务的结果，得不到结果，此线程不会往下面执行。
			int firstResult = fistTask.join();
			int secondResult = secondTask.join();
			//合并两个儿子的执行结果。
			sum = firstResult+ secondResult;
		}
		return sum;
	}
}
