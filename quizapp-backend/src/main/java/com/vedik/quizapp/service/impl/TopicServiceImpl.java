package com.vedik.quizapp.service.impl;

import com.vedik.quizapp.dto.TopicDto;
import com.vedik.quizapp.entity.Topic;
import com.vedik.quizapp.exception.ResourceNotFoundException;
import com.vedik.quizapp.exception.TopicAlreadyExistsException;
import com.vedik.quizapp.mapper.TopicMapper;
import com.vedik.quizapp.repository.TopicRepository;
import com.vedik.quizapp.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TopicServiceImpl implements TopicService {

    private TopicRepository topicRepository;

    private TopicMapper topicMapper;

    @Override
    @Transactional
    public TopicDto createTopic(TopicDto topicDto) {
        Optional<Topic> topicSaved = topicRepository.findByTopicName(topicDto.getTopicName());
        if(topicSaved.isPresent()){
            throw new TopicAlreadyExistsException("Topic with name : '" + topicDto.getTopicName() + "' already exists");
        }
        Topic topic = topicMapper.mapTopicDtoToTopic(new Topic(), topicDto);
        Topic createdTopic = topicRepository.save(topic);
        return topicMapper.mapTopicToTopicDto(createdTopic, new TopicDto());
    }

    @Override
    public TopicDto updateTopic(long topicId, TopicDto topicDto) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        if(!topic.isPresent()){
            throw new ResourceNotFoundException("Topic", "Id", String.valueOf(topicId));
        }
        topicMapper.mapTopicDtoToTopic(topic.get(), topicDto);
        Topic updatedTopic = topicRepository.save(topic.get());
        return topicMapper.mapTopicToTopicDto(updatedTopic, new TopicDto());
    }

    @Override
    public TopicDto getTopic(long topicId) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        if(!topic.isPresent()){
            throw new ResourceNotFoundException("Topic", "Id", String.valueOf(topicId));
        }
        return topicMapper.mapTopicToTopicDto(topic.get(), new TopicDto());
    }

    @Override
    public List<TopicDto> getTopics() {
        return topicRepository.findAll().stream()
                .map(topic -> topicMapper.mapTopicToTopicDto(topic, new TopicDto()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTopic(long topicId) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        if(!topic.isPresent()){
            throw new ResourceNotFoundException("Role", "Id", String.valueOf(topicId));
        }
        topicRepository.deleteById(topicId);
    }
}
