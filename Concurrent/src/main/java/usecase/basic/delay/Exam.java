package usecase.basic.delay;

/**
 *
 * http://hovertree.com/h/bjaf/rn47rhdd.htm
 *
 * (1) 实例场景

 模拟一个考试的日子，考试时间为120分钟，30分钟后才可交卷，当时间到了，或学生都交完卷了考试结束。

 这个场景中几个点需要注意：

 考试时间为120分钟，30分钟后才可交卷，初始化考生完成试卷时间最小应为30分钟
 对于能够在120分钟内交卷的考生，如何实现这些考生交卷
 对于120分钟内没有完成考试的考生，在120分钟考试时间到后需要让他们强制交卷
 在所有的考生都交完卷后，需要将控制线程关闭
 实现思想：用DelayQueue存储考生（Student类），每一个考生都有自己的名字和完成试卷的时间，
 Teacher线程对DelayQueue进行监控，收取完成试卷小于120分钟的学生的试卷。
 当考试时间120分钟到时，先关闭Teacher线程，然后强制DelayQueue中还存在的考生交卷。
 每一个考生交卷都会进行一次countDownLatch.countDown()，
 当countDownLatch.await()不再阻塞说明所有考生都交完卷了，而后结束考试。
 *
 */

public class Exam {

}
