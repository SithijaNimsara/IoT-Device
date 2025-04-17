package org.iot.device.service;

import org.iot.device.entity.IoTDevice;
import org.iot.device.dto.ResponseEntity;
import org.iot.device.dto.Status;
import org.iot.device.repository.IoTDeviceRepository;

import java.sql.Timestamp;
import java.util.List;

public class IoTDeviceRepositoryService {

    private final IoTDeviceRepository ioTDeviceRepository;

    public IoTDeviceRepositoryService(IoTDeviceRepository ioTDeviceRepository) {
        this.ioTDeviceRepository = ioTDeviceRepository;
    }

    public ResponseEntity<List<IoTDevice>> findAll() {
        try {
            List<IoTDevice> list = ioTDeviceRepository.findAll();
            return new ResponseEntity<>(list, new Status(200, "OK"));
        } catch (Exception e) {
            return new ResponseEntity<>(null, new Status(500, e.getMessage()));
        }
    }

    public ResponseEntity<IoTDevice> findById(int id) {
        ResponseEntity<IoTDevice> responseEntity = new ResponseEntity<>();
        try {
            IoTDevice ioTDevice = ioTDeviceRepository.findById(id);
            if(ioTDevice == null) {
                responseEntity.setData(null);
                responseEntity.setStatus(new Status(404, "Data Not Found"));
                return responseEntity;
            }
            responseEntity.setData(ioTDevice);
            responseEntity.setStatus(new Status(200, "OK"));
            return responseEntity;
        } catch (Exception e) {
            return new ResponseEntity<>(null, new Status(500, e.getMessage()));
        }
    }

    public ResponseEntity<String> save(IoTDevice ioTDevice) {
        ResponseEntity<String> responseEntity = new ResponseEntity<>();
        try {
            ioTDevice.setTime(new Timestamp(System.currentTimeMillis()));
            ioTDeviceRepository.save(ioTDevice);
            responseEntity.setData("Successfully save the IOT Device");
            responseEntity.setStatus(new Status(201, "Created"));
            return responseEntity;
        } catch (Exception e) {
            System.out.println("Exception");
            return new ResponseEntity<>(null, new Status(500, e.getMessage()));
        }
    }

    public ResponseEntity<String> update(int id, IoTDevice ioTDevice) {
        ResponseEntity<String> responseEntity = new ResponseEntity<>();
        try {
            IoTDevice findDevice = ioTDeviceRepository.findById(id);
            if(findDevice == null) {
                responseEntity.setData("IOT Device not found");
                responseEntity.setStatus(new Status(404, "Not Found"));
                return responseEntity;
            } else {
                ioTDevice.setTime(new Timestamp(System.currentTimeMillis()));
                ioTDeviceRepository.update(id, ioTDevice);
                responseEntity.setData("Successfully update the IOT Device");
                responseEntity.setStatus(new Status(200, "Success"));
                return responseEntity;
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, new Status(500, e.getMessage()));
        }
    }

    public ResponseEntity<IoTDevice> delete(int id) {
        ResponseEntity<IoTDevice> responseEntity = null;
        try {
            IoTDevice findDevice = ioTDeviceRepository.findById(id);
            if(findDevice == null) {
                responseEntity.setData(null);
                responseEntity.setStatus(new Status(404, "Data Not Found"));
                return responseEntity;
            } else {
                ioTDeviceRepository.deleteById(id);
                responseEntity.setStatus(new Status(204, "No Content"));
                return responseEntity;
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, new Status(500, e.getMessage()));
        }
    }
}
