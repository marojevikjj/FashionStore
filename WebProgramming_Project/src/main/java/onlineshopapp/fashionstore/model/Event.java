package onlineshopapp.fashionstore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;
import java.time.LocalDateTime;

import lombok.Data;

@Entity
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime finish;

    @ManyToOne
    private User user;

    public Event(String title, String description, LocalDateTime start, LocalDateTime finish, User user) {
        this.title = title;
        this.description = description;
        this.start = start;
        this.finish = finish;
        this.user = user;
    }

    public Event() {
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", title=" + title + ", description=" + description + ", start=" + start
                + ", finish=" + finish + "]";
    }
}