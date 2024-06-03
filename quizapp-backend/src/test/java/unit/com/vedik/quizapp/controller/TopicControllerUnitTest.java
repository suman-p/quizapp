package com.vedik.quizapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vedik.quizapp.dto.TopicDto;
import com.vedik.quizapp.exception.ResourceNotFoundException;
import com.vedik.quizapp.exception.TopicAlreadyExistsException;
import com.vedik.quizapp.service.TopicService;
import com.vedik.quizapp.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TopicController.class)
@AutoConfigureMockMvc
public class TopicControllerUnitTest {
    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper=new ObjectMapper();
    @MockBean
    private TopicService topicService;

    @Test
    void postTopic() throws Exception {
        //given
        TopicDto topicDto = TestUtil.topicRecord();

        String json = objectMapper.writeValueAsString(topicDto);

        Mockito.when(topicService.createTopic(Mockito.any(TopicDto.class))).thenReturn(topicDto);

        MvcResult result =  this.mockMvc.perform(post("/api/v1/topics")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertEquals("http://localhost/api/v1/topics/1", response.getHeader(HttpHeaders.LOCATION));

    }

    @Test
    void postTopicWithoutName() throws Exception {
        //given
        TopicDto topicDto = new TopicDto(0, "", "negative scenario");

        String json = objectMapper.writeValueAsString(topicDto);

        Mockito.when(topicService.createTopic(Mockito.any(TopicDto.class))).thenReturn(topicDto);

        MvcResult result =  this.mockMvc.perform(post("/api/v1/topics")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        assertTrue(response.getContentAsString()
                .contains("Topic Name cannot be empty, must be between 3 and 30 characters"));

    }

    @Test
    void postTopicWithDuplicateName() throws Exception {
        //given
        TopicDto topicDto = TestUtil.topicRecordWithInvalidValues();

        String json = objectMapper.writeValueAsString(topicDto);

        Mockito.when(topicService.createTopic(Mockito.any(TopicDto.class)))
                .thenThrow(new TopicAlreadyExistsException("Topic with name : 'Kafka' already exists"));

        MvcResult result =  this.mockMvc.perform(post("/api/v1/topics")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

        assertTrue(response.getContentAsString()
                .contains("Topic with name : 'Kafka' already exists"));

    }

    @Test
    public void getTopics() throws Exception {

        TopicDto topic1 = new TopicDto(1L, "Sports", "Group game");
        TopicDto topic2 = new TopicDto(2L, "Politics", "for the people");
        List<TopicDto> topics = Arrays.asList(topic1, topic2);

        Mockito.when(topicService.getTopics()).thenReturn(topics);

        this.mockMvc.perform(get("/api/v1/topics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].topicName").value("Sports"))
                .andExpect(jsonPath("$[1].topicName").value("Politics"));
    }

    @Test
    public void getTopic() throws Exception {
        TopicDto topic1 = new TopicDto(1L, "Sports", "Group game");
        Mockito.when(topicService.getTopic(1L)).thenReturn(topic1);

        mockMvc.perform(get("/api/v1/topics/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.topicName").value("Sports"));
    }
}
