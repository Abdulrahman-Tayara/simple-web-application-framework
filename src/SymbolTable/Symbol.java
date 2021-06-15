package SymbolTable;

import com.google.gson.annotations.SerializedName;

public abstract class Symbol {

	private transient static int COUNT_ID = 0;
	//some info about a symbol
	//..
	//..
	@SerializedName("symbol")
	public String name;
	private transient int id;

	public Symbol(String name) {
		this.name = name;
		this.assignId();
	}

	private void assignId() {
		this.id = COUNT_ID;
		COUNT_ID++;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return this.name + ",\n";
	}
}

