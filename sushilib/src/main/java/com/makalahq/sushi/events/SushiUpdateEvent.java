package com.makalahq.sushi.events;

/**
 * Created 02/11/2014
 * for SushiDemo
 * by rme
 */
public class SushiUpdateEvent {
    private String objectClass;

    public SushiUpdateEvent(String objectClass) {
        this.objectClass = objectClass;
    }

    public String getObjectClass() {
        return objectClass;
    }
}