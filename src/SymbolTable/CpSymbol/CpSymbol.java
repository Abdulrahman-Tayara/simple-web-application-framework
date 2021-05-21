package SymbolTable.CpSymbol;

import SymbolTable.Scope;
import SymbolTable.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CpSymbol extends Symbol implements Scope {
    protected Scope enclosingScope;
    protected Map<String, Symbol> table;

    public CpSymbol(String name, Scope scope) {
        super(name);
        this.table = new HashMap<>();
        this.enclosingScope = scope;
    }

    @Override
    public String getScopeName() {
        return this.name;
    }

    @Override
    public void addSymbol(Symbol s) {
        this.table.put(s.name, s);
    }

    @Override
    public Symbol bindOrLookUpSymbol(String name) {
        Symbol s = this.table.get(name);

        //return if found in current scope (in the table)
        if (s != null) {
            return s;
        }

        //if the current symbol is not defined in this scope (not in the table)
        //look it up in the enclosing scope's table
        if (this.enclosingScope != null)
            return this.enclosingScope.bindOrLookUpSymbol(name);

        //not found in any enclosing table.
        return null;
    }

    @Override
    public Scope getEnclosingScope() {
        return this.enclosingScope;
    }


    @Override
    public String toString() {
        return " \n" + this.name + " : { \n" + this.table + "\n}\n";
    }
}
