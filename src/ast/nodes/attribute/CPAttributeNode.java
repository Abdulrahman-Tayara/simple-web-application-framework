package ast.nodes.attribute;

import ast.nodes.html.HTMLTagNode;
import cg.scripting.ScriptCode;
import cg.scripting.ScriptableNode;

public abstract class CPAttributeNode<T> extends AttributeNode<T> implements ScriptableNode {

    @Override
    public ScriptCode generateScript(HTMLTagNode tag) {
        return new ScriptCode();
    }

}
