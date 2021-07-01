package cg;

import ast.nodes.attribute.CPModelAttributeNode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TemplateScriptsGenerator {

    private StringBuilder scriptBuilder = new StringBuilder();

    List<String> generateScripts() {
        List<String> scripts = new ArrayList<>();
        scripts.add(getTemplates());
        return scripts;
    }

    private String getTemplates() {
        try {
            File cpModelScriptTemplate = new File("src/cg/template/template.js");
            Scanner reader = new Scanner(cpModelScriptTemplate);
            while (reader.hasNextLine()) scriptBuilder.append(reader.nextLine());
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return scriptBuilder.toString();
    }
}
