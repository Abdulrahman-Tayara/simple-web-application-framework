package ast.nodes.attribute;

import ast.nodes.expression.value.ValuableNode;
import ast.nodes.html.HTMLTagNode;
import cg.scripting.CPModelScriptCode;
import cg.scripting.ScriptCode;

public class CPModelAttributeNode extends CPAttributeNode<ValuableNode> {

    @Override
    public ScriptCode generateScript(HTMLTagNode tag) {
        if (!tag.getName().equals("input")) {
            return new ScriptCode();
        }

        return new CPModelScriptCode(tag.getId(), getValue());
    }
}
