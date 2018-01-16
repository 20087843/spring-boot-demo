package pri.smilly.util;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.charset.Charset;

public class FileUtil {

    public static byte[] read(File file) throws IOException {
        try (FileInputStream in = new FileInputStream(file);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            StreamUtil.copy(in, out);
            return out.toByteArray();
        }
    }

    public static String read(File file, Charset charset) throws IOException {
        try (FileInputStream in = new FileInputStream(file);
             BufferedInputStream bin = new BufferedInputStream(in)) {
            return StreamUtils.copyToString(bin, charset);
        }
    }

    public static void save(byte[] bytes, File file, boolean append) throws IOException {
        try (ByteArrayInputStream in = new ByteArrayInputStream(bytes);
             FileOutputStream out = new FileOutputStream(file, append)) {
            StreamUtil.copy(in, out);
        }
    }

    public static void copy(File source, File dest) throws IOException {
        if (dest.isDirectory()) {
            dest = new File(dest, source.getName());
        }

        try (FileInputStream in = new FileInputStream(source);
             FileOutputStream out = new FileOutputStream(dest)) {
            StreamUtil.copy(in, out);
        }
    }

    public static File createDir(File baseDir, Comparable... items) {
        if (items.length != 0) {
            StringBuilder str = new StringBuilder();
            for (Comparable item : items) {
                str.append(item).append(File.pathSeparator);
            }
            baseDir = new File(baseDir, str.deleteCharAt(str.length()).toString());
        }

        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
        return baseDir;
    }

    public static File zip(File source) throws IOException {
        return zip(source, (File) null);
    }

    public static File zip(File source, File target) throws IOException {
        if (null == target) {
            target = source.getParentFile();
        }
        if (target.isDirectory()) {
            target = new File(target, source.getName() + ".zip");
        }

        try (ZipArchiveOutputStream out = new ZipArchiveOutputStream(target)) {
            zip(source, out);
        }
        return target;
    }

    private static void zip(File file, ZipArchiveOutputStream out) throws IOException {
        File[] children;
        if (file.isDirectory() && (children = file.listFiles()).length != 0) {
            for (File child : children) {
                zip(child, out);
            }
        } else {
            try (FileInputStream in = new FileInputStream(file)) {
                ZipArchiveEntry entry = new ZipArchiveEntry(file.getName());
                out.addRawArchiveEntry(entry, in);
            }
        }
    }

    public static File unzip(File file, File target) throws IOException {
        if (target == null) {
            target = file.getParentFile();
        }

        if (target.isFile()) {
            target = target.getParentFile();
        }

        try (ZipArchiveInputStream in = new ZipArchiveInputStream(new FileInputStream(file))) {
            ZipArchiveEntry entry;
            if ((entry = in.getNextZipEntry()) != null) {
                File item = new File(file, entry.getName());
                if (item.isDirectory()) {
                    item.mkdirs();
                } else {
                    try (FileOutputStream out = new FileOutputStream(item)) {
                        IOUtils.copy(in, out);
                    }
                }
            }
        }

        return target;
    }

}
