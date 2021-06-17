package SymbolTable.expression;

public class PairExpression extends ExpressionSymbol{

    public transient static final String PAIR_EXPRESSION = "pair expression";

    public VariableExpressionSymbol variableExpressionSymbol1;
    public VariableExpressionSymbol variableExpressionSymbol2;

    public PairExpression(VariableExpressionSymbol variableExpressionSymbol1, VariableExpressionSymbol variableExpressionSymbol2, String type) {
        super(PAIR_EXPRESSION, type);
        this.variableExpressionSymbol1 = variableExpressionSymbol1;
        this.variableExpressionSymbol2 = variableExpressionSymbol2;
    }

}
