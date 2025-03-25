package com.example.sugarStudioBot.service.repositories;

import com.example.sugarStudioBot.service.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepository extends JpaRepository<Images, Long> {

    Images findImagesByName(String name);
}
