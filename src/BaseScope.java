import java.util.LinkedHashMap;
import java.util.Map;

public class BaseScope implements Scope{
    private final Scope enclosingScope;
    private final Map<String, Symbol> symbols = new LinkedHashMap<>();
    private String name;

    public BaseScope(String name, Scope enclosingScope) {
        this.name = name;
        this.enclosingScope = enclosingScope;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Scope getEnclosingScope() {
        return this.enclosingScope;
    }

    public Map<String, Symbol> getSymbols() {
        return this.symbols;
    }

    public void define(Symbol symbol) {
        if(symbols.containsKey(symbol.getName())){
            if(symbol instanceof FunctionSymbol){
                FunctionSymbol fs = (FunctionSymbol) symbol;
                fs.errOutput();
            }
            else{
                symbol.errOutput();
            }
            return;
        }
        symbols.put(symbol.getName(), symbol);
        System.out.println("+" + symbol.getName());
    }

    public void checkVariable(String name, int lineno){
        if(!symbols.containsKey(name)){
            if(enclosingScope==null){
                System.err.println("Error type 1 at Line "+lineno+": Undefined variable: "+name+".");
                return ;
            }
            enclosingScope.checkVariable(name,lineno);
        }
    }

    public void checkFunction(String name, int lineno){
        if(!symbols.containsKey(name)){
            if(enclosingScope==null){
                System.err.println("Error type 2 at Line "+lineno+": Undefined function: "+name+".");
                return ;
            }
            enclosingScope.checkFunction(name,lineno);
        }
    }

    public void setArray(String name){
        Symbol symbol = symbols.get(name);
    }

    public Symbol resolve(String name) {
        Symbol symbol = symbols.get(name);
        if (symbol != null) {
            System.out.println("*" + name);
            return symbol;
        }

        if (enclosingScope != null) {
            return enclosingScope.resolve(name);
        }

//        System.err.println("Cannot find " + name);
        return null;
    }

    public String toString() {
        return null;
    }
}