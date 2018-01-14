package pri.smilly.common;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipException;

@Log4j
public class PackageScanner {
    private PathMatcher pathMatcher = new AntPathMatcher();

    public static void main(String[] args) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        Set<BeanDefinition> beans = provider.findCandidateComponents("pri.smilly");

        System.out.println(Arrays.toString(beans.toArray()));
    }

    protected Set<Resource> doFindPathMatchingFileResources(Resource rootDirResource, String subPattern) throws IOException {
        try {
            File rootDir = rootDirResource.getFile().getAbsoluteFile();
            return this.doFindMatchingFileSystemResources(rootDir, subPattern);
        } catch (IOException var5) {
            return Collections.emptySet();
        }
    }

    private Set<Resource> doFindPathMatchingJarResources(Resource rootDirResource, String subPattern) throws IOException {
        boolean newJarFile = false;
        JarFile jarFile;
        String jarFileUrl;
        String rootEntryPath;

        URLConnection con = rootDirResource.getURL().openConnection();
        if (con instanceof JarURLConnection) {
            JarURLConnection jarCon = (JarURLConnection) con;
            useCachesIfNecessary(jarCon);
            jarFile = jarCon.getJarFile();
//            jarFileUrl = jarCon.getJarFileURL().toExternalForm();
            JarEntry jarEntry = jarCon.getJarEntry();
            rootEntryPath = jarEntry != null ? jarEntry.getName() : "";
        } else {
            String urlFile = rootDirResource.getURL().getFile();
            try {
                int separatorIndex = urlFile.indexOf("!/");
                if (separatorIndex != -1) {
                    jarFileUrl = urlFile.substring(0, separatorIndex);
                    rootEntryPath = urlFile.substring(separatorIndex + "!/".length());
                    jarFile = this.getJarFile(jarFileUrl);
                } else {
                    jarFile = new JarFile(urlFile);
//                    jarFileUrl = urlFile;
                    rootEntryPath = "";
                }

                newJarFile = true;
            } catch (ZipException var16) {
                return Collections.emptySet();
            }
        }

        try {
            if (!"".equals(rootEntryPath) && !rootEntryPath.endsWith("/")) {
                rootEntryPath = rootEntryPath + "/";
            }

            Set<Resource> result = new LinkedHashSet(8);
            Enumeration entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = (JarEntry) entries.nextElement();
                String entryPath = entry.getName();
                if (entryPath.startsWith(rootEntryPath)) {
                    String relativePath = entryPath.substring(rootEntryPath.length());
                    if (this.pathMatcher.match(subPattern, relativePath)) {
                        result.add(rootDirResource.createRelative(relativePath));
                    }
                }
            }
            return result;
        } finally {
            if (newJarFile) {
                jarFile.close();
            }
        }
    }

    protected JarFile getJarFile(String jarFileUrl) throws IOException {
        if (jarFileUrl.startsWith("file:")) {
            try {
                return new JarFile(toURI(jarFileUrl).getSchemeSpecificPart());
            } catch (URISyntaxException e) {
                return new JarFile(jarFileUrl.substring("file:".length()));
            }
        } else {
            return new JarFile(jarFileUrl);
        }
    }

    protected Set<Resource> doFindMatchingFileSystemResources(File rootDir, String subPattern) throws IOException {
        Set<File> matchingFiles = this.retrieveMatchingFiles(rootDir, subPattern);
        Set<Resource> result = new LinkedHashSet(matchingFiles.size());

        Iterator var5 = matchingFiles.iterator();
        while (var5.hasNext()) {
            File file = (File) var5.next();
            result.add(new FileSystemResource(file));
        }

        return result;
    }

    protected Set<File> retrieveMatchingFiles(File rootDir, String pattern) throws IOException {
        if (!rootDir.exists()) {
            return Collections.emptySet();
        } else if (!rootDir.isDirectory()) {
            return Collections.emptySet();
        } else if (!rootDir.canRead()) {
            return Collections.emptySet();
        } else {
            String fullPattern = StringUtils.replace(rootDir.getAbsolutePath(), File.separator, "/");
            if (!pattern.startsWith("/")) {
                fullPattern = fullPattern + "/";
            }

            fullPattern = fullPattern + StringUtils.replace(pattern, File.separator, "/");
            Set<File> result = new LinkedHashSet(8);
            this.doRetrieveMatchingFiles(fullPattern, rootDir, result);
            return result;
        }
    }

    protected void doRetrieveMatchingFiles(String fullPattern, File dir, Set<File> result) throws IOException {
        File[] dirContents = dir.listFiles();
        if (dirContents != null) {
            File[] var5 = dirContents;
            int var6 = dirContents.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                File content = var5[var7];
                String currPath = StringUtils.replace(content.getAbsolutePath(), File.separator, "/");
                if (content.isDirectory() && this.pathMatcher.matchStart(fullPattern, currPath + "/")) {
                    if (!content.canRead()) {
                    } else {
                        this.doRetrieveMatchingFiles(fullPattern, content, result);
                    }
                }

                if (this.pathMatcher.match(fullPattern, currPath)) {
                    result.add(content);
                }
            }
        }
    }

    private void useCachesIfNecessary(URLConnection con) {
        con.setUseCaches(con.getClass().getSimpleName().startsWith("JNLP"));
    }

    public URI toURI(String location) throws URISyntaxException {
        return new URI(StringUtils.replace(location, " ", "%20"));
    }
}
