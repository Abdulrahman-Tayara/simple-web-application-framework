package cg.scripting;

import ast.nodes.expression.value.variable.FunctionExpressionNode;
import util.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class CPEventScriptCode extends ScriptCode {
    private final String tagId;
    private final String eventName;
    private final FunctionExpressionNode callback;

    public CPEventScriptCode(String tagId, String eventName, FunctionExpressionNode callback) {
        this.tagId = tagId;
        this.eventName = eventName;
        this.callback = callback;
        generateCode();
    }

    private void generateCode() {

        String call = Utils.readCpCallFile("event_call");

        List<String> params = callback.getParams().stream().map(FunctionExpressionNode.FunctionParam::toHtml)
                .collect(Collectors.toList());

        String line = String.format(call, tagId, eventName, callback.getFunctionName(), Utils.toJson(params));

        addLine(line);
    }
}
