package expresiones;

public class ExprLiteral extends Expresion {
    final Object value;

    public ExprLiteral(Object value) {
        this.value = value;
    }

    void escribe(){
        System.out.println(value);
    }
}