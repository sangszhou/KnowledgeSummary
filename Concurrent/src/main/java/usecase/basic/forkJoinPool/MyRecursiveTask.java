package usecase.basic.forkJoinPool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by xinszhou on 7/10/16.
 */
public class MyRecursiveTask extends RecursiveTask<Long> {

    Long workload;

    public MyRecursiveTask(Long workload) {
        this.workload = workload;
    }

    @Override
    protected Long compute() {

        if(this.workload >= 2) {
            List<MyRecursiveTask> subTasks = new LinkedList<>();

            MyRecursiveTask task1 = new MyRecursiveTask(workload / 2);
            MyRecursiveTask task2 = new MyRecursiveTask(workload / 2);

            subTasks.add(task1);
            subTasks.add(task2);

            //very importan to fork those subtask
            for(MyRecursiveTask task: subTasks) {
                task.fork();
            }

            Long result = 0L;

            for(MyRecursiveTask task: subTasks) {
                result += task.join();
            }

            return result;

        } else {
            System.out.println("small amount of work, i will do it myself");
            return 1L;
        }

    }
}
