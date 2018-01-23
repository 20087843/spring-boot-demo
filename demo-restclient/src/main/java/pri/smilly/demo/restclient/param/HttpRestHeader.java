package pri.smilly.demo.restclient.param;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.*;

public class HttpRestHeader {
    private Map<String, String> headers = new HashMap<>();

    public static HttpRestHeader instance() {
        return new HttpRestHeader();
    }

    public List<Header> getHeadersList() {
        List<Header> list = new ArrayList<>();
        headers.forEach((key, val) -> list.add(new BasicHeader(key, val)));
        return list;
    }

    public Header[] getHeaders() {
        return getHeadersList().toArray(new Header[headers.size()]);
    }

    public HttpRestHeader addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public HttpRestHeader addHeaders(Map<String, String> headers) {
        headers.forEach(this::addHeader);
        return this;
    }

    public HttpRestHeader addHeaders(Header[] headers) {
        Arrays.asList(headers).forEach(header -> this.headers.put(header.getName(), header.getValue()));
        return this;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }
}
