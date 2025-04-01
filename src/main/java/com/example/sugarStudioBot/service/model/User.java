package com.example.sugarStudioBot.service.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Модель таблицы, куда будут попадать все пользователи бота.
 */
@EqualsAndHashCode(exclude = "id", callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users_contact_info")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "is_admin")
    private boolean isAdmin = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Review> reviews;
}
