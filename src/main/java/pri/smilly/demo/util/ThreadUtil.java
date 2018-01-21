package pri.smilly.demo.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadUtil {
    private static ExecutorService executor = Executors.newCachedThreadPool();

    public static void invoke(Runnable task) {
        executor.execute(task);
    }

    public static void invoke(Callable<?> task) {
        executor.submit(task);
    }

    public static Future execute(Runnable task) {
        return executor.submit(task);
    }

    public static <T> Future<T> execute(Callable<T> task) {
        return executor.submit(task);
    }

}
