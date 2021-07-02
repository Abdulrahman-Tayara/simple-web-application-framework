package ast.nodes.expression.value.literal;

import ast.nodes.expression.value.IndexableNode;
import ast.nodes.expression.value.ValuableNode;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Array definition, ex: [1, 's', true, ...]
 */
public class ArrayNode extends LiteralExpressionNode<List<ArrayNode.ArrayItemNode>>
        implements IndexableNode {


    public static class ArrayItemNode {
        @SerializedName("array item value")
        private ValuableNode value;

        public ValuableNode getValue() {
            return value;
        }

        public void setValue(ValuableNode value) {
            this.value = value;
        }
    }

    @Override
    public String toHtml() {
        StringBuilder builder = new StringBuilder();
        value.forEach(val -> builder.append(val).append(", "));
        builder.setLength(builder.length() - 1);
        return '[' + builder.toString() + ']';
    }
}
