package SymbolTable.CpSymbol;

import SymbolTable.Scope;

public class CpEventScope extends CpScope {
    public CpEventScope(Scope enclosingScope) {
        super("event Scope", enclosingScope);
    }
}
