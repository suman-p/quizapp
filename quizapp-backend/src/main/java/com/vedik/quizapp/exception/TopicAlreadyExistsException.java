package com.vedik.quizapp.exception;


import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus()
public class TopicAlreadyExistsException extends RuntimeException{
    public TopicAlreadyExistsException(String message) {
        super(message);
    }
}
