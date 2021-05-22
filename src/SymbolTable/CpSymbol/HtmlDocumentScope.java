package SymbolTable.CpSymbol;

import SymbolTable.BasicScope;
import SymbolTable.Scope;

public class HtmlDocumentScope extends BasicScope {

    public HtmlDocumentScope(Scope enclosingScope) {
        super("HtmlDocument Scope", enclosingScope);
    }
}
