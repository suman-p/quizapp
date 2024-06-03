package com.vedik.quizapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.boot.model.relational.ColumnOrderingStrategy;

@Entity
@Table(name="topics")
@Data
public class Topic extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long topicId;

    @Column(name="topic_name")
    private String topicName;

    @Column(name="topic_description")
    private String topicDescription;

}
