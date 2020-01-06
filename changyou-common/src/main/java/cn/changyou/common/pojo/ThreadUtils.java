package cn.changyou.common.pojo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xgl
 * @create 2019-12-26 20:44
 */
public class ThreadUtils {
    private static final ExecutorService es = Executors.newFixedThreadPool(10);

    public static void execute(Runnable runnable) {
        es.submit(runnable);
    }
}
