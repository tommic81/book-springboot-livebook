package pl.bykowski.sptest;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "page-info")
public class PageInfo {
   // @Value("${page-info.author}")
    private String author;

    //@Value("${page-info.creation-date}")
    private int creationDate;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(int creationDate) {
        this.creationDate = creationDate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void get(){
        System.out.println(author);
        System.out.println(creationDate);
    }
}
