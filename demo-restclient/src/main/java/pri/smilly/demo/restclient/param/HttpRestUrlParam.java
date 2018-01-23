package pri.smilly.demo.restclient.param;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import pri.smilly.demo.exception.NotPermittedException;

import java.util.ArrayList;
import java.util.List;

public class HttpRestUrlParam extends HttpRestBasicParam {

    @Override
    public HttpEntity getEntity() throws NotPermittedException {
        throw new NotPermittedException("url param has no entity");
    }

    @Override
    public String toString() {
        List<NameValuePair> list = new ArrayList<>();
        params.forEach((key, val) -> list.add(new BasicNameValuePair(key, String.valueOf(val))));
        return URLEncodedUtils.format(list, HTTP.DEF_CONTENT_CHARSET);
    }
}
