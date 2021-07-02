package ast.nodes.attribute;

import ast.nodes.html.HTMLTagNode;
import cg.scripting.CPShowScriptCode;
import cg.scripting.ScriptCode;

public class CPShowAttributeNode extends CPConditionalAttributeNode implements Structural {

    @Override
    public ScriptCode generateScript(HTMLTagNode tag) {
        return new CPShowScriptCode(tag.getId(), getValue());
    }
}
