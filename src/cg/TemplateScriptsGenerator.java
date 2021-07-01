package cg;

import ast.nodes.attribute.CPModelAttributeNode;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TemplateScriptsGenerator {

    private StringBuilder scriptBuilder = new StringBuilder();

    List<String> generateScripts(List<String> classesUsed) {
        List<String> scripts = new ArrayList<>();
        classesUsed.forEach(nodeClassName -> scripts.add(generateCorrespondingScript(nodeClassName)));
        return scripts;
    }

    private String generateCorrespondingScript(String nodeClassName) {
        if (nodeClassName.equals(CPModelAttributeNode.class.getSimpleName())) {
            return getCpModelScript();
        } //todo implement others
//        throw new NotImplementedException();
        return "";
    }

    private String getCpModelScript() {
        try {
            File cpModelScriptTemplate = new File("src/cg/template/cp-model.html");
            Scanner reader = new Scanner(cpModelScriptTemplate);
            while (reader.hasNextLine()) scriptBuilder.append(reader.nextLine());
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String result = scriptBuilder.toString();
        scriptBuilder.setLength(0);

//        BufferedReader br = new BufferedReader(new FileReader("file.txt"));
//        try {
//            StringBuilder sb = new StringBuilder();
//            String line = br.readLine();
//
//            while (line != null) {
//                sb.append(line);
//                sb.append(System.lineSeparator());
//                line = br.readLine();
//            }
//            String everything = sb.toString();
//        } finally {
//            br.close();
//        }
        return result;
    }
}
