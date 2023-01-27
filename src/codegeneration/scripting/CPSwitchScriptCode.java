package cg.scripting;

import ast.nodes.attribute.CPSwitchCaseAttributeNode;
import ast.nodes.attribute.CPSwitchDefaultAttributeNode;
import ast.nodes.expression.value.ValuableNode;
import ast.nodes.html.HTMLTagNode;
import com.google.gson.annotations.SerializedName;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

public class CPSwitchScriptCode extends ScriptCode {

    private final HTMLTagNode tag;
    private final ValuableNode expression;

    public CPSwitchScriptCode(HTMLTagNode tag, ValuableNode expression) {
        this.tag = tag;
        this.expression = expression;
        generateCode();
    }

    private void generateCode() {
        String call = Utils.readCpCallFile("cp_switch_call");

        List<SwitchCase> cases = fetchCases();

        String line = String.format(call, expression.toHtml(), Utils.toJson(cases));

        addLine(line);
    }

    private List<SwitchCase> fetchCases() {
        List<SwitchCase> cases = new ArrayList<>();

        tag.getContent().forEach(element -> {
            if (element instanceof HTMLTagNode) {
                SwitchCase switchCase = fetchCase((HTMLTagNode) element);

                if (switchCase != null)
                    cases.add(switchCase);
            }
        });

        return cases;
    }

    private SwitchCase fetchCase(HTMLTagNode tag) {
        int caseIndex = tag.indexOf(CPSwitchCaseAttributeNode.class);

        if (caseIndex >= 0) {
            CPSwitchCaseAttributeNode caseNode = (CPSwitchCaseAttributeNode) tag.getAttribute(caseIndex);
            return new SwitchCase(tag.getId(), caseNode.getValue());
        } else {
            int defaultCaseIndex = tag.indexOf(CPSwitchDefaultAttributeNode.class);
            if (defaultCaseIndex >= 0)
                return new SwitchCase(tag.getId(), null);
        }

        return null;
    }



    private static class SwitchCase {
        @SerializedName("childId")
        private final String tagId;
        @SerializedName("case")
        private final String expression;

        public SwitchCase(String tagId, ValuableNode expression) {
            this.tagId = tagId;
            this.expression = expression != null ? expression.toHtml() : "null";
        }
    }
}
