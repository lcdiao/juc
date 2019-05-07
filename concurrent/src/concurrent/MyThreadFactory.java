package concurrent;

import java.lang.String;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: diao
 * @Description:
 * @Date: 2019/5/7 15:41
 */
public class MyThreadFactory implements ThreadFactory {
    AtomicInteger tag = new AtomicInteger(1);
    private String name;
    public MyThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        //thread.setName(name+"-"+tag.getAndIncrement());
        thread.setName(String.format(name,tag.getAndIncrement()));

        return thread;
    };

}
