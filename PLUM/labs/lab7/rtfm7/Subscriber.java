import org.eclipse.paho.client.mqttv3.*;
import javax.swing.*;


public class Subscriber {
    private MqttClient client;
    private String topic = "IU/9/voronkov";
    private PictureForm1 gui;

    public Subscriber(PictureForm1 gui  ) throws MqttException{
        String broker = "tcp://broker.emqx.io:1883";
        String clientId = "voronkov_subscriber";
        try {
            this.client = new MqttClient(broker, clientId);
            MqttConnectOptions opts = new MqttConnectOptions();

            client.setCallback(new MqttCallback() {
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String mes = new String(message.getPayload());
                    System.out.println("message content: " + mes);
                    String[] nums = mes.split(" ");
                    if (nums.length != 3) {
                        System.out.println("Incorrect input");
                        return;
                    }
                    try {
                        Vector vc = new Vector(mes);
                        double n = vc.norm();
                        System.out.println("Euclidian norm: " + n);
                        String result = "Вектор: " + mes + " | Норма: " + n;
                        SwingUtilities.invokeLater(() -> {
                            gui.updateLog(result);
                        });

                    } catch (NumberFormatException e) {
                        System.out.println("Incorrect input");
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
