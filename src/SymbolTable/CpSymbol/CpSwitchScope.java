package SymbolTable.CpSymbol;

import SymbolTable.Scope;

public class CpSwitchScope extends CpScope {
    public CpSwitchScope(Scope enclosingScope) {
        super("cp-switch Scope", enclosingScope);
    }
}
