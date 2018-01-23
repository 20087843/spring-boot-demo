package pri.smilly.demo.util;

import java.util.Objects;

public class CommonUtil {

    public static boolean isNull(Object object) {
        return Objects.isNull(object);
    }

    public static boolean isNotNull(Object object) {
        return !Objects.nonNull(object);
    }
}
