import org.eclipse.paho.client.mqttv3.*;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;


public class Subscriber {
    private MqttClient client;
    private String topic = "IU/9/voronkov";
    private PictureForm gui;

    public Subscriber(PictureForm gui  ) throws MqttException{
        String broker = "tcp://broker.emqx.io:1883";
        String clientId = "voronkov_subscriber";
        try {
            this.client = new MqttClient(broker, clientId);
            MqttConnectOptions opts = new MqttConnectOptions();

            client.setCallback(new MqttCallback() {
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String mes = new String(message.getPayload());
                    System.out.println("message content: " + mes);
                    try {
                        InputStream body = new java.io.ByteArrayInputStream(mes.getBytes());
                        Parser parser = new Parser();
                        Tree tree = parser.parse(body);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        PrintStream ps = new PrintStream(baos, true, "UTF-8");
                        tree.print(ps, "", true);
                        String result = baos.toString("UTF-8");
                        SwingUtilities.invokeLater(() -> {
                            gui.updateLog(result);
                        });

                    } catch (Exception e) {
                        SwingUtilities.invokeLater(() -> {
                            gui.appendMessage("Ошибка: " + e.getMessage());
                        });
                    }
                }

                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost: " + cause.getMessage());
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete: " + token.isComplete());
                }
            });

            System.out.println("Connecting to broker...");
            client.connect(opts);

            client.subscribe(topic);
            System.out.println("waiting");
        } catch (MqttException e) {
            System.err.println("Ошибка MQTT: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
