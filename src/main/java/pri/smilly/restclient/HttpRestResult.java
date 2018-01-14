package pri.smilly.restclient;

import lombok.Builder;
import lombok.Getter;
import pri.smilly.restclient.param.HttpRestHeader;

@Getter
@Builder
public class HttpRestResult {

    private String url;
    private int status;
    private String message;
    private String method;
    private HttpRestHeader header;
    private Exception error;

    public boolean isSuccess() {
        return status >= 200 && status < 300;
    }

    @Override
    public String toString() {
        return status + " " + message;
    }
}
