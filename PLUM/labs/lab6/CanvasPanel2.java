import javax.swing.*;
import java.awt.*;
import java.lang.Math;

public class CanvasPanel2 extends JPanel{
    private int a = 20;
    private int b = 40;
    private int alpha = 45;
    public void setA(int a) {
        this.a = a;
        repaint();
    }
    public void setB(int b) {
        this.b = b;
        repaint();
    }
    public void setAlpha(int alpha) {
        this.alpha = alpha;
        repaint();
    }

    public int newX(int x, int y) {
        double alph = -Math.toRadians(alpha);
        return (int)Math.round(x * Math.cos(alph) - y * Math.sin(alph));
    }

    public int newY(int x, int y) {
        double alph = -Math.toRadians(alpha);
        return (int)Math.round(x * Math.sin(alph) + y * Math.cos(alph));
    }

    protected void paintComponent (Graphics g) {
        super.paintComponent (g);

        if (a < 0 || b < 0) {
            g.drawString("Нельзя построить", 10, 20);
            return;
        }

        g.setColor(Color.RED);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int bigger;
        int lower;
        bigger = Math.max(a, b);
        lower = Math.min(a, b);
        int[] xpoints = {cx + newX(-bigger/2, -lower/2), cx + newX(bigger/2, -lower/2),
                cx + newX(bigger/2, lower/2), cx + newX(-bigger/2, lower/2)};
        int[] ypoints = {cy + newY(-bigger/2, -lower/2), cy + newY(bigger/2, -lower/2),
                cy + newY(bigger/2, lower/2), cy + newY(-bigger/2, lower/2)};

        g.drawPolygon(xpoints, ypoints, 4);
    }
}
