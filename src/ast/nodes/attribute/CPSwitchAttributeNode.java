package ast.nodes.attribute;

import ast.nodes.expression.value.ValuableNode;
import ast.nodes.html.HTMLTagNode;
import cg.scripting.CPSwitchScriptCode;
import cg.scripting.ScriptCode;

public class CPSwitchAttributeNode extends CPAttributeNode<ValuableNode> {

    @Override
    public ScriptCode generateScript(HTMLTagNode tag) {
        return new CPSwitchScriptCode(tag, getValue());
    }
}
