package pri.smilly.domain;

import lombok.SneakyThrows;
import pri.smilly.util.ConverterUtil;

import java.io.Serializable;
import java.util.Map;

public abstract class BaseDomain implements Serializable {

    public String json() throws Exception {
        return ConverterUtil.json(this);
    }

    @SneakyThrows
    public Map<String, Object> map() {
        return ConverterUtil.map(this);
    }

}
