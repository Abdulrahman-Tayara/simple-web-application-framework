package util;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Utils {

    public static String readFile(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            return "";
        }
    }

    public static String readCpCallFile(String fileName) {
        return readFile("src/cg/template/cpcalls/" + fileName + (fileName.endsWith(".txt") ? "" : ".txt"));
    }

    public static String toJson(Object o) {
        return new Gson().toJson(o);
    }
}
