package concurrent;


import java.util.Random;
import java.util.concurrent.Semaphore;

class Wc extends Thread {
    private String name;
    private Semaphore wc;

    public Wc(String name, Semaphore wc) {
        this.name = name;
        this.wc = wc;
    }

    @Override
    public void run() {
        int a = wc.availablePermits();
        if (a > 0) {
            System.out.println(name + "抢到坑了！");
        } else {
            System.out.println(name + "抢不到坑，继续排队");
        }

        try {
            //申请资源
            wc.acquire();
            System.out.println(name + "终于抢到坑辣");
            //模拟上厕所的时间
            Thread.sleep(new Random().nextInt(3000));
            System.out.println(name + "上完厕所");
            //释放资源
            wc.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * Semaphore是一种基于计数的信号量。它可以设定一个阙值，基于此，多 个线程竞争获取许可信号，做自己的申请后归还，
 * 超过阙值后，线程申请许可信号将会被阻塞。Semaphore可以用来构建一些对象池，资源池之类的，比如数据库连接池，
 * 我们也可以创建计数为1的Semaphore，将其作为一种类似互斥锁的机制，这也叫二元信号量，表示两种互斥状态。
 * 它的用法如下：availablePermits函数用来获取当前可用的资源数量
 * wc.acquire();    //申请资源
 * wc.release();    //释放资源
 *
 * 一个信号量有且仅有3种操作，且它们全部是原子的：初始化、增加和减少
 * 增加可以为一个进程解除阻塞；
 * 减少可以让一个进程进入阻塞。
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        //例子：只有3间厕所，多个人排队等待使用
        Semaphore wc = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new Wc("第" + i + "个人",wc).start();
        }
    }
}
