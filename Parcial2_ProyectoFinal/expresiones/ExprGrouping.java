package expresiones;
import analizador.*;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;

public class ExprGrouping extends Expresion {
    final Expresion expression;

    ExprGrouping(Expresion expression) {
        this.expression = expression;
    }

    void escribe(){
        System.out.println(expression);
    }
}