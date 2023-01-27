package cg.scripting;

import ast.nodes.expression.value.ValuableNode;
import util.Utils;

public class CPModelScriptCode extends ScriptCode {

    private final String tagId;
    private final ValuableNode expression;

    public CPModelScriptCode(String tagId, ValuableNode expression) {
        this.tagId = tagId;
        this.expression = expression;
        generateCode();
    }

    private void generateCode() {
        String call = Utils.readCpCallFile("cp_model_call");

        String line = String.format(call, tagId, expression.toHtml());

        addLine(line);
    }
}
