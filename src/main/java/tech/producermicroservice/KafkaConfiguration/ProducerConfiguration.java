package tech.producermicroservice.KafkaConfiguration;

import java.util.Properties;

public class ProducerConfiguration {

    public static Properties producerConfig(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return properties;

    }
}
