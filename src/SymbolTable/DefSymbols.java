package SymbolTable;

import SymbolTable.attribute.CpAttribute;
import SymbolTable.expression.*;
import SymbolTable.scopes.HtmlBasicScope;
import SymbolTable.scopes.HtmlDocumentScope;
import gen.HTMLParser;
import gen.HTMLParserBaseListener;


public class DefSymbols extends HTMLParserBaseListener {

    //the global scope that holds symbols, which in turn may implement scopes. so that's the starting point
    public BasicScope globalScope;
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
    public void exitCpApp(HTMLParser.CpAppContext ctx) {

        //create expression
        ExpressionSymbol expressionSymbol = new VariableExpressionSymbol(
                ctx.ANY_NAME().getText(),
                "cp app expression"
        );

        //create the cp app attribute
        CpAttribute cpAppAttribute = new CpAttribute("cp-app", expressionSymbol);
        currentScope.addSymbol(cpAppAttribute);

    }

    @Override
    public void enterHtmlElement(HTMLParser.HtmlElementContext ctx) {
        HtmlBasicScope htmlBasicScope = new HtmlBasicScope(ctx.TAG_NAME().get(0).getText(), currentScope);
        currentScope.addSymbol(htmlBasicScope);
        currentScope = htmlBasicScope;
    }

    @Override
    public void exitHtmlElement(HTMLParser.HtmlElementContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }

    @Override
    public void exitCpIF(HTMLParser.CpIFContext ctx) {
        this.addTypicalCpSymbol(ctx.expression(), "cp-if");
    }

    @Override
    public void exitCpElseIf(HTMLParser.CpElseIfContext ctx) {
        this.addTypicalCpSymbol(ctx.expression(), "cp-if-else");
    }

    @Override
    public void exitCpElse(HTMLParser.CpElseContext ctx) {

        //there is no expression in cp else

        //create the attribute
        CpAttribute cpElseAttribute = new CpAttribute("cp-else", null);
        currentScope.addSymbol(cpElseAttribute);

        //no expression to be added to scope

    }

    /**
     * Adds the iterators in the new cp for scope.
     *
     * @param ctx
     */

    @Override
    public void exitForInExpression(HTMLParser.ForInExpressionContext ctx) {
        //create expressions
        ExpressionSymbol countersExpression;
        ExpressionSymbol forExpression;
        if (ctx.pairExpression() != null) { //pair expression for loop

            countersExpression = ExpressionSymbolFactory.make(ctx.pairExpression());
            forExpression = ExpressionSymbolFactory.make(ctx.expression(0));
        } else {//normal for in

            countersExpression = ExpressionSymbolFactory.make(ctx.expression(0));
            forExpression = ExpressionSymbolFactory.make(ctx.expression(1));
        }
        ForInExpression forInExpression = new ForInExpression(
                countersExpression,
                forExpression,
                ctx.getClass().getName()
        );

        //create attributes

        CpAttribute forAttribute = new CpAttribute("cp-for", forInExpression);
        this.currentScope.addSymbol(forAttribute);

        //add to scope

        this.addToAppropriateScope(forInExpression);

    }

    @Override
    public void exitVariableScopeContent(HTMLParser.VariableScopeContentContext ctx) {


        System.out.println(((BasicScope)this.currentScope).symbols);
        ExpressionSymbol expression = ExpressionSymbolFactory.make(ctx.expression());

        if(this.currentScope.bindOrLookUpSymbol(expression) == null){
            System.out.println("no thing in current scope");
            this.globalScope.addSymbol(expression);
        }

    }

    @Override
    public void exitCpSHOW(HTMLParser.CpSHOWContext ctx) {

        this.addTypicalCpSymbol(ctx.expression(), "cp-show");

    }

    @Override
    public void exitCpHIDE(HTMLParser.CpHIDEContext ctx) {
        this.addTypicalCpSymbol(ctx.expression(), "cp-hide");
    }

    @Override
    public void exitCpMODEL(HTMLParser.CpMODELContext ctx) {

        //create the expression
        ExpressionSymbol expression = ExpressionSymbolFactory.make(ctx.expression());

        //create the attribute
        CpAttribute cpModelAttr = new CpAttribute("cp-model", expression);
        currentScope.addSymbol(cpModelAttr);

        //add to scope
        this.addToAppropriateScope(expression);

    }

    @Override
    public void exitCpSWITCH(HTMLParser.CpSWITCHContext ctx) {
        this.addTypicalCpSymbol(ctx.expression(), "cp-switch");
    }

    @Override
    public void exitCpSWITCH_CASE(HTMLParser.CpSWITCH_CASEContext ctx) {
        this.addTypicalCpSymbol(ctx.expression(), "cp-switch-case");
    }

    @Override
    public void exitCpSWITCH_DEFAULT(HTMLParser.CpSWITCH_DEFAULTContext ctx) {

        //create attribute
        CpAttribute cpAttribute = new CpAttribute("cp-switch-default", null);
        this.currentScope.addSymbol(cpAttribute);

    }

    @Override
    public void exitEvent(HTMLParser.EventContext ctx) {
        this.addTypicalCpSymbol(ctx.expression(), "cp-event");
    }


    private void addToAppropriateScope(ExpressionSymbol expressionSymbol) {
        if (expressionSymbol instanceof VariableExpressionSymbol) {
            globalScope.addSymbol(expressionSymbol);
        } else if (expressionSymbol instanceof LiteralExpressionSymbol) {
            //dont add to global add only to attribute
        } else if (expressionSymbol instanceof ParsableExpressionSymbol) {
            //just for now
            globalScope.addSymbol(expressionSymbol);
        } else if (expressionSymbol instanceof ForInExpression) {

            //add counters
            if(((ForInExpression) expressionSymbol).countersExpression instanceof PairExpression){
                this.currentScope.addSymbol(((PairExpression) ((ForInExpression) expressionSymbol).countersExpression).variableExpressionSymbol1);
                this.currentScope.addSymbol(((PairExpression) ((ForInExpression) expressionSymbol).countersExpression).variableExpressionSymbol2);
            }
            if(((ForInExpression) expressionSymbol).countersExpression instanceof VariableExpressionSymbol){
                this.currentScope.addSymbol(((ForInExpression) expressionSymbol).countersExpression);
            }

            //add expression
            if(((ForInExpression) expressionSymbol).forExpression instanceof VariableExpressionSymbol){
                this.globalScope.addSymbol(((ForInExpression) expressionSymbol).forExpression);
            }


        } else{
            System.out.println("something is fucked up");
        }
    }


    /*
     *   A typical cp symbol has an expression (a lot of potential expressions ex: variable, literal string, ..)
     *   its expression will be added to the same scope the cp symbol is in.
     *   and if the expression should be added globally it will be added later.
     *
     * */
    private void addTypicalCpSymbol(HTMLParser.ExpressionContext expressionContext, String cpName) {
        //create the expression
        ExpressionSymbol expression = ExpressionSymbolFactory.make(expressionContext);

        //create attribute
        CpAttribute cpAttribute = new CpAttribute(cpName, expression);
        this.currentScope.addSymbol(cpAttribute);

        //add to scope
        this.addToAppropriateScope(expression);

    }

}
