package org.playerbot.ai.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.playerbot.ai.annotation.Column;
import org.playerbot.ai.annotation.Columns;
import org.playerbot.ai.annotation.For;
import org.playerbot.ai.annotation.Key;
import org.playerbot.ai.annotation.Table;

@XmlAccessorType(XmlAccessType.NONE)
@Table("character_inventory")
public class CharacterInventory {
    @Key
    private long guid;
    private long bag;
    private long slot;
    private long item;
    @For("r2")
    @Columns({@Column(value = "item_template", version = "r2")})
    private long item_template;

    public long getGuid() {
        return guid;
    }

    public void setGuid(long guid) {
        this.guid = guid;
    }

    public long getBag() {
        return bag;
    }

    public void setBag(long bag) {
        this.bag = bag;
    }

    public long getSlot() {
        return slot;
    }

    public void setSlot(long slot) {
        this.slot = slot;
    }

    public long getItem() {
        return item;
    }

    public void setItem(long item) {
        this.item = item;
    }

    public long getItem_template() {
        return item_template;
    }

    public void setItem_template(long item_template) {
        this.item_template = item_template;
    }

}
