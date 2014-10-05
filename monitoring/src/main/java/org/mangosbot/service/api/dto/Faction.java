package org.mangosbot.service.api.dto;

public enum Faction {
    Alliance(1, 3, 4, 7, 11),
    Horde(2, 5, 6, 8, 10);

    private Integer[] races;

    private Faction(Integer... races) {
        this.races = races;
    }

    public Integer[] getRaces() {
        return races;
    }
}
