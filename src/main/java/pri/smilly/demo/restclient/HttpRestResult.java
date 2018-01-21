package pri.smilly.demo.restclient;

import lombok.Builder;
import lombok.Getter;
import pri.smilly.demo.restclient.param.HttpRestHeader;

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

    public boolean isError() {
        return error == null;
    }

    public boolean isEmptyMsg() {
        return message == null || "".equals(message);
    }

    @Override
    public String toString() {
        return status + " " + message;
    }
}
