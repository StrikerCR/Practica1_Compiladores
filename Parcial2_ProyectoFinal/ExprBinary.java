package expresiones;
import analizador.*;

public class ExprBinary extends Expresion{
    final Expresion left;
    final Token operator;
    final Expresion right;

    public ExprBinary(Expresion left, Token operator, Expresion right){
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    void escribe(){
        System.out.println(""+left+operator+right);
    }
}