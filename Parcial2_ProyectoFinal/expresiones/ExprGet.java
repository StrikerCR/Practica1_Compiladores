package expresiones;
import analizador.*;

public class ExprGet extends Expresion{
    final Expresion object;
    final Token name;

    public ExprGet(Expresion object, Token name) {
        this.object = object;
        this.name = name;
    }

    void escribe(){
        System.out.println(""+object+name);
    }
}