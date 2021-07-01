package ast.nodes.attribute;

import ast.nodes.expression.condition.LogicalNode;
import ast.nodes.html.HTMLTagNode;
import cg.scripting.ScriptCode;
import cg.scripting.ScriptableNode;

/**
 * For attribute that accepts only conditional expression
 */
public abstract class CPConditionalAttributeNode extends CPAttributeNode<LogicalNode> {

}
