package org.playerbot.ai.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.playerbot.ai.annotation.For;
import org.playerbot.ai.annotation.Key;
import org.playerbot.ai.annotation.MaxLength;
import org.playerbot.ai.annotation.SpaceSeparated;
import org.playerbot.ai.annotation.Table;

@XmlAccessorType(XmlAccessType.NONE)
@Table(value = "item_instance")
public class ItemInstance {
    private long guid;

    @Key
    private long owner_guid;

    @For({"r2","mangoszero"})
    @SpaceSeparated({ @MaxLength(version = "r2", value = 256), @MaxLength(version = "mangoszero", value = 48) })
    private String data;

    @For({"r2", "tc"})
    private String text;

    @For("tc")
    private long itemEntry;

    @For("tc")
    private long creatorGuid;

    @For("tc")
    private long giftCreatorGuid;

    @For("tc")
    private long count = 1;

    @For("tc")
    private long duration;

    @For("tc")
    private String charges;

    @For("tc")
    private long flags;

    @For("tc")
    private String enchantments = "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ";

    @For("tc")
    private long randomPropertyId;

    @For("tc")
    private long playedTime;

    @For("tc")
    private long durability;

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

    public long getItemEntry() {
        return itemEntry;
    }

    public void setItemEntry(long itemEntry) {
        this.itemEntry = itemEntry;
    }

    public long getCreatorGuid() {
        return creatorGuid;
    }

    public void setCreatorGuid(long creatorGuid) {
        this.creatorGuid = creatorGuid;
    }

    public long getGiftCreatorGuid() {
        return giftCreatorGuid;
    }

    public void setGiftCreatorGuid(long giftCreatorGuid) {
        this.giftCreatorGuid = giftCreatorGuid;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public long getFlags() {
        return flags;
    }

    public void setFlags(long flags) {
        this.flags = flags;
    }

    public String getEnchantments() {
        return enchantments;
    }

    public void setEnchantments(String enchantments) {
        this.enchantments = enchantments;
    }

    public long getRandomPropertyId() {
        return randomPropertyId;
    }

    public void setRandomPropertyId(long randomPropertyId) {
        this.randomPropertyId = randomPropertyId;
    }

    public long getPlayedTime() {
        return playedTime;
    }

    public void setPlayedTime(long playedTime) {
        this.playedTime = playedTime;
    }

    public long getDurability() {
        return durability;
    }

    public void setDurability(long durability) {
        this.durability = durability;
    }
}
