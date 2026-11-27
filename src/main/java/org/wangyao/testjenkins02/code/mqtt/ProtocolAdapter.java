package org.wangyao.testjenkins02.code.mqtt;

/**
 *
 * @author WangYao
 * date: 2026/11/26/22:25
 * description:
 */
public interface ProtocolAdapter {

    String getProtocol();

    DeviceMessage decode(byte[] payload);
}