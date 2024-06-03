package com.vedik.quizapp.util;

import com.vedik.quizapp.dto.TopicDto;


public class TestUtil {

    public static TopicDto topicRecord(){
        return new TopicDto(1, "Politics","Democracy");
    }

    public static TopicDto topicNewRecord(){
        return new TopicDto(0, "Politics","Democracy");
    }

    public static TopicDto topicRecordWithInvalidValues(){
        return new TopicDto(0, "Kafka","Kafka Using Spring Boot" );
    }

//    public static LibraryEvent libraryEventRecord(){
//
//        return
//                new LibraryEvent(null,
//                        LibraryEventType.NEW,
//                        bookRecord());
//    }
//
//    public static LibraryEvent newLibraryEventRecordWithLibraryEventId(){
//
//        return
//                new LibraryEvent(123,
//                        LibraryEventType.NEW,
//                        bookRecord());
//    }
//
//    public static LibraryEvent libraryEventRecordUpdate(){
//
//        return
//                new LibraryEvent(123,
//                        LibraryEventType.UPDATE,
//                        bookRecord());
//    }
//
//    public static LibraryEvent libraryEventRecordUpdateWithNullLibraryEventId(){
//
//        return
//                new LibraryEvent(null,
//                        LibraryEventType.UPDATE,
//                        bookRecord());
//    }
//
//    public static LibraryEvent libraryEventRecordWithInvalidBook(){
//
//        return
//                new LibraryEvent(null,
//                        LibraryEventType.NEW,
//                        bookRecordWithInvalidValues());
//    }
//
//    public static LibraryEvent parseLibraryEventRecord(ObjectMapper objectMapper , String json){
//
//        try {
//            return  objectMapper.readValue(json, LibraryEvent.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
}
