package expresiones;
import analizador.Token;

public class ExprAssign extends Expresion{

    final Token name;
    final Expresion value;
    
    public ExprAssign(Token name, Expresion value){
        this.name = name;
        this.value = value;
    }

    void escribe(){
        System.out.println("ExprAssign(" + this.name + this.value + ")");
    }
}