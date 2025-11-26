package org.wangyao.testjenkins02.redisTest;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {

    private final RedisTemplate<String, Object> redisTemplate;

    public void sendMessage(String streamKey, Object message) {
        RecordId recordId = redisTemplate
                .opsForStream().add(StreamRecords.newRecord()
                        .ofMap(Collections.singletonMap("data", message))
                        .withStreamKey(streamKey));
        if (recordId!= null) {
            log.info("Message sent to Stream '{}' with RecordId: {}", streamKey, recordId);
        }
    }
}
