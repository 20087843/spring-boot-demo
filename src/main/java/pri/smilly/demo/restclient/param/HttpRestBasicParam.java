package pri.smilly.demo.restclient.param;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import pri.smilly.demo.domain.BaseDomain;
import pri.smilly.demo.util.ConverterUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class HttpRestBasicParam {
    private HttpRestHeader header = new HttpRestHeader();
    protected Map<String, Object> params = new HashMap<>();

    public List<Header> getHeaders() {
        return header.getHeadersList();
    }

    public HttpRestBasicParam addHeader(String key, String value) {
        header.addHeader(key, value);
        return this;
    }

    public HttpRestBasicParam addHeaders(Map<String, String> headers) {
        header.addHeaders(headers);
        return this;
    }

    public HttpRestBasicParam addHeaders(Header[] headers) {
        header.addHeaders(headers);
        return this;
    }

    public HttpRestBasicParam addParam(String key, Object val) {
        params.put(key, val);
        return this;
    }

    public HttpRestBasicParam addParams(Map<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    public HttpRestBasicParam addParams(BaseDomain domain) {
        this.params.putAll(domain.map());
        return this;
    }

    public HttpRestBasicParam addParams(String jsonObject) throws Exception {
        this.params.putAll(ConverterUtil.parserBean(jsonObject, Map.class));
        return this;
    }

    public abstract HttpEntity getEntity() throws Exception;

}
