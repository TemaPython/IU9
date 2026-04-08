import org.eclipse.paho.client.mqttv3.MqttException;

import javax.swing.*;
import java.awt.*;

public class PictureForm {
    private JPanel mainPanel;
    private JTextArea textArea;
    private Publisher publisher;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    public void updateLog(String text) {
        appendMessage(text);
    }

    public PictureForm(Publisher publisher) {
        JFrame frame = new JFrame("Парсер");
        this.publisher = publisher;
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(chatArea);
        scroll.setPreferredSize(new Dimension(450, 350));

        inputField = new JTextField();
        inputField.addActionListener(e -> sendMessage());

        sendButton = new JButton("Отправить");
        sendButton.addActionListener(e -> sendMessage());

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            publisher.sendMessage(text);
            appendMessage("Result: " + text);
            inputField.setText("");
        }
    }

    public void appendMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }

    public static void main(String[] args) {
        try {
            Publisher pub = new Publisher();
            PictureForm form = new PictureForm(pub);
            new Subscriber(form);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
