package expresiones;
import analizador.*;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;

public class StmtReturn extends Statement {
    final Expresion value;

    StmtReturn(Expresion value) {
        this.value = value;
    }

    void escribe(){
        System.out.println(""+value);
    }
}