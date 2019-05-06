package concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用生产者消费者模式测试BlockingQueue
 */
public class BlockingQueueDemo2 {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(6);

        Prodcer p1 = new Prodcer(blockingQueue);
        Prodcer p2 = new Prodcer(blockingQueue);
        Consumer c1 = new Consumer(blockingQueue);

        Thread t1 = new Thread(p1);
        Thread t2 = new Thread(p2);
        Thread t3 = new Thread(c1);

        t1.start();
        t2.start();
        t3.start();

        //执行10秒
        Thread.sleep(10000);
        p1.stop();
        p2.stop();


    }
}


//生产者
class Prodcer implements Runnable{
    private BlockingQueue<String> queue;
    public volatile boolean flag = true;
    public AtomicInteger count = new AtomicInteger(0);

    public Prodcer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("生产者线程开始执行辣");
        while (flag) {
            System.out.println("生产中。。。");
            String data = count.incrementAndGet() + "";
            try {
                //阻塞1秒钟，等待队列空出位置，1秒钟未插入则失败
                boolean offer = queue.offer(Thread.currentThread().getName()+"-->"+data,1, TimeUnit.SECONDS);
                if (offer) {
                    System.out.println("生产者"+Thread.currentThread().getName()+"，存入" + data + "到队列中，成功");
                } else {
                    System.out.println("生产者"+Thread.currentThread().getName()+"，存入" + data + "到队列中，失败！！！");
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("生产者"+Thread.currentThread().getName()+"退出线程");
    }

    public void stop(){
        this.flag = false;
    }
}

//消费者
class Consumer implements Runnable {
    private BlockingQueue<String> queue;
    public volatile boolean flag = true;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (flag) {
            try {
                //阻塞两秒去获得数据
                String data = queue.poll(2,TimeUnit.SECONDS);
                if (data != null) {
                    System.out.println("消费者，拿到队列中的数据：" + data);
                    Thread.sleep(1000);
                } else {
                    System.out.println("消费者超过2秒未获取到数据...");
                    stop();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("消费者退出线程");
    }

    public void stop(){
        this.flag = false;
    }
}