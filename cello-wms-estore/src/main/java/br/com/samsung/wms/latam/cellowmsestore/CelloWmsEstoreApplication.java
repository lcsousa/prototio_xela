package br.com.samsung.wms.latam.cellowmsestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {"br.com.samsung.wms.latam.cellowmsestore"})
public class CelloWmsEstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CelloWmsEstoreApplication.class, args);
	}

}
