import org.eclipse.paho.client.mqttv3.MqttException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PictureForm1 {
    private JPanel mainPanel;
    private JSpinner xSpinner;
    private JSpinner ySpinner;
    private JSpinner zSpinner;
    private JTextArea textArea;
    private Publisher publisher;
    public void updateLog(String text) {
        textArea.append(text + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public PictureForm1(Publisher publisher) {
        this.publisher = publisher;
        xSpinner.setValue(10);
        ySpinner.setValue(10);
        zSpinner.setValue(10);
        Vector vector = new Vector(10, 10, 10);
        xSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int x = (int)xSpinner.getValue();
                vector.changeX(x);
                publisher.sendMessage(vector.toString());
            }
        });
        ySpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int y = (int)ySpinner.getValue();
                vector.changeY(y);
                publisher.sendMessage(vector.toString());
            }
        });
        zSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int z = (int)zSpinner.getValue();
                vector.changeZ(z);
                publisher.sendMessage(vector.toString());
            }
        });

    }


    public static void main(String[] args) {
        try {
            Publisher pub = new Publisher();
            PictureForm1 form = new PictureForm1(pub);
            JFrame frame = new JFrame("Вектор");
            frame.setContentPane(form.mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            new Subscriber(form);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
