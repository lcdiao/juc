package concurrent;

import java.util.concurrent.CountDownLatch;

class ThreadDemo extends Thread {
    private CountDownLatch countDownLatch;

    public ThreadDemo(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getName() + "线程执行完毕");
        //执行完记得countDown
        countDownLatch.countDown();
    }
}

/**
 * （闭锁）计数器
 * CountDownLatch是一个同步工具类，它允许一个或多个线程一直等待，直到其他线程执行完后再执行。
 * 例如，应用程序的主线程希望在负责启动框架服务的线程已经启动所有框架服务之后执行。
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        //线程执行完后countDown就减一，直到为0才执行await之后
        CountDownLatch c = new CountDownLatch(2);

        ThreadDemo t1 = new ThreadDemo(c);
        ThreadDemo t2 = new ThreadDemo(c);

        t1.start();
        t2.start();

        Thread.sleep(300);

        //如果不加就会先执行完主线程，再执行线程0和1
        c.await();

        System.out.println("主线程结束!");
    }
}
