package com.kat.possimulator;

import com.kat.possimulator.config.TopicsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties.class)
@EnableScheduling
public class PosSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PosSimulatorApplication.class, args);
	}

}
