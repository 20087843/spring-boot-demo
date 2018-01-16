package pri.smilly.util;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {

    public static void copy(InputStream in, OutputStream out) throws IOException {
        StreamUtils.copy(in, out);
    }

    public static void drain(InputStream in) throws IOException {
        StreamUtils.drain(in);
    }

}
