package pri.smilly.demo.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;

public class CollectionUtil {

    public static boolean isEmpty(Collection coll) {
        return CollectionUtils.isEmpty(coll);
    }

    public static <T> Collection<T> diff(Collection<T> left, Collection<T> right) {
        return CollectionUtils.subtract(left, right);
    }

    public static <T> Collection<T> union(Collection<T> left, Collection<T> right) {
        return CollectionUtils.union(left, right);
    }

    public static <T> Collection<T> same(Collection<T> left, Collection<T> right) {
        return CollectionUtils.retainAll(left, right);
    }
}
