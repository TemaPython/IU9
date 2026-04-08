import java.io.InputStream ;
import java.text.ParseException;

public class Parser {
    Lexer lex;

    Tree Chain() throws ParseException {
        if (lex.curToken == Token.IDENT) {
            String ident = lex.curStr;
            lex.nextToken();
            return new Tree("<Chain>", new Tree(ident), Tail());
        } else {
                throw new ParseException("syntax error at pos " + lex.curPos, lex.curPos);
        }
    }

    Tree Tail() throws ParseException {
        switch (lex.curToken) {
            case POINT:
                lex.nextToken();
                if (lex.curToken != Token.IDENT) {
                    throw new ParseException("syntax error at pos " + lex.curPos, lex.curPos);
                }
                String ident = lex.curStr;
                lex.nextToken();
                if (lex.curToken != Token.LPAREN) {
                    throw new ParseException("syntax error at pos " + lex.curPos, lex.curPos);
                }
                lex.nextToken();
                Tree sup = Arg();
                if (lex.curToken != Token.RPAREN) {
                    throw new ParseException("syntax error at pos " + lex.curPos, lex.curPos);
                }
                lex.nextToken();
                Tree sup2 = Tail();
                return new Tree("<Tail>", new Tree("."),
                        new Tree(ident), new Tree("("), sup, new Tree(")"), sup2);
            case RPAREN:
                case END:
                return new Tree("<Tail>");
            default:
                throw new ParseException("syntax error at pos " + lex.curPos,  lex.curPos);
        }
    }
    Tree Arg() throws ParseException {
        switch (lex.curToken) {
            case STRING:
            case NUM:
                String curStr = lex.curStr;
                lex.nextToken();
                return new Tree("<Arg>", new Tree(curStr));
            case IDENT:
                return new Tree("<Arg>", Chain());
            default:
                throw new ParseException("syntax error at pos " + lex.curPos, lex.curPos);
        }
    }

    Tree parse(InputStream is) throws ParseException {
        lex = new Lexer(is);
        lex.nextToken();

        return Chain();
    }
}
