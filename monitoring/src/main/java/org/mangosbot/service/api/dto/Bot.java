package org.mangosbot.service.api.dto;

public class Bot {
    private long guid;
    private String name;
    private long cls;
    private long race;
    private long gender;

    public long getGuid() {
        return guid;
    }

    public void setGuid(long guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCls() {
        return cls;
    }

    public void setCls(long cls) {
        this.cls = cls;
    }

    public long getRace() {
        return race;
    }

    public void setRace(long race) {
        this.race = race;
    }

    public long getGender() {
        return gender;
    }

    public void setGender(long gender) {
        this.gender = gender;
    }

}
