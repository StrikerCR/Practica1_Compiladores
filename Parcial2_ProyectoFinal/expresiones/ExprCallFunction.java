package expresiones;
import analizador.*;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;

public class ExprCallFunction extends Expresion{
    final Expresion callee;
    // final Token paren;
    final List<Expresion> arguments;

    ExprCallFunction(Expresion callee, List<Expresion> arguments){
        this.callee = callee;
        this.arguments = arguments;
    }

    void escribe(){
        System.out.println(""+callee+arguments);
    }
}