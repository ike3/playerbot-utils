package org.playerbot.ai.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.playerbot.ai.annotation.For;
import org.playerbot.ai.annotation.Key;
import org.playerbot.ai.annotation.Table;

@XmlAccessorType(XmlAccessType.NONE)
@Table("character_achievement_progress")
@For({ "r2", "mangostwo" })
public class CharacterAchievementProgress {
    @Key
    private long guid;
    private long criteria;
    private long counter;
    private long date;

    public long getGuid() {
        return guid;
    }

    public void setGuid(long guid) {
        this.guid = guid;
    }

    public long getCriteria() {
        return criteria;
    }

    public void setCriteria(long criteria) {
        this.criteria = criteria;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

}
