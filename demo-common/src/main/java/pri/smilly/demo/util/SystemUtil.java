package pri.smilly.demo.util;


import org.springframework.util.ResourceUtils;
import org.springframework.util.SocketUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class SystemUtil {

    private static final int portSearchMin = 20000;
    private static final int portSearchMax = 40000;

    private static String osName = System.getProperty("os.name").split(" ")[0];
    private static int addressSize = Integer.getInteger("sun.arch.data.model");

    public static File getResource(String relatePath) throws FileNotFoundException {
        String path = ResourceUtils.getFile(relatePath).toURI().getPath();
        return new File(path);
    }

    public static boolean isWindows() {
        return osName.contains("windows");
    }

    public static boolean isLinux() {
        return osName.contains("linux");
    }

    public static int getAvailablePort() {
        return SocketUtils.findAvailableTcpPort(portSearchMin, portSearchMax);
    }

    public static boolean isX64Bit() {
        return addressSize != 32;
    }
}
