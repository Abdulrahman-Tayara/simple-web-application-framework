package cg;

import java.util.List;

public class RenderScriptGenerator {

    private StringBuilder builder = new StringBuilder();

    public String generateRenderScripts(List<String> preRenderCalls, List<String> postRenderCalls) {
        builder.append("function render() {\n" +
                "      setInterval(() => {\n" +
                "\n");


        preRenderCalls.forEach(preRenderCall -> builder.append(preRenderCall));


        builder.append("                //render the changes of the cp-model (input=>variable) binding\n" +
                "                for (var i = 0; i < changes.length; i++) {\n" +
                "                    // console.log(changes[i]);\n" +
                "                    changes[i]();\n" +
                "                }\n" +
                "                changes = [];\n" +
                "\n" +
                "                //(variable => input) binding\n" +
                "                for (let index = 0; index < inits.length; index++) {\n" +
                "                    inits[index]();\n" +
                "                }\n" +
                "\n" +
                "\n" +
                "\n" +
                "                //render the fors\n" +
                "                for (let index = 0; index < forRenders.length; index++) {\n" +
                "                    forRenders[index]();\n" +
                "                }");


        postRenderCalls.forEach(postRenderCall -> builder.append(postRenderCall));


        builder.append("\n" +
                "\n" +
                "            }, 200);\n" +
                "        }\n" +
                "        render();\n");

        return builder.toString();
    }
}
