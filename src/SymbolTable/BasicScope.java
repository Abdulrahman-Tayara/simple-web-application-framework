package SymbolTable;


import SymbolTable.attribute.CpAttribute;
import SymbolTable.expression.VariableExpressionSymbol;
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

        if(symbol instanceof VariableExpressionSymbol){
            if(this.bindOrLookUpSymbol(symbol) == null){
                this.symbols.add(symbol);
            }
        }else{
            this.symbols.add(symbol);
        }

//        if (symbol instanceof Scope) { //scope
//            symbols.add(symbol);
//        } else { //variable
//            if (this.bindOrLookUpSymbol(symbol) == null) {
//                this.symbols.add(symbol);
//            }
//
//        }
    }

    public void addSymbolWithoutBinding(Symbol symbol){
        this.symbols.add(symbol);
    }

    public CpAttribute getCpAttribute(String attributeName){
        for (Symbol symbol : this.symbols) {
            if(symbol instanceof CpAttribute && ((CpAttribute) symbol).attributeName.equals(attributeName)){
                return (CpAttribute) symbol;
            }
        }
        return null;
    }


    /**
     * searches in the current table and above
     */

    @Override
    public Symbol bindOrLookUpSymbol(Symbol searchedForSymbol) {

        if (searchedForSymbol instanceof VariableExpressionSymbol) {

            Symbol s = null;
            for (Symbol symbol : this.symbols) {
                if (symbol instanceof VariableExpressionSymbol) {
                    if (((VariableExpressionSymbol) searchedForSymbol).compareSymbolWith((VariableExpressionSymbol) symbol)) {
                        s = symbol;
                    }
                }
            }

            if (s != null) return s;
            if (getEnclosingScope() != null) {
                return getEnclosingScope().bindOrLookUpSymbol(searchedForSymbol);
            }
        }
        return null;
    }

//    public boolean bindOrLookUpComparableSymbol(ICompareableSymbol<Symbol> searchedForSymbol) {
//        boolean match = false;
//        for (Symbol symbol : this.symbols) {
//            if(symbol instanceof ICompareableSymbol){
//                if(searchedForSymbol.compareSymbolWith(symbol)){
//                    match = true;
//                    break;
//                }
//            }
//        }
//        if (match) return true;
//        if (getEnclosingScope() != null) {
//            return ((BasicScope)getEnclosingScope()).bindOrLookUpComparableSymbol(searchedForSymbol);
//        }
//        return false;
//    }

    @Override
    public Scope getEnclosingScope() {
        return enclosingScope;
    }

//    public void addToGlobalIfCould(ICompareableSymbol<Symbol> symbol, BasicScope globalScope){
//
//        if(!this.bindOrLookUpComparableSymbol(symbol)){
//            globalScope.addSymbol((Symbol) symbol);
//        }
//
//    }
}
