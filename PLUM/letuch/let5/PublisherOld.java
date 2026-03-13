import org.eclipse.paho.client.mqttv3.*;
import java.util.Scanner;

public class PublisherOld {
    public static void main(String[] args) {
        String broker = "tcp://broker.emqx.io:1883";
        String clientId = "voronkov_java_pub";
        String topic = "IU/9/voronkov";

        try {
            MqttClient client = new MqttClient(broker, clientId);
            MqttConnectOptions opts = new MqttConnectOptions();
            opts.setCleanSession(true);

            System.out.println("Connecting to broker...");
            client.connect(opts);
            System.out.println("Connected!");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String payload = scanner.nextLine();
                MqttMessage message = new MqttMessage(payload.getBytes());
                client.publish(topic, message);
                System.out.println("ok");
            }

        } catch (MqttException e) {
            System.err.println("Ошибка MQTT: " + e.getMessage());
            e.printStackTrace();
        }
    }
}