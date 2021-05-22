package SymbolTable.CpSymbol;

import SymbolTable.Scope;

public class CpElseScope extends CpScope {
    public CpElseScope(Scope currentScope) {
        super("cp-else Scope", currentScope);
    }
}
