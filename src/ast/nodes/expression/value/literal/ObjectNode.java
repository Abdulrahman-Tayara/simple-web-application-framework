package ast.nodes.expression.value.literal;

import ast.nodes.expression.ExpressionNode;
import ast.nodes.expression.value.ValuableNode;
import ast.nodes.expression.value.ValueExpressionNode;

import java.util.List;

public class ObjectNode extends ValueExpressionNode {

    private List<ObjectItemNode> objectItemNodes;

    public List<ObjectItemNode> getObjectItemNodes() {
        return objectItemNodes;
    }

    public void setObjectItemNodes(List<ObjectItemNode> objectItemNodes) {
        this.objectItemNodes = objectItemNodes;
    }

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
