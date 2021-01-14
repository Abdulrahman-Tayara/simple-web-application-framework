package gen;// Generated from C:/Users/Abdulrahman/IdeaProjects/CompilerProject/src\HTMLParser.g4 by ANTLR 4.8
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link HTMLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface HTMLParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link HTMLParser#htmlDocument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlDocument(HTMLParser.HtmlDocumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#scriptletOrSeaWs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScriptletOrSeaWs(HTMLParser.ScriptletOrSeaWsContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#htmlElements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlElements(HTMLParser.HtmlElementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#htmlElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlElement(HTMLParser.HtmlElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#htmlContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlContent(HTMLParser.HtmlContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#htmlAttribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlAttribute(HTMLParser.HtmlAttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#variableScopeContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableScopeContent(HTMLParser.VariableScopeContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#htmlChardata}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlChardata(HTMLParser.HtmlChardataContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#htmlMisc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlMisc(HTMLParser.HtmlMiscContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#htmlComment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlComment(HTMLParser.HtmlCommentContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#script}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScript(HTMLParser.ScriptContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#style}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStyle(HTMLParser.StyleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenthesizedExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesizedExpression(HTMLParser.ParenthesizedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralArrayExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralArrayExpression(HTMLParser.LiteralArrayExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoOperandsConditionExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoOperandsConditionExpression(HTMLParser.TwoOperandsConditionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConditionalExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalExpression(HTMLParser.ConditionalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MathematicalExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMathematicalExpression(HTMLParser.MathematicalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IndexedVariableExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexedVariableExpression(HTMLParser.IndexedVariableExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PipeExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPipeExpression(HTMLParser.PipeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralBooleanExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralBooleanExpression(HTMLParser.LiteralBooleanExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OneOperandConditionExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOneOperandConditionExpression(HTMLParser.OneOperandConditionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralNumericExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralNumericExpression(HTMLParser.LiteralNumericExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionCallExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCallExpression(HTMLParser.FunctionCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralObjectExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralObjectExpression(HTMLParser.LiteralObjectExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralStringExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralStringExpression(HTMLParser.LiteralStringExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VariableConcatExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableConcatExpression(HTMLParser.VariableConcatExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConcatConditionExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConcatConditionExpression(HTMLParser.ConcatConditionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VariableNameExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableNameExpression(HTMLParser.VariableNameExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#literalNumericValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralNumericValue(HTMLParser.LiteralNumericValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#literalStringValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralStringValue(HTMLParser.LiteralStringValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#literalBooleanValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralBooleanValue(HTMLParser.LiteralBooleanValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#literalArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralArray(HTMLParser.LiteralArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#literalObject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralObject(HTMLParser.LiteralObjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#objectItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectItem(HTMLParser.ObjectItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#arrayItemValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayItemValue(HTMLParser.ArrayItemValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#functionParams}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionParams(HTMLParser.FunctionParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#variableName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableName(HTMLParser.VariableNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#arrayIndexExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayIndexExpression(HTMLParser.ArrayIndexExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#cpIF}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCpIF(HTMLParser.CpIFContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#cpSHOW}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCpSHOW(HTMLParser.CpSHOWContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#cpHIDE}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCpHIDE(HTMLParser.CpHIDEContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#cpFOR}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCpFOR(HTMLParser.CpFORContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#forInExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInExpression(HTMLParser.ForInExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#pairExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairExpression(HTMLParser.PairExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#indexExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexExpression(HTMLParser.IndexExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#cpSWITCH}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCpSWITCH(HTMLParser.CpSWITCHContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#cpSWITCH_CASE}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCpSWITCH_CASE(HTMLParser.CpSWITCH_CASEContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#cpSWITCH_DEFAULT}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCpSWITCH_DEFAULT(HTMLParser.CpSWITCH_DEFAULTContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#cpMODEL}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCpMODEL(HTMLParser.CpMODELContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#event}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEvent(HTMLParser.EventContext ctx);
	/**
	 * Visit a parse tree produced by {@link HTMLParser#eventName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEventName(HTMLParser.EventNameContext ctx);
}