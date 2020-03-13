package com.tbagrel1.lunch.api.controllers;

import com.tbagrel1.lunch.api.db.models.Answer;
import com.tbagrel1.lunch.api.db.models.AnswerId;
import com.tbagrel1.lunch.api.db.repositories.AccountRepository;
import com.tbagrel1.lunch.api.db.repositories.AnswerRepository;
import com.tbagrel1.lunch.api.db.repositories.CandidateRepository;
import com.tbagrel1.lunch.api.db.repositories.EventRepository;
import com.tbagrel1.lunch.api.exceptions.BadRequestException;
import com.tbagrel1.lunch.api.exceptions.NotFoundException;
import com.tbagrel1.lunch.core.models.input.InputAnswer;
import com.tbagrel1.lunch.core.models.output.OutputAnswer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AnswerController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    EventRepository eventRepository;

    @RequestMapping(path = "/api/answer",
                    method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputAnswer> postAnswer(Authentication authentication, @RequestBody InputAnswer inputAnswer) {
        String username = authentication.getName();
        Calendar today = new GregorianCalendar();
        if (!candidateRepository.isCandidate(username, today)) {
            throw new BadRequestException();
        }
        if (answerRepository.findById(new AnswerId(username, today)).isPresent()) {
            throw new BadRequestException();
        }
        Answer answer = new Answer(username, today, inputAnswer.isYes(), inputAnswer.getComment());
        answerRepository.save(answer);

        OutputAnswer outputAnswer = answer.toOutput(accountRepository);
        eventRepository.answerPosted(outputAnswer);

        return new ResponseEntity<OutputAnswer>(
            outputAnswer,
            HttpStatus.OK
        );
    }

    @RequestMapping(path = "/api/answer",
                    method = RequestMethod.PATCH,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OutputAnswer> patchAnswer(Authentication authentication, @RequestBody InputAnswer inputAnswer) {
        String username = authentication.getName();
        Calendar today = new GregorianCalendar();
        Answer answer = answerRepository.findById(new AnswerId(username, today))
            .orElseThrow(NotFoundException::new);
        OutputAnswer prevOutputAnswer = answer.toOutput(accountRepository);

        answer.setYes(inputAnswer.isYes());
        answer.setComment(inputAnswer.getComment());
        answerRepository.save(answer);

        OutputAnswer newOutputAnswer = answer.toOutput(accountRepository);
        eventRepository.answerPatched(prevOutputAnswer, newOutputAnswer);

        return new ResponseEntity<OutputAnswer>(
            newOutputAnswer,
            HttpStatus.OK
        );
    }
}
