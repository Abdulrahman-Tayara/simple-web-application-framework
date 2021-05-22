package SymbolTable;

import SymbolTable.CpSymbol.*;
import com.sun.org.apache.xpath.internal.operations.Variable;
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
        globalScope = new HtmlDocumentScope(null);
        currentScope = globalScope;
    }

    @Override
    public void exitHtmlDocument(HTMLParser.HtmlDocumentContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }

    @Override
    public void enterCpApp(HTMLParser.CpAppContext ctx) {
        VariableSymbol variableSymbol = new VariableSymbol(ctx.ANY_NAME().getText());
        currentScope.addSymbol(variableSymbol);
    }

    @Override
    public void exitCpApp(HTMLParser.CpAppContext ctx) {
        CpAppScope cpAppScope = new CpAppScope(currentScope); //which is the global scope
        cpAppScope.grandpaHash = ctx.parent.parent.hashCode();
        currentScope.addSymbol(cpAppScope); // add this symbol (which is the cpapp scope) to the current scope (which is global)
        currentScope = cpAppScope;
    }

    @Override
    public void exitHtmlElement(HTMLParser.HtmlElementContext ctx) {
        int hash = ctx.hashCode();
        if (currentScope instanceof CpScope) {
            if (((CpScope) currentScope).grandpaHash == hash) //the same containing element of
                currentScope = currentScope.getEnclosingScope();
        }
    }

    @Override
    public void exitCpIF(HTMLParser.CpIFContext ctx) {
        CpScope cpScope = new CpIfScope(currentScope);
        currentScope.addSymbol(cpScope);
        cpScope.grandpaHash = ctx.parent.parent.hashCode();
        currentScope = cpScope;
    }

    @Override
    public void exitCpElseIf(HTMLParser.CpElseIfContext ctx) {
        CpScope cpScope = new CpElseIfScope(currentScope);
        currentScope.addSymbol(cpScope);
        cpScope.grandpaHash = ctx.parent.parent.hashCode();
        currentScope = cpScope;
    }

    @Override
    public void exitCpElse(HTMLParser.CpElseContext ctx) {
        CpScope cpScope = new CpElseScope(currentScope);
        currentScope.addSymbol(cpScope);
        cpScope.grandpaHash = ctx.parent.parent.hashCode();
        currentScope = cpScope;
    }

    /**
     * Adds the iterators in the new cp for scope.
     * @param ctx
     */

    @Override
    public void exitForInExpression(HTMLParser.ForInExpressionContext ctx) {
        CpScope cpForInScope = new CpForScope(currentScope);
        if (ctx.expression().size() == 2) {//counter & iterable
            cpForInScope.addSymbol(new VariableSymbol(ctx.expression(0).getText())); //the iterator, and the iterable will be added in enter variable name function
        } else if(ctx.expression().size() == 1) {//only iterable, the counter is a pair expression
            cpForInScope.addSymbol(new VariableSymbol(ctx.pairExpression().variableName().get(0).getText())); //the first iterable, and counters will be added in pair expression
            cpForInScope.addSymbol(new VariableSymbol(ctx.pairExpression().variableName().get(1).getText())); //the first iterable, and counters will be added in pair expression
        }
        currentScope.addSymbol(cpForInScope);
        cpForInScope.grandpaHash = ctx.parent.parent.hashCode();
        currentScope = cpForInScope;
    }

    @Override
    public void exitCpSHOW(HTMLParser.CpSHOWContext ctx) {
        CpScope cpShowScope = new CpShowScope(currentScope);
        currentScope.addSymbol(cpShowScope);
        cpShowScope.grandpaHash = ctx.parent.parent.hashCode();
        currentScope = cpShowScope;
    }

    @Override
    public void exitCpHIDE(HTMLParser.CpHIDEContext ctx) {
        CpScope cpHideScope = new CpHideScope(currentScope);
        currentScope.addSymbol(cpHideScope);
        cpHideScope.grandpaHash = ctx.parent.parent.hashCode();
        currentScope = cpHideScope;
    }

    @Override
    public void exitCpMODEL(HTMLParser.CpMODELContext ctx) {
        CpScope cpScope = new CpModelScope(currentScope);
        currentScope.addSymbol(cpScope);
        cpScope.grandpaHash = ctx.parent.parent.hashCode();
        currentScope = cpScope;
    }

    @Override
    public void enterVariableName(HTMLParser.VariableNameContext ctx) {

        //exclude for in iterators and add the iterables here.
        if (ctx.parent.parent instanceof HTMLParser.ForInExpressionContext) {
            List<HTMLParser.ExpressionContext> expressionContexts = ((HTMLParser.ForInExpressionContext) ctx.parent.parent).expression();
            if (expressionContexts.size() == 2) { //counter and iterable
                currentScope.addSymbol(new VariableSymbol(expressionContexts.get(1).getText())); //add the iterable to the current scope (which is probably going to be the enclosing scope)
            } else if (expressionContexts.size() == 1) { //the counter is a pair expression, and the iterable is the only expression in the list
                currentScope.addSymbol(new VariableSymbol(expressionContexts.get(0).getText())); //add the iterable to the current scope
            }
            return;
        }

        currentScope.addSymbol(new VariableSymbol(ctx.ANY_NAME().getText()));
    }

    @Override
    public void exitCpSWITCH(HTMLParser.CpSWITCHContext ctx) {
        CpScope cpScope = new CpSwitchScope(currentScope);
        currentScope.addSymbol(cpScope);
        cpScope.grandpaHash = ctx.parent.parent.hashCode();
        currentScope = cpScope;
    }

    @Override
    public void exitCpSWITCH_CASE(HTMLParser.CpSWITCH_CASEContext ctx) {
        CpScope cpScope = new CpSwitchCaseScope(currentScope);
        currentScope.addSymbol(cpScope);
        cpScope.grandpaHash = ctx.parent.parent.hashCode();
        currentScope = cpScope;
    }

    @Override
    public void exitCpSWITCH_DEFAULT(HTMLParser.CpSWITCH_DEFAULTContext ctx) {
        CpScope cpScope = new CpSwitchDefaultScope(currentScope);
        currentScope.addSymbol(cpScope);
        cpScope.grandpaHash = ctx.parent.parent.hashCode();
        currentScope = cpScope;
    }

    @Override
    public void exitEvent(HTMLParser.EventContext ctx) {
        CpScope cpScope = new CpEventScope(currentScope);
        currentScope.addSymbol(cpScope);
        cpScope.grandpaHash = ctx.parent.parent.hashCode();
        currentScope = cpScope;
    }
}
