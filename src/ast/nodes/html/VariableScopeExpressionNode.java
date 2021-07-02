package ast.nodes.html;
import ast.nodes.expression.ExpressionNode;
import cg.scripting.ScriptCode;
import cg.scripting.ScriptableNode;
import cg.scripting.VariableScopeScriptCode;

/**
 * Variable scope, ex: {{ x }}, {{ x > 2 ? 's' : 's1' }}
 */
public class VariableScopeExpressionNode extends HTMLElementNode implements ScriptableNode {
    private ExpressionNode scopeExpression;

    public ExpressionNode getScopeValue() {
        return scopeExpression;
    }

    public void setScopeExpression(ExpressionNode scopeValues) {
        this.scopeExpression = scopeValues;
    }

    @Override
    public String toHtml() {
        return "{{" + scopeExpression.toHtml() + "}}";
    }

    @Override
    public ScriptCode generateScript(HTMLTagNode tag) {
        return new VariableScopeScriptCode(tag.getId(), scopeExpression);
    }
}
