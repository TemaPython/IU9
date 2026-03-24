import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PictureForm {
    private JPanel panel;
    private JLabel labelFi;
    private JLabel labelV;
    private JLabel labelG;
    private JSpinner spinnerFi;
    private JSpinner spinnerV;
    private JSpinner spinnerG;
    private CanvasPanel canvasPanel;


    private double fi = 45.0, v = 10.0, g = 9.81;

    public PictureForm(CanvasPanel canvasPanel) {
        this.canvasPanel = canvasPanel;
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        labelFi = new JLabel("Fi");
        labelV = new JLabel("V");
        labelG = new JLabel("g");

        spinnerFi = new JSpinner(new SpinnerNumberModel(45.0, 0.0, 90.0, 1.0));
        spinnerV = new JSpinner(new SpinnerNumberModel(10.0, 0.0, 1000.0, 0.1));
        spinnerG = new JSpinner(new SpinnerNumberModel(9.81, 0, 100, 0.01));

        ChangeListener listener = e -> updateTrajectory();
        spinnerFi.addChangeListener(listener);
        spinnerV.addChangeListener(listener);
        spinnerG.addChangeListener(listener);



        panel.add(labelFi);
        panel.add(spinnerFi);
        panel.add(labelV);
        panel.add(spinnerV);
        panel.add(labelG);
        panel.add(spinnerG);
        updateTrajectory();
    }

    private void updateTrajectory() {
        double fi = ((Number) spinnerFi.getValue()).doubleValue();
        double v  = ((Number) spinnerV.getValue()).doubleValue();
        double g  = ((Number) spinnerG.getValue()).doubleValue();

        double fiRad = Math.toRadians(fi);
        double tFlight = 2 * v * Math.sin(fiRad) / g;

        Roots<Double> times = new Roots<>(new ArrayList<>());
        int steps = 100;
        for (int i = 0; i <= steps; i++)
            times.container.add(i * tFlight / steps);

        Roots<Point2D.Double> points = times.map(t -> {
            double x = v * Math.cos(fiRad) * t;
            double y = v * Math.sin(fiRad) * t - 0.5 * g * t * t;
            return new Point2D.Double(x, y);
        });

        canvasPanel.setPoints(points);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Траектория полета");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        CanvasPanel canvasPanel = new CanvasPanel();
        canvasPanel.setPreferredSize(new Dimension(300,200));
        frame.add(new PictureForm(canvasPanel).panel, BorderLayout.NORTH);
        frame.add(canvasPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }
}
