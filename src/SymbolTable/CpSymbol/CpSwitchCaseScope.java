package SymbolTable.CpSymbol;

import SymbolTable.Scope;

public class CpSwitchCaseScope extends CpScope {
    public CpSwitchCaseScope(Scope enclosingScope) {
        super("cp-switch-case Scope", enclosingScope);
    }
}
