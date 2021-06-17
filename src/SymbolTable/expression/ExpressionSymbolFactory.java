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

    public static PairExpression make(HTMLParser.PairExpressionContext pairExpression) {

        return new PairExpression(
                new VariableExpressionSymbol(pairExpression.variableName().get(0).ANY_NAME().getText(),
                        pairExpression.variableName().get(0).getClass().getName()
                ),
                new VariableExpressionSymbol(pairExpression.variableName().get(1).ANY_NAME().getText(),
                        pairExpression.variableName().get(1).getClass().getName()
                ),
                pairExpression.getClass().getName()
        );
    }
}
