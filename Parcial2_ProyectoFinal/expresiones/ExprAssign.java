package expresiones;
import analizador.*;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;

public class ExprAssign extends Expresion{

    final Token name;
    final Expresion value;
    
    ExprAssign(Token name, Expresion value){
        this.name = name;
        this.value = value;
    }

    void escribe(){
        System.out.println("ExprAssign(" + this.name + this.value + ")");
    }
}