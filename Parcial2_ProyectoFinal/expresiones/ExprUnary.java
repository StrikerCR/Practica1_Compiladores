package expresiones;
import analizador.*;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;

public class ExprUnary extends Expresion{
    final Token operator;
    final Expresion right;

    ExprUnary(Token operator, Expresion right) {
        this.operator = operator;
        this.right = right;
    }

    void escribe(){
        System.out.println(""+operator+right);
    }
}