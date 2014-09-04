package org.playerbot.ai.entity;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.apache.commons.lang.StringUtils;
import org.playerbot.ai.annotation.For;
import org.playerbot.ai.annotation.Key;
import org.playerbot.ai.annotation.MaxLength;
import org.playerbot.ai.annotation.SpaceSeparated;
import org.playerbot.ai.annotation.Table;

@XmlAccessorType(XmlAccessType.NONE)
@Table(value = "item_instance")
public class ItemInstance implements PostProcessor {
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

    @Override
    public void postProcess() {
        if (creatorGuid == 0) {
            creatorGuid = owner_guid;
        }

        // 0       1          2 3    4          5 6   7 8   9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27
        // 5231232 1191182336 3 2361 1065353216 0 712 0 712 0 0  0  0  0  1  0  0  0  0  0  0  0  0  0  0  0  0  0  0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 25 25 0 0
        if (data != null) {
            String[] values = data.split(" ");

            if (values.length > 3)
                itemEntry = Long.parseLong(values[3]); // OBJECT_FIELD_ENTRY = 3

            if (values.length > 10)
                creatorGuid = Long.parseLong(values[10]);

            if (values.length > 12)
                giftCreatorGuid = Long.parseLong(values[12]);

            if (values.length > 14)
                count = Long.parseLong(values[14]); // FIELD_STACK_COUNT = 6 + 8

            if (values.length > 15)
                duration = Long.parseLong(values[15]); // ITEM_FIELD_DURATION

            if (values.length > 16)
                charges = values[16]; // ITEM_FIELD_SPELL_CHARGES

            if (values.length > 21)
                flags = Long.parseLong(values[21]); // ITEM_FIELD_FLAGS

            if (values.length > 57)
                enchantments = StringUtils.join(Arrays.copyOfRange(values, 22, 57), " ");

            if (values.length > 59)
                randomPropertyId = Long.parseLong(values[59]); // ITEM_FIELD_RANDOM_PROPERTIES_ID

            if (values.length > 62)
                playedTime = Long.parseLong(values[62]);// ITEM_FIELD_CREATE_PLAYED_TIME
        }

        if (durability == 0) {
            durability = 100;
        }
    }

}
