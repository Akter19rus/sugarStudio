package com.example.sugarStudioBot.service.repositories;

import com.example.sugarStudioBot.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(long id);

    User findUserByChatId(long id);
}
