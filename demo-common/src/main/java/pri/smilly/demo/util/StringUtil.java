package pri.smilly.demo.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    public static boolean isNotBlank(String str) {
        return StringUtils.isNotBlank(str);
    }

    public static String replace(String str, String pattern, String replace) {
        Pattern ptn = Pattern.compile(pattern);
        Matcher mtc = ptn.matcher(str);
        while (mtc.find()) {
            String old = mtc.group();
            str.replace(old, replace);
        }
        return str;
    }

}
