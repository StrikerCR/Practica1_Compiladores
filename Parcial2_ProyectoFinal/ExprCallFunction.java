package expresiones;
import java.util.List;

public class ExprCallFunction extends Expresion{
    final Expresion callee;
    // final Token paren;
    final List<Expresion> arguments;

    public ExprCallFunction(Expresion callee, List<Expresion> arguments){
        this.callee = callee;
        this.arguments = arguments;
    }

    void escribe(){
        System.out.println(""+callee+arguments);
    }
}