package org.wangyao.testjenkins02.code.mqtt;

/**
 *
 * @author WangYao
 * date: 2026/11/26/09:45
 * description:
 */
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class DroneClient {

    // ================= 配置 =================
    private static final String BROKER = "tcp://127.0.0.1:28883";
    private static final String CLIENT_ID = "drone-java-001";
    private static final String TOPIC_TELEMETRY = "drone/001/telemetry";
    private static final String TOPIC_COMMAND   = "drone/001/command";
    private static final String TOPIC_STATUS    = "drone/001/status";

    public static void main(String[] args) {
        try {
            // 1. 创建客户端（MemoryPersistence 表示不持久化未发送消息，适合设备端简单场景）
            MqttClient client = new MqttClient(BROKER, CLIENT_ID, new MemoryPersistence());

            // 2. 设置回调：处理收到的指令和连接断开事件
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    // 连接意外断开时触发。遗嘱消息此时应由 Broker 发布。
                    System.out.println("[断开] 连接丢失: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
                    System.out.println("[指令] 主题: " + topic + "，内容: " + payload);
                    if (payload.contains("land")) {
                        System.out.println(">>> 执行降落");
                    } else if (payload.contains("return")) {
                        System.out.println(">>> 执行返航");
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // 发布完成回调（此处不强制处理）
                }
            });

            // 3. 配置连接选项：关键在 connect() 之前设置遗嘱
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setKeepAliveInterval(60);
            options.setConnectionTimeout(10);
            options.setAutomaticReconnect(true); // 可选：断线自动重连

            // 【核心】设置遗嘱消息：主题、载荷、QoS、是否保留
            // 无人机异常掉线（如断电、网络中断）时，Broker 会代发 "offline"
            options.setWill(
                    TOPIC_STATUS,
                    "offline".getBytes(StandardCharsets.UTF_8),
                    1,
                    true
            );
            System.out.println("[遗嘱] 已设置：异常掉线将发布 offline");

            // 4. 连接 Broker
            client.connect(options);
            System.out.println("[连接] 成功");

            // 5. 连接成功后订阅指令主题，并发布上线状态
            client.subscribe(TOPIC_COMMAND, 1);
            client.publish(TOPIC_STATUS,
                    new MqttMessage("online".getBytes(StandardCharsets.UTF_8)));
            System.out.println("[状态] 已发布 online");

            // 6. 模拟周期发布遥测数据
            Random random = new Random();
            for (int i = 0; i < 5; i++) {
                String telemetry = String.format(
                        "{\"drone\":\"%s\",\"battery\":%d,\"altitude\":%.1f,\"seq\":%d}",
                        CLIENT_ID,
                        60 + random.nextInt(40),
                        10.0 + random.nextDouble() * 40,
                        i
                );
                MqttMessage msg = new MqttMessage(telemetry.getBytes(StandardCharsets.UTF_8));
                msg.setQos(1);
                client.publish(TOPIC_TELEMETRY, msg);
                System.out.println("[遥测] " + telemetry);
                Thread.sleep(2000);
            }

            // 7. 正常断开：不会触发遗嘱（Broker 收到 DISCONNECT 报文）
            // 若想模拟异常掉线，注释掉下面两行，直接结束进程即可
            client.disconnect();
            System.out.println("[退出] 正常断开");

        } catch (MqttException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}