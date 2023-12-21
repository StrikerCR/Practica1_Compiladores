package expresiones;

public class StmtExpression extends Statement {
    final Expresion expression;

    public StmtExpression(Expresion expression) {
        this.expression = expression;
    }

    void escribe(){
        System.out.println(""+expression);
    }
}