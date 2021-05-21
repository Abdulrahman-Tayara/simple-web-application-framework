package SymbolTable;

import SymbolTable.CpSymbol.CpAppScope;
import SymbolTable.CpSymbol.CpIfScope;
import SymbolTable.CpSymbol.CpScope;
import gen.HTMLParser;
import gen.HTMLParserBaseListener;


public class DefSymbols extends HTMLParserBaseListener {

    //the global scope that holds symbols, which in turn may implement scopes. so that's the starting point
    private BasicScope globalScope;
    //used for defining the current scope.
    public transient Scope currentScope;


    @Override
    public void enterHtmlDocument(HTMLParser.HtmlDocumentContext ctx) {
        globalScope = new BasicScope("Global Scope", null);
        currentScope = globalScope;
    }

    @Override
    public void exitHtmlDocument(HTMLParser.HtmlDocumentContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }

    @Override
    public void enterCpApp(HTMLParser.CpAppContext ctx) {


        CpAppScope cpAppScope = new CpAppScope(currentScope); //which is the global scope

        cpAppScope.grandpaHash = ctx.parent.parent.hashCode();

        VariableSymbol variableSymbol = new VariableSymbol(ctx.ANY_NAME().getText());
        currentScope.addSymbol(variableSymbol);

        currentScope.addSymbol(cpAppScope); // add this symbol (which is the cpapp scope) to the current scope (which is global)

        currentScope = cpAppScope;
    }

//    @Override
//    public void exitCpApp(HTMLParser.CpAppContext ctx) {
//        currentScope = currentScope.getEnclosingScope(); //which is probably the global scope
//    }

    @Override
    public void exitHtmlElement(HTMLParser.HtmlElementContext ctx) {
        int hash = ctx.hashCode();
        if (currentScope instanceof CpScope) {
            if (((CpScope)currentScope).grandpaHash == hash) //the same containing element of
                currentScope = currentScope.getEnclosingScope();
        }
    }


//    @Override
//    public void enterCpIF(HTMLParser.CpIFContext ctx) {
//        CpIfScope s = new CpIfScope(currentScope);
//        currentScope.addSymbol(s);
//        currentScope = s;
//    }
//
//    @Override
//    public void exitCpIF(HTMLParser.CpIFContext ctx) {
//        currentScope = currentScope.getEnclosingScope();
//    }
//

    @Override
    public void enterVariableName(HTMLParser.VariableNameContext ctx) {
        System.out.println("entering vari");
//        if(currentScope instanceof CpAppScope) {
        currentScope.addSymbol(new VariableSymbol(ctx.ANY_NAME().getText()));
//        }
    }


}
