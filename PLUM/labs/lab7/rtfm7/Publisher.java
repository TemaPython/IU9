import org.eclipse.paho.client.mqttv3.*;
import java.util.Scanner;


public class Publisher {
    private MqttClient client;
    private String topic = "IU/9/voronkov";

    public Publisher() throws MqttException {
        String broker = "tcp://broker.emqx.io:1883";
        String clientId = "voronkov_publisher";
        client = new MqttClient(broker, clientId);
        client.connect();
    }
        public void sendMessage(String text) {
            try{
                client.publish(topic, new MqttMessage(text.getBytes()));
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }