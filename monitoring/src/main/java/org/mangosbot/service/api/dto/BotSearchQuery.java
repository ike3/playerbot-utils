package org.mangosbot.service.api.dto;

public class BotSearchQuery {
    private String name;
    private String faction;

    public BotSearchQuery() {
    }

    public BotSearchQuery(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

}
