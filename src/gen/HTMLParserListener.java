package gen;// Generated from C:/Users/Abdulrahman/IdeaProjects/CompilerProject/src\HTMLParser.g4 by ANTLR 4.9
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HTMLParser}.
 */
public interface HTMLParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HTMLParser#htmlDocument}.
	 * @param ctx the parse tree
	 */
	void enterHtmlDocument(HTMLParser.HtmlDocumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#htmlDocument}.
	 * @param ctx the parse tree
	 */
	void exitHtmlDocument(HTMLParser.HtmlDocumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#scriptletOrSeaWs}.
	 * @param ctx the parse tree
	 */
	void enterScriptletOrSeaWs(HTMLParser.ScriptletOrSeaWsContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#scriptletOrSeaWs}.
	 * @param ctx the parse tree
	 */
	void exitScriptletOrSeaWs(HTMLParser.ScriptletOrSeaWsContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#htmlElements}.
	 * @param ctx the parse tree
	 */
	void enterHtmlElements(HTMLParser.HtmlElementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#htmlElements}.
	 * @param ctx the parse tree
	 */
	void exitHtmlElements(HTMLParser.HtmlElementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void enterHtmlElement(HTMLParser.HtmlElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void exitHtmlElement(HTMLParser.HtmlElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#htmlContent}.
	 * @param ctx the parse tree
	 */
	void enterHtmlContent(HTMLParser.HtmlContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#htmlContent}.
	 * @param ctx the parse tree
	 */
	void exitHtmlContent(HTMLParser.HtmlContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#htmlAttribute}.
	 * @param ctx the parse tree
	 */
	void enterHtmlAttribute(HTMLParser.HtmlAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#htmlAttribute}.
	 * @param ctx the parse tree
	 */
	void exitHtmlAttribute(HTMLParser.HtmlAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#variableScopeContent}.
	 * @param ctx the parse tree
	 */
	void enterVariableScopeContent(HTMLParser.VariableScopeContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#variableScopeContent}.
	 * @param ctx the parse tree
	 */
	void exitVariableScopeContent(HTMLParser.VariableScopeContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#htmlChardata}.
	 * @param ctx the parse tree
	 */
	void enterHtmlChardata(HTMLParser.HtmlChardataContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#htmlChardata}.
	 * @param ctx the parse tree
	 */
	void exitHtmlChardata(HTMLParser.HtmlChardataContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#htmlMisc}.
	 * @param ctx the parse tree
	 */
	void enterHtmlMisc(HTMLParser.HtmlMiscContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#htmlMisc}.
	 * @param ctx the parse tree
	 */
	void exitHtmlMisc(HTMLParser.HtmlMiscContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#htmlComment}.
	 * @param ctx the parse tree
	 */
	void enterHtmlComment(HTMLParser.HtmlCommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#htmlComment}.
	 * @param ctx the parse tree
	 */
	void exitHtmlComment(HTMLParser.HtmlCommentContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#script}.
	 * @param ctx the parse tree
	 */
	void enterScript(HTMLParser.ScriptContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#script}.
	 * @param ctx the parse tree
	 */
	void exitScript(HTMLParser.ScriptContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#style}.
	 * @param ctx the parse tree
	 */
	void enterStyle(HTMLParser.StyleContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#style}.
	 * @param ctx the parse tree
	 */
	void exitStyle(HTMLParser.StyleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenthesizedExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedExpression(HTMLParser.ParenthesizedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenthesizedExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedExpression(HTMLParser.ParenthesizedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralArrayExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLiteralArrayExpression(HTMLParser.LiteralArrayExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralArrayExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLiteralArrayExpression(HTMLParser.LiteralArrayExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TwoOperandsConditionExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterTwoOperandsConditionExpression(HTMLParser.TwoOperandsConditionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TwoOperandsConditionExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitTwoOperandsConditionExpression(HTMLParser.TwoOperandsConditionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConditionalExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExpression(HTMLParser.ConditionalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConditionalExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalExpression(HTMLParser.ConditionalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MathematicalExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMathematicalExpression(HTMLParser.MathematicalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MathematicalExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMathematicalExpression(HTMLParser.MathematicalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IndexedVariableExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIndexedVariableExpression(HTMLParser.IndexedVariableExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IndexedVariableExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIndexedVariableExpression(HTMLParser.IndexedVariableExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PipeExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPipeExpression(HTMLParser.PipeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PipeExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPipeExpression(HTMLParser.PipeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralBooleanExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLiteralBooleanExpression(HTMLParser.LiteralBooleanExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralBooleanExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLiteralBooleanExpression(HTMLParser.LiteralBooleanExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OneOperandConditionExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOneOperandConditionExpression(HTMLParser.OneOperandConditionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OneOperandConditionExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOneOperandConditionExpression(HTMLParser.OneOperandConditionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralNumericExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLiteralNumericExpression(HTMLParser.LiteralNumericExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralNumericExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLiteralNumericExpression(HTMLParser.LiteralNumericExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionCallExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpression(HTMLParser.FunctionCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionCallExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpression(HTMLParser.FunctionCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralObjectExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLiteralObjectExpression(HTMLParser.LiteralObjectExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralObjectExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLiteralObjectExpression(HTMLParser.LiteralObjectExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralStringExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLiteralStringExpression(HTMLParser.LiteralStringExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralStringExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLiteralStringExpression(HTMLParser.LiteralStringExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VariableConcatExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterVariableConcatExpression(HTMLParser.VariableConcatExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VariableConcatExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitVariableConcatExpression(HTMLParser.VariableConcatExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConcatConditionExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConcatConditionExpression(HTMLParser.ConcatConditionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConcatConditionExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConcatConditionExpression(HTMLParser.ConcatConditionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VariableNameExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterVariableNameExpression(HTMLParser.VariableNameExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VariableNameExpression}
	 * labeled alternative in {@link HTMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitVariableNameExpression(HTMLParser.VariableNameExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#literalNumericValue}.
	 * @param ctx the parse tree
	 */
	void enterLiteralNumericValue(HTMLParser.LiteralNumericValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#literalNumericValue}.
	 * @param ctx the parse tree
	 */
	void exitLiteralNumericValue(HTMLParser.LiteralNumericValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#literalStringValue}.
	 * @param ctx the parse tree
	 */
	void enterLiteralStringValue(HTMLParser.LiteralStringValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#literalStringValue}.
	 * @param ctx the parse tree
	 */
	void exitLiteralStringValue(HTMLParser.LiteralStringValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#literalBooleanValue}.
	 * @param ctx the parse tree
	 */
	void enterLiteralBooleanValue(HTMLParser.LiteralBooleanValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#literalBooleanValue}.
	 * @param ctx the parse tree
	 */
	void exitLiteralBooleanValue(HTMLParser.LiteralBooleanValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#literalArray}.
	 * @param ctx the parse tree
	 */
	void enterLiteralArray(HTMLParser.LiteralArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#literalArray}.
	 * @param ctx the parse tree
	 */
	void exitLiteralArray(HTMLParser.LiteralArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#literalObject}.
	 * @param ctx the parse tree
	 */
	void enterLiteralObject(HTMLParser.LiteralObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#literalObject}.
	 * @param ctx the parse tree
	 */
	void exitLiteralObject(HTMLParser.LiteralObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#objectItem}.
	 * @param ctx the parse tree
	 */
	void enterObjectItem(HTMLParser.ObjectItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#objectItem}.
	 * @param ctx the parse tree
	 */
	void exitObjectItem(HTMLParser.ObjectItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#arrayItemValue}.
	 * @param ctx the parse tree
	 */
	void enterArrayItemValue(HTMLParser.ArrayItemValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#arrayItemValue}.
	 * @param ctx the parse tree
	 */
	void exitArrayItemValue(HTMLParser.ArrayItemValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#functionParams}.
	 * @param ctx the parse tree
	 */
	void enterFunctionParams(HTMLParser.FunctionParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#functionParams}.
	 * @param ctx the parse tree
	 */
	void exitFunctionParams(HTMLParser.FunctionParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#variableName}.
	 * @param ctx the parse tree
	 */
	void enterVariableName(HTMLParser.VariableNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#variableName}.
	 * @param ctx the parse tree
	 */
	void exitVariableName(HTMLParser.VariableNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#arrayIndexExpression}.
	 * @param ctx the parse tree
	 */
	void enterArrayIndexExpression(HTMLParser.ArrayIndexExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#arrayIndexExpression}.
	 * @param ctx the parse tree
	 */
	void exitArrayIndexExpression(HTMLParser.ArrayIndexExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#cpApp}.
	 * @param ctx the parse tree
	 */
	void enterCpApp(HTMLParser.CpAppContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#cpApp}.
	 * @param ctx the parse tree
	 */
	void exitCpApp(HTMLParser.CpAppContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#cpIF}.
	 * @param ctx the parse tree
	 */
	void enterCpIF(HTMLParser.CpIFContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#cpIF}.
	 * @param ctx the parse tree
	 */
	void exitCpIF(HTMLParser.CpIFContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#cpElseIf}.
	 * @param ctx the parse tree
	 */
	void enterCpElseIf(HTMLParser.CpElseIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#cpElseIf}.
	 * @param ctx the parse tree
	 */
	void exitCpElseIf(HTMLParser.CpElseIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#cpElse}.
	 * @param ctx the parse tree
	 */
	void enterCpElse(HTMLParser.CpElseContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#cpElse}.
	 * @param ctx the parse tree
	 */
	void exitCpElse(HTMLParser.CpElseContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#cpSHOW}.
	 * @param ctx the parse tree
	 */
	void enterCpSHOW(HTMLParser.CpSHOWContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#cpSHOW}.
	 * @param ctx the parse tree
	 */
	void exitCpSHOW(HTMLParser.CpSHOWContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#cpHIDE}.
	 * @param ctx the parse tree
	 */
	void enterCpHIDE(HTMLParser.CpHIDEContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#cpHIDE}.
	 * @param ctx the parse tree
	 */
	void exitCpHIDE(HTMLParser.CpHIDEContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#cpFOR}.
	 * @param ctx the parse tree
	 */
	void enterCpFOR(HTMLParser.CpFORContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#cpFOR}.
	 * @param ctx the parse tree
	 */
	void exitCpFOR(HTMLParser.CpFORContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#forInExpression}.
	 * @param ctx the parse tree
	 */
	void enterForInExpression(HTMLParser.ForInExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#forInExpression}.
	 * @param ctx the parse tree
	 */
	void exitForInExpression(HTMLParser.ForInExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#pairExpression}.
	 * @param ctx the parse tree
	 */
	void enterPairExpression(HTMLParser.PairExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#pairExpression}.
	 * @param ctx the parse tree
	 */
	void exitPairExpression(HTMLParser.PairExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#indexExpression}.
	 * @param ctx the parse tree
	 */
	void enterIndexExpression(HTMLParser.IndexExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#indexExpression}.
	 * @param ctx the parse tree
	 */
	void exitIndexExpression(HTMLParser.IndexExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#cpSWITCH}.
	 * @param ctx the parse tree
	 */
	void enterCpSWITCH(HTMLParser.CpSWITCHContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#cpSWITCH}.
	 * @param ctx the parse tree
	 */
	void exitCpSWITCH(HTMLParser.CpSWITCHContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#cpSWITCH_CASE}.
	 * @param ctx the parse tree
	 */
	void enterCpSWITCH_CASE(HTMLParser.CpSWITCH_CASEContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#cpSWITCH_CASE}.
	 * @param ctx the parse tree
	 */
	void exitCpSWITCH_CASE(HTMLParser.CpSWITCH_CASEContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#cpSWITCH_DEFAULT}.
	 * @param ctx the parse tree
	 */
	void enterCpSWITCH_DEFAULT(HTMLParser.CpSWITCH_DEFAULTContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#cpSWITCH_DEFAULT}.
	 * @param ctx the parse tree
	 */
	void exitCpSWITCH_DEFAULT(HTMLParser.CpSWITCH_DEFAULTContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#cpMODEL}.
	 * @param ctx the parse tree
	 */
	void enterCpMODEL(HTMLParser.CpMODELContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#cpMODEL}.
	 * @param ctx the parse tree
	 */
	void exitCpMODEL(HTMLParser.CpMODELContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#event}.
	 * @param ctx the parse tree
	 */
	void enterEvent(HTMLParser.EventContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#event}.
	 * @param ctx the parse tree
	 */
	void exitEvent(HTMLParser.EventContext ctx);
	/**
	 * Enter a parse tree produced by {@link HTMLParser#eventName}.
	 * @param ctx the parse tree
	 */
	void enterEventName(HTMLParser.EventNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link HTMLParser#eventName}.
	 * @param ctx the parse tree
	 */
	void exitEventName(HTMLParser.EventNameContext ctx);
}