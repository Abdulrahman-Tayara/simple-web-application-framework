package SymbolTable.CpSymbol;

import SymbolTable.BasicScope;
import SymbolTable.expression.ExpressionSymbol;
import SymbolTable.Scope;

import java.util.*;

public abstract class CpScope extends BasicScope {

    public transient int grandpaHash;
    public List<ExpressionSymbol> cpExpressionSymbols = new ArrayList<>();

    public CpScope(String name, Scope enclosingScope) {
        super(name, enclosingScope);
    }


    public void addToCpExpressions(ExpressionSymbol expressionSymbol){
        this.cpExpressionSymbols.add(expressionSymbol);
    }

}
