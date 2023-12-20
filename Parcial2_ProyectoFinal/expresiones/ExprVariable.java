package expresiones;
import analizador.*;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;

class ExprVariable extends Expresion {
    final Token name;

    ExprVariable(Token name) {
        this.name = name;
    }

    void escribe(){
        System.out.println(name);
    }
}