package expresiones;

public class StmtPrint extends Statement {
    final Expresion expression;

    public StmtPrint(Expresion expression) {
        this.expression = expression;
    }

    void escribe(){
        System.out.println(""+expression);
    }
}