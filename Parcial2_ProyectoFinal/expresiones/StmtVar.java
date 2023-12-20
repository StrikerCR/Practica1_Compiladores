package expresiones;
import analizador.*;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;

public class StmtVar extends Statement {
    final Token name;
    final Expresion initializer;

    StmtVar(Token name, Expresion initializer) {
        this.name = name;
        this.initializer = initializer;
    }

    void escribe(){
        System.out.println("StmtVar("+name+initializer+")");
    }
}