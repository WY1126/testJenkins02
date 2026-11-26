package org.wangyao.testjenkins02.code.mqtt;

/**
 *
 * @author WangYao
 * date: 2026/11/26/10:26
 * description:
 */

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class UavMqttDevice {

    private static final String BROKER = "tcp://localhost:28883";
    private static final String DEVICE_ID = "UAV001";

    private static final String STATUS_TOPIC =
            "uav/" + DEVICE_ID + "/status";
    private static final String TELEMETRY_TOPIC =
            "uav/" + DEVICE_ID + "/telemetry";
    private static final String COMMAND_TOPIC =
            "uav/" + DEVICE_ID + "/command";

    private MqttClient client;

    private final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    public void start() throws Exception {
        // 同一设备必须使用稳定且唯一的 ClientId
        client = new MqttClient(
                BROKER,
                DEVICE_ID,
                new MemoryPersistence()
        );

        MqttConnectOptions options = new MqttConnectOptions();

        // MQTT 3.1.1 持久会话
        options.setCleanSession(false);

        // 连接保活，单位秒
        options.setKeepAliveInterval(60);

        // 自动重连
        options.setAutomaticReconnect(true);

        // 连接超时时间
        options.setConnectionTimeout(10);

        // 如果 Broker 开启认证，配置账号密码
        // options.setUserName("uav_user");
        // options.setPassword("your_password".toCharArray());

        // 设置遗嘱：异常离线时由 Broker 发布
        String willPayload =
                "{\"deviceId\":\"" + DEVICE_ID +
                        "\",\"status\":\"offline\"}";

        options.setWill(
                STATUS_TOPIC,
                willPayload.getBytes(StandardCharsets.UTF_8),
                1,
                true
        );

        // 注册回调
        client.setCallback(new MqttCallbackExtended() {

            @Override
            public void connectComplete(
                    boolean reconnect,
                    String serverURI) {

                System.out.println("MQTT连接成功: " + serverURI
                        + ", reconnect=" + reconnect);

                if (reconnect) {
                    // 自动重连成功后，重新订阅指令
                    subscribeCommands();

                    // 恢复在线状态
                    publishStatus("online");
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("MQTT连接断开: "
                        + cause.getMessage());
            }

            @Override
            public void messageArrived(
                    String topic,
                    MqttMessage message) {

                String payload = new String(
                        message.getPayload(),
                        StandardCharsets.UTF_8
                );

                System.out.println("收到平台消息: topic="
                        + topic + ", payload=" + payload);

                // TODO: 根据 commandId、commandType 执行实际业务
                // 例如：拍照、开始巡检、返航等
            }

            @Override
            public void deliveryComplete(
                    IMqttDeliveryToken token) {
                System.out.println("消息发送完成");
            }
        });

        // 建立连接
        client.connect(options);

        // 初次连接订阅指令
        subscribeCommands();

        // 发布在线状态，retain=true
        publishStatus("online");

        // 定时发布遥测
        scheduler.scheduleAtFixedRate(
                this::publishTelemetry,
                0,
                5,
                TimeUnit.SECONDS
        );
    }

    /**
     * 订阅平台下发指令
     */
    private void subscribeCommands() {
        try {
            if (client != null && client.isConnected()) {
                client.subscribe(COMMAND_TOPIC, 1);
                System.out.println("已订阅指令: " + COMMAND_TOPIC);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布设备状态
     */
    private void publishStatus(String status) {
        try {
            if (client == null || !client.isConnected()) {
                return;
            }

            String payload =
                    "{\"deviceId\":\"" + DEVICE_ID +
                            "\",\"status\":\"" + status + "\"}";

            MqttMessage message = new MqttMessage(
                    payload.getBytes(StandardCharsets.UTF_8)
            );

            message.setQos(1);
            message.setRetained(true);

            client.publish(STATUS_TOPIC, message);

            System.out.println("发布状态: " + payload);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布无人机遥测
     */
    private void publishTelemetry() {
        try {
            if (client == null || !client.isConnected()) {
                return;
            }

            // 模拟遥测数据
            int battery = 85;
            double longitude = 118.123456;
            double latitude = 32.123456;

            String payload =
                    "{\"deviceId\":\"" + DEVICE_ID +
                            "\",\"battery\":" + battery +
                            ",\"longitude\":" + longitude +
                            ",\"latitude\":" + latitude + "}";

            MqttMessage message = new MqttMessage(
                    payload.getBytes(StandardCharsets.UTF_8)
            );

            message.setQos(1);
            message.setRetained(false);

            client.publish(TELEMETRY_TOPIC, message);

            System.out.println("发布遥测: " + payload);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 正常关闭设备
     */
    public void stop() {
        scheduler.shutdown();

        try {
            if (client != null && client.isConnected()) {
                // 正常下线：主动发布 offline
                // 再发送 DISCONNECT，Broker 不会发布 Will
                publishStatus("offline");

                client.disconnect();
                client.close();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        UavMqttDevice device = new UavMqttDevice();

        device.start();

        Runtime.getRuntime().addShutdownHook(
                new Thread(device::stop)
        );
    }
}
