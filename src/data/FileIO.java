package data;

import com.sun.istack.internal.NotNull;
import core.SeriesContainer;
import util.Util;

import java.io.*;
import java.util.logging.Level;

public class FileIO {
    private static final String filepath = "src/data/";

    public static <T extends Serializable> void save(@NotNull T object) throws IOException {
        String completePath = filepath + object.getClass().getSimpleName();
        FileOutputStream fileOut = new FileOutputStream(completePath);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(object);
        out.close();
        Util.logger.log(Level.INFO, "{0} successfully saved at {1}", new Object[]{object.getClass().getSimpleName(), completePath});
    }

    public static <T extends Serializable> T load(@NotNull Class c) throws IOException {
        T obj = null;
        try {
            String completePath = filepath + c.getSimpleName();
            FileInputStream fileIn = new FileInputStream(completePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            obj = (T) in.readObject();
            in.close();
            Util.logger.log(Level.INFO, "{0} successfully loaded at {1}", new Object[]{c.getSimpleName(), completePath});
        } catch (ClassNotFoundException e) {
            Util.logger.log(Level.SEVERE, "An unexpected {0} occured: {1}", new Object[]{e.getClass(), e.getMessage()});
        }
        return obj;
    }

    public static <T extends Serializable> boolean exists(Class c) {
        String completePath = filepath + c.getSimpleName();
        return new File(completePath).exists();
    }
}
