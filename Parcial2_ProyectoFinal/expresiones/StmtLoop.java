package expresiones;
import analizador.*;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;

public class StmtLoop extends Statement {
    final Expresion condition;
    final Statement body;

    StmtLoop(Expresion condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    void escribe(){
        System.out.println(""+condition+body);
    }
}