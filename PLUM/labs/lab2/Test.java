public class Test {
    public static void main (String [] args) {
        Point p1 = new Point(0,0, 0);
        Point p2 = new Point(30,20, 10);
        Point p3 = new Point(10,30, 40);
        Triangle trian = new Triangle(p1, p2, p3);
        System.out.println(trian);
        System.out.println("Square: " + trian.getSquare());
    }
}
