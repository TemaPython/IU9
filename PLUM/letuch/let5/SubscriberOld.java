import org.eclipse.paho.client.mqttv3.*;

public class SubscriberOld {

    public static void main(String[] args) {
        String broker = "tcp://broker.emqx.io:1883";
        String topic = "IU/9/voronkov";
        String clientId = "voronkov_subscriber";
        try {
            MqttClient client = new MqttClient(broker, clientId);
            MqttConnectOptions opts = new MqttConnectOptions();

            client.setCallback(new MqttCallback() {
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String mes = new String(message.getPayload());
                    System.out.println("message content: " + mes);
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
