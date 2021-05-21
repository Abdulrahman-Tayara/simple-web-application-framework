package SymbolTable.CpSymbol;

import SymbolTable.Scope;

public class CpAppScope extends CpScope {


    public CpAppScope(Scope enclosingScope) {
        super("cp-app Scope", enclosingScope);
    }

    @Override
    public String getScopeName() {
        return this.name;
    }
}
