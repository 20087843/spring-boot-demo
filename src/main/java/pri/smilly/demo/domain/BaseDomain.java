package pri.smilly.demo.domain;

import lombok.SneakyThrows;
import pri.smilly.demo.util.ConverterUtil;

import java.io.Serializable;
import java.util.Map;

public abstract class BaseDomain<T extends BaseDomain<?>> implements Serializable {

    public String json() throws Exception {
        return ConverterUtil.json(this);
    }

    @SneakyThrows
    public Map<String, Object> map() {
        return ConverterUtil.map(this);
    }

}
