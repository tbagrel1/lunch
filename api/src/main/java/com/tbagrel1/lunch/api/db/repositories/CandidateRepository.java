package com.tbagrel1.lunch.api.db.repositories;

import com.tbagrel1.lunch.api.db.models.NonCandidateDay;
import com.tbagrel1.lunch.api.db.models.NonCandidateDayId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface CandidateRepository extends JpaRepository<NonCandidateDay, NonCandidateDayId> {
    Optional<NonCandidateDay> findById(NonCandidateDayId id);
    Collection<NonCandidateDay> findByUsername(String username);

    default boolean isCandidate(String username, Calendar day) {
        return this.findById(new NonCandidateDayId(username, day)).isEmpty();
    }
    default void makeCandidate(String username, Calendar day) {
        this.findById(new NonCandidateDayId(username, day)).map(nonCandidateDay -> {
            this.delete(nonCandidateDay);
            return nonCandidateDay;
        });
    }
    default void makeNonCandidate(String username, Calendar day) {
        NonCandidateDay nonCandidateDay = new NonCandidateDay();
        nonCandidateDay.setUsername(username);
        nonCandidateDay.setDay(day);
        if (this.findById(new NonCandidateDayId(username, day)).isEmpty()) {
            this.save(nonCandidateDay);
        }
    }

    @Query((
        "SELECT nonCandidateDay" +
        "FROM NonCandidateDay nonCandidateDay" +
        "WHERE nonCandidateDay.username = ?1 AND nonCandidateDay.day >= ?2"))
    Collection<NonCandidateDay> _getFutureNonCandidateDays(String username, Calendar from);

    default Collection<Calendar> getFutureNonCandidateDays(String username, Calendar from) {
        return _getFutureNonCandidateDays(username, from).stream()
            .map(NonCandidateDay::getDay)
            .collect(Collectors.toList());
    }

    default void setFutureNonCandidateDays(String username, Calendar from, Collection<Calendar> days) {
        Set<NonCandidateDay> nonCandidateDaysToDelete = new HashSet<>(_getFutureNonCandidateDays(username, from));
        Set<NonCandidateDay> temp = new HashSet<>(nonCandidateDaysToDelete);
        Set<NonCandidateDay> nonCandidateDaysToCreate = days.stream().map(day -> new NonCandidateDay(username, day)).collect(Collectors.toSet());
        nonCandidateDaysToDelete.removeAll(nonCandidateDaysToCreate);
        nonCandidateDaysToCreate.removeAll(temp);
        for (NonCandidateDay nonCandidateDay: nonCandidateDaysToDelete) {
            this.delete(nonCandidateDay);
        }
        for (NonCandidateDay nonCandidateDay: nonCandidateDaysToCreate) {
            this.save(nonCandidateDay);
        }
    }
}
