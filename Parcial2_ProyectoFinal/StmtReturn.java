package expresiones;

public class StmtReturn extends Statement {
    final Expresion value;

    public StmtReturn(Expresion value) {
        this.value = value;
    }

    void escribe(){
        System.out.println(""+value);
    }
}