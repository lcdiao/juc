package concurrent;

import java.util.concurrent.*;

/**
 线程池作用：会把之前执行某个线程完毕的线程不会释放掉，会留到线程池中给下一个调用的线程直接使用
 前提：AB线程再同一个线程池里面，A线程执行完毕，B线程进来了，就直接去替换原来A线程的run方法，执行B
 */
public class CachedThreadPoolDemo {

    public static void main(String[] args) throws InterruptedException {
        /*
        创建一个可缓存线程池，如何线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
         */
        //ExecutorService newCacheThreadPool = Executors.newCachedThreadPool();
        //手动创建线程池
        ExecutorService newCacheThreadPool = new ThreadPoolExecutor(0,Integer.MAX_VALUE,60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),new MyThreadFactory("缓存线程-%d"),new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            Thread.sleep(500);
            newCacheThreadPool.execute(
                new Thread(new Pool01()));
        }

        newCacheThreadPool.shutdown();
    }

}

class Pool01 implements Runnable{
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