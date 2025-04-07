package com.example.sugarStudioBot.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

@EqualsAndHashCode(exclude = "id", callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user_review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_chat_id", referencedColumnName = "chat_id")
    private User user;

    @Override
    public String toString() {
        return "❤️" + user.getName() + " " + user.getSurname() + "❤️"
                + "\n" + text + "\n\n";
    }
}
