package pl.bykowski.sptest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SpTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpTestApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void get(){
        System.out.println("Hello world");
    }

}
