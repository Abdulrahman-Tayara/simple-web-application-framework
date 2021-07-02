package cg.scripting;

import ast.nodes.expression.condition.LogicalNode;
import util.Utils;

public class CPIfScriptCode extends ScriptCode {

    private final String tagId;
    private final LogicalNode expression;

    public CPIfScriptCode(String tagId, LogicalNode expression) {
        this.tagId = tagId;
        this.expression = expression;
        generateCode();
    }

    private void generateCode() {
        String call = Utils.readCpCallFile("cp_if_call");

        String line = String.format(call, tagId, expression.toHtml());

        addLine(line);
    }

}
