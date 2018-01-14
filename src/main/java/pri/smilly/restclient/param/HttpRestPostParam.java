package pri.smilly.restclient.param;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HttpRestPostParam extends HttpRestBasicParam {
    private Charset charset = StandardCharsets.UTF_8;

    public HttpRestPostParam() {
        addHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + charset);
    }

    @Override
    public HttpEntity getEntity() {
        List<NameValuePair> list = new ArrayList<>();
        params.forEach((key, val) -> list.add(new BasicNameValuePair(key, String.valueOf(val))));
        return new UrlEncodedFormEntity(list, charset);
    }
}
