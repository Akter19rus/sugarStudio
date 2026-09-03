package com.example.sugarStudioBot.service.repositories;

import com.example.sugarStudioBot.service.model.RecordVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordVisitRepository extends JpaRepository<RecordVisit, Long> {
}
