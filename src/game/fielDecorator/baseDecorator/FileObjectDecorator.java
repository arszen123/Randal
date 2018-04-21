package game.fielDecorator.baseDecorator;

import game.fielDecorator.MapLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileObjectDecorator {

    private final String basePath = MapLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "data/";
    protected String dirName;

    /**
     * Loads saved object from file
     *
     * @param fileName Where to load the Object
     * @return The saved Object
     */
    public Object loadObject(String fileName) {
        Object obj = null;
        if (isDirectoryAccessable()) {
            try (FileInputStream fis = new FileInputStream(basePath + dirName + "/" + fileName); ObjectInputStream ois = new ObjectInputStream(fis)) {
                obj = ois.readObject();
            } catch (Exception e) {

            }
        }
        return obj;
    }

    /**
     * Write Object to file
     *
     * @param fileName The name of the file where to write the object
     * @param obj      The object to write.
     * @return true if success
     */
    public boolean saveObject(String fileName, Object obj) {
        boolean success = true;
        if (isDirectoryAccessable()) {
            try (FileOutputStream fos = new FileOutputStream(basePath + dirName + "/" + fileName); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(obj);
            } catch (Exception e) {
                success = false;
            }
        }
        return success;
    }

    private boolean isDirectoryAccessable() {
        try {
            if (Files.notExists(Paths.get(basePath + dirName))) {
                new File(basePath + dirName).mkdirs();
            }
            return true;
        } catch (Exception e) {
            System.out.println(basePath + dirName + " is not writable/readable or it's not exist.");
        }
        return false;
    }

}
