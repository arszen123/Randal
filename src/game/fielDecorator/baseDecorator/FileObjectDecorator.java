package game.fielDecorator.baseDecorator;

import game.fielDecorator.MapLoader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileObjectDecorator {

    private String basePath = MapLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "data/";
    protected String dirName;

    public Object loadObject(String fileName) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object obj = null;
        try {
            fis = new FileInputStream(basePath + dirName + "/" + fileName);
            ois = new ObjectInputStream(fis);
            obj = ois.readObject();
        } catch (Exception e) {
            System.out.println("Failed: " + e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e){

                }
            }
            if(ois != null) {
                try {
                    ois.close();
                } catch (Exception e){

                }
            }
        }
        return obj;
    }

    public boolean saveObject(String fileName, Object obj) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        boolean success = true;
        try {
            fos = new FileOutputStream(basePath + dirName + "/" + fileName);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
        } catch (Exception e) {
            success = false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e){

                }
            }
            if(oos != null) {
                try {
                    oos.close();
                } catch (Exception e){

                }
            }
        }
        return success;
    }

}
