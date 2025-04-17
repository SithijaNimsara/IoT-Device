package org.iot.device.dto;

public class ResponseEntity<T> {

    private T data;
    private Status status;


    public ResponseEntity() {
    }

    public ResponseEntity(T data, Status status) {
        this.data = data;
        this.status = status;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public Status getStatus() {
        return status;
    }
}
