lexer grammar  HTMLLexer;

VARIABLE_SCOPE_OPEN
    : '{{' -> pushMode(CP)
    ;

HTML_COMMENT
    : '<!--' .*? '-->'
    ;

HTML_CONDITIONAL_COMMENT
    : '<![' .*? ']>'
    ;

XML
    : '<?xml' .*? '>'
    ;

CDATA
    : '<![CDATA[' .*? ']]>'
    ;

DTD
    : '<!' .*? '>'
    ;

SCRIPTLET
    : '<?' .*? '?>'
    | '<%' .*? '%>'
    ;

SEA_WS
    :  (' '|'\t'|'\r'? '\n')+
    ;

SCRIPT_OPEN
    : '<script' .*? '>' ->pushMode(SCRIPT)
    ;

STYLE_OPEN
    : '<style' .*? '>'  ->pushMode(STYLE)
    ;

TAG_OPEN
    : '<' -> pushMode(TAG)
    ;

HTML_TEXT
    : ~('<' | '{')+
    ;


// tag declarations

mode TAG;

TAG_CLOSE
    : '>' -> popMode
    ;

TAG_SLASH_CLOSE
    : '/>' -> popMode
    ;

TAG_SLASH
    : '/'
    ;


// lexing mode for attribute values

CP_APP
    : 'cp-app' -> pushMode(CP)
    ;

CP_IF
    : 'cp-if' -> pushMode(CP)
    ;

CP_SHOW
    : 'cp-show' -> pushMode(CP)
    ;

CP_HIDE
    : 'cp-hide' -> pushMode(CP)
    ;

CP_FOR
    : 'cp-for' -> pushMode(CP)
    ;

CP_SWITCH
    : 'cp-switch' -> pushMode(CP)
    ;

CP_SWITCH_CASE
    : 'cp-switch-case' -> pushMode(CP)
    ;

CP_SWITCH_DEFAULT
    : 'cp-switchDefault'
    ;

CP_MODEL
    : 'cp-model' -> pushMode(CP)
    ;

EVENT_AT
    : '@' -> pushMode(CP)
    ;

TAG_EQUALS
    : '=' -> pushMode(ATTVALUE)
    ;


TAG_NAME
    : TAG_NameStartChar TAG_NameChar*
    ;

TAG_WHITESPACE
    : [ \t\r\n] -> channel(HIDDEN)
    ;

fragment
HEXDIGIT
    : [a-fA-F0-9]
    ;

fragment
DIGIT
    : [0-9]
    ;

fragment
TAG_NameChar
    : TAG_NameStartChar
    | '-'
    | '_'
    | '.'
    | DIGIT
    | '\u00B7'
    | '\u0300'..'\u036F'
    | '\u203F'..'\u2040'
    ;

fragment
TAG_NameStartChar
    : [:a-zA-Z]
    | '\u2070'..'\u218F'
    | '\u2C00'..'\u2FEF'
    | '\u3001'..'\uD7FF'
    | '\uF900'..'\uFDCF'
    | '\uFDF0'..'\uFFFD'
    ;


// <scripts>

mode SCRIPT;

SCRIPT_BODY
    : .*? '</script>' -> popMode
    ;

SCRIPT_SHORT_BODY
    : .*? '</>' -> popMode
    ;


// <styles>

mode STYLE;

STYLE_BODY
    : .*? '</style>' -> popMode
    ;

STYLE_SHORT_BODY
    : .*? '</>' -> popMode
    ;


// attribute values

mode ATTVALUE;

// an attribute value may have spaces b/t the '=' and the value

ATTVALUE_VALUE
    : ' '* ATTRIBUTE -> popMode
    ;

ATTRIBUTE
    : DOUBLE_QUOTE_STRING
    | SINGLE_QUOTE_STRING
    | ATTCHARS
    | HEXCHARS
    | DECCHARS
    ;

fragment ATTCHARS
    : ATTCHAR+ ' '?
    ;

fragment ATTCHAR
    : '-'
    | '_'
    | '.'
    | '/'
    | '+'
    | ','
    | '?'
    | '='
    | ':'
    | ';'
    | '#'
    | [0-9a-zA-Z]
    ;

fragment HEXCHARS
    : '#' [0-9a-fA-F]+
    ;

fragment DECCHARS
    : [0-9]+ '%'?
    ;

fragment DOUBLE_QUOTE_STRING
    : '"' ~[<"]* '"'
    ;
fragment SINGLE_QUOTE_STRING
    : '\'' ~[<']* '\''
    ;

mode CP;

CP_WHITESPACE
    : [ \t\r\n] -> channel(HIDDEN)
    ;

CP_OPEN
    : EQUALS '"' -> popMode, pushMode(CP)
    ;

CP_CLOSE
    : '"' -> popMode
    ;

VARIABLE_SCOPE_CLOSE
    : '}}' -> popMode
    ;

VARIABLE_CONCAT
    : '.'
    ;

NUMERIC_VALUE
    : '-'? DIGIT+ ('.' DIGIT+)?
    ;

BOOLEAN_VALUE
    : 'true' | 'TRUE' | 'false' | 'FALSE';

STRING_VALUE
    : '\'' .*? '\''
    ;

ARRAY_OPEN: '[';

ARRAY_CLOSE: ']';

EQUALS: '=';

SPACE: ' ';

SEMI_COLON:';';

COMMA: ',';

PIPE: '|';

FOR_IN: 'in';

ANY_NAME
    : VARIABLE_NAME
    | FUNCTION_NAME
    ;

VARIABLE_NAME
    : VARIABLE_NameStartChar VARIABLE_NameChar*
    ;

OBJECT_OPEN: '{';
OBJECT_CLOSE: '}';


PARENTHESE_OPEN: '(';

PARENTHESE_CLOSE: ')';

FUNCTION_NAME
    : FUNCTION_NameStartChar FUNCTION_NameChar*
    ;

ADDITIVE_OPERATOR
    : ADD | MINUS
    ;

MULTIPLICATIVE_OPERATOR
    : MULTIPLY | DIVIDE | MOD
    ;

CONDITIONAL_OPERATORS_TWO_OPERAND
    : '<'
    | '>'
    | '=='
    | '>='
    | '<='
    ;

CONDITIONAL_OPERATORS_ONE_OPERAND
    : '!'
    ;

CONDITIONAL_OPERATORS_CONCAT
    : '||'
    | '&&'
    ;

STRING_OF_CHARACHTERS
    :  [a-zA-Z]+
    ;

QUESTION_MARK
    : '?'
    ;

COLON
    : ':'
    ;

ADD
   : '+'
   ;

MINUS
    : '-'
    ;

MULTIPLY
    : '*'
    ;

DIVIDE
    : '/'
    ;

MOD
    : '%'
    ;

fragment
FUNCTION_NameChar
    : FUNCTION_NameStartChar
    | DIGIT
    | '_'
    ;

fragment
FUNCTION_NameStartChar
    : [a-zA-Z]
    | '_'
    ;

fragment
VARIABLE_NameChar
    : VARIABLE_NameStartChar
    | DIGIT
    | '_'
    ;

fragment
VARIABLE_NameStartChar
    : [a-zA-Z]
    | '_'
    ;