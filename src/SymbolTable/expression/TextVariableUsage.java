package SymbolTable.expression;

public class TextVariableUsage extends ExpressionSymbol{
    public static final String TEXT_VARIABLE = "text variable usage";
    public String variableName;
    public TextVariableUsage(String variableName, String type) {
        super(TEXT_VARIABLE, type);
        this.variableName = variableName;
    }
}
