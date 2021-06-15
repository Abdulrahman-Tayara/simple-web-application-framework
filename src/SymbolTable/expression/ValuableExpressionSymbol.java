package SymbolTable.expression;

public abstract class ValuableExpressionSymbol extends ExpressionSymbol{

    public String value;

    public ValuableExpressionSymbol(String name, String value, String type) {
        super(name, type);
        this.value = value;
    }
}
