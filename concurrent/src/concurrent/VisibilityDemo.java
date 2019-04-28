package concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @Author: diao
 * @Description: 可见性demo
 * @Date: 2019/4/28 9:42
 */
public class VisibilityDemo {
    /*
    并发中保证变量可见性的方式：
    1、final变量
    2、Synchronized
    3、用volatile修饰
     */

    // 状态标识 (多线程共享的全局变量)
    //3.方式二：加上volatile，使变量可见    （但输出的值为负数。。。如：-348264622)
    private static volatile boolean is = true;

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (VisibilityDemo.is) {
                    //2.方式一：加上synchronized 同步关键字，保证可以看到变量的最新值，i值可以输出，程序会停止
//                    synchronized (this) {
//                        i++;
//                    }

                    i++;
                }
                System.out.println(i);
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //设置is为false，使上面的线程结束while循环
        VisibilityDemo.is = false;
        System.out.println("被置为false了.");
        //1.虽然被置了false了，但程序不会停止，还是死循环
    }
}






/*
JAVA内存模型及操作规范：
1、共享变量必须存放在主内存中；
2、线程有自己的工作内存，线程只可操作自己的工作内存；
3、线程要操作共享变量，需从主内存中读取到工作内存，改变值后需从工作内存同步到主内存中。
java线程1    <--->  工作内存   <--->
java线程2    <--->  工作内存   <--->  同步协议  <--->  主内存
java线程3    <--->  工作内存   <--->

同步交互协议，规定了8种原子操作：
1、lock(锁定)  ：将主内存中的变量锁定，为一个线程所独占
2、unlock(解锁)：将lock加的锁定解除，此时其他的线程可以有机会访问此变量
3、read(读取)  ：作用于主内存变量，将主内存中的变量值读到工作内存当中
4、load(载入)  ：作用于工作内存变量，将read读取的值保存到工作内存中的变量副本中
5、use(使用)   ：作用于工作内存变量，将值传递给线程的代码执行引擎
6、assign(赋值)：作用于工作内存变量，将执行引擎处理返回的值重新赋值给变量副本
7、store(存储) ：作用于工作内存变量，将变量副本的值传送到主内存中
8、write(写入) ：作用于主内存变量，将store传送过来的值写入到主内存的共享变量中

Synchronized怎么做到可见性？
Synchronized语义规范：
1、进入同步块前，先清空工作内存中的共享变量，从主内存中重新加载。
2、解锁前必须把修改的共享变量同步回主内存

Synchronized是如何做到线程安全的？
1、锁机制保护共享资源，只有获得锁的线程才可操作共享资源；
2、Synchronized语义规范保证了修改共享资源后，会同步回主内存，就做到了线程安全

volatile怎么做到可见性？
1、使用volatile变量时，必须重新从主内存加载，并且read、load是连续的。
2、修改volatile变量后，必须立马同步回主内存，并且store、write是连续的。

volatile能做到线程安全吗？
1、不能，因为它没有锁机制，线程可并发操作共享资源

Sunchronized可以保证可见性，为啥要用volatile？
1、主要原因：使用volatile比synchronuzed简单
2、volatile性能比synchronized稍好。

volatile其他用途：
volatile可用于限制局部代码指令重排序
1、线程A和线程B的部分代码：
线程A：                                线程B
content = initContent(); //(1)          if(isInit) //(3)
isInit = true;  //(2)                       content.operation(); //(4)
2、jvm优化指令重排序后，代码的执行顺序可能如下：
    (2)-->(1)   如果执行(2)后又先执行了(3)，就会报空指针异常！！！
3、当两个线程并发执行时，就可能出现线程B中抛空指针异常。
4、当我们再变量上加volatile修饰时，则用到该变量的块中就不会进行指令重排优化。


volatile的使用场景
volatile的使用范围：
1、volatile只可修饰成员变量(静态的、非静态的)
2、多线程并发下，才需要使用它
volatile典型的应用场景
1、只有一个修改者，多个使用者，要求保证可见性的场景
    1.状态标识，如实例中的介绍标识
    2.数据定期发布，多个获取者
 */
