package com.example.sugarStudioBot.service.controller.swagger;

import com.example.sugarStudioBot.service.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Контроллер пользователей",
        description = "Создание пользователя.  " +
                "Получение пользователей из базы.  " +
                "Удаление пользователей из базы.  " +
                "Прочие операции.")
public interface UserControllerSwagger {
    @Operation(summary = "Создание пользователя",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Создать запись о пользователе",

                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            )
    )
    @PostMapping("/create")
    ResponseEntity<User> createUser(@RequestBody User user);

    @Operation(summary = "Редактирование пользователя",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Редактировать запись пользователя",

                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            )
    )

    @PutMapping("/update/{userId}")
    ResponseEntity<User> updateUser(@PathVariable long userId, @RequestBody User user);

    @Operation(summary = "Получаем всех пользователей из базы данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "найденные ползователи",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = User.class))
                            )
                    )
            })
    @GetMapping("/all")
    ResponseEntity<List<User>> getAllUser();

    @Operation(summary = "Удаляем пользователя из базы данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    )
            })

    @DeleteMapping("/delete/{userId}")
    ResponseEntity<Void> deleteUserById(@PathVariable Long userId);
}
