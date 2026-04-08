import java.io.IOException ;
import java.io.InputStream ;
import java.text.ParseException ;

public class Lexer {
    InputStream is;
    int curChar;
    int curPos;
    Token curToken;
    String curStr = "";

    public Lexer(InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    private boolean isBlank(int c) {
        return c == ' ' || c == '\r' || c == '\n' || c == '\t';
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    public void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }
        switch (curChar) {
            case '(':
                nextChar();
                curToken = Token.LPAREN;
                break;
            case ')':
                nextChar();
                curToken = Token.RPAREN;
                break;
            case '.':
                nextChar();
                curToken = Token.POINT;
                break;
            case '"':
                nextChar();
                curStr = "";
                while (curChar != '"') {
                    if (curChar == -1 || curChar == '\n' || curChar == '\r') {
                        throw new ParseException("string without end", curPos);
                    }
                    curStr += (char) curChar;
                    nextChar();
                }
                nextChar();
                curToken = Token.STRING;
                break;
            case -1:
                curToken = Token.END;
                break;
            default:
                if (Character.isDigit(curChar)) {
                    curStr = "";
                    while (Character.isDigit(curChar)) {
                        curStr += (char) curChar;
                        nextChar();
                    }
                    curToken = Token.NUM;
                } else if (Character.isAlphabetic(curChar)) {
                        curStr = "";
                    while (Character.isAlphabetic(curChar) || Character.isDigit(curChar)) {
                            curStr += (char) curChar;
                            nextChar();
                        }
                        curToken = Token.IDENT;
                } else{
                    throw new ParseException("notKnown char" + (char) curChar, curPos);
                }
                break;
        }
    }

    public Token curToken() {return curToken;}
    public int curPos() {return curPos;}
    public String curStr() { return curStr; }
}
