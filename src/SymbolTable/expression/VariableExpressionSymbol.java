package SymbolTable.expression;

public class VariableExpressionSymbol extends ExpressionSymbol {

    public transient static final String VARIABLE_EXPRESSION = "variable expression";
    public String variableName;

    public VariableExpressionSymbol(String variableName, String type) {
        super(VARIABLE_EXPRESSION, type);
        this.variableName = variableName;
    }
}
