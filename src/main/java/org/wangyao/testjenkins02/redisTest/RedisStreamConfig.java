package org.wangyao.testjenkins02.redisTest;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;

import java.net.InetAddress;
import java.time.Duration;
import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class RedisStreamConfig implements InitializingBean, DisposableBean {
    private final RedisTemplate<String, Object> redisTemplate;

    public static String streamName = "user-event-stream";
    public static String userEventGroup = "user-event-group";
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 消息侦听器容器，用于监听 Redis Stream 中的消息
     *
     * @param connectionFactory Redis 连接工厂，用于创建 Redis 连接
     * @param messageConsumer   消息消费者，用于处理接收到的消息
     * @return 返回 {@link StreamMessageListenerContainer}<{@link String}, {@link ObjectRecord}<{@link String}, {@link String}>> 类型的消息侦听器容器
     */
    @Bean
    public StreamMessageListenerContainer<String, ObjectRecord<String, String>> messageListenerContainer(RedisConnectionFactory connectionFactory, MessageConsumer messageConsumer) {
        StreamMessageListenerContainer<String, ObjectRecord<String, String>> listenerContainer = streamContainer(streamName, connectionFactory, messageConsumer);
        listenerContainer.start();
        return listenerContainer;
    }

    /**
     * 创建一个流容器，用于监听 Redis Stream 中的数据
     *
     * @param streamName        Redis Stream 的名称
     * @param connectionFactory Redis 连接工厂
     * @param streamListener    绑定的监听类
     * @return 返回 StreamMessageListenerContainer 对象
     */
    @SneakyThrows
    private StreamMessageListenerContainer<String, ObjectRecord<String, String>> streamContainer(String streamName, RedisConnectionFactory connectionFactory, StreamListener<String, ObjectRecord<String, String>> streamListener) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        .pollTimeout(Duration.ofSeconds(5)) // 拉取消息超时时间
                        .batchSize(10) // 批量抓取消息
                        .targetType(String.class) // 传递的数据类型
                        .executor(threadPoolTaskExecutor)
                        .build();
        StreamMessageListenerContainer<String, ObjectRecord<String, String>> container = StreamMessageListenerContainer
                .create(connectionFactory, options);
        // 指定消费最新的消息
        StreamOffset<String> offset = StreamOffset.create(streamName, ReadOffset.lastConsumed());
        // 创建消费者
        StreamMessageListenerContainer.StreamReadRequest<String> streamReadRequest = buildStreamReadRequest(offset, streamListener);
        // 指定消费者对象
        container.register(streamReadRequest, streamListener);
        return container;
    }

    /**
     * 生成流读取请求
     *
     * @param offset         偏移量，用于指定从 Redis Stream 中的哪个位置开始读取消息
     * @param streamListener 流侦听器，用于处理接收到的消息
     * @return 返回一个 StreamReadRequest 对象，表示一个流读取请求
     * @throws Exception 当 streamListener 无法识别为 MessageConsumer 类型时，抛出异常
     */
    private StreamMessageListenerContainer.StreamReadRequest<String> buildStreamReadRequest(StreamOffset<String> offset, StreamListener<String, ObjectRecord<String, String>> streamListener) throws Exception {
        Consumer consumer;
        if (streamListener instanceof MessageConsumer) {
            consumer = Consumer.from(userEventGroup, InetAddress.getLocalHost().getHostName());
        } else {
            throw new Exception("无法识别的 stream key");
        }
        // 关闭自动 ack 确认
        return StreamMessageListenerContainer.StreamReadRequest.builder(offset)
                .errorHandler((error) -> {
                    log.error(error.getMessage());
                })
                .cancelOnError(e -> false)
                .consumer(consumer)
                // 关闭自动 ack 确认
                .autoAcknowledge(false)
                .build();
    }

    /**
     * 检查 Redis 版本是否符合要求
     *
     * @throws IllegalStateException 如果 Redis 版本小于 5.0.0 版本，抛出该异常
     */
    private void checkRedisVersion() {
        // 获得 Redis 版本
        Properties info = redisTemplate.execute((RedisCallback<Properties>) RedisServerCommands::info);
        Assert.notNull(info, "Redis info is null");
        Object redisVersion = info.get("redis_version");
        Integer anInt = Convert.toInt(redisVersion);
        if (anInt < 5) {
            throw new IllegalStateException(StrUtil.format("您当前的 Redis 版本为 {}，小于最低要求的 5.0.0 版本！", redisVersion));
        }
    }

    @Override
    public void destroy() {
    }
    @Override
    public void afterPropertiesSet() {
        checkRedisVersion();
        StreamOperations<String, Object, Object> streamOperations = redisTemplate.opsForStream();
        if (!redisTemplate.hasKey(streamName)) {
            streamOperations.createGroup(streamName, ReadOffset.from("0"), userEventGroup);
        }
    }
}