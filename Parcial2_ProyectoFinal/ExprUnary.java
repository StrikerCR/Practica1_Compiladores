package expresiones;
import analizador.*;

public class ExprUnary extends Expresion{
    final Token operator;
    final Expresion right;

    public ExprUnary(Token operator, Expresion right) {
        this.operator = operator;
        this.right = right;
    }

    void escribe(){
        System.out.println(""+operator+right);
    }
}