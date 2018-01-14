package pri.smilly.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {

    public static void copy(InputStream in, OutputStream out) throws IOException {
        IOUtils.copy(in, out);
    }


}
