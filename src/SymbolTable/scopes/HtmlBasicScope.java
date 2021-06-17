package SymbolTable.scopes;

import SymbolTable.BasicScope;
import SymbolTable.Scope;

public class HtmlBasicScope extends BasicScope {

    public transient static final String HTML_ELEMENT = "html element";
    public final String tagName;

    public HtmlBasicScope(String name, Scope enclosingScope) {
        super(HTML_ELEMENT, enclosingScope);
        this.tagName = name;
    }
}
