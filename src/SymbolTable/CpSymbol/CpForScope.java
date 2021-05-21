package SymbolTable.CpSymbol;

import SymbolTable.Scope;

public class CpForScope extends CpScope {

    public CpForScope(Scope enclosingScope) {
        super("cp-for Scope", enclosingScope);
    }

    @Override
    public String getScopeName() {
        return this.name;
    }
}
