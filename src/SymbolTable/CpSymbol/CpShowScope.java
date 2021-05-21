package SymbolTable.CpSymbol;

import SymbolTable.Scope;

public class CpShowScope extends CpScope {
    public CpShowScope(Scope enclosingScope) {
        super("cp-show Scope", enclosingScope);
    }
}
