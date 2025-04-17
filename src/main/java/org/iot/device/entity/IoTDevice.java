package org.iot.device.entity;

import java.sql.Timestamp;

public class IoTDevice {
    public IoTDevice(int id, String name, String type, boolean active, Timestamp time) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.active = active;
        this.time = time;
    }

    private int id;
    private String name;
    private String type;
    private boolean active;
    private Timestamp time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", active=" + active +
                ", time=" + time +
                '}';
    }
}
