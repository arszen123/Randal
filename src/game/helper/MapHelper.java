package game.helper;

import game.map.field.*;

public class MapHelper {

    public static Field getField(String field) {
        Field result = null;
        if (field.equals((new Water()).drawingElement())) {
            result = new Water();
        }
        if (field.equals((new Station()).drawingElement())) {
            result = new Station();
        }
        if (field.equals((new Prairie()).drawingElement())) {
            result = new Prairie();
        }
        if (field.equals((new Rock()).drawingElement())) {
            result = new Rock();
        }
        if (field.equals((new Road()).drawingElement())) {
            result = new Road();
        }
        return result;
    }

    public static Field[][] initMapFieldsFromFile(String[] mapInString, int[] mapSize) {
        Field[][] map = new Field[mapSize[0]][mapSize[1]];
        for (int i = 0; i < mapInString.length; i++) {
            for (int j = 0; j < mapInString[i].length(); j++) {
                map[i][j] = MapHelper.getField(mapInString[i].charAt(j) + "");
            }
        }
        return map;
    }

    public static boolean isStartPositionValid(Field[][] map, int[] startPosition, int[] mapSize) {
        return (startPosition[0] >= 0 && startPosition[1] >= 0) &&
                (startPosition[0] < mapSize[0] && startPosition[1] < mapSize[1]) &&
                (map[startPosition[0]][startPosition[1]] instanceof Station);
    }
}
