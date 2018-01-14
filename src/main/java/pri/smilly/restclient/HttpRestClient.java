package pri.smilly.restclient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import pri.smilly.restclient.handler.HttpRestBaseHandler;
import pri.smilly.restclient.handler.HttpRestDefaultHandler;
import pri.smilly.restclient.handler.HttpRestMultiPartHandler;
import pri.smilly.restclient.param.HttpRestBasicParam;
import pri.smilly.restclient.param.HttpRestHeader;
import pri.smilly.restclient.param.HttpRestMultipartParam;
import pri.smilly.restclient.param.HttpRestUrlParam;

import java.io.File;

public class HttpRestClient {
    private static final int defaultTimeout = 1000 * 60 * 5;

    public static HttpRestResult doGet(String url, HttpRestUrlParam param) {
        return execute(url, new HttpGet(), defaultTimeout, new HttpRestDefaultHandler(), param);
    }

    public static HttpRestResult doDelete(String url, HttpRestUrlParam param) {
        return execute(url, new HttpGet(), defaultTimeout, new HttpRestDefaultHandler(), param);
    }

    public static HttpRestResult doPost(String url, HttpRestBasicParam... params) {
        return execute(url, new HttpPost(), defaultTimeout, new HttpRestDefaultHandler(), params);
    }

    public static HttpRestResult doPut(String url, HttpRestBasicParam... params) {
        return execute(url, new HttpPut(), defaultTimeout, new HttpRestDefaultHandler(), params);
    }

    public static HttpRestResult doPatch(String url, HttpRestBasicParam... params) {
        return execute(url, new HttpPatch(), defaultTimeout, new HttpRestDefaultHandler(), params);
    }

    public static HttpRestResult doUpload(String url, HttpRestMultipartParam param) {
        return execute(url, new HttpPost(), defaultTimeout, new HttpRestDefaultHandler(), param);
    }

    public static HttpRestResult doDownload(String url, HttpRestUrlParam param, File target) {
        return execute(url, new HttpGet(), defaultTimeout, new HttpRestMultiPartHandler(target), param);
    }

    private static HttpRestResult execute(String url, HttpRequestBase method, int timeout, HttpRestBaseHandler handler, HttpRestBasicParam... params) {
        try {
            if (params != null && params.length > 0) {
                for (HttpRestBasicParam param : params) {
                    param.getHeaders().forEach(method::addHeader);
                    if (param instanceof HttpRestUrlParam) {
                        url = url + "?" + param.toString();
                    } else if (method instanceof HttpEntityEnclosingRequestBase) {
                        ((HttpEntityEnclosingRequestBase) method).setEntity(param.getEntity());
                    }
                }
            }

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(timeout)
                    .build();
            method.setConfig(requestConfig);

            CloseableHttpClient client = HttpClientBuilder.create().build();
            CloseableHttpResponse response = client.execute(method);

            return HttpRestResult.builder()
                    .url(url)
                    .status(response.getStatusLine().getStatusCode())
                    .header(HttpRestHeader.instance().addHeaders(response.getAllHeaders()))
                    .message(handler.handle(response).toString())
                    .method(method.getMethod())
                    .build();
        } catch (Exception e) {
            return HttpRestResult.builder()
                    .url(url)
                    .status(400)
                    .message(e.getMessage())
                    .method(method.getMethod())
                    .error(e)
                    .build();
        }

    }

}
