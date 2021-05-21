package SymbolTable.CpSymbol;

import SymbolTable.Scope;

public class CpModelScope extends CpScope{
    public CpModelScope(Scope enclosingScope) {
        super("cp-model Scope", enclosingScope);
    }
}
