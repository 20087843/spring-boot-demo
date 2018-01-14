package pri.smilly.runtime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import pri.smilly.util.StringUtil;
import pri.smilly.util.ThreadUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

@Getter
@Setter
@Builder
public class ProcessWrapper {
    private Charset charset = StandardCharsets.UTF_8;
    private int exitValue;
    private ByteArrayOutputStream outputStream;
    private Process process;

    private int expectExitValue = 0;

    public boolean isSuccess() {
        return exitValue == expectExitValue;
    }

    @SneakyThrows
    public String line() {
        return outputStream.toString(charset.name());
    }

    public List<String> lines() {
        List<String> lines = new ArrayList<>();
        Arrays.asList(line().split("\n")).stream()
                .filter(line -> StringUtil.isNotBlank(line.trim()))
                .forEach(lines::add);
        return lines;
    }

    public void consume() {
        ThreadUtil.invoke(() -> {
            try {
                outputStream.writeTo(out);
            } catch (IOException e) {
                ;
            }
        });
    }

}
