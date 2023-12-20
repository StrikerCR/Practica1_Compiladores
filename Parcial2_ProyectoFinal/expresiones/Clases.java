package expresiones;
import java.beans.Expression;
import java.util.List;

public class Clases{
}

public class ExprAssign extends Clases{

    final Token name;
    final Clases value;
    
    ExprAssign(Token name, Clases value){
        this.name = name;
        this.value = value;
    }

    void escribe(){
        System.out.println("ExprAssign(" + this.name + this.value + ")");
    }
}

public class ExprBinary extends Clases{
    final Clases left;
    final Token operator;
    final Clases right;

    ExprBinary(Clases left, Token operator, Clases right){
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    void escribe(){
        System.out.println(""+left+operator+right);
    }
}

public class ExprCallFunction extends Clases{
    final Clases callee;
    // final Token paren;
    final List<Clases> arguments;

    ExprCallFunction(Clases callee, List<Clases> arguments){
        this.callee = callee;
        this.arguments = arguments;
    }

    void escribe(){
        System.out.println(""+callee+arguments);
    }
}

public class ExprGet extends Clases{
    final Clases object;
    final Token name;

    ExprGet(Clases object, Token name) {
        this.object = object;
        this.name = name;
    }

    void escribe(){
        System.out.println(""+object+name);
    }
}

public class ExprGrouping extends Clases {
    final Clases expression;

    ExprGrouping(Clases expression) {
        this.expression = expression;
    }

    void escribe(){
        System.out.println(expression);
    }
}

public class ExprLiteral extends Clases {
    final Object value;

    ExprLiteral(Object value) {
        this.value = value;
    }

    void escribe(){
        System.out.println(value);
    }
}

public class ExprLogical extends Clases{
    final Clases left;
    final Token operator;
    final Clases right;

    ExprLogical(Clases left, Token operator, Clases right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    void escribe(){
        System.out.println(""+left+operator+right);
    }
}

public class ExprSet extends Clases{
    final Clases object;
    final Token name;
    final Clases value;

    ExprSet(Clases object, Token name, Clases value) {
        this.object = object;
        this.name = name;
        this.value = value;
    }

    void escribe(){
        System.out.println(""+object+name+value);
    }
}

public class ExprSuper extends Clases {
    // final Token keyword;
    final Token method;

    ExprSuper(Token method) {
        // this.keyword = keyword;
        this.method = method;
    }

    void escribe(){
        System.out.println(method);
    }
}

public class ExprThis extends Clases{
    // final Token keyword;

    ExprThis() {
        // this.keyword = keyword;
    }

    void escribe(){
        System.out.println("");
    }
}

public class ExprUnary extends Clases{
    final Token operator;
    final Clases right;

    ExprUnary(Token operator, Clases right) {
        this.operator = operator;
        this.right = right;
    }

    void escribe(){
        System.out.println(""+operator+right);
    }
}

class ExprVariable extends Clases {
    final Token name;

    ExprVariable(Token name) {
        this.name = name;
    }

    void escribe(){
        System.out.println(name);
    }
}

public abstract class Statement {
}

public class StmtBlock extends Statement{
    final List<Statement> statements;

    StmtBlock(List<Statement> statements) {
        this.statements = statements;
    }

    void escribe(){
        System.out.println(statements);
    }
}

public class StmtClass extends Statement {
    final Token name;
    final ExprVariable superclass;
    final List<StmtFunction> methods;

    StmtClass(Token name, ExprVariable superclass, List<StmtFunction> methods) {
        this.name = name;
        this.superclass = superclass;
        this.methods = methods;
    }

    void escribe(){
        System.out.println(""+name+superclass+methods);
    }
}

public class StmtExpression extends Statement {
    final Clases expression;

    StmtExpression(Clases expression) {
        this.expression = expression;
    }

    void escribe(){
        System.out.println(""+expression);
    }
}

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

public class StmtIf extends Statement {
    final Clases condition;
    final Statement thenBranch;
    final Statement elseBranch;

    StmtIf(Clases condition, Statement thenBranch, Statement elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    void escribe(){
        System.out.println(""+condition+thenBranch+elseBranch);
    }
}

public class StmtLoop extends Statement {
    final Clases condition;
    final Statement body;

    StmtLoop(Clases condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    void escribe(){
        System.out.println(""+condition+body);
    }
}

public class StmtPrint extends Statement {
    final Clases expression;

    StmtPrint(Clases expression) {
        this.expression = expression;
    }

    void escribe(){
        System.out.println(""+expression);
    }
}

public class StmtReturn extends Statement {
    final Clases value;

    StmtReturn(Clases value) {
        this.value = value;
    }

    void escribe(){
        System.out.println(""+value);
    }
}

public class StmtVar extends Statement {
    final Token name;
    final Clases initializer;

    StmtVar(Token name, Clases initializer) {
        this.name = name;
        this.initializer = initializer;
    }

    void escribe(){
        System.out.println("StmtVar("+name+initializer+")");
    }
}