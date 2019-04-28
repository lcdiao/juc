package concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: diao
 * @Description: 测试原子性
 * @Date: 2019/4/28 10:57
 */
public class AtomicityDemo {
    static volatile int count = 0;

    public static void increase() {
        count++;
    }

    public static void main(String[] args) {
        int threads = 20;
        CountDownLatch cdl = new CountDownLatch(threads);
        for (int i = 0; i < threads; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        AtomicityDemo.increase();
                    }
                    cdl.countDown();
                }
            }).start();
        }

        try {
            //等待所有的线程执行完再继续执行
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //66411
        //输出不是200000，说明volatile不能保证原子性
        System.out.println(AtomicityDemo.count);
    }
}
