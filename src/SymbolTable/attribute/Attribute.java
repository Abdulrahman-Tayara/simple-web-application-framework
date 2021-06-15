package SymbolTable.attribute;

import SymbolTable.Symbol;

public abstract class Attribute<T> extends Symbol {

    public String attributeName;
    public T value;

    public Attribute(String symbolName, String attributeName, T value) {
        super(symbolName);
        this.attributeName = attributeName;
        this.value = value;
    }
}
