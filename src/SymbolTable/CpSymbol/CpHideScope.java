package SymbolTable.CpSymbol;

import SymbolTable.Scope;

public class CpHideScope extends CpScope{
    public CpHideScope(Scope enclosingScope) {
        super("cp-hide Scope", enclosingScope);
    }
}
