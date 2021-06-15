package SymbolTable.attribute;

import SymbolTable.expression.ExpressionSymbol;

public class CpAttribute extends Attribute<ExpressionSymbol>{

    public transient static final String CP_ATTRIBUTE = "cp attribute";

    public CpAttribute(String attributeName, ExpressionSymbol value) {
        super(CP_ATTRIBUTE, attributeName, value);
    }
}
