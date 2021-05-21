package SymbolTable.CpSymbol;

import SymbolTable.BasicScope;
import SymbolTable.Scope;
import SymbolTable.Symbol;

import java.util.*;

public abstract class CpScope extends BasicScope {

    public transient int grandpaHash;

    public CpScope(String name, Scope enclosingScope) {
        super(name, enclosingScope);
    }
}
