package pri.smilly.demo.server.component;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import pri.smilly.demo.server.config.SystemConfiguration;
import pri.smilly.demo.server.domain.UploadInfo;
import pri.smilly.demo.util.ConverterUtil;
import pri.smilly.demo.util.FileUtil;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

@Component
public class FileCacheComponent {
    private Charset charset = StandardCharsets.UTF_8;
    private String cacheFileName = "cache.cache";
    private File cacheFile;
    private UnaryOperator<UploadInfo> operator = (item) -> item;

    List<UploadInfo> cache = new ArrayList<>();

    @PostConstruct
    public void initial() {
        cacheFile = new File(SystemConfiguration.cachePath, cacheFileName);
    }

    @PreDestroy
    public void destroy() {
        save();
    }

    public List<UploadInfo> get() {
        synchronized (cacheFile) {
            read(cacheFile).stream()
                    .filter(item -> !cache.contains(item))
                    .forEach(cache::add);
        }

        return cache;
    }

    @SneakyThrows
    public void save() {
        write(ConverterUtil.bean2Json(cache), cacheFile, false);
    }

    @SneakyThrows
    public void update(List<UploadInfo> list) {
        cache.forEach(operator::apply);
        save();
    }

    public void add(UploadInfo obj) {
        cache.add(obj);
    }

    public void add(List<UploadInfo> list) {
        cache.addAll(list);
    }

    public void delete(List<UploadInfo> list) {
        cache.removeAll(list);
    }

    @SneakyThrows
    private void write(String message, File file, boolean append) {
        synchronized (cacheFile) {
            FileUtil.save(message.getBytes(charset), file, append);
        }
    }

    @SneakyThrows
    private List<UploadInfo> read(File file) {
        synchronized (cacheFile) {
            String json = FileUtil.read(file, charset);
            return ConverterUtil.json2List(json, UploadInfo.class);
        }
    }
}
