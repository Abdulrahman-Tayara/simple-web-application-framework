package symantec_check;

import ast.nodes.Node;
import ast.nodes.expression.PipeExpressionNode;
import ast.nodes.expression.value.literal.StringValueNode;
import ast.nodes.html.*;

import java.util.ArrayList;
import java.util.List;

public class PipesChecker {

    List<Exception> exceptions = new ArrayList<>();

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public void check(Node node) {
        if (node instanceof HtmlDocumentNode) {
            HtmlDocumentNode htmlDocNode = ((HtmlDocumentNode) node);
            for (HTMLElementNode elementNode : htmlDocNode.getElements()) {
                check(elementNode);
            }
        } else if (node instanceof HTMLTagNode) {
            HTMLTagNode tagNode = ((HTMLTagNode) node);
            for (HTMLElementNode elementNode : tagNode.getContent()) {
                check(elementNode);
            }
        } else if (node instanceof HTMLElementNode) {
            if (node instanceof VariableScopeExpressionNode) {
                VariableScopeExpressionNode varScopeNode = ((VariableScopeExpressionNode) node);
                if (varScopeNode.getScopeValue() instanceof PipeExpressionNode) {
                    PipeExpressionNode pipeExpressionNode = ((PipeExpressionNode) varScopeNode.getScopeValue());
                    checkCurrencyFunctionInPipe(pipeExpressionNode);
                    checkLowerFunctionInPipe(pipeExpressionNode);
                    checkUpperFunctionInPipe(pipeExpressionNode);
                    checkDateFunctionInPipe(pipeExpressionNode);
                }
            }
        }
    }

    private void checkCurrencyFunctionInPipe(PipeExpressionNode pipeExpressionNode) {
        if (pipeExpressionNode.getTransformer().getFunctionName().equals("currency")) {
            int numberOfParams = pipeExpressionNode.getTransformer().getParams().size();
            if (numberOfParams == 0) {
                exceptions.add(new Exception("Currency function should at least have one parameter."));
            } else if (numberOfParams == 1) {
                if (pipeExpressionNode.getTransformer().getParams().get(0).getValue() instanceof StringValueNode) {
                    StringValueNode stringValueNode = ((StringValueNode) pipeExpressionNode.getTransformer().getParams().get(0).getValue());
                    if (stringValueNode.getValue().length() != 1) {
                        exceptions.add(new Exception("Parameter should be one character only"));
                    }
                }
            }
        }
    }

    private void checkLowerFunctionInPipe(PipeExpressionNode pipeExpressionNode) {
        if (pipeExpressionNode.getTransformer().getFunctionName().equals("lower")) {
            int numberOfParams = pipeExpressionNode.getTransformer().getParams().size();
            if (numberOfParams != 0) {
                exceptions.add(new Exception("Lower function should not have any parameters."));
            }
        }
    }

    private void checkUpperFunctionInPipe(PipeExpressionNode pipeExpressionNode) {
        if (pipeExpressionNode.getTransformer().getFunctionName().equals("upper")) {
            int numberOfParams = pipeExpressionNode.getTransformer().getParams().size();
            if (numberOfParams != 0) {
                exceptions.add(new Exception("Upper function should not have any parameters."));
            }
        }
    }

    private void checkDateFunctionInPipe(PipeExpressionNode pipeExpressionNode) {
        if (pipeExpressionNode.getTransformer().getFunctionName().equals("date")) {
            int numberOfParams = pipeExpressionNode.getTransformer().getParams().size();
            if (numberOfParams == 0) {
                exceptions.add(new Exception("Date function is not provided with a format"));
            } else if (numberOfParams == 1) {
                if (pipeExpressionNode.getTransformer().getParams().get(0).getValue() instanceof StringValueNode) {
                    StringValueNode stringValueNode = ((StringValueNode) pipeExpressionNode.getTransformer().getParams().get(0).getValue());
                    if (!stringValueNode.getValue().equalsIgnoreCase("mm-dd-yyyy")) {
                        exceptions.add(new Exception("format cannot be other than mm-dd-yyyy"));
                    }
                }
            } else {
                exceptions.add(new Exception("Date function cannot have more than 1 parameter"));
            }
        }
    }
}
