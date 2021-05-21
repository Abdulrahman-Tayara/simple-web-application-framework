package SymbolTable;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.*;

public class BasicScope extends Symbol implements Scope {

    protected transient Scope enclosingScope;
    @SerializedName("Scope")
    public List<Symbol> symbols = new ArrayList<>();

    public BasicScope(String name, Scope enclosingScope) {
        super(name);
        this.enclosingScope = enclosingScope;
    }

    @Override
    public String getScopeName() {
        return "Global Scope";
    }

    @Override
    public void addSymbol(Symbol symbol) {
        if (symbol instanceof Scope) { //scope
            symbols.add(symbol);
        } else { //variable
            if (this.bindOrLookUpSymbol(symbol) == null) {
                this.symbols.add(symbol);
            }

        }
    }


        /**
         * searches in the current table and above
         */

    @Override
    public Symbol bindOrLookUpSymbol(Symbol searchedForSymbol) {
		Symbol s = this.symbols.stream().filter(sym -> sym.name.equals(searchedForSymbol.name)).findAny().orElse(null);
        if (s != null) return s;
        if (getEnclosingScope() != null) {
            return getEnclosingScope().bindOrLookUpSymbol(searchedForSymbol);
        }
        return null;
    }

    @Override
    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    @Override
    public String toString() {
        return "Global Scope : { " +
                this.symbols +
                "}";
    }
}
