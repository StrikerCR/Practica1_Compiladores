package analizador;
import java.util.List;

public class ASDR implements Parser{
    private int i = 0;
    private boolean hayErrores = false;
    private Token preanalisis;
    private final List<Token> tokens;

    public ASDR(List<Token> tokens) {
        this.tokens = tokens;
        preanalisis = this.tokens.get(i);
    }
    
    @Override
    public boolean parse(){
        PROGRAM();
        if((i == tokens.size()) && !hayErrores){
            System.out.println("Consulta correcta");
            return true;
        }else System.out.println("Se encontraron errores");
        return false;
    }

    private void PROGRAM(){
        DECLARATION();
    }

    private void DECLARATION(){
        System.out.println("DECLARATION()");
        if(hayErrores) return;
        if(preanalisis.tipo == TipoToken.FUN){
            FUN_DECL();
            DECLARATION();
        }else if(preanalisis.tipo == TipoToken.VAR){
            VAR_DECL();
            DECLARATION();
        }else if(primeroSTATEMENT(preanalisis.tipo)){
            STATEMENT();
            DECLARATION();
        }
    }

    private void FUN_DECL(){
        System.out.println("FUN_DECL");
        if(hayErrores) return;
        match(TipoToken.FUN);
        FUNCTION();
    }

    private void VAR_DECL(){
        System.out.println("VAR_DECL");
        if(hayErrores) return;
        match(TipoToken.VAR);
        match(TipoToken.IDENTIFIER);
        VAR_INIT();
        match(TipoToken.SEMICOLON);
    }

    private void VAR_INIT(){
        System.out.println("VAR_INIT");
        if(hayErrores) return;
        if(preanalisis.tipo == TipoToken.EQUAL){
            match(TipoToken.EQUAL);
            EXPRESSION();
        }
    }

    private void STATEMENT(){
        System.out.println("STATEMENT");
        if(hayErrores) return;
        if(preanalisis.tipo == TipoToken.FOR){
            FOR_STMT();
        }else if(preanalisis.tipo == TipoToken.IF){
            IF_STMT();
        }else if(preanalisis.tipo == TipoToken.PRINT){
            PRINT_STMT();
        }else if(preanalisis.tipo == TipoToken.RETURN){
            RETURN_STMT();
        }else if(preanalisis.tipo == TipoToken.WHILE){
            WHILE_STMT();
        }else if(preanalisis.tipo == TipoToken.LEFT_BRACE){
            BLOCK();
        }else if(primeroEXPR_STMT(preanalisis.tipo)){
            EXPR_STMT();
        }else hayErrores = true;
    }

    private void EXPR_STMT(){
        System.out.println("EXPR_STMT");
        if(hayErrores) return;
        EXPRESSION();
        match(TipoToken.SEMICOLON);
    }

    private void FOR_STMT(){
        System.out.println("FOR_STMT");
        if(hayErrores) return;
        match(TipoToken.FOR);
        match(TipoToken.LEFT_PAREN);
        FOR_STMT_1();
        FOR_STMT_2();
        FOR_STMT_3();
        match(TipoToken.RIGHT_PAREN);
        STATEMENT();
    }

    private void FOR_STMT_1(){
        System.out.println("FOR_STMT_1");
        if(hayErrores) return;
        if(preanalisis.tipo == TipoToken.VAR){
            VAR_DECL();
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

    private void EXPRESSION(){
        System.out.println("EXPRESSION");
        if(hayErrores) return;
        ASSIGNMENT();
    }

    private void ASSIGNMENT(){
        System.out.println("ASSIGNMENT");
        if(hayErrores) return;
        LOGIC_OR();
        ASSIGNMENT_OPC();
    }
    
    private void ASSIGNMENT_OPC(){
        System.out.println("ASSIGNMENT_OPC");
        if(hayErrores) return;
        else if(preanalisis.tipo == TipoToken.EQUAL){
            match(TipoToken.EQUAL);
            EXPRESSION();
        }
    }

    private void LOGIC_OR(){
        System.out.println("LOGIC_OR");
        if(hayErrores) return;
        LOGIC_AND();
        LOGIC_OR_2();
    }

    private void LOGIC_OR_2(){
        System.out.println("LOGIC_OR_2");
        if(hayErrores) return;
        else if(preanalisis.tipo == TipoToken.OR){
            match(TipoToken.OR);
            LOGIC_AND();
            LOGIC_OR_2();
        }
    }

    private void LOGIC_AND(){
        System.out.println("LOGIC_AND");
        if(hayErrores) return;
        EQUALITY();
        LOGIC_AND_2();
    }
    
    private void LOGIC_AND_2(){
        System.out.println("LOGIC_AND_2");
        if(hayErrores) return;
        else if(preanalisis.tipo == TipoToken.AND){
            match(TipoToken.AND);
            EQUALITY();
            LOGIC_AND_2();
        }
    }

    private void EQUALITY(){
        System.out.println("EQUALITY");
        if(hayErrores) return;
        COMPARISON();
        EQUALITY_2();
    }

    private void EQUALITY_2(){
        System.out.println("EQUALITY_2");
        if(hayErrores) return;
        else if(preanalisis.tipo == TipoToken.BANG_EQUAL){
            match(TipoToken.BANG_EQUAL);
            COMPARISON();
            EQUALITY_2();
        } else if(preanalisis.tipo == TipoToken.EQUAL_EQUAL){
            match(TipoToken.EQUAL_EQUAL);
            COMPARISON();
            EQUALITY_2();
        }
    }

    private void COMPARISON(){
        System.out.println("COMPARISON");
        if(hayErrores) return;
        TERM();
        COMPARISON_2();
    }
    
    private void COMPARISON_2(){
        System.out.println("COMPARISON_2");
        if(hayErrores) return;
        else if(preanalisis.tipo == TipoToken.GREATER){
            match(TipoToken.GREATER);
            TERM();
            COMPARISON_2();
        } else if(preanalisis.tipo == TipoToken.GREATER_EQUAL){
            match(TipoToken.GREATER_EQUAL);
            TERM();
            COMPARISON_2();
        } else if(preanalisis.tipo == TipoToken.LESS){
            match(TipoToken.LESS);
            TERM();
            COMPARISON_2();
        } else if(preanalisis.tipo == TipoToken.LESS_EQUAL){
            match(TipoToken.LESS_EQUAL);
            TERM();
            COMPARISON_2();
        }
    }

    private void TERM(){
        System.out.println("TERM");
        if(hayErrores) return;
        FACTOR();
        TERM_2();
    }
    
    private void TERM_2(){
        System.out.println("TERM_2");
        if(hayErrores) return;
        else if(preanalisis.tipo == TipoToken.MINUS){
            match(TipoToken.MINUS);
            FACTOR();
            TERM_2();
        } else if(preanalisis.tipo == TipoToken.PLUS){
            match(TipoToken.PLUS);
            FACTOR();
            TERM_2();
        }
    }

    private void FACTOR(){
        System.out.println("FACTOR");
        if(hayErrores) return;
        UNARY();
        FACTOR_2();
    }

    private void FACTOR_2(){
        System.out.println("FACTOR_2");
        if(hayErrores) return;
        else if(preanalisis.tipo == TipoToken.SLASH){
            match(TipoToken.SLASH);
            UNARY();
            FACTOR_2();
        } else if(preanalisis.tipo == TipoToken.STAR){
            match(TipoToken.STAR);
            UNARY();
            FACTOR_2();
        }
    }

    private void UNARY(){
        System.out.println("UNARY");
        if(hayErrores) return;
        else if(preanalisis.tipo == TipoToken.BANG){
            match(TipoToken.BANG);
            UNARY();
        } else if(preanalisis.tipo == TipoToken.MINUS){
            match(TipoToken.MINUS);
            UNARY();
        } else {
            CALL();
        }
    }

    private void CALL(){
        System.out.println("CALL");
        if(hayErrores) return;
        PRIMARY();
        CALL_2();
    }
    
    private void CALL_2(){
        System.out.println("CALL_2");
        if(hayErrores) return;
        else if(preanalisis.tipo == TipoToken.LEFT_PAREN){
            match(TipoToken.LEFT_PAREN);
            ARGUMENTS_OPC();
            match(TipoToken.RIGHT_PAREN);
            //CALL_2(); No se usa, dicho por el profesor
        }
    }
    
    private void PRIMARY(){
        System.out.println("PRIMARY");
        if(hayErrores) return;
        else if(preanalisis.tipo == TipoToken.TRUE){
            match(TipoToken.TRUE);
        } else if(preanalisis.tipo == TipoToken.FALSE){
            match(TipoToken.FALSE);
        } else if(preanalisis.tipo == TipoToken.NULL){
            match(TipoToken.NULL);
        } else if(preanalisis.tipo == TipoToken.NUMBER){
            match(TipoToken.NUMBER);
        } else if(preanalisis.tipo == TipoToken.STRING){
            match(TipoToken.STRING);
        } else if(preanalisis.tipo == TipoToken.IDENTIFIER){
            match(TipoToken.IDENTIFIER);
        } else if(preanalisis.tipo == TipoToken.LEFT_PAREN){
            match(TipoToken.LEFT_PAREN);
            EXPRESSION();
            match(TipoToken.RIGHT_PAREN);
        }
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
        PARAMETERS();
    }
    
    private void PARAMETERS(){
        System.out.println("PARAMETERS");
        match(TipoToken.IDENTIFIER);
        PARAMETERS_2();
    }
    
    private void PARAMETERS_2(){
        System.out.println("PARAMETERS_2");
        if(hayErrores) return;
        else if(preanalisis.tipo == TipoToken.COMMA){
            match(TipoToken.COMMA);
            match(TipoToken.IDENTIFIER);
            PARAMETERS_2();
        }
    }
    
    private void ARGUMENTS_OPC(){
        System.out.println("ARGUMENTS_OPC");
        if(hayErrores) return;
        else if(primeroEXPR_STMT(preanalisis.tipo)){
            EXPRESSION();
            ARGUMENTS();
        }
    }
    
    private void ARGUMENTS(){
        System.out.println("ARGUMENTS");
        if(hayErrores) return;
        else if(preanalisis.tipo == TipoToken.COMMA){
            match(TipoToken.COMMA);
            EXPRESSION();
            ARGUMENTS();
        }
    }

    private void match(TipoToken tt){
        if(preanalisis.tipo == tt){
            i++;
            if(i < tokens.size()) preanalisis = tokens.get(i);
        }else{
            hayErrores = true;
            System.out.println("Error encontrado");
        }
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