package ast.nodes.expression;


import ast.nodes.expression.value.ValuableNode;
import ast.nodes.expression.value.variable.FunctionExpressionNode;
import ast.nodes.expression.value.variable.VariableExpressionNode;

/**
 * Pipe expression, ex: pipedExpression | functionName:factor
 */
public class PipeExpressionNode extends ExpressionNode implements ValuableNode {

    private ExpressionNode pipedData;
    private FunctionExpressionNode transformer;

    public ExpressionNode getPipedData() {
        return pipedData;
    }

    public void setPipedData(ExpressionNode pipedData) {
        this.pipedData = pipedData;
    }

    public FunctionExpressionNode getTransformer() {
        return transformer;
    }

    public void setTransformer(FunctionExpressionNode transformer) {
        this.transformer = transformer;
    }

    @Override
    public String toHtml() {
        String pipedExpression = pipedData.toHtml();

        ValuableNode transformerParam = new VariableExpressionNode(pipedExpression);

        transformer.addParamInFirst(new FunctionExpressionNode.FunctionParam(transformerParam));

        return transformer.toHtml();
    }
}
