package ast.nodes.html;

import ast.nodes.expression.ExpressionNode;

/**
 * Variable scope, ex: {{ x }}, {{ x > 2 ? 's' : 's1' }}
 */
public class VariableScopeExpressionNode extends HTMLElementNode {
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
}
