package expresiones;
import analizador.*;

public class ExprSuper extends Expresion {
    // final Token keyword;
    final Token method;

    public ExprSuper(Token method) {
        // this.keyword = keyword;
        this.method = method;
    }

    void escribe(){
        System.out.println(method);
    }
}