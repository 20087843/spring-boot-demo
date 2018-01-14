package pri.smilly.restclient.param;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import pri.smilly.util.ConverterUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpRestJsonParam extends HttpRestBasicParam {
    private Charset charset = StandardCharsets.UTF_8;

    public HttpRestJsonParam() {
        addHeader("Content-Type", "application/json; charset=" + charset);
    }

    @Override
    public HttpEntity getEntity() throws Exception {
        return new StringEntity(ConverterUtil.json(params), charset);
    }
}
