package concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列是一个支持两个附加操作的队列：
 * 1、当队列为空时，获取元素的线程会等待队列变为非空
 * 2、当队列满时，存储元素的线程会等待队列可用
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue blockingQueue = new LinkedBlockingQueue(3);
        blockingQueue.add("zs");
        blockingQueue.add("ls");
        blockingQueue.add("ww");
        //满的时候继续添加会抛异常：java.lang.IllegalStateException: Queue full
        //blockingQueue.add("zl");
        //阻塞两秒钟，依旧插不进去就失败，不会继续执行插入
        blockingQueue.offer("zl",2, TimeUnit.SECONDS);
        System.out.println(blockingQueue.size());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.size());


    }
}
