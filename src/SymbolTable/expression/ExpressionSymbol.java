package SymbolTable.expression;

import SymbolTable.Symbol;

public abstract class ExpressionSymbol extends Symbol {

    public String type;

    public ExpressionSymbol(String name, String type) {
        super(name);
        this.type = type;
    }
}
