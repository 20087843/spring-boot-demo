package pri.smilly.demo.restclient.handler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import pri.smilly.demo.util.StreamUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class HttpRestMultiPartHandler implements HttpRestBaseHandler<String> {
    private File target;

    public HttpRestMultiPartHandler(File target) {
        this.target = target;
    }

    @Override
    public String handle(HttpResponse response) throws IOException {
        if (target.isDirectory()) {
            if (!target.exists()) {
                target.mkdirs();
            }

            target = new File(target, parserFileName(response));
        }

        HttpEntity entity = response.getEntity();
        if (entity == null) {
            throw new IOException("response entity is null");
        }

        try (BufferedInputStream in = new BufferedInputStream(entity.getContent());
             FileOutputStream out = new FileOutputStream(target)) {
            StreamUtil.copy(in, out);
        }

        return target.getAbsolutePath();
    }

    private String parserFileName(HttpResponse response) throws IOException {
        Header contentDescHeader = response.getLastHeader("Content-Disposition");
        if (contentDescHeader != null) {
            String contentDesc = contentDescHeader.getValue();
            return contentDesc.substring(contentDesc.indexOf("filename=") + 9);
        }

        throw new IOException("file name not found in response");
    }
}
