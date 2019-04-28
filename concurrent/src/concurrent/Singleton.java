package concurrent;

/**
 * @Author: diao
 * @Description: 双重检查的单例，用到volatile关键字
 * @Date: 2019/4/28 11:29
 */
public class Singleton {
    /**
     * 如果去掉volatile关键字，依旧是个单例，但不适合高并发下的场景！
     * 如10个并发请求先执行到(2)，创建了实例，但之后1000个并发请求执行到(1)时（还未执行完（3），instance还未同步回去），instance还是为null
     * 加了volatile关键字后，保证并发的场景下，不会有太多的并发进入到锁机制！！
     */
    private static volatile Singleton instance;
    private Singleton(){}

    public static Singleton getInstance() {
        //第一次检查
        if (instance == null) {     //(1)
            //创建实例
            synchronized (Singleton.class) {        //(3)
                //第二次检查 保证只会有一个人先进来创建
                if (instance == null) {                 //(2)
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
