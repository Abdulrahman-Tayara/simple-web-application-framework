package ast.nodes.attribute;

import ast.nodes.html.HTMLTagNode;
import cg.scripting.CPHideScriptCode;
import cg.scripting.ScriptCode;

public class CPHideAttributeNode extends CPConditionalAttributeNode implements Structural {
    @Override
    public ScriptCode generateScript(HTMLTagNode tag) {
        return new CPHideScriptCode(tag.getId(), getValue());
    }
}
