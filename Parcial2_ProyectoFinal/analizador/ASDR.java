package analizador;
import java.beans.Expression;
import java.beans.Statement;
import java.util.ArrayList;
import java.util.List;
import expresiones.*;

public class ASDR implements Parser{
    private int i = 0;
    private boolean hayErrores = false;
    private Token preanalisis;
    private final List<Token> tokens;

    public ASDR(List<Token> tokens) {
        this.tokens = tokens;
        if(this.tokens.size() > 0)
            preanalisis = this.tokens.get(i);
    }
    
    @Override
    public boolean parse(){
        if(this.tokens.size() == 0){
            System.out.println("Consulta correcta");
            return true;
        }
        PROGRAM();
        if((i == tokens.size()) && !hayErrores){
            System.out.println("Consulta correcta");
            return true;
        }else System.out.println("Se encontraron errores");
        return false;
    }

    private void PROGRAM(){
        ArrayList<Expresion> statements = new ArrayList<>();
        DECLARATION(statements);
    }

    private ArrayList DECLARATION(ArrayList statements){
        System.out.println("DECLARATION");
        if(hayErrores) return null;
        if(preanalisis.tipo == TipoToken.FUN){
            Expresion fundcl = FUN_DECL();
            statements.add(fundcl);
            DECLARATION(statements);
        }else if(preanalisis.tipo == TipoToken.VAR){
            Expresion vardcl = VAR_DECL();
            statements.add(vardcl);
            DECLARATION(statements);
        }else if(primeroSTATEMENT(preanalisis.tipo)){
            Expresion stmt = STATEMENT();
            statements.add(stmt);
            DECLARATION(statements);
        } return statements;
    }

    private Expresion FUN_DECL(){
        System.out.println("FUN_DECL");
        if(hayErrores) return null;
        match(TipoToken.FUN);
        Expresion fun = FUNCTION();
        return fun;
    }

    private Expresion VAR_DECL(){
        System.out.println("VAR_DECL");
        if(hayErrores) return null;
        match(TipoToken.VAR);
        match(TipoToken.IDENTIFIER);
        Expresion name = previous();
        Expresion initializer = VAR_INIT();
        match(TipoToken.SEMICOLON);
        Expresion pc = previous();
        Expresion stmtv = StmtVar(name, str(initializer));
        return stmtv;
    }

    private Expresion VAR_INIT(){
        System.out.println("VAR_INIT");
        if(hayErrores) return null;
        if(preanalisis.tipo == TipoToken.EQUAL){
            match(TipoToken.EQUAL);
            Expresion initializer = EXPRESSION();
            return initializer;
        }
    }

    private Expresion STATEMENT(){
        System.out.println("STATEMENT");
        if(hayErrores) return null;
        if(preanalisis.tipo == TipoToken.FOR){
            return FOR_STMT();
        }else if(preanalisis.tipo == TipoToken.IF){
            return IF_STMT();
        }else if(preanalisis.tipo == TipoToken.PRINT){
            return PRINT_STMT();
        }else if(preanalisis.tipo == TipoToken.RETURN){
            Expresion value = RETURN_STMT();
            return value;
        }else if(preanalisis.tipo == TipoToken.WHILE){
            return WHILE_STMT();
        }else if(preanalisis.tipo == TipoToken.LEFT_BRACE){
            return BLOCK();
        }else if(primeroEXPR_STMT(preanalisis.tipo)){
            return EXPR_STMT();
        }else hayErrores = true;
    }

    private void EXPR_STMT(){
        System.out.println("EXPR_STMT");
        if(hayErrores) return;
        Expresion expression = EXPRESSION();
        match(TipoToken.SEMICOLON);
        return StmtExpression(expression);
    }

    private Expresion FOR_STMT(){
        System.out.println("FOR_STMT");
        if(hayErrores) return;
        match(TipoToken.FOR);
        match(TipoToken.LEFT_PAREN);
        Expresion initializer = FOR_STMT_1();
        Expresion condition = FOR_STMT_2();
        Expresion increment = FOR_STMT_3();
        match(TipoToken.RIGHT_PAREN);
        Expresion body = STATEMENT();
        if(increment != null){
            body = StmtBlock(body, StmtExpression(increment));
        } else if(condition != null){
            condition = ExprLiteral(true);
            body = StmtLoop(condition, body);
        } if(initializer != null){
            body = StmtBlock(initializer, body);
        } return body;
    }

    private void FOR_STMT_1(){
        System.out.println("FOR_STMT_1");
        if(hayErrores) return;
        if(preanalisis.tipo == TipoToken.VAR){
            return VAR_DECL();
        }else if(preanalisis.tipo == TipoToken.SEMICOLON){
            match(TipoToken.SEMICOLON);
        }else if(primeroEXPR_STMT(preanalisis.tipo)){
            EXPR_STMT();
        }else hayErrores = true;
    }
    
    private void FOR_STMT_2(){
        System.out.println("FOR_STMT_2");
        if(hayErrores) return;
        if(preanalisis.tipo == TipoToken.SEMICOLON){
            match(TipoToken.SEMICOLON);
        }else if(primeroEXPR_STMT(preanalisis.tipo)){
            EXPRESSION();
            match(TipoToken.SEMICOLON);
        }else hayErrores = true;
    }
    
    private void FOR_STMT_3(){
        System.out.println("FOR_STMT_3");
        if(hayErrores) return;
        if(primeroEXPR_STMT(preanalisis.tipo)) EXPRESSION();
    }

    private void IF_STMT(){
        System.out.println("if_STMT");
        if(hayErrores) return;
        match(TipoToken.IF);
        match(TipoToken.LEFT_PAREN);
        EXPRESSION();
        match(TipoToken.RIGHT_PAREN);
        STATEMENT();
        ELSE_STATEMENT();
    }

    private void ELSE_STATEMENT(){
        System.out.println("ELSE_STATEMENT");
        if(hayErrores) return;
        if(preanalisis.tipo == TipoToken.ELSE){
            match(TipoToken.ELSE);
            STATEMENT();
        }
    }

    private void PRINT_STMT(){
        System.out.println("PRINT_STMT");
        if(hayErrores) return;
        match(TipoToken.PRINT);
        EXPRESSION();
        match(TipoToken.SEMICOLON);
    }

    private void RETURN_STMT(){
        System.out.println("RETURN_STMT");
        if(hayErrores) return;
        match(TipoToken.RETURN);
        RETURN_EXP_OPC();
        match(TipoToken.SEMICOLON);
    }

    private void RETURN_EXP_OPC(){
        System.out.println("RETURN_EXP_OPC");
        if(hayErrores) return;
        if(primeroEXPR_STMT(preanalisis.tipo)){
            EXPRESSION();
        }
    }

    private void WHILE_STMT(){
        System.out.println("WHILE_STMT");
        if(hayErrores) return;
        match(TipoToken.WHILE);
        match(TipoToken.LEFT_PAREN);
        EXPRESSION();
        match(TipoToken.RIGHT_PAREN);
        STATEMENT();
    }

    private void BLOCK(){
        System.out.println("BLOCK");
        if(hayErrores) return;
        match(TipoToken.LEFT_BRACE);
        DECLARATION();
        match(TipoToken.RIGHT_BRACE);
    }

    private Expresion EXPRESSION(){
        //System.out.println("EXPRESSION");
        if(hayErrores) return null;
        return ASSIGNMENT();
    }

    //Incompleta por ASSIGNMENT_OPC
    private Expresion ASSIGNMENT(){
        System.out.println("ASSIGNMENT");
        if(hayErrores) return null;
        Expresion asg = LOGIC_OR();
        asg = ASSIGNMENT_OPC(asg);
        return asg;
    }
    
    //ExprAssign necesita un Token, pero tenemos una Expresion en su lugar (asg)
    private Expresion ASSIGNMENT_OPC(Expresion asg){
        System.out.println("ASSIGNMENT_OPC");
        if(hayErrores) return null;
        if(preanalisis.tipo == TipoToken.EQUAL){
            match(TipoToken.EQUAL);
            Expresion val = EXPRESSION();
            return new ExprAssign(asg, val);
        }return null;
    }

    private Expresion LOGIC_OR(){
        //System.out.println("LOGIC_OR");
        if(hayErrores) return null;
        Expresion expr = LOGIC_AND();
        expr = LOGIC_OR_2(expr);
        return expr;
    }

    private Expresion LOGIC_OR_2(Expresion expr){
        //System.out.println("LOGIC_OR_2");
        if(hayErrores) return null;
        if(preanalisis.tipo == TipoToken.OR){
            match(TipoToken.OR);
            Token operador = previous();
            Expresion expr2 = LOGIC_AND();
            Expresion expl = new ExprLogical(expr, operador, expr2);
            return LOGIC_OR_2(expl);
        }return null;
    }

    private Expresion LOGIC_AND(){
        //System.out.println("LOGIC_AND");
        if(hayErrores) return null;
        Expresion expr = EQUALITY();
        expr = LOGIC_AND_2(expr);
        return expr;
    }
    
    private Expresion LOGIC_AND_2(Expresion expr){
        //System.out.println("LOGIC_AND_2");
        if(hayErrores) return null;
        if(preanalisis.tipo == TipoToken.AND){
            match(TipoToken.AND);
            Token operador = previous();
            Expresion expr2 = EQUALITY();
            Expresion expl = new ExprLogical(expr, operador, expr2);
            return LOGIC_AND_2(expl);
        }
        return null;
    }

    private Expresion EQUALITY(){
        //System.out.println("EQUALITY");
        if(hayErrores) return null;
        Expresion expr = COMPARISON();
        expr = EQUALITY_2(expr);
        return expr;
    }

    private Expresion EQUALITY_2(Expresion expr){
        //System.out.println("EQUALITY_2");
        if(hayErrores) return null;
        if(preanalisis.tipo == TipoToken.BANG_EQUAL){
            match(TipoToken.BANG_EQUAL);
            Token operador = previous();
            Expresion expr2 = COMPARISON();
            Expresion expb = new ExprBinary(expr, operador, expr2);
            return EQUALITY_2(expb);
        } else if(preanalisis.tipo == TipoToken.EQUAL_EQUAL){
            match(TipoToken.EQUAL_EQUAL);
            Token operador = previous();
            Expresion expr2 = COMPARISON();
            Expresion expb = new ExprBinary(expr, operador, expr2);
            return EQUALITY_2(expb);
        }return null;
    }

    //Incompleta por TERM
    private Expresion COMPARISON(){
        System.out.println("COMPARISON");
        if(hayErrores) return null;
        Expresion expr = TERM();
        expr = COMPARISON_2(expr);
        return expr;
    }
    
    //Incompleta por TERM
    private Expresion COMPARISON_2(Expresion expr){
        System.out.println("COMPARISON_2");
        if(hayErrores) return null;
        if(preanalisis.tipo == TipoToken.GREATER){
            match(TipoToken.GREATER);
            Token operador = previous();
            Expresion expr2 = TERM();
            Expresion expb = new ExprBinary(expr, operador, expr2);
            return COMPARISON_2(expb);
        } else if(preanalisis.tipo == TipoToken.GREATER_EQUAL){
            match(TipoToken.GREATER_EQUAL);
            Token operador = previous();
            Expresion expr2 = TERM();
            Expresion expb = new ExprBinary(expr, operador, expr2);
            return COMPARISON_2(expb);
        } else if(preanalisis.tipo == TipoToken.LESS){
            match(TipoToken.LESS);
            Token operador = previous();
            Expresion expr2 = TERM();
            Expresion expb = new ExprBinary(expr, operador, expr2);
            return COMPARISON_2(expb);
        } else if(preanalisis.tipo == TipoToken.LESS_EQUAL){
            match(TipoToken.LESS_EQUAL);
            Token operador = previous();
            Expresion expr2 = TERM();
            Expresion expb = new ExprBinary(expr, operador, expr2);
            return COMPARISON_2(expb);
        } return expr;
    }

    private Expresion TERM(){
        System.out.println("TERM");
        if(hayErrores) return null;
        Expresion expr = FACTOR();
        expr = TERM_2(expr);
        return expr;
    }
    
    private Expresion TERM_2(Expresion expr){
        System.out.println("TERM_2");
        if(hayErrores) return null;
        if(preanalisis.tipo == TipoToken.MINUS){
            match(TipoToken.MINUS);
            Token operador = previous();
            Expresion expr2 = FACTOR();
            Expresion expb = new ExprBinary(expr, operador, expr2);
            return TERM_2(expb);
        } else if(preanalisis.tipo == TipoToken.PLUS){
            match(TipoToken.PLUS);
            Token operador = previous();
            Expresion expr2 = FACTOR();
            Expresion expb = new ExprBinary(expr, operador, expr2);
            return TERM_2(expb);
        } return null;
    }

    private Expresion FACTOR(){
        System.out.println("FACTOR");
        if(hayErrores) return null;
        Expresion expr = UNARY();
        expr = FACTOR_2(expr);
        return expr;
    }

    private Expresion FACTOR_2(Expresion expr){
        //System.out.println("FACTOR_2");
        if(hayErrores) return null;
        if(preanalisis.tipo == TipoToken.SLASH){
            match(TipoToken.SLASH);
            Token operador = previous();
            Expresion expr2 = UNARY();
            ExprBinary expb = new ExprBinary(expr, operador, expr2);
            return FACTOR_2(expb);
        } else if(preanalisis.tipo == TipoToken.STAR){
            match(TipoToken.STAR);
            Token operador = previous();
            Expresion expr2 = UNARY();
            ExprBinary expb = new ExprBinary(expr, operador, expr2);
            return FACTOR_2(expb);
        }return null;
    }

    private Expresion UNARY(){
        //System.out.println("UNARY");
        if(hayErrores) return null;
        if(preanalisis.tipo == TipoToken.BANG){
            match(TipoToken.BANG);
            Token operador = previous();
            Expresion expr = UNARY();
            return new ExprUnary(operador, expr);
        } else if(preanalisis.tipo == TipoToken.MINUS){
            match(TipoToken.MINUS);
            Token operador = previous();
            Expresion expr = UNARY();
            return new ExprUnary(operador, expr);
        } else {
            return CALL();
        }
    }

    private Expresion CALL(){
        //System.out.println("CALL");
        if(hayErrores) return null;
        Expresion expr = PRIMARY();
        expr = CALL_2(expr);
        return expr;
    }
    
    private Expresion CALL_2(Expresion expr){
        //System.out.println("CALL_2");
        if(hayErrores) return null;
        if(preanalisis.tipo == TipoToken.LEFT_PAREN){
            match(TipoToken.LEFT_PAREN);
            List<Expresion> lstArguments = ARGUMENTS_OPC();
            match(TipoToken.RIGHT_PAREN);
            //CALL_2(); No se usa, dicho por el profesor
            ExprCallFunction ecf = new ExprCallFunction(expr, lstArguments);
            return CALL_2(ecf);
        } return null;
    }
    
    private Expresion PRIMARY(){
        //System.out.println("PRIMARY");
        if(hayErrores) return null;
        if(preanalisis.tipo == TipoToken.TRUE){
            match(TipoToken.TRUE);
            return new ExprLiteral(true);
        } else if(preanalisis.tipo == TipoToken.FALSE){
            match(TipoToken.FALSE);
            return new ExprLiteral(false);
        } else if(preanalisis.tipo == TipoToken.NULL){
            match(TipoToken.NULL);
            return new ExprLiteral(null);
        } else if(preanalisis.tipo == TipoToken.NUMBER){
            match(TipoToken.NUMBER);
            Token numero = previous();
            return new ExprLiteral(numero.getLiteral());
        } else if(preanalisis.tipo == TipoToken.STRING){
            match(TipoToken.STRING);
            Token cadena = previous();
            return new ExprLiteral(cadena.getLiteral());
        } else if(preanalisis.tipo == TipoToken.IDENTIFIER){
            match(TipoToken.IDENTIFIER);
            Token id = previous();
            return new ExprVariable(id);
        } else if(preanalisis.tipo == TipoToken.LEFT_PAREN){
            match(TipoToken.LEFT_PAREN);
            Expresion expr = EXPRESSION();
            match(TipoToken.RIGHT_PAREN);
            return new ExprGrouping(expr);
        }return null;
    }
    
    private void FUNCTION(){
        System.out.println("FUNCTION");
        if(hayErrores) return;
        match(TipoToken.IDENTIFIER);
        match(TipoToken.LEFT_PAREN);
        PARAMETERS_OPC();
        match(TipoToken.RIGHT_PAREN);
        BLOCK();
    } 
    
    /*private void FUNCTIONS(){
        if(hayErrores) return;
        else if(preanalisis.tipo == TipoToken.FUN){
            match(TipoToken.FUN);
            FUNCTIONS();
        }
    }*/

    private void PARAMETERS_OPC(){
        System.out.println("PARAMETERS_OPC");
        if(hayErrores) return;
        if(preanalisis.tipo == TipoToken.IDENTIFIER){
            PARAMETERS();
        }
    }
    
    private void PARAMETERS(){
        System.out.println("PARAMETERS");
        match(TipoToken.IDENTIFIER);
        PARAMETERS_2();
    }
    
    private void PARAMETERS_2(){
        System.out.println("PARAMETERS_2");
        if(hayErrores) return;
        if(preanalisis.tipo == TipoToken.COMMA){
            match(TipoToken.COMMA);
            match(TipoToken.IDENTIFIER);
            PARAMETERS_2();
        }
    }
    
    private List<Expresion> ARGUMENTS_OPC(){
        //System.out.println("ARGUMENTS_OPC");
        if(hayErrores) return null;
        if(primeroEXPR_STMT(preanalisis.tipo)){
            List<Expresion> lstArguments =  new ArrayList<Expresion>();
            lstArguments.add(EXPRESSION());
            lstArguments.addAll(ARGUMENTS());
            return lstArguments;
        } return null;
    }
    
    private List<Expresion> ARGUMENTS(){
        //System.out.println("ARGUMENTS");
        if(hayErrores) return null;
        if(preanalisis.tipo == TipoToken.COMMA){
            List<Expresion> lstArguments =  new ArrayList<Expresion>();
            match(TipoToken.COMMA);
            lstArguments.add(EXPRESSION());
            lstArguments.addAll(ARGUMENTS());
            return lstArguments;
        }return null;
    }

    private void match(TipoToken tt){
        if(preanalisis.tipo == tt){
            i++;
            if(i < tokens.size()) preanalisis = tokens.get(i);
        }else{
            hayErrores = true;
            System.out.println("Error encontrado, no se tiene "+tt);
        }
    }

    private Token previous() {
        return this.tokens.get(i - 1);
    }

    private boolean primeroSTATEMENT(TipoToken tt){
        switch (tt) {
            case BANG:
            case MINUS:
            case TRUE:
            case FALSE:
            case NULL:
            case NUMBER:
            case STRING:
            case IDENTIFIER:
            case LEFT_PAREN:
            case FOR:
            case IF:
            case PRINT:
            case RETURN:
            case WHILE:
            case LEFT_BRACE:
                return true;
            default:
                return false;
        }
    }

    private boolean primeroEXPR_STMT(TipoToken tt){
        switch (tt) {
            case BANG:
            case MINUS:
            case TRUE:
            case FALSE:
            case NULL:
            case NUMBER:
            case STRING:
            case IDENTIFIER:
            case LEFT_PAREN:
                return true;
            default:
                return false;
        }
    }
}