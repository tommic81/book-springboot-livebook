package pl.bykowski.sptest;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("Basic")
public class ShopBasic {

    @EventListener(ApplicationReadyEvent.class)
    public void get(){
        System.out.println("ShopBasic");
    }
}
