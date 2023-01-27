package cg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RenderScriptGenerator {

    private StringBuilder scriptBuilder = new StringBuilder();

    public String getRenderScript() {
        try {
            File cpModelScriptTemplate = new File("src/cg/template/render.js");
            Scanner reader = new Scanner(cpModelScriptTemplate);
            while (reader.hasNextLine()) scriptBuilder.append(reader.nextLine());
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return scriptBuilder.toString();
    }
}
