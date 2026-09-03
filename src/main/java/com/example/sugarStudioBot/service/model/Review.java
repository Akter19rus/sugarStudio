package com.example.sugarStudioBot.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
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
    @ToString.Exclude
    private User user;

    @Override
    public String toString() {
        return "❤️" + user.getName() + " " +
                Optional.ofNullable(user.getSurname()).orElse("") + "❤️"
                + "\n" + text + "\n\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        return id != null && id.equals(((Review) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
