package org.playerbot.ai.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.playerbot.ai.annotation.Key;
import org.playerbot.ai.annotation.Table;

@XmlAccessorType(XmlAccessType.NONE)
@Table("character_action")
public class CharacterAction {
    @Key
    private long guid;
    private long spec;
    private long button;
    private long action;
    private long type;

    public long getGuid() {
        return guid;
    }

    public void setGuid(long guid) {
        this.guid = guid;
    }

    public long getSpec() {
        return spec;
    }

    public void setSpec(long spec) {
        this.spec = spec;
    }

    public long getButton() {
        return button;
    }

    public void setButton(long button) {
        this.button = button;
    }

    public long getAction() {
        return action;
    }

    public void setAction(long action) {
        this.action = action;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

}
