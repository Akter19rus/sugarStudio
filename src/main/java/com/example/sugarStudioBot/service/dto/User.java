package com.example.sugarStudioBot.service.dto;


import jakarta.persistence.*;
import lombok.*;

/**
 * Модель таблицы, куда будут попадать все пользователи бота.
 */
@EqualsAndHashCode(exclude = "id")

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_contact_info")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "age")
    private Integer age;
}
