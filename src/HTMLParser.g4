parser grammar HTMLParser;

options { tokenVocab=HTMLLexer; }

htmlDocument
    : scriptletOrSeaWs* XML? scriptletOrSeaWs* DTD? scriptletOrSeaWs* htmlElements*
    ;

scriptletOrSeaWs
    : SCRIPTLET
    | SEA_WS
    ;

htmlElements
    : htmlMisc* htmlElement htmlMisc*
    ;

htmlElement
    : TAG_OPEN TAG_NAME htmlAttribute*
      (TAG_CLOSE (htmlContent TAG_OPEN TAG_SLASH TAG_NAME TAG_CLOSE)? | TAG_SLASH_CLOSE)
    | SCRIPTLET
    | script
    | style
    ;

htmlContent
    : htmlChardata? ((htmlElement | CDATA | htmlComment | variableScopeContent) htmlChardata?)*
    ;


htmlAttribute
    : cpIF
    | cpSHOW
    | cpHIDE
    | cpFOR
    | cpSWITCH
    | cpSWITCH_CASE
    | cpSWITCH_DEFAULT
    | cpMODEL
    | event
    | TAG_NAME (TAG_EQUALS ATTVALUE_VALUE)?
    ;

variableScopeContent
    : VARIABLE_SCOPE_OPEN expression VARIABLE_SCOPE_CLOSE
    ;

htmlChardata
    : HTML_TEXT
    | SEA_WS
    ;

htmlMisc
    : htmlComment
    | SEA_WS
    ;

htmlComment
    : HTML_COMMENT
    | HTML_CONDITIONAL_COMMENT
    ;

script
    : SCRIPT_OPEN (SCRIPT_BODY | SCRIPT_SHORT_BODY)
    ;

style
    : STYLE_OPEN (STYLE_BODY | STYLE_SHORT_BODY)
    ;

expression
    : <assoc=right> expression CONDITIONAL_OPERATORS_TWO_OPERAND expression       #TwoOperandsConditionExpression
    | <assoc=right> expression CONDITIONAL_OPERATORS_CONCAT expression            #ConcatConditionExpression
    | expression QUESTION_MARK expression COLON expression                        #ConditionalExpression
    | <assoc=right> expression MULTIPLICATIVE_OPERATOR expression                 #MathematicalExpression
    | <assoc=right> expression ADDITIVE_OPERATOR expression                       #MathematicalExpression
    | expression functionParams                                                   #FunctionCallExpression
    | variableName                                                                #VariableNameExpression
    | expression VARIABLE_CONCAT expression                                       #VariableConcatExpression
    | literalNumericValue                                                         #LiteralNumericExpression
    | literalStringValue                                                          #LiteralStringExpression
    | literalBooleanValue                                                         #LiteralBooleanExpression
    | literalArray                                                                #LiteralArrayExpression
    | literalObject                                                               #LiteralObjectExpression
    | expression arrayIndexExpression                                             #IndexedVariableExpression
    | CONDITIONAL_OPERATORS_ONE_OPERAND expression                                #OneOperandConditionExpression
    | PARENTHESE_OPEN expression PARENTHESE_CLOSE                                 #ParenthesizedExpression
    ;


literalNumericValue
    :  NUMERIC_VALUE
    ;

literalStringValue
    : STRING_VALUE
    ;

literalBooleanValue
    : BOOLEAN_VALUE;

literalArray
    : ARRAY_OPEN (arrayItemValue (COMMA arrayItemValue)*)? ARRAY_CLOSE
    ;

literalObject
    : OBJECT_OPEN (objectItem (COMMA objectItem)*)? OBJECT_CLOSE
    ;

objectItem
    : variableName COLON expression
    ;

arrayItemValue
    : expression
    ;

functionParams
    : PARENTHESE_OPEN ((expression) (COMMA expression)*)? PARENTHESE_CLOSE
    ;

variableName
    : ANY_NAME
    ;

arrayIndexExpression
    : ARRAY_OPEN (expression) ARRAY_CLOSE
    ;


// CP_IF
cpIF
    : CP_IF CP_OPEN expression CP_CLOSE
    ;

// CP_SHOW
cpSHOW
    : CP_SHOW CP_OPEN expression CP_CLOSE
    ;

// CP_HIDE
cpHIDE
    : CP_HIDE CP_OPEN expression CP_CLOSE
    ;

// CP_FOR
cpFOR
    : CP_FOR CP_OPEN forInExpression CP_CLOSE
    ;

forInExpression
    : expression FOR_IN (expression) (indexExpression)? // x in [1, 2]
    | pairExpression FOR_IN expression // x,y in [1, 2]
    ;

pairExpression
    : variableName COMMA variableName
    | PARENTHESE_OPEN (variableName COMMA variableName) PARENTHESE_CLOSE
    ;

indexExpression
    :  SEMI_COLON expression EQUALS expression
    ;

// CP_SWITCH
cpSWITCH
    : CP_SWITCH CP_OPEN expression CP_CLOSE
    ;

// CP_SWITCH_CASE
cpSWITCH_CASE
    : CP_SWITCH_CASE CP_OPEN expression CP_CLOSE
    ;

// CP_SWITCH_DEFAULT
cpSWITCH_DEFAULT
    : CP_SWITCH_DEFAULT
    ;

// CP_MODEL
cpMODEL
    : CP_MODEL CP_OPEN expression CP_CLOSE
    ;

// Event
event
    : EVENT_AT eventName CP_OPEN expression CP_CLOSE
    ;

eventName
    : ANY_NAME
    ;