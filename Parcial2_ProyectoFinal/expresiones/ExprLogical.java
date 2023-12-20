package expresiones;
import analizador.*;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;

public class ExprLogical extends Expresion{
    final Expresion left;
    final Token operator;
    final Expresion right;

    ExprLogical(Expresion left, Token operator, Expresion right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    void escribe(){
        System.out.println(""+left+operator+right);
    }
}