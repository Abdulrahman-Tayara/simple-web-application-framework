package cg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InitialFieldsScriptGenerator {

    StringBuilder builder = new StringBuilder();

    public String generateScript() {
        try {
            File cpModelScriptTemplate = new File("src/cg/template/initial.js");
            Scanner reader = new Scanner(cpModelScriptTemplate);
            while (reader.hasNextLine()) builder.append(reader.nextLine());
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
