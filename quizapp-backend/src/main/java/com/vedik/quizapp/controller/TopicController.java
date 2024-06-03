package com.vedik.quizapp.controller;


import com.vedik.quizapp.dto.TopicDto;
import com.vedik.quizapp.entity.Topic;
import com.vedik.quizapp.service.TopicService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/topics", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
@Validated
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping
    public ResponseEntity<TopicDto> createTopic(@Valid @RequestBody TopicDto topic){
        TopicDto createdTopic = topicService.createTopic(topic);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTopic.getTopicId())
                .toUri();
        return ResponseEntity.created(location)
                .body(createdTopic);
    }

    @PutMapping("/{topicId}")
    public ResponseEntity<TopicDto> updateTopic(@PathVariable long topicId, @Valid @RequestBody TopicDto topicDto){
        TopicDto updatedTopic = topicService.updateTopic(topicId, topicDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updatedTopic.getTopicId())
                .toUri();
        return ResponseEntity.ok()
                .body(updatedTopic);
    }

    @GetMapping
    public ResponseEntity<List<TopicDto>> getTopics(){
        List<TopicDto> topicDtos = topicService.getTopics();
        return ResponseEntity
                .ok()
                .body(topicDtos);
    }

    @GetMapping(path="/{topicId}")
    public ResponseEntity<TopicDto> getTopic(@PathVariable long topicId){
        TopicDto topicDto = topicService.getTopic(topicId);
        return ResponseEntity.ok()
                .body(topicDto);
    }

    @DeleteMapping(path="/{topicId}")
    public ResponseEntity<String> deleteTopic(@PathVariable long topicId){
        topicService.deleteTopic(topicId);
        return ResponseEntity.ok()
                .body("Topic deleted successfully");
    }
}
