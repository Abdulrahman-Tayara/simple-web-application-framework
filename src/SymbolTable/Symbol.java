package SymbolTable;

public abstract class Symbol {
	//some info about a symbol
	//..
	//..
	public String name;

	public Symbol(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name + ",\n";
	}
}

