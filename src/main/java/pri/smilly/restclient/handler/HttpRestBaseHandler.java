package pri.smilly.restclient.handler;

import org.apache.http.HttpResponse;

import java.io.IOException;

public interface HttpRestBaseHandler<T> {

    T handle(HttpResponse response) throws IOException;
}
