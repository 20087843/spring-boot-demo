package pri.smilly.demo.util;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConverterUtil {
    private static ObjectMapper jsonUtil = new ObjectMapper();
    private static XmlMapper xmlUtil = new XmlMapper();

    static {
        jsonUtil.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
        xmlUtil.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
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

    public static String bean2Json(Object bean) throws Exception {
        return jsonUtil.writeValueAsString(bean);
    }

    public static Map<String, Object> bean2Map(Object bean) throws Exception {
        return jsonUtil.readValue(bean2Json(bean), Map.class);
    }

    public static <T> T jsonBean(Map map, Class<T> clazz) throws Exception {
        return jsonBean(bean2Json(map), clazz);
    }

    public static <T> T jsonBean(String json, Class<T> clazz) throws Exception {
        return jsonUtil.readValue(json, clazz);
    }

    public static <T> List<T> json2List(String json, Class<T> clazz) throws Exception {
        List<T> list = new ArrayList<>();
        for (Object item : jsonBean(json, List.class)) {
            list.add(jsonBean(bean2Json(item), clazz));
        }
        return list;
    }

    public static String bean2Xml(Object object) throws Exception {
        return xmlUtil.writeValueAsString(object);
    }

    public static <T> T xml2Bean(String xml, Class<T> clazz) throws IOException {
        return xmlUtil.readValue(xml, clazz);
    }

    public static <T> T xml2Bean(File file, Class<T> clazz) throws IOException {
        return xmlUtil.readValue(file, clazz);
    }
}
