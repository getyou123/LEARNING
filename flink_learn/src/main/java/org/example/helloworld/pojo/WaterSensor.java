package org.example.helloworld.pojo;


import java.io.Serializable;

public class WaterSensor implements Serializable {
    public String id;
    public Long ts;
    public Integer vc;

    public WaterSensor() {
    }

    public Integer getVc() {
        return vc;
    }

    public String getId() {
        return id;
    }

    public Long getTs() {
        return ts;
    }


    public WaterSensor(String id, Long ts, Integer vc) {
        this.id = id;
        this.ts = ts;
        this.vc = vc;
    }

    @Override
    public String toString() {
        return "WaterSensor{" +
                "id='" + id + '\'' +
                ", ts=" + ts +
                ", vc=" + vc +
                '}';
    }
}
