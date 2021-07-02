package ast.nodes.attribute;

import ast.nodes.expression.value.variable.FunctionExpressionNode;
import ast.nodes.html.HTMLTagNode;
import cg.scripting.CPEventScriptCode;
import cg.scripting.ScriptCode;

public class EventAttributeNode extends CPAttributeNode<FunctionExpressionNode> {

    public static final String EVENTS_FILE_DIR = "src/ast/nodes/attribute/events.txt";


    @Override
    public ScriptCode generateScript(HTMLTagNode tag) {
        return new CPEventScriptCode(tag.getId(), getName(), getValue());
    }
}
