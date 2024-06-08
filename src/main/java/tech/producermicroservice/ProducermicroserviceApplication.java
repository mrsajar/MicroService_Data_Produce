package tech.producermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.producermicroservice.KafkaConfiguration.ProducerConfiguration;
import tech.producermicroservice.SendDataService.ProducerTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class ProducermicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducermicroserviceApplication.class, args);
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		// call kafka producer class
		ProducerTask producer = new ProducerTask(ProducerConfiguration.producerConfig());
		executorService.submit(producer);
		executorService.shutdown();
	}

}
