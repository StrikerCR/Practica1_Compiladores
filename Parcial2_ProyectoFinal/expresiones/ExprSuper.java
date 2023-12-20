package expresiones;
import analizador.*;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;

public class ExprSuper extends Expresion {
    // final Token keyword;
    final Token method;

    ExprSuper(Token method) {
        // this.keyword = keyword;
        this.method = method;
    }

    void escribe(){
        System.out.println(method);
    }
}