package ua.social.network.util;

import java.util.List;
import java.util.function.BiFunction;

/**
 * @author Mykola Yashchenko
 */
public class ListUtils {

    public static <T1, T2> boolean isEquals(List<? extends T1> list1, List<? extends T2> list2, BiFunction<T1, T2, Boolean> comparator) {
        if (list1 == list2) {
            return true;
        }

        if (list1 == null || list2 == null || list1.size() != list2.size()) {
            return false;
        }

        for (T1 object1 : list1) {
            boolean isFound = false;
            for (T2 object2 : list2) {
                if (isFound = comparator.apply(object1, object2)) {
                    break;
                }
            }
            if (!isFound) {
                return false;
            }
        }

        return true;
    }
}
