package tn.esprit.cloud_in_mypocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EssaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EssaiApplication.class, args);
    }

}
