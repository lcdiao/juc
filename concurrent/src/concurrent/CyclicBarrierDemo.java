package concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class ThreadDemo2 extends Thread {
    private CyclicBarrier cyclicBarrier;

    public ThreadDemo2(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        System.out.println(getName() + "开始执行线程！");

        try {
            //等待栅栏到达公共屏障点
            cyclicBarrier.await();
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(getName() + "结束线程");

    }
}

/**
 *  一个同步辅助类,它允许一组线程互相等待,直到到达某个公共屏障点 (common barrier point)。
 *  字面意思回环栅栏，通过它可以实现让一组线程等待至某个状态之后再全部同时执行。
 *  叫做回环是因为当所有等待线程都被释放以后，CyclicBarrier可以被重用。
 *  叫做栅栏，大概是描述所有线程被栅栏挡住了，当都达到时，一起跳过栅栏执行，也算形象。我们可以把这个状态就叫做barrier。
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        //如果设置为2，第三个线程不会继续往下执行,或者设置为4时，3个线程都不会继续往下执行cyclicBarrier.await();后的代码
        CyclicBarrier barrier = new CyclicBarrier(2);

        ThreadDemo2 t1 = new ThreadDemo2(barrier);
        ThreadDemo2 t2 = new ThreadDemo2(barrier);
        ThreadDemo2 t3 = new ThreadDemo2(barrier);

        t1.start();
        t2.start();
        t3.start();


        System.out.println("主线程执行结束！");
    }
}
