package ast.nodes.attribute;

import ast.nodes.html.HTMLTagNode;
import cg.scripting.CPIfScriptCode;
import cg.scripting.ScriptCode;

import static cg.template.FunctionCalls.addCpShowIdBinding;
import static cg.template.FunctionCalls.bindCpModelToVar;

public class CPIFAttributeNode extends CPConditionalAttributeNode implements Structural {

    @Override
    public ScriptCode generateScript(HTMLTagNode tag) {
        return new CPIfScriptCode(tag.getId(), getValue());
    }

}
