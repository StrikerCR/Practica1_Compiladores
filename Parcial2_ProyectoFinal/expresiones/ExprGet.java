package expresiones;
import analizador.*;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;

public class ExprGet extends Expresion{
    final Expresion object;
    final Token name;

    ExprGet(Expresion object, Token name) {
        this.object = object;
        this.name = name;
    }

    void escribe(){
        System.out.println(""+object+name);
    }
}