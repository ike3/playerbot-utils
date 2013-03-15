package org.playerbot.ai.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.playerbot.ai.annotation.For;
import org.playerbot.ai.annotation.Key;
import org.playerbot.ai.annotation.MaxLength;
import org.playerbot.ai.annotation.SpaceSeparated;
import org.playerbot.ai.annotation.Table;

@XmlAccessorType(XmlAccessType.NONE)
@Table("item_instance")
public class ItemInstance {
    private long guid;
    @Key
    private long owner_guid;
    @SpaceSeparated({ @MaxLength(version = "r2", value = 256), @MaxLength(version = "mangoszero", value = 48) })
    private String data;

    @For("r2")
    private String text;

    public long getGuid() {
        return guid;
    }

    public void setGuid(long guid) {
        this.guid = guid;
    }

    public long getOwner_guid() {
        return owner_guid;
    }

    public void setOwner_guid(long owner_guid) {
        this.owner_guid = owner_guid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
