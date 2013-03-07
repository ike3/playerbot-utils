package org.playerbot.ai.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.playerbot.ai.annotation.Key;
import org.playerbot.ai.annotation.Table;

@XmlAccessorType(XmlAccessType.NONE)
@Table("character_reputation")
public class CharacterReputation {
    @Key
    private long guid;
    private long faction;
    private long standing;
    private long flags;

    public long getGuid() {
        return guid;
    }

    public void setGuid(long guid) {
        this.guid = guid;
    }

    public long getFaction() {
        return faction;
    }

    public void setFaction(long faction) {
        this.faction = faction;
    }

    public long getStanding() {
        return standing;
    }

    public void setStanding(long standing) {
        this.standing = standing;
    }

    public long getFlags() {
        return flags;
    }

    public void setFlags(long flags) {
        this.flags = flags;
    }

}
