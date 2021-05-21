package SymbolTable;

public interface Scope {
	String getScopeName();
	void addSymbol(Symbol s);
	Symbol bindOrLookUpSymbol(String name);
	Scope getEnclosingScope();
}