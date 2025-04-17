package com.example.sugarStudioBot.service.service.review;

import com.example.sugarStudioBot.service.model.Review;
import com.example.sugarStudioBot.service.model.User;
import com.example.sugarStudioBot.service.repositories.ReviewRepository;
import com.example.sugarStudioBot.service.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review updateReview(Review review) {
        if (!reviewRepository.existsById(review.getId())) {
            throw new IllegalArgumentException("нет комментария с таким ID: " + review.getId());
        }
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

//    @Transactional
//    public void saveReviewForUser(long chatId, String text) {
//        try {
//            User user = entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.reviews WHERE u.chatId = :chatId"
//                            , User.class)
//                    .setParameter("chatId", chatId)
//                    .getSingleResult();
//
//            Review review = new Review();
//            review.setText(text);
//            review.setUser(user);
//            entityManager.persist(review);
//        } catch (NoResultException e) {
//            log.error("Пользователь не найден с Id: " + chatId);
//        } catch (Exception e) {
//            log.error("Ошибка сохранения отзыва в базу данных");
//        }
//    }

    @Transactional
    public void saveReviewForUser(long chatId, String text) {
        try {
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.chatId = :chatId", User.class)
                    .setParameter("chatId", chatId)
                    .getSingleResult();

            Review review = new Review();
            review.setText(text);
            review.setUser(user);
            entityManager.persist(review);
        } catch (NoResultException e) {
            log.error("Пользователь не найден с Id: " + chatId);
        } catch (Exception e) {
            log.error("Ошибка сохранения отзыва в базу данных: " + e.getMessage());
        }
    }
}
