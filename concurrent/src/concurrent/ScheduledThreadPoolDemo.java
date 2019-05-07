package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ScheduledThreadPoolDemo {

    public static void main(String[] args) throws InterruptedException {
        /*
        创建一个固定大小的线程池，支持定时及周期性任务执行。
         */
        ExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 10; i++) {
            final int temp = i;
            Thread.sleep(500);
            ((ScheduledExecutorService) newScheduledThreadPool).schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("i:" + temp);
                }
                //延迟执行3秒
            },3, TimeUnit.SECONDS);
        }

        newScheduledThreadPool.shutdown();
    }

}
