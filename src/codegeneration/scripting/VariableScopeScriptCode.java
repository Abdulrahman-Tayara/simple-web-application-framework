package cg.scripting;

import ast.nodes.expression.ExpressionNode;
import util.Utils;

public class VariableScopeScriptCode extends ScriptCode {

    private final String tagId;
    private final ExpressionNode expression;
    private final String content;

    public VariableScopeScriptCode(String tagId, ExpressionNode expression, String content) {
        this.tagId = tagId;
        this.expression = expression;
        this.content = content;
        generateCode();
    }

    private void generateCode() {
        String call = Utils.readCpCallFile("variable_scope_call");

        String line = String.format(call, tagId, expression.toHtml(), expression.toHtml());

        addLine(line);
    }
}
