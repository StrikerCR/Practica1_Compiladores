package expresiones;
import analizador.*;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;

public class StmtExpression extends Statement {
    final Expresion expression;

    StmtExpression(Expresion expression) {
        this.expression = expression;
    }

    void escribe(){
        System.out.println(""+expression);
    }
}