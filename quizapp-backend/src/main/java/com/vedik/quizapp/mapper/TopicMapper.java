package com.vedik.quizapp.mapper;

import com.vedik.quizapp.dto.TopicDto;
import com.vedik.quizapp.entity.Topic;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper {
    public TopicDto mapTopicToTopicDto(Topic topic, TopicDto topicDto){
        topicDto.setTopicId(topic.getTopicId());
        topicDto.setTopicName(topic.getTopicName());
        topicDto.setTopicDescription(topic.getTopicDescription());
        return topicDto;
    }

    public Topic mapTopicDtoToTopic(Topic topic, TopicDto topicDto){
        topic.setTopicId(topicDto.getTopicId());
        topic.setTopicName(topicDto.getTopicName());
        topic.setTopicDescription(topicDto.getTopicDescription());
        return topic;
    }
}
