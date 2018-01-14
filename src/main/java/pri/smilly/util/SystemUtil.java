package pri.smilly.util;


import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class SystemUtil {

    private static ApplicationContext ctx;
    private static Environment env;
    private static String osName = System.getProperty("os.name").split(" ")[0];
    private static int addressSise = Integer.getInteger("sun.arch.data.model");

    public static void initial(ApplicationContext ctx, Environment env) {
        SystemUtil.ctx = ctx;
        SystemUtil.env = env;
    }

    public static <T> T getBean(Class<T> calzz) {
        return ctx.getBean(calzz);
    }

    public static <T> T getProperty(String name, Class<T> clazz) {
        return env.getProperty(name, clazz);
    }

    public static String getProperty(String name) {
        return env.getProperty(name);
    }

    public static File getResource(String relatePath) throws FileNotFoundException {
        return ResourceUtils.getFile(relatePath).getAbsoluteFile();
    }

    public static boolean isWindows() {
        return osName.contains("windows");
    }

    public static boolean isLinux() {
        return osName.contains("linux");
    }
}
