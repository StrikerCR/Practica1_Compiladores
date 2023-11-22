import java.util.List;
import java.util.PrimitiveIterator;

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
        if(preanalisis.tipo == TipoToken.EOF && !hayErrores){
            System.out.println("Consulta correcta");
            return true;
        }else System.out.println("Se encontraron errores");
        return false;
    }

    private void PROGRAM(){
        DECLARATION();
    }

    private void DECLARATION(){
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
        if(hayErrores) return;
        match(TipoToken.FUN);
        //FUNCTION();
    }

    private void VAR_DECL(){
        if(hayErrores) return;
        match(TipoToken.VAR);
        match(TipoToken.IDENTIFIER);
        VAR_INIT();
    }

    private void VAR_INIT(){
        if(hayErrores) return;
        if(preanalisis.tipo == TipoToken.EQUAL){
            match(TipoToken.EQUAL);
            EXPRESSION();
        }
    }

    private void STATEMENT(){
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
        if(hayErrores) return;
        EXPRESSION();
        match(TipoToken.SEMICOLON);
    }

    private void FOR_STMT(){
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
        if(hayErrores) return;
        if(preanalisis.tipo == TipoToken.SEMICOLON){
            match(TipoToken.SEMICOLON);
        }else if(primeroEXPR_STMT(preanalisis.tipo)){
            EXPRESSION();
            match(TipoToken.SEMICOLON);
        }else hayErrores = true;
    }

    private void FOR_STMT_3(){
        if(hayErrores) return;
        if(primeroEXPR_STMT(preanalisis.tipo)) EXPRESSION();
    }

    private void IF_STMT(){
        if(hayErrores) return;
        match(TipoToken.IF);
        match(TipoToken.LEFT_PAREN);
        EXPRESSION();
        match(TipoToken.RIGHT_PAREN);
        STATEMENT();
        ELSE_STATEMENT();
    }

    private void ELSE_STATEMENT(){
        if(hayErrores) return;
        if(preanalisis.tipo == TipoToken.ELSE){
            match(TipoToken.ELSE);
            STATEMENT();
        }
    }

    private void PRINT_STMT(){
        if(hayErrores) return;
        match(TipoToken.PRINT);
        EXPRESSION();
    }

    private void RETURN_STMT(){
        if(hayErrores) return;
        match(TipoToken.RETURN);
        RETURN_EXP_OPC();
        match(TipoToken.SEMICOLON);
    }

    private void RETURN_EXP_OPC(){
        if(hayErrores) return;
        if(primeroEXPR_STMT(preanalisis.tipo)){
            EXPRESSION();
        }
    }

    private void WHILE_STMT(){
        if(hayErrores) return;
        match(TipoToken.WHILE);
        match(TipoToken.LEFT_PAREN);
        EXPRESSION();
        match(TipoToken.RIGHT_PAREN);
        STATEMENT();
    }

    private void BLOCK(){
        if(hayErrores) return;
        match(TipoToken.LEFT_BRACE);
        DECLARATION();
        match(TipoToken.RIGHT_BRACE);
    }

    private void EXPRESSION(){
        //ASSIGNMENT();
    }

    private void match(TipoToken tt){
        if(preanalisis.tipo == tt){
            i++;
            preanalisis = tokens.get(i);
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