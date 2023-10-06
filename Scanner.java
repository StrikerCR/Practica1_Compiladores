import java.io.EOFException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import javax.lang.model.type.NullType;

public class Scanner {

    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and",    TipoToken.AND);
        palabrasReservadas.put("else",   TipoToken.ELSE);
        palabrasReservadas.put("false",  TipoToken.FALSE);
        palabrasReservadas.put("for",    TipoToken.FOR);
        palabrasReservadas.put("fun",    TipoToken.FUN);
        palabrasReservadas.put("if",     TipoToken.IF);
        palabrasReservadas.put("null",   TipoToken.NULL);
        palabrasReservadas.put("or",     TipoToken.OR);
        palabrasReservadas.put("print",  TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("true",   TipoToken.TRUE);
        palabrasReservadas.put("var",    TipoToken.VAR);
        palabrasReservadas.put("while",  TipoToken.WHILE);
    }

    private final String source;

    private final List<Token> tokens = new ArrayList<>();
    
    public Scanner(String source){
        this.source = source + " ";
    }

    public List<Token> scan() throws Exception {
        String lexema = "";
        int estado = 0;
        int cont = 1;
        char c;

        for(int i=0; i<source.length(); i++){
            c = source.charAt(i);
            if(c == '\n') cont++;

            switch (estado){
                case 0:
                    if(Character.isLetter(c)){
                        estado = 9;
                        lexema += c;
                    } else if(Character.isDigit(c)){
                        estado = 11;
                        lexema += c;
                        /*while(Character.isDigit(c)){
                            lexema += c;
                            i++;
                            c = source.charAt(i);
                        }
                        Token t = new Token(TipoToken.NUMBER, lexema);
                        lexema = "";
                        estado = 0;
                        tokens.add(t);
                        */
                    } else if(c == '/'){
                        estado = 26;
                        //lexema += c;
                    } else if(c == '>'){
                        estado = 1;
                        lexema +=c;
                    } else if(c == '<'){
                        estado = 4;
                        lexema +=c;
                    } else if(c == '='){
                        estado = 7;
                        lexema +=c;
                    } else if(c == '!'){
                        estado = 10;
                        lexema +=c;
                    } else if(c == '"'){
                        estado = 24;
                        lexema +=c;
                    }else if( c == '(' || c == ')' || c == '{' || c == '}' || c == ',' || c == '.' || c == '-' || c == ';' || c =='*' ){
                        Token t;
                        lexema += c;
                        switch(c){
                            case '(':
                                t = new Token(TipoToken.LEFT_PAREN, lexema);
                                tokens.add(t);
                            break;
                            case ')':
                                t = new Token(TipoToken.RIGHT_PAREN, lexema);
                                tokens.add(t);
                            break;
                            case '{':
                                t = new Token(TipoToken.LEFT_BRACE, lexema);
                                tokens.add(t);
                            break;
                            case '}':
                                t = new Token(TipoToken.RIGHT_BRACE, lexema);
                                tokens.add(t);
                            break;
                            case ',':
                                t = new Token(TipoToken.COMMA, lexema);
                                tokens.add(t);
                            break;
                            case '.':
                                t = new Token(TipoToken.DOT, lexema);
                                tokens.add(t);
                            break;
                            case '-':
                                t = new Token(TipoToken.MINUS, lexema);
                                tokens.add(t);
                            break;
                            case ';':
                                t = new Token(TipoToken.SEMICOLON, lexema);
                                tokens.add(t);
                            break;
                            case '*':
                                t = new Token(TipoToken.STAR, lexema);
                                tokens.add(t);
                            break;
                            
                        }
                        estado = 0;
                        lexema = "";
                    }

                    break;
                case 24:
                    if(c == '\n'){
                        Interprete.error(cont-1, "No se cerr\u00f3 la cadena");
                        estado = 0;
                        lexema = "";
                    }else if(c == '"'){
                        lexema +=c;
                        Token t = new Token(TipoToken.STRING, lexema, lexema.replace('"', Character.MIN_VALUE));
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    }else{
                        lexema +=c;
                    }
                    break;
                case 1:
                    if(c == '='){
                        lexema +=c;
                        Token t = new Token(TipoToken.GREATER_EQUAL, lexema);
                        tokens.add(t);
                    }else{
                        Token t = new Token(TipoToken.GREATER, lexema);
                        tokens.add(t);
                        i--;
                    } estado = 0;
                    lexema = "";
                    break;
                case 4:
                    if(c == '='){
                        lexema +=c;
                        Token t = new Token(TipoToken.LESS_EQUAL, lexema);
                        tokens.add(t);
                    }else{
                        Token t = new Token(TipoToken.LESS, lexema);
                        tokens.add(t);
                        i--;
                    } estado = 0;
                    lexema = "";
                    break;
                case 7:
                    if(c == '='){
                        lexema +=c;
                        Token t = new Token(TipoToken.EQUAL_EQUAL, lexema);
                        tokens.add(t);
                    }else{
                        Token t = new Token(TipoToken.EQUAL, lexema);
                        tokens.add(t);
                        i--;
                    } estado = 0;
                    lexema = "";
                    break;
                case 10:
                    if(c == '='){
                        lexema +=c;
                        Token t = new Token(TipoToken.BANG_EQUAL, lexema);
                        tokens.add(t);
                    }else{
                        Token t = new Token(TipoToken.BANG, lexema);
                        tokens.add(t);
                        i--;
                    } estado = 0;
                    lexema = "";
                    break;
                case 9:
                    if(Character.isLetter(c) || Character.isDigit(c) || c == '_'){
                        estado = 9;
                        lexema += c;
                    }
                    else{
                        // Vamos a crear el Token de identificador o palabra reservada
                        TipoToken tt = palabrasReservadas.get(lexema);

                        if(tt == null){
                            Token t = new Token(TipoToken.IDENTIFIER, lexema);
                            tokens.add(t);
                        }
                        else{
                            Token t = new Token(tt, lexema);
                            tokens.add(t);
                        }

                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 11:
                    if(Character.isDigit(c)){
                        estado = 11;
                        lexema += c;
                    }
                    else if(c == '.'){
                        estado = 17;
                        lexema += c;
                    }
                    else if(c == 'E'){
                        estado = 18;
                        lexema += c;
                    }
                    else{
                        // Vamos a crear el Token de un número entero
                        Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema));
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                    }
                    break;
                case 17:
                    if(Character.isDigit(c)){
                        estado = 17;
                        lexema += c;
                    } else if(c == 'E'){
                        estado = 18;
                        lexema += c;
                    } else{
                        // Vamos a crear el Token de un número decimal
                        Token t = new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema));
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                    }
                break;
                case 18:
                    if(c == '+' || c == '-'){
                        estado = 20;
                        lexema += c;
                    }
                    else if(Character.isDigit(c)){
                        estado = 20;
                        lexema += c;
                    }
                break;
                case 20:
                    if(Character.isDigit(c)){
                        estado = 20;
                        lexema += c;
                    } else{
                        // Vamos a crear el Token de un número exponencial
                        Token t = new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema));
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                    }
                break;
                case 26:
                    //Comprobando si es un comentario 
                    if(c == '/'){
                        estado = 30;
                    } else if(c == '*'){
                        estado = 27;
                    }else{
                        //return SLASH
                        //Preguntar a que se refiere con esto
                        lexema = "/";
                        Token t = new Token(TipoToken.SLASH, lexema);
                        tokens.add(t);
                        
                        estado = 0;
                        lexema = "";
                    }
                break;
                case 27:
                    if(c == '*'){
                        estado = 28;
                    } else if(Character.isLetter(c) || Character.isDigit(c) ){
                        estado = 27;
                    }else{
                        if(i+1== source.length()){
                            //No hay cierre de comentario 
                            Interprete.error(cont, "El  comentario multilíena no tiene cierre...");
                            estado = 0;
                        }else{
                            estado = 27;
                        }
                        
                    }
                break;
                
                case 28: 
                   //Comprobando si es un comentario multilinea 
                    if(c == '*'){
                        estado = 28;
                    } else if(c == '/'){
                        //Fin comentario multilinea, NO GENERA TOKEN
                        estado = 0;
                        lexema = "";
                    } else if(Character.isLetter(c) || Character.isDigit(c) ){
                        estado = 27;
                    }else{
                        //No hay cierre del comentario multilinea
                        //Caso /*Hola *
                        if(i+1== source.length()){
                            //No hay cierre de comentario 
                            Interprete.error(cont, "El comentario multilínea no tiene cierre");
                            estado = 0;
                        }else{
                            estado = 27;
                        }
                    }
                break;

                case 30:
                    //Comentario de una sola línea
                    if(Character.isLetter(c) || Character.isDigit(c) || c == ' '){
                        estado = 30;
                    }else if(c == '\n'){
                        //Fin de comentario, NO GENERA TOKEN
                        estado = 0;
                        lexema = "";
                    }
                break;
            }
        }


        return tokens;
    }
}
