package ast.nodes.html;

import ast.nodes.attribute.AttributeNode;
import ast.nodes.attribute.HTMLAttributeNode;
import ast.nodes.expression.ExpressionNode;
import ast.nodes.expression.value.ValuableNode;
import cg.scripting.ScriptCode;
import cg.scripting.ScriptableNode;

import static cg.template.FunctionCalls.THIS_DOT;
import static cg.template.FunctionCalls.bindVariableUsageWithVariable;

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
        ScriptCode scriptCode = new ScriptCode();

        StringBuilder builder = new StringBuilder();
        String tagId = tag.getId();

        if (tagId.equals("")) {
            throw new RuntimeException("id not found in tag, tried to generate code for variable usage and no id found in tag");
        }

        builder.append(bindVariableUsageWithVariable)
                .append('(')
                .append("\"").append(tagId).append("\", ")
                .append("\"").append(scopeExpression.toHtml()).append("\"")
                .append(')')
                .append(';');

        scriptCode.addLine(builder.toString());
        return scriptCode;
    }
}
