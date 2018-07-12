package demo.thread;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
public class RecursiveTaskDemo {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Integer count = new ForkJoinPool().invoke(new CountingTask(Paths.get("D:/fish")));

		System.out.println("D:盘fish下面总文件数量："+count);
		System.out.println("Thread Main End!");
	}
}
// 处理单个目录的任务
class CountingTask extends RecursiveTask<Integer> {
	private Path dir;
	public CountingTask(Path dir) {
		this.dir = dir;
	}
	@Override
	protected Integer compute() {
		int count = 0;
		List<CountingTask> subTasks = new ArrayList<CountingTask>();
		// 读取目录 dir 的子路径。
		try {
			DirectoryStream<Path> ds = Files.newDirectoryStream(dir);
			for (Path subPath : ds) {
				if (Files.isDirectory(subPath, LinkOption.NOFOLLOW_LINKS)) {
					// 对每个子目录都新建一个子任务。
					subTasks.add(new CountingTask(subPath));
				} else {
					// 遇到文件，则计数器增加 1。
					count++;
				}
			}
			if (!subTasks.isEmpty()) {
				// 在当前的 ForkJoinPool 上调度所有的子任务。
				for (CountingTask subTask : invokeAll(subTasks)) {
					if(subTask.isCompletedAbnormally())
					{
					    System.out.println(subTask.getException());
					}
					count += subTask.join();
				}
			}
		} catch (IOException ex) {
			return 0;
		}
		return count;
	}
}
