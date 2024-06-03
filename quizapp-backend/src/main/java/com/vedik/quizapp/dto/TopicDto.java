package com.vedik.quizapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto {
    private long topicId;

    @Size(min = 3, max = 30, message = "Topic Name cannot be empty, must be between 3 and 30 characters")
    private String topicName;

    @NotBlank(message = "Topic Description cannot be Empty")
    private String topicDescription;
}
