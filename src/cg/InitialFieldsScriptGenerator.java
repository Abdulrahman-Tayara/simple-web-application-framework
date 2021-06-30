package cg;

import java.util.List;

public class InitialFieldsScriptGenerator {

    StringBuilder builder = new StringBuilder();

    public String generateScript(List<String> cpsUsed) {
        builder.append("<script>");
        builder.append("\n" +
                "        let defaultBodyCode = document.body.innerHTML;\n" +
                "        let inits = [];\n" +
                "        let changes = [];\n" +
                "    ");
        cpsUsed.forEach(cpClassName -> builder.append(appendCorrespondingInitialField(cpClassName)));
        builder.append("</script>");
        return builder.toString();
    }

    private String appendCorrespondingInitialField(String className) {
        return "";
    }
}
