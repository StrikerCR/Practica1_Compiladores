package expresiones;
import analizador.*;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;

public class ExprSet extends Expresion{
    final Expresion object;
    final Token name;
    final Expresion value;

    ExprSet(Expresion object, Token name, Expresion value) {
        this.object = object;
        this.name = name;
        this.value = value;
    }

    void escribe(){
        System.out.println(""+object+name+value);
    }
}