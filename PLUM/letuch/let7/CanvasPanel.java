import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.Point2D;

public class CanvasPanel extends JPanel {
    private double fi=45.0, v=10.0, g=9.81;
    private Roots<Point2D.Double> points = new Roots<>(new ArrayList<>());

    public void setPoints(Roots<Point2D.Double> points) {
        this.points = points;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g2) {
        super.paintComponent(g2);

        g2.setColor(Color.RED);

        int prevX = 0;
        int prevY = getHeight();
        double scale = 10;

        for (Point2D.Double p : points.container) {
            int drawX = (int) (p.x * scale);
            int drawY = getHeight() - (int) (p.y * scale);

            g2.drawLine(prevX, prevY, drawX, drawY);

            prevX = drawX;
            prevY = drawY;
        }
    }
}
