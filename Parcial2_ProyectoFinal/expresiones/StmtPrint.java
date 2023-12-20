package expresiones;
import analizador.*;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;

public class StmtPrint extends Statement {
    final Expresion expression;

    StmtPrint(Expresion expression) {
        this.expression = expression;
    }

    void escribe(){
        System.out.println(""+expression);
    }
}