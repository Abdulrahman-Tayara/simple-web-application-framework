package SymbolTable.expression;

import SymbolTable.Symbol;
import SymbolTable.expression.ExpressionSymbol;
import SymbolTable.expression.LiteralExpressionSymbol;
import SymbolTable.expression.VariableExpressionSymbol;
import gen.HTMLParser;

public class ExpressionSymbolFactory {

    public static ExpressionSymbol make(HTMLParser.ExpressionContext context) {
        if (context instanceof HTMLParser.VariableNameExpressionContext) {
            return new VariableExpressionSymbol(
                    ((HTMLParser.VariableNameExpressionContext) context).variableName().ANY_NAME().getText(),
                    context.getClass().getName()
            );
        } else if (context instanceof HTMLParser.LiteralBooleanExpressionContext ||
                context instanceof HTMLParser.LiteralArrayExpressionContext ||
                context instanceof HTMLParser.LiteralNumericExpressionContext ||
                context instanceof HTMLParser.LiteralStringExpressionContext ||
                context instanceof HTMLParser.LiteralObjectExpressionContext
        ) {
            return new LiteralExpressionSymbol(context.getText(), context.getClass().getName());
        } else {
            return null;
        }

    }

}
