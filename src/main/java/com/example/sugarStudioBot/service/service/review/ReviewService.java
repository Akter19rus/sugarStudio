package com.example.sugarStudioBot.service.service.review;

import com.example.sugarStudioBot.service.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Review addReview(Review review);

    Optional<Review> getReviewById(Long id);

    List<Review> getAllReviews();

    Review updateReview(Review review);

    void deleteReview(Long id);

    void saveReviewForUser(long chatId, String text);
}
