package SymbolTable.CpSymbol;

import SymbolTable.Symbol;

public class CpSymbol extends Symbol {

    private final Symbol  expressionSymbol;

    public CpSymbol(String name, Symbol expressionSymbol) {
        super(name);
        this.expressionSymbol = expressionSymbol;
    }

    public Symbol getExpressionSymbol() {
        return expressionSymbol;
    }
}
