package expresiones;
import analizador.*;

public class StmtVar extends Statement {
    final Token name;
    final Expresion initializer;

    public StmtVar(Token name, Expresion initializer) {
        this.name = name;
        this.initializer = initializer;
    }

    void escribe(){
        System.out.println("StmtVar("+name+initializer+")");
    }
}