package SymbolTable.expression;

public class ForInExpression extends ExpressionSymbol{

    public transient static final String FOR_EXPRESSION = "for expression";

    public ExpressionSymbol countersExpression;
    public ExpressionSymbol forExpression;

    public ForInExpression(ExpressionSymbol countersExpression, ExpressionSymbol forExpression, String type) {
        super(FOR_EXPRESSION, type);
        this.countersExpression = countersExpression;
        this.forExpression = forExpression;
    }
}
