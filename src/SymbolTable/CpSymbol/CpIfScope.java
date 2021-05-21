package SymbolTable.CpSymbol;

import SymbolTable.Scope;

public class CpIfScope extends CpScope {


    public CpIfScope(Scope enclosingScope) {
        super("cp-if scope", enclosingScope);
    }

    @Override
    public String getScopeName() {
        return this.name;
    }
}
