package ast.nodes.attribute;

import ast.nodes.expression.value.IndexableNode;
import ast.nodes.expression.value.ValuableNode;
import ast.nodes.html.HTMLTagNode;
import cg.scripting.CPForScriptCode;
import cg.scripting.ScriptCode;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class CPForAttributeNode extends CPAttributeNode<CPForAttributeNode.ForExpressionNode> implements Structural {

    @Override
    public ScriptCode generateScript(HTMLTagNode tag) {
        return new CPForScriptCode(tag.getId(), getValue().value, getIterators());
    }

    private List<String> getIterators() {
        List<String> iterators = new ArrayList<>();
        if (getValue().variableName != null) {
            iterators.add(getValue().variableName);
        } else {
            iterators.add(getValue().pair.key);
            iterators.add(getValue().pair.value);
        }

        return iterators;
    }

    public static class ForExpressionNode {
        @SerializedName("iterator")
        private String variableName;
        @SerializedName("pair iterator")
        private ForPairNode pair;
        @SerializedName("iterated value")
        private IndexableNode value;
        private ForIndexExpressionNode index;

        public void setVariableName(String variableName) {
            this.variableName = variableName;
        }

        public void setPair(ForPairNode pair) {
            this.pair = pair;
        }

        public void setValue(IndexableNode value) {
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
