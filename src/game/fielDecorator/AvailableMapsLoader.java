package game.fielDecorator;

import game.fielDecorator.baseDecorator.FileObjectDecorator;

import java.util.ArrayList;

public class AvailableMapsLoader extends FileObjectDecorator {

    public AvailableMapsLoader() {
        super.dirName = "game";
    }

    public ArrayList<String> loadObject(String fileName) {
        return (ArrayList<String>) super.loadObject(fileName);
    }
}
