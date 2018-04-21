package game.helper;

public class ArrayHelper {

    public static boolean contains(Object[] objs, Object obj) {
        for (Object object : objs) {
            if (object.equals(obj)) {
                return true;
            }
        }
        return false;
    }

}
