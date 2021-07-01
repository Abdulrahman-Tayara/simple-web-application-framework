package ast.nodes.attribute;

import ast.nodes.expression.value.ValuableNode;
import ast.nodes.html.HTMLTagNode;
import cg.scripting.ScriptCode;

import static cg.template.FunctionCalls.THIS_DOT;
import static cg.template.FunctionCalls.bindCpModelToVar;

public class CPModelAttributeNode extends CPAttributeNode<ValuableNode> {

    @Override
    public ScriptCode generateScript(HTMLTagNode tag) {
        ScriptCode code = new ScriptCode();

        if (!tag.getName().equals("input")) {
            return code;
        }

        String tagId = tag.getId();

        if (tagId == null || tagId.isEmpty())
            throw new RuntimeException("id not found in tag, tried to generate code for cp-model and no id found in tag");

        code.addLine(
                THIS_DOT + bindCpModelToVar + "(\"" + tagId + "\", \"" + getValue().toHtml() + "\");"
        );

        return code;
    }
}
