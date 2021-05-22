package SymbolTable.CpSymbol;

import SymbolTable.Scope;

public class CpSwitchDefaultScope extends CpScope {
    public CpSwitchDefaultScope(Scope enclosingScope) {
        super("cp-switch-default Scope", enclosingScope);
    }
}
