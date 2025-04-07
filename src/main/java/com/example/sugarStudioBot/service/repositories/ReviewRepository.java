package com.example.sugarStudioBot.service.repositories;

import com.example.sugarStudioBot.service.model.Review;
import com.example.sugarStudioBot.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUser(User user);
}
