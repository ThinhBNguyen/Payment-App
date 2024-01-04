package com.example.Revagenda.Rev_Pay;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication()
public class RevPayApplication {

	private static Logger logger = LogManager.getLogger("fileLogger");
	public static void main(String[] args) {
		SpringApplication.run(RevPayApplication.class, args);
	}

}
