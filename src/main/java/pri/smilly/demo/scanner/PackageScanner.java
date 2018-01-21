package pri.smilly.demo.scanner;

import lombok.SneakyThrows;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackageScanner {
    private PathMatcher matcher = new AntPathMatcher();
    private String pattern = "**/*.class";

    public Set<String> scan(String basePackage) throws Exception {
        String basePkgRelativePath = ClassUtil.package2Path(basePackage);
        List<URL> resources = ClassUtil.getResources(basePkgRelativePath);

        Set<String> classes = new HashSet<>();
        for (URL resource : resources) {
            if (ClassUtil.isJarURL(resource)) {
                File jar = getJarFile(resource);
                classes.addAll(scanJar(jar, basePkgRelativePath, pattern));
            } else if (ClassUtil.isFileURL(resource)) {
                File path = new File(resource.toURI().getPath());
                classes.addAll(scanPath(path, basePkgRelativePath, pattern));
            }
        }

        return classes;
    }

    private Set<String> scanPath(File rootPath, String rootPkgRelativePath, String pattern) throws IOException {
        if (!rootPath.exists()) {
            throw new FileNotFoundException(rootPath + " not found");
        }

        Set<String> calsses = new HashSet<>();
        if (rootPath.getAbsolutePath().contains(rootPkgRelativePath)) {
            scanPath(calsses, rootPath, rootPkgRelativePath, pattern);
        }

        return calsses;
    }

    private void scanPath(Set<String> classes, File file, String basePkgPath, String pattern) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                scanPath(classes, child, basePkgPath, pattern);
            }
        } else {
            String filePath = file.getAbsolutePath();
            String relativePath = filePath.substring(filePath.indexOf(basePkgPath) + basePkgPath.length() + 1);
            if (matcher.match(pattern, relativePath)) {
                classes.add(basePkgPath + File.separatorChar + relativePath);
            }
        }
    }

    private Set<String> scanJar(File jar, String rootPkgRelativePath, String pattern) throws IOException {
        Set<String> classes = new HashSet<>();

        try (JarFile jarFile = new JarFile(jar.getAbsoluteFile())) {
            Enumeration<JarEntry> entries = jarFile.entries();

            JarEntry entry;
            while (entries.hasMoreElements() && (entry = entries.nextElement()) != null) {
                String entryPath = entry.getName();
                if (!entryPath.contains(rootPkgRelativePath)) {
                    continue;
                }

                String relativeEntryPath = entryPath.substring(rootPkgRelativePath.length() + 2);
                if (matcher.match(pattern, relativeEntryPath)) {
                    classes.add(entryPath);
                }
            }
        }

        return classes;
    }

    @SneakyThrows(URISyntaxException.class)
    private File getJarFile(URL jarFileUrl) throws IOException {
        String urlStr = jarFileUrl.toURI().getPath();
        if (urlStr.startsWith("file:")) {
            try {
                return new File(jarFileUrl.toURI().getSchemeSpecificPart());
            } catch (URISyntaxException var3) {
                return new File(urlStr.substring("file:".length()));
            }
        } else {
            return new File(urlStr);
        }
    }
}
