package org.wangyao.testjenkins02.code.mqtt.adapter;

import org.springframework.stereotype.Component;
import org.wangyao.testjenkins02.code.mqtt.DeviceMessage;
import org.wangyao.testjenkins02.code.mqtt.ProtocolAdapter;

/**
 *
 * @author WangYao
 * date: 2026/11/26/22:28
 * description:
 */
@Component
public class TcpProtocolAdapter implements ProtocolAdapter {
    @Override
    public String getProtocol() {
        return "TCP";
    }

    @Override
    public DeviceMessage decode(byte[] payload) {
        return new DeviceMessage();
    }
}
