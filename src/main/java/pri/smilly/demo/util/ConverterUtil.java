package pri.smilly.demo.util;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConverterUtil {
    private static ObjectMapper jsonUtil = new ObjectMapper();

    static {
        jsonUtil.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
    }

    public static void copyProps(Object target, Object source) throws Exception {
        BeanUtils.copyProperties(target, source);
    }

    public static <A, B> A convert(B source, Class<A> clazz) throws Exception {
        A target = clazz.newInstance();
        BeanUtilsBean.getInstance().copyProperties(target, source);
        return target;
    }

    public static <A, B> List<A> convert(List<B> source, Class<A> clazz) throws Exception {
        List<A> list = new ArrayList<>();
        for (B item : source) {
            list.add(convert(item, clazz));
        }
        return list;
    }

    public static String json(Object bean) throws Exception {
        return jsonUtil.writeValueAsString(bean);
    }

    public static Map<String, Object> map(Object bean) throws Exception {
        return jsonUtil.readValue(json(bean), Map.class);
    }

    public static <T> T parserBean(Map map, Class<T> clazz) throws Exception {
        return parserBean(json(map), clazz);
    }

    public static <T> T parserBean(String json, Class<T> clazz) throws Exception {
        return jsonUtil.readValue(json, clazz);
    }

    public static <T> List<T> parserList(String json, Class<T> clazz) throws Exception {
        List<T> list = new ArrayList<>();
        for (Object item : parserBean(json, List.class)) {
            list.add(parserBean(json(item), clazz));
        }
        return list;
    }
}
