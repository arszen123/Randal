package game.map.create;

/**
 * Contains some Input validator to check if the user give as what we want.
 */
public class InputValidator {

    /**
     * Parse String array with size 2.
     *
     * @param size String number array.
     * @return The parsed array in integer
     */
    public static int[] parseMapSize(String size[]) {
        int[] mapSize = new int[2];
        try {
            mapSize[0] = Integer.parseInt(size[0]);
            mapSize[1] = Integer.parseInt(size[1]);
        } catch (Exception e) {
            mapSize = new int[]{0, 0};
        }
        return mapSize;
    }

    /**
     * Parse String array with size 2.
     *
     * @param size String number array.
     * @return The parsed array in integer
     */
    public static int[] parseStartPosition(String size[]) {
        return parseMapSize(size);
    }

    /**
     * Checks the map size is greater or equal then 6x6
     *
     * @param size map size.
     * @return true if map size is valid.
     */
    public static boolean isMapSizeValid(int[] size) {
        return size.length == 2 && size[0] >= 6 && size[1] >= 6;
    }

}
