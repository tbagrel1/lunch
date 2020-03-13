package com.tbagrel1.lunch.api.db.repositories;

import com.tbagrel1.lunch.api.db.models.Answer;
import com.tbagrel1.lunch.api.db.models.AnswerId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, AnswerId> {
    Optional<Answer> findById(AnswerId answerId);
}
