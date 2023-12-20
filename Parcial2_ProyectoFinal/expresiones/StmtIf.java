package expresiones;
import analizador.*;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;

public class StmtIf extends Statement {
    final Expresion condition;
    final Statement thenBranch;
    final Statement elseBranch;

    StmtIf(Expresion condition, Statement thenBranch, Statement elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    void escribe(){
        System.out.println(""+condition+thenBranch+elseBranch);
    }
}