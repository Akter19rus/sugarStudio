package com.example.sugarStudioBot.service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Контроллер пользователей",
        description = "Создание пользователя.  " +
                "Получение пользователей из базы.  " +
                "Удаление пользователей из базы.  " +
                "Прочие операции.")
@RequestMapping("/users")
@RestController
public class UserController {
}
