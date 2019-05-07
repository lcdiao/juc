package concurrent;

import java.util.concurrent.*;

/**
 线程池作用：会把之前执行某个线程完毕的线程不会释放掉，会留到线程池中给下一个调用的线程直接使用
 前提：AB线程再同一个线程池里面，A线程执行完毕，B线程进来了，就直接去替换原来A线程的run方法，执行B
 */
public class SingleThreadExecutorDemo {

    public static void main(String[] args) throws InterruptedException {
        /*
        只有一个线程的线程池
         */
        //ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        //手动创建线程池
        ExecutorService newSingleThreadExecutor = new ThreadPoolExecutor(1,1,0L,
                TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(),new MyThreadFactory("单线程-%d"),
                new ThreadPoolExecutor.AbortPolicy());
        /*
        各参数解释：
        corePoolSize - 线程池核心池的大小。
        maximumPoolSize - 线程池的最大线程数。
        keepAliveTime - 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
        unit - keepAliveTime 的时间单位。
        workQueue - 用来储存等待执行任务的队列。
        threadFactory - 线程工厂。
        handler - 拒绝策略。
         */
        for (int i = 0; i < 10; i++) {
            Thread.sleep(500);
            newSingleThreadExecutor.execute(
                new Thread(new Pool04()));
        }

        newSingleThreadExecutor.shutdown();

    }

}

class Pool04 implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "运行开始");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }
}