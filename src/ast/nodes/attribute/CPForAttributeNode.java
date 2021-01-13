package ast.nodes.attribute;

import ast.nodes.expression.value.IterableNode;
import ast.nodes.expression.value.ValuableNode;
import com.google.gson.annotations.SerializedName;


public class CPForAttributeNode extends CPAttributeNode<CPForAttributeNode.ForExpressionNode> {


    public static class ForExpressionNode {
        @SerializedName("iterator")
        private String variableName;
        @SerializedName("pair iterator")
        private ForPairNode pair;
        @SerializedName("iterated value")
        private IterableNode value;
        private ForIndexExpressionNode index;

        public void setVariableName(String variableName) {
            this.variableName = variableName;
        }

        public void setPair(ForPairNode pair) {
            this.pair = pair;
        }

        public void setValue(IterableNode value) {
            this.value = value;
        }

        public void setIndex(ForIndexExpressionNode index) {
            this.index = index;
        }

        public static class ForIndexExpressionNode {
            private String indexName;
            private ValuableNode indexValue;

            public void setIndexName(String indexName) {
                this.indexName = indexName;
            }

            public void setIndexValue(ValuableNode indexValue) {
                this.indexValue = indexValue;
            }
        }
    }

    public static class ForPairNode {
        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
