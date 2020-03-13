package com.tbagrel1.lunch.api.db.repositories;

import com.tbagrel1.lunch.api.db.models.Answer;
import com.tbagrel1.lunch.api.db.models.Event;
import com.tbagrel1.lunch.api.db.models.EventId;
import com.tbagrel1.lunch.core.models.output.OutputAnswer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

// TODO: correct HTTP codes
// TODO: strip fields

public interface EventRepository extends JpaRepository<Event, EventId> {
    static String presenceString(OutputAnswer outputAnswer) {
        return String.format(
            "%s %s présent au RU ce midi%s",
            outputAnswer.getAccount().getDisplayedName(),
            outputAnswer.isYes() ? "sera" : "ne sera pas",
            outputAnswer.getComment().isEmpty() ? "" : String.format(" (%s)", outputAnswer.getComment())
        );
    }

    Optional<Event> findById(EventId eventId);

    default void answerPosted(OutputAnswer outputAnswer) {
        this.save(new Event(
            new Date(),
            outputAnswer.getAccount().getUsername(),
            presenceString(outputAnswer)
        ));
    }

    default void answerPatched(OutputAnswer prevOutputAnswer, OutputAnswer newOutputAnswer) {
        String content = null;
        String displayedName = newOutputAnswer.getAccount().getDisplayedName();

        if (prevOutputAnswer.isYes() != newOutputAnswer.isYes()) {
            content = String.format(
                "%s a changé sa réponse : %s",
                displayedName,
                presenceString(newOutputAnswer)
            );
        } else if (!prevOutputAnswer.getComment().equals(newOutputAnswer.getComment())) {
            if (newOutputAnswer.getComment().isEmpty()) {
                content = String.format(
                    "%s a enlevé son commentaire",
                    displayedName
                );
            } else if (prevOutputAnswer.getComment().isEmpty()) {
                content = String.format(
                    "%s a ajouté un commentaire : %s",
                    displayedName,
                    newOutputAnswer.getComment()
                );
            } else {
                content = String.format(
                    "%s a changé son commentaire : %s",
                    displayedName,
                    newOutputAnswer.getComment()
                );
            }
        }
        
        if (content != null) {
            this.save(new Event(
                new Date(),
                newOutputAnswer.getAccount().getUsername(),
                content
            ));
        }
    }
}
