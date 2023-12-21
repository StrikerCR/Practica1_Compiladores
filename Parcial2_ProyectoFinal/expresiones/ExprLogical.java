package expresiones;
import analizador.*;

public class ExprLogical extends Expresion{
    final Expresion left;
    final Token operator;
    final Expresion right;

    public ExprLogical(Expresion left, Token operator, Expresion right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    void escribe(){
        System.out.println(""+left+operator+right);
    }
}