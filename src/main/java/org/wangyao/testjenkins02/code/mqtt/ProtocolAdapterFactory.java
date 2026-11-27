package org.wangyao.testjenkins02.code.mqtt;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author WangYao
 * date: 2026/11/26/22:29
 * description:
 */
@Component
public class ProtocolAdapterFactory {

    private final Map<String, ProtocolAdapter> adapterMap = new HashMap<>();

    public ProtocolAdapterFactory(List<ProtocolAdapter> adapters) {

        for (ProtocolAdapter adapter : adapters) {
            adapterMap.put(adapter.getProtocol(), adapter);
        }
    }

    public ProtocolAdapter getAdapter(String protocol) {

        ProtocolAdapter adapter = adapterMap.get(protocol);

        if (adapter == null) {
            throw new IllegalArgumentException("不支持的协议: " + protocol);
        }

        return adapter;
    }
}