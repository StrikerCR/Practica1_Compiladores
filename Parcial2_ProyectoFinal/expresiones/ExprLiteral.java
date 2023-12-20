package expresiones;
import analizador.*;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;

public class ExprLiteral extends Expresion {
    final Object value;

    ExprLiteral(Object value) {
        this.value = value;
    }

    void escribe(){
        System.out.println(value);
    }
}