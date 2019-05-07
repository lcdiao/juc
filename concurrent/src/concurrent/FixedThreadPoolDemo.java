package concurrent;

import java.util.concurrent.*;

/**
 线程池作用：会把之前执行某个线程完毕的线程不会释放掉，会留到线程池中给下一个调用的线程直接使用
 前提：AB线程再同一个线程池里面，A线程执行完毕，B线程进来了，就直接去替换原来A线程的run方法，执行B
 */
public class FixedThreadPoolDemo {

    public static void main(String[] args) throws InterruptedException {
        /*
        创建一个固定大小的线程池，可控制线程最大并发数，超出的线程会在队列中等待。
         */
        //ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);
        //手动创建线程池
        ExecutorService newFixedThreadPool = new ThreadPoolExecutor(3,3,0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),new MyThreadFactory("我的线程-%d"),new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            Thread.sleep(500);
            newFixedThreadPool.execute(
                new Thread(new Pool02()));
        }

        newFixedThreadPool.shutdown();
    }

}

class Pool02 implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "运行开始");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }
}