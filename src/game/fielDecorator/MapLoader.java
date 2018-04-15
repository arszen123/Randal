package game.fielDecorator;

import game.fielDecorator.baseDecorator.FileObjectDecorator;
import game.map.Map;

public class MapLoader extends FileObjectDecorator{

    public MapLoader() {
        super.dirName = "maps";
    }

    public Map loadObject(String fileName) {
        return (Map) super.loadObject(fileName);
    }
}
