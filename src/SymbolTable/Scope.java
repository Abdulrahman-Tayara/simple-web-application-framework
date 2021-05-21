package SymbolTable;

public interface Scope {
	String getScopeName();
	void addSymbol(Symbol symbol);
	Symbol bindOrLookUpSymbol(Symbol symbol);
	Scope getEnclosingScope();
}