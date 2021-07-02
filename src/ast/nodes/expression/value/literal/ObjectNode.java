package ast.nodes.expression.value.literal;

import ast.nodes.expression.value.ConcatableNode;
import ast.nodes.expression.value.IndexableNode;
import ast.nodes.expression.value.ValuableNode;

import java.util.Map;

/**
 * JS Object Node: {x: 3, y: [1, 3]}
 */
public class ObjectNode extends LiteralExpressionNode<Map<String, ValuableNode>>
        implements ConcatableNode, IndexableNode {

    public static class ObjectItemNode {
        private String key;
        private ValuableNode value;

        public String getKey() {
            return key;
        }

        public ValuableNode getValue() {
            return value;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setValue(ValuableNode value) {
            this.value = value;
        }
    }

    @Override
    public String toHtml() {
        StringBuilder builder = new StringBuilder();
        builder.append('{');
        value.forEach((key, value) -> builder.append(key).append(": ").append(value.toHtml()).append(","));
        builder.setLength(builder.length() - 1);
        builder.append('}');
        return builder.toString();
    }
}
