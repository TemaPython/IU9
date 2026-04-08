import java.io.*;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        try {
            InputStream is = new ByteArrayInputStream("a.mul(3).sub(b.div(c))".getBytes());
            Tree root = parser.parse(is);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos, true, "UTF-8");
            root.print(ps, "", true);

            System.out.println(baos.toString("UTF-8"));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println("Encoding error: " + e.getMessage());
        }
    }
}