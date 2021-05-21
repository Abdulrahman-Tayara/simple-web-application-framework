package SymbolTable;

import SymbolTable.CpSymbol.CpAppSymbol;
import SymbolTable.CpSymbol.CpForSymbol;
import SymbolTable.CpSymbol.CpIfSymbol;
import gen.HTMLParser;
import gen.HTMLParserBaseListener;


public class DefSymbols extends HTMLParserBaseListener {

    //the global scope that holds symbols, which in turn may implement scopes. so that's the starting point
    private BasicScope globalScope;
    //used for defining the current scope.
    public Scope currentScope;


    @Override
    public void enterHtmlDocument(HTMLParser.HtmlDocumentContext ctx) {
        globalScope = new BasicScope(null);
        currentScope = globalScope;
    }


    @Override
    public void enterCpApp(HTMLParser.CpAppContext ctx) {
        String name = ctx.ANY_NAME().getText();
        CpAppSymbol cpAppSymbol = new CpAppSymbol(name, currentScope);
        currentScope.addSymbol(cpAppSymbol);
        currentScope = cpAppSymbol;

    }

    @Override
    public void exitCpApp(HTMLParser.CpAppContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }


    @Override
    public void enterCpIF(HTMLParser.CpIFContext ctx) {
        String name = ctx.CP_IF().getText();
        CpIfSymbol s = new CpIfSymbol(name, currentScope);
        currentScope.addSymbol(s);
        currentScope = s;
    }

    @Override
    public void exitCpIF(HTMLParser.CpIFContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }


    @Override
    public void enterVariableName(HTMLParser.VariableNameContext ctx) {
        currentScope.addSymbol(new VariableSymbol(ctx.ANY_NAME().getText()));
    }


}
