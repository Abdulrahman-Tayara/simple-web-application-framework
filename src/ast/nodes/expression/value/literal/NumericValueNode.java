package ast.nodes.expression.value.literal;

/**
 * Literal Numeric Value, ex: 1, 100, 1.2
 */
public class NumericValueNode extends LiteralExpressionNode<Float> {
    @Override
    public String toHtml() {
        float fValue = value;
        if (fValue == (int) fValue)
            return String.format("%d", (int) fValue);
        else
            return String.format("%s", fValue);
    }
}
