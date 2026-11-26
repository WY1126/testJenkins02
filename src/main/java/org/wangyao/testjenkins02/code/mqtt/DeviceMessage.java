package org.wangyao.testjenkins02.code.mqtt;

import lombok.Data;

/**
 *
 * @author WangYao
 * date: 2026/11/26/22:26
 * description:
 */
@Data
public class DeviceMessage {
    String name;
    String protocol;
    String deviceId;
    String status;
}
