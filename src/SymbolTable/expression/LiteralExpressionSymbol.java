package SymbolTable.expression;

public class LiteralExpressionSymbol extends ValuableExpressionSymbol{

    public transient static final String SYMBOL_NAME = "literal expression";

    public LiteralExpressionSymbol(String value, String type) {
        super(SYMBOL_NAME, value, type);
    }
}
