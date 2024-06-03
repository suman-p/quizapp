package com.vedik.quizapp.repository;

import com.vedik.quizapp.dto.TopicDto;
import com.vedik.quizapp.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> findByTopicName(String topicName);

    //Page<Topic> getAllTopicsByPage(Pageable pageable);
}
