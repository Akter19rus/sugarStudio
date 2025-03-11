package com.example.sugarStudioBot.service.service.user;

import com.example.sugarStudioBot.service.dto.User;
import com.example.sugarStudioBot.service.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Сохраняет пользователя в БД
     *
     * @param user пользователь
     * @return сохранение пользователя
     */
    @Override
    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не может быть пустым");
        }
        return userRepository.save(user);
    }

    /**
     * Обновляет пользователю
     * Имя Фамилию Возраст в БД
     *
     * @param id идентификатор пользователя
     * @param user пользователь
     * @return сохранение изменений в БД
     */
    @Override
    public User updateUser(long id, User user) {
        User updateUser = userRepository.findUserById(id);
        if (updateUser == null) {
            throw new IllegalArgumentException("Нет пользователя с таким ID: " + id);
        }
        updateUser.setName(user.getName());
        updateUser.setSurname(user.getSurname());
        updateUser.setAge(user.getAge());
        return userRepository.save(updateUser);
    }

    /**
     *Показывает всех пользователей из БД
     *
     * @return возвращает всех пользователей
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Удаляет пользователя из БД
     *
     * @param userId идентификатор пользователя
     */
    @Override
    public void deleteUserById(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("Нет пользователя с таким ID: " + userId);
        }
        userRepository.deleteById(userId);
    }
}
