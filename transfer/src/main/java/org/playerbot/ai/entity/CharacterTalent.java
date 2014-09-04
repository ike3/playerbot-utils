package org.playerbot.ai.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.playerbot.ai.annotation.For;
import org.playerbot.ai.annotation.Key;
import org.playerbot.ai.annotation.Table;

@XmlAccessorType(XmlAccessType.NONE)
@Table("character_talent")
@For({"r2", "mangostwo"})
public class CharacterTalent {
    @Key
    private long guid;
    @For("r2")
    private long talent_id;
    @For("r2")
    private long current_rank;
    private long spec;
    @For("tc")
    private long spell;

    public long getGuid() {
        return guid;
    }

    public void setGuid(long guid) {
        this.guid = guid;
    }

    public long getTalent_id() {
        return talent_id;
    }

    public void setTalent_id(long talent_id) {
        this.talent_id = talent_id;
    }

    public long getCurrent_rank() {
        return current_rank;
    }

    public void setCurrent_rank(long current_rank) {
        this.current_rank = current_rank;
    }

    public long getSpec() {
        return spec;
    }

    public void setSpec(long spec) {
        this.spec = spec;
    }

    public long getSpell() {
        return spell;
    }

    public void setSpell(long spell) {
        this.spell = spell;
    }
}