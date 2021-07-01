package ast.nodes.attribute;

import ast.nodes.html.HTMLTagNode;
import cg.scripting.ScriptCode;

import static cg.template.FunctionCalls.addCpHideIdBinding;

public class CPHideAttributeNode extends CPConditionalAttributeNode implements Structural {
    @Override
    public ScriptCode generateScript(HTMLTagNode tag) {
        ScriptCode code = new ScriptCode();

        String tagId = tag.getId();

        if (tagId == null || tagId.isEmpty())
            throw new RuntimeException("id not found in tag, tried to generate code for cp-model and no id found in tag");

        code.addLine(
                addCpHideIdBinding + "(\"" + tagId + "\", \"" + getValue().toHtml() + "\");"
        );

        return code;
    }
}
