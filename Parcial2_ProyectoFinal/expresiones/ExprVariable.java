package expresiones;
import analizador.*;

public class ExprVariable extends Expresion {
    final Token name;

    public ExprVariable(Token name) {
        this.name = name;
    }

    void escribe(){
        System.out.println(name);
    }
}