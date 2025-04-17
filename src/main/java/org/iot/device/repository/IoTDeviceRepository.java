package org.iot.device.repository;
import org.iot.device.entity.IoTDevice;

import java.util.*;

public class IoTDeviceRepository {

    private Map<Integer, IoTDevice> deviceMap;

    public IoTDeviceRepository(Map<Integer, IoTDevice> deviceMapNew) {
        this.deviceMap = deviceMapNew;
    }

    public List<IoTDevice> findAll() {
        return new ArrayList<>(deviceMap.values());
    }

    public IoTDevice findById(int id) {
        if (deviceMap.isEmpty()) {
            return null;
        }
        return deviceMap.get(id);
    }

    public IoTDevice save(IoTDevice device) {
        deviceMap.put(device.getId(), device);
        return device;
    }

    public IoTDevice update(int id, IoTDevice device) {
        if (deviceMap.isEmpty()) {
            return null;
        } else if(deviceMap.containsKey(id)) {
            deviceMap.put(id, device);
            return device;
        }
        return device;
    }

    public void deleteById(int id) {
        deviceMap.remove(id);
    }

}
