package org.iot.device.test.service;

import org.iot.device.entity.IoTDevice;
import org.iot.device.dto.ResponseEntity;
import org.iot.device.repository.IoTDeviceRepository;
import org.iot.device.service.IoTDeviceRepositoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Mockito.*;

public class IoTDeviceRepositoryServiceTest {

    @Mock
    private static IoTDeviceRepository mockIoTDeviceRepository;

    @InjectMocks
    private static IoTDeviceRepositoryService mockIoTDeviceService;

    private static IoTDeviceRepository ioTDeviceRepository;

    private static IoTDeviceRepositoryService ioTDeviceRepositoryService;

    private static Map<Integer, IoTDevice> deviceMap;

    @BeforeAll
    public static void setUpBeforeClass() {
        deviceMap = new ConcurrentHashMap<>();
        deviceMap.put(1, new IoTDevice(1, "Temperature Sensor", "Sensor", true, Timestamp.valueOf("2025-04-01 10:15:30")));
        deviceMap.put(2, new IoTDevice(2, "Smart Light", "Actuator", false, Timestamp.valueOf("2025-03-28 08:45:00")));
        ioTDeviceRepository = new IoTDeviceRepository(deviceMap);
        ioTDeviceRepositoryService = new IoTDeviceRepositoryService(ioTDeviceRepository);
        mockIoTDeviceRepository =mock(IoTDeviceRepository.class);
        mockIoTDeviceService = new IoTDeviceRepositoryService(mockIoTDeviceRepository);
    }

    @Test
    public void testFindAll() {
        ResponseEntity<List<IoTDevice>> list = ioTDeviceRepositoryService.findAll();
        Assertions.assertEquals(deviceMap.size(), list.getData().size());
    }

    @Test
    public void testFindById() {
        when(mockIoTDeviceRepository.findById(1)).thenReturn(deviceMap.get(1));
        IoTDevice iotDevice = ioTDeviceRepositoryService.findById(1).getData();
//        verify(mockIoTDeviceRepository, times(1)).findById(1);
        Assertions.assertEquals(iotDevice, deviceMap.get(1));
    }

    @Test
    public void testUpdate() {
        IoTDevice ioTDevice = new IoTDevice(1, "Temperature New Sensor", "Sensor", true, new Timestamp(System.currentTimeMillis()));
        ioTDeviceRepositoryService.update(1, ioTDevice);
        Assertions.assertEquals("Temperature New Sensor", ioTDeviceRepositoryService.findById(1).getData().getName());
    }

    @Test
    public void testSave() {
        IoTDevice newIoTDevice = new IoTDevice(3, "Humidity Monitor", "Sensor", false, new Timestamp(System.currentTimeMillis()));
        mockIoTDeviceService.save(newIoTDevice);
        verify(mockIoTDeviceRepository, times(1)).save(newIoTDevice);
//        Assertions.assertEquals(mockIoTDeviceService.findById(0).getData(), newIoTDevice);
    }

    @Test
    public void testDelete() {
        IoTDevice newIoTDevice = new IoTDevice(3, "Humidity Monitor", "Sensor", false, new Timestamp(System.currentTimeMillis()));
        mockIoTDeviceService.save(newIoTDevice);
        mockIoTDeviceService.delete(3);
        Assertions.assertNull(mockIoTDeviceRepository.findById(3));
    }






}
