package ast.nodes.expression.value.literal;

import ast.nodes.expression.value.ConcatableNode;
import ast.nodes.expression.value.ValuableNode;
import ast.nodes.expression.value.ValueExpressionNode;

import java.util.List;

/**
 * JS Object Node: {x: 3, y: [1, 3]}
 */
public class ObjectNode extends LiteralExpressionNode<List<ObjectNode.ObjectItemNode>> implements ConcatableNode {

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
}
