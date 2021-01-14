package ast.nodes.html;

import ast.nodes.expression.ExpressionNode;

/**
 * Variable scope, ex: {{ x }}, {{ x > 2 ? 's' : 's1' }}
 */
public class VariableScopeExpressionNode extends HTMLElementNode {
    private ExpressionNode scopeValue;

    public ExpressionNode getScopeValue() {
        return scopeValue;
    }

    public void setScopeValue(ExpressionNode scopeValue) {
        this.scopeValue = scopeValue;
    }
}
