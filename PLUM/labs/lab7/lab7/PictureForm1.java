import org.eclipse.paho.client.mqttv3.MqttException;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

public class PictureForm1 {
    private JPanel mainPanel;
    private JSpinner xSpinner;
    private JSpinner ySpinner;
    private JSpinner zSpinner;
    private JTextArea textArea;
    private JTextPane textPane1;
    private JButton sendButton;
    private JTextField textField1;
    private JTextField textField2;
    private JButton deleteChatButton;
    private JTextArea textArea1;
    private Publisher publisher;
    private String username = "Тёма";
    public void updateLog(String text) {
        if (text.equals("delete")) {
            textArea.setText("");
        } else {
            textArea.append(gtTime() + " " + text + "\n");
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }

    public String gtTime() {
        LocalDate date = LocalDate.now();
        String formatted = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        LocalTime time = LocalTime.now();
        String formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        return formatted + " " + formattedTime;
    }


    public PictureForm1(Publisher publisher) {
        this.publisher = publisher;


        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = textField2.getText();
                String text = textField1.getText();
                if (!text.isEmpty()) {
                    publisher.sendMessage(username + ": " + text);
                    textField1.setText("");
                }
            }
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = textField2.getText();
                publisher.sendMessage(textField1.getText());
                textField1.setText("");
            }
        });
        textField2.setText(username);
        deleteChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                publisher.sendMessage("delete");
            }
        });
    }


    public static void main(String[] args) {
        try {
            Publisher pub = new Publisher();
            PictureForm1 form = new PictureForm1(pub);
            JFrame frame = new JFrame("Чат");
            frame.setContentPane(form.mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            form.textField1.requestFocusInWindow();
            new Subscriber(form);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
