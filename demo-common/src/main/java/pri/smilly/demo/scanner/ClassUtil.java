package pri.smilly.demo.scanner;

import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClassUtil {

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        if (cl == null) {
            cl = ClassUtil.class.getClassLoader();
        }

        if (cl == null) {
            cl = ClassLoader.getSystemClassLoader();
        }

        return cl;
    }

    /**
     * load resources in classpath
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static List<URL> getResources(String path) throws Exception {
        ClassLoader loader = getDefaultClassLoader();
        Enumeration<URL> urls;
        if (loader == null) {
            urls = ClassLoader.getSystemResources(path);
        } else {
            urls = loader.getResources(path);
        }

        List<URL> resources = new ArrayList<>();
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            resources.add(url);
        }

        return resources;
    }

    public static String package2Path(String pkg) {
        return pkg.replace('.', File.separatorChar);
    }

    public static boolean isFileURL(URL url) {
        String protocol = url.getProtocol();
        return "file".equals(protocol) || "vfsfile".equals(protocol) || "vfs".equals(protocol);
    }

    public static boolean isJarURL(URL url) {
        String protocol = url.getProtocol();
        return "jar".equals(protocol) || "war".equals(protocol) || "zip".equals(protocol) || "vfszip".equals(protocol) || "wsjar".equals(protocol);
    }

    public static URI toURI(URL url) throws URISyntaxException {
        return url.toURI();
    }

    public static URI toURI(String location) throws URISyntaxException {
        return new URI(StringUtils.replace(location, " ", "%20"));
    }

    public static void useCachesIfNecessary(URLConnection con) {
        con.setUseCaches(con.getClass().getSimpleName().startsWith("JNLP"));
    }

}
