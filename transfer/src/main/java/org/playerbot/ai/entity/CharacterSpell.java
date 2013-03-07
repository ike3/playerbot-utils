package org.playerbot.ai.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.playerbot.ai.annotation.Key;
import org.playerbot.ai.annotation.Table;

@XmlAccessorType(XmlAccessType.NONE)
@Table("character_spell")
public class CharacterSpell {
    @Key
    private long guid;
    private long spell;
    private long active;
    private long disabled;
    public long getGuid() {
        return guid;
    }
    public void setGuid(long guid) {
        this.guid = guid;
    }
    public long getSpell() {
        return spell;
    }
    public void setSpell(long spell) {
        this.spell = spell;
    }
    public long getActive() {
        return active;
    }
    public void setActive(long active) {
        this.active = active;
    }
    public long getDisabled() {
        return disabled;
    }
    public void setDisabled(long disabled) {
        this.disabled = disabled;
    }


}
