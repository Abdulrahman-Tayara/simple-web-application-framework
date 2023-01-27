package cg.scripting;

import ast.nodes.expression.value.IndexableNode;
import util.Utils;

import java.util.List;

public class CPForScriptCode extends ScriptCode {

    private final String tagId;
    private final IndexableNode expression;
    private final List<String> iterators;

    public CPForScriptCode(String tagId, IndexableNode expression, List<String> iterators) {
        this.tagId = tagId;
        this.expression = expression;
        this.iterators = iterators;
        generateCode();
    }

    private void generateCode() {
        String call = Utils.readCpCallFile("cp_for_call");

        String line = String.format(
                call,
                tagId,
                expression.toHtml(),
                iterators.get(0),
                iterators.size() > 1 ? ("\"" + iterators.get(1) + "\"") : null
        );

        addLine(line);
    }

}
