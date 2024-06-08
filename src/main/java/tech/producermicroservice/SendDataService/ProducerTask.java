package tech.producermicroservice.SendDataService;

import org.apache.kafka.clients.producer.*;
import java.util.Scanner;


import java.util.Properties;
public class ProducerTask implements Runnable{

    Producer<String, String> producer;

    private int success;
    private int failed;

    public ProducerTask(Properties properties) {
        this.producer = new KafkaProducer<>(properties);
        this.success = 0;
        this.failed = 0;
    }


    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);

            // Ask the user to enter the number of messages
            while (true) {
                System.out.println("....................Enter the number of messages:............");
                int numberOfMessages = scanner.nextInt();

                for (int i = 0; i < numberOfMessages; i++) {
                    String message = "hello";

                    // Create a message with a unique key and value
                    String key = message + "-" + i;
                    String value = message + "-" + i;

                    // Send the message to the topic
                    ProducerRecord<String, String> record = new ProducerRecord<>("hello-topic", key, value);
                    producer.send(record, new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
                            if (exception != null) {
                                System.out.println("fail to send message on kafka with exception " + exception.getMessage());
                                failed++;
                            } else {
                                System.out.printf("Record sent successfully to topic %s, partition %d, offset %d%n", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
                                success++;
                            }
                        }
                    });
                    Thread.sleep(300);
                }

                // Ask the user if they want to continue
                System.out.print("Do you want to continue (yes/no)? ");
                String continueOption = scanner.next();
                if (!continueOption.equalsIgnoreCase("yes")) {
                    break; // Exit the loop if the user doesn't want to continue
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            producer.flush();
            producer.close();
            System.out.println("Success count: " + success);
            System.out.println("Failure count: " + failed);
        }
    }

}
