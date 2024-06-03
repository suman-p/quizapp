package com.vedik.quizapp.service;

import com.vedik.quizapp.dto.TopicDto;
import com.vedik.quizapp.entity.Topic;

import java.util.List;

public interface TopicService {
    TopicDto createTopic(TopicDto topicDto);

    TopicDto updateTopic(long topicId, TopicDto topicDto);

    List<TopicDto> getTopics();

    TopicDto getTopic(long topicId);

    void deleteTopic(long topicId);
}
