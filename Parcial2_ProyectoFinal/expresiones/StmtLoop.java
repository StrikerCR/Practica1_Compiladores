package expresiones;

public class StmtLoop extends Statement {
    final Expresion condition;
    final Statement body;

    public StmtLoop(Expresion condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    void escribe(){
        System.out.println(""+condition+body);
    }
}