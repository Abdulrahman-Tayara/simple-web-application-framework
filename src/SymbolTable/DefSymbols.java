package SymbolTable;

import SymbolTable.CpSymbol.*;
import gen.HTMLParser;
import gen.HTMLParserBaseListener;

import java.util.List;


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
            if (((CpScope) currentScope).grandpaHash == hash) //the same containing element of
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


    @Override
    public void enterCpFOR(HTMLParser.CpFORContext ctx) {

        String counter = ctx.forInExpression().expression(0).getText();
        String iterative = ctx.forInExpression().expression(1).getText();

        VariableSymbol iterativeSymbol = new VariableSymbol(iterative);

        currentScope.addSymbol(iterativeSymbol);

        CpForScope cpForScope = new CpForScope(currentScope);
        currentScope.addSymbol(cpForScope);
        currentScope = cpForScope;
    }

    @Override
    public void enterVariableName(HTMLParser.VariableNameContext ctx) {
        System.out.println("entering vari");
//        if(currentScope instanceof CpAppScope) {
        currentScope.addSymbol(new VariableSymbol(ctx.ANY_NAME().getText()));
//        }
    }


    @Override
    public void exitCpSHOW(HTMLParser.CpSHOWContext ctx) {
        CpScope cpShowScope = new CpShowScope(currentScope);
        currentScope.addSymbol(cpShowScope);
        currentScope = cpShowScope;
    }


    @Override
    public void exitCpHIDE(HTMLParser.CpHIDEContext ctx) {
        CpScope cpHideScope = new CpHideScope(currentScope);
        currentScope.addSymbol(cpHideScope);
        currentScope = cpHideScope;
    }

    @Override
    public void exitCpIF(HTMLParser.CpIFContext ctx) {
        CpScope cpScope = new CpIfScope(currentScope);
        currentScope.addSymbol(cpScope);
        currentScope = cpScope;
    }

    @Override
    public void exitCpMODEL(HTMLParser.CpMODELContext ctx) {
        CpScope cpScope = new CpModelScope(currentScope);
        currentScope.addSymbol(cpScope);
        currentScope = cpScope;
    }
}
