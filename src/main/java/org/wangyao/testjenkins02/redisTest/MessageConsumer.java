package org.wangyao.testjenkins02.redisTest;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessageConsumer implements StreamListener<String, ObjectRecord<String, String>> {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(ObjectRecord<String, String> message) {
        String stream = message.getStream();
        String messageId = message.getId().toString();
        String messageBody = message.getValue();

        System.out.println("Received message from Stream '" + stream + "' with messageId: " + messageId);
        System.out.println("Message body: " + messageBody);

//        消息应答
        redisTemplate.opsForStream().acknowledge(RedisStreamConfig.streamName, RedisStreamConfig.userEventGroup, message.getId());
    }
}
