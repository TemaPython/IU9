import javax.swing.*;
import java.awt.*;
import java.lang.Math;

public class CanvasPanel extends JPanel {
    private int a = 20;
    private int b = 20;
    private int c = 20;
    private double x = Math.PI / 3;
    private double y = Math.PI / 3;
    private double z = Math.PI / 3;
    public void setA(int a) {
        this.a = a;
        recalcAngles();
        repaint();
    }
    public void setB(int b) {
        this.b = b;
        recalcAngles();
        repaint();
    }
    public void setC(int c) {
        this.c = c;
        recalcAngles();
        repaint();
    }
    public int getAngleX() { return (int)Math.toDegrees(x); }
    public int getAngleY() { return (int)Math.toDegrees(y); }
    public int getAngleZ() { return (int)Math.toDegrees(z); }

    private void recalcAngles() {
        if (a + b <= c || a + c <= b || b + c <= a) return;
        x = Math.acos((a * a + b * b - c * c) / (2.0 * a * b));
        y = Math.acos((a * a + c * c - b * b) / (2.0 * a * c));
        z = Math.PI - x - y;
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (a + b <= c || a + c <= b || b + c <= a) {
            g.drawString("Нельзя построить", 10, 20);
            return;
        }

        g.setColor(new Color((250 * getAngleX() / 180),
                (250 * getAngleY() / 180),
                (250 * getAngleZ() / 180)));


        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int[] xpoints = {cx - a/2, cx + a/2, cx  - a/2 + (int)(b * Math.cos(x))};
        int[] ypoints = {cy, cy, cy - (int)(b * Math.sin(x))};
        g.fillPolygon(xpoints, ypoints, 3);
        g.drawPolygon(xpoints, ypoints, 3);
    }
}