package ast.nodes.expression;


import ast.nodes.expression.value.variable.FunctionExpressionNode;

/**
 * Pipe expression, ex: pipedExpression | functionName:factor
 */
public class PipeExpressionNode extends ExpressionNode {

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
}
