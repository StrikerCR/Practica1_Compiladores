package expresiones;

public class ExprGrouping extends Expresion {
    final Expresion expression;

    public ExprGrouping(Expresion expression) {
        this.expression = expression;
    }

    void escribe(){
        System.out.println(expression);
    }
}