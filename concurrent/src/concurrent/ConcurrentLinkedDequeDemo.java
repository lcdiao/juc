package concurrent;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 concurrentLinkedQueue:是一个适用于高并发场景下的队列，通过无锁的方式，实现了高并发状态下的高性能，
 通常concurrentLinkedDeque性能好于BlockingQueue,它是一个基于链表的无界（Integer.MAX_VALUE）线程安全队列.
 改队列的元素遵循先进先出的原则。头是最先加入的，尾是最近加入的，该队列不允许null元素。FIFO

 add和offer都是加入元素的方法，没区别
 */
public class ConcurrentLinkedDequeDemo {

    public static void main(String[] args) {

        /*
        ConcurrentLinkedQueue 是单向链表结构的无界并发队列。元素操作按照 FIFO (first-in-first-out 先入先出) 的顺序。
        适合“单生产，多消费”的场景。内存一致性遵循对ConcurrentLinkedQueue的插入操作先行发生于(happen-before)访问或移除操作。

        ConcurrentLinkedDeque 是双向链表结构的无界并发队列。与 ConcurrentLinkedQueue 的区别是该阻塞队列同时支持FIFO和FILO两种操作方式，
        即可以从队列的头和尾同时操作(插入/删除)。适合“多生产，多消费”的场景。
        内存一致性遵循对 ConcurrentLinkedDeque 的插入操作先行发生于(happen-before)访问或移除操作

         */

        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.add(1);
        deque.offer(2);
        deque.add(3);
        //concurrentLinkedDeque.forEach(System.out::println);

        while (!deque.isEmpty()) {
            System.out.println(deque.pop());
        }

        System.out.println("========================");

        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(1);
        queue.offer(2);
        queue.add(3);
        while (!queue.isEmpty()){
            System.out.println(queue.poll());
        }
    }
}
