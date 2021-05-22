package SymbolTable.CpSymbol;

import SymbolTable.BasicScope;
import SymbolTable.Scope;

public class CpElseIfScope extends CpScope {
    public CpElseIfScope(Scope enclosingScope) {
        super("cp-elseif Scope", enclosingScope);
    }
}
