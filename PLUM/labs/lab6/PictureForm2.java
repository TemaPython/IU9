import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PictureForm2 {
    private JPanel mainPanel;
    private JSpinner aSpinner;
    private JSpinner bSpinner;
    private JSpinner alphaSpinner;
    private CanvasPanel2 canvasPanel2;

    public PictureForm2() {
        aSpinner.setValue(20);
        bSpinner.setValue(40);
        alphaSpinner.setValue(45);
        aSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int a = (int)aSpinner.getValue();
                canvasPanel2.setA(a);
            }
        });
        bSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int b = (int)bSpinner.getValue();
                canvasPanel2.setB(b);
            }
        });
        alphaSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int alpha = (int)alphaSpinner.getValue();
                canvasPanel2.setAlpha(alpha);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Прямоугольник");
        frame.setContentPane (new PictureForm2().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
