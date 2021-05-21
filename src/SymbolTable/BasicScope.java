package SymbolTable;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicScope implements Scope {
	protected Scope enclosingScope;
	public Map<String,Symbol> variables = new HashMap<>();
	public List<Scope> scopes = new ArrayList<>();

	public BasicScope(Scope enclosingScope) {
		this.enclosingScope = enclosingScope;
	}

	@Override
	public String getScopeName() {
		return "<unknown>";
	}

	@Override
	public void addSymbol(Symbol s) {
//		table.put(s.name, s);
		if(s instanceof Scope){
			scopes.add((Scope) s);
		}else{
			if(this.bindOrLookUpSymbol(s.name) == null){
				variables.put(s.name, s);
			}
		}
	}




	@Override
	public Symbol bindOrLookUpSymbol(String name) {
		Symbol s = variables.get(name);
		if ( s!=null ) return s;
		if ( getEnclosingScope()!=null ) {
			return getEnclosingScope().bindOrLookUpSymbol(name);
		}
		return null;
	}

	@Override
	public Scope getEnclosingScope() {
		return enclosingScope;
	}

	@Override
	public String toString() {
		return "Global Scope : vars  { " +
				variables+
				"},\n scopes{ \n" + scopes + "\n}";
	}
}
