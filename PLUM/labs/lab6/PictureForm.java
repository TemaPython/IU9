import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PictureForm {
    private JPanel mainPanel;
    private JTextField areaField;
    private JSpinner radiusSpinner;
    private CanvasPanel canvasPanel;
    private JSpinner aSpinner;
    private JSpinner bSpinner;
    private JSpinner cSpinner;
    private JTextPane xtext;
    private JTextPane ytext;
    private JTextPane ztext;

    public void setXYZText() {
        xtext.setText(String.format("%d градусов", canvasPanel.getAngleX()));
        ytext.setText(String.format("%d градусов", canvasPanel.getAngleY()));
        ztext.setText(String.format("%d градусов", canvasPanel.getAngleZ()));
    }

    public PictureForm() {
        aSpinner.setValue(20);
        bSpinner.setValue(20);
        cSpinner.setValue(20);
        setXYZText();
        aSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                    int a = (int)aSpinner.getValue();
                    canvasPanel.setA(a);
                    setXYZText();
            }
        });
        bSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int b = (int)bSpinner.getValue();
                canvasPanel.setB(b);
                setXYZText();
            }
        });
        cSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int c = (int)cSpinner.getValue();
                canvasPanel.setC(c);
                setXYZText();
            }
        });
    }

    public static void main (String[ ]  args) {
        JFrame frame = new JFrame("Треугольник");
        frame.setContentPane (new PictureForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
