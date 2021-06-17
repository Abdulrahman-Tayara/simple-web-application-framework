package SymbolTable.expression;

import SymbolTable.ICompareableSymbol;

public class VariableExpressionSymbol extends ExpressionSymbol implements ICompareableSymbol<VariableExpressionSymbol> {

    public transient static final String VARIABLE_EXPRESSION = "variable expression";
    public String variableName;

    public VariableExpressionSymbol(String variableName, String type) {
        super(VARIABLE_EXPRESSION, type);
        this.variableName = variableName;
    }


    @Override
    public boolean compareSymbolWith(VariableExpressionSymbol bindableSymbol) {
        return this.variableName.equals(bindableSymbol.variableName);
    }
}
