package pri.smilly.demo.restclient.param;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.File;

public class HttpRestMultipartParam extends HttpRestBasicParam {

    public HttpRestMultipartParam() {
        addHeader("Content-Type", "multipart/form-data");
    }

    @Override
    public HttpEntity getEntity() {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        params.forEach((key, val) -> {
            if (val instanceof File) {
                builder.addBinaryBody(key, (File) val);
            } else {
                builder.addTextBody(key, String.valueOf(val));
            }
        });
        return builder.build();
    }
}
