package SymbolTable;

import com.google.gson.annotations.SerializedName;

public abstract class Symbol {
	//some info about a symbol
	//..
	//..
	@SerializedName("symbol")
	public String name;

	public Symbol(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name + ",\n";
	}
}

