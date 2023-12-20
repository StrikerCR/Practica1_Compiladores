package expresiones;
import analizador.*;
import java.beans.Expression;
import java.util.List;
import java.util.TreeMap;

public class StmtFunction extends Statement {
    final Token name;
    final List<Token> params;
    final StmtBlock body;

    StmtFunction(Token name, List<Token> params, StmtBlock body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    void escribe(){
        System.out.println(""+name+params+body);
    }
}