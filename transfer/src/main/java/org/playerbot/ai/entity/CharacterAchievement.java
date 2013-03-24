package org.playerbot.ai.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.playerbot.ai.annotation.For;
import org.playerbot.ai.annotation.Key;
import org.playerbot.ai.annotation.Table;

@XmlAccessorType(XmlAccessType.NONE)
@Table("character_achievement")
@For({"r2", "mangostwo"})
public class CharacterAchievement {
    @Key
    private long guid;
    private long achievement;
    private long date;

    public long getGuid() {
        return guid;
    }

    public void setGuid(long guid) {
        this.guid = guid;
    }

    public long getAchievement() {
        return achievement;
    }

    public void setAchievement(long achievement) {
        this.achievement = achievement;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

}
