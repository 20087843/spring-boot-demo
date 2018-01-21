package pri.smilly.demo.restclient.handler;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpRestDefaultHandler implements HttpRestBaseHandler<String> {
    private Charset charset = StandardCharsets.UTF_8;

    @Override
    public String handle(HttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity(), charset);
    }
}
