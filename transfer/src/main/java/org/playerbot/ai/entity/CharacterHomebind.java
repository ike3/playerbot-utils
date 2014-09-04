package org.playerbot.ai.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.playerbot.ai.annotation.Column;
import org.playerbot.ai.annotation.Columns;
import org.playerbot.ai.annotation.Key;
import org.playerbot.ai.annotation.Table;

@XmlAccessorType(XmlAccessType.NONE)
@Table("character_homebind")
public class CharacterHomebind {
    @Key
    private long guid;
    @Columns({@Column(value = "map", version = "r2"), @Column(value = "mapId", version = "tc")})
    private long map;
    @Columns({@Column(value = "zone", version = "r2"), @Column(value = "zoneId", version = "tc")})
    private long zone;
    @Columns({@Column(value = "position_x", version = "r2"), @Column(value = "posX", version = "tc")})
    private float position_x;
    @Columns({@Column(value = "position_y", version = "r2"), @Column(value = "posY", version = "tc")})
    private float position_y;
    @Columns({@Column(value = "position_z", version = "r2"), @Column(value = "posZ", version = "tc")})
    private float position_z;

    public long getGuid() {
        return guid;
    }

    public void setGuid(long guid) {
        this.guid = guid;
    }

    public long getMap() {
        return map;
    }

    public void setMap(long map) {
        this.map = map;
    }

    public long getZone() {
        return zone;
    }

    public void setZone(long zone) {
        this.zone = zone;
    }

    public float getPosition_x() {
        return position_x;
    }

    public void setPosition_x(float position_x) {
        this.position_x = position_x;
    }

    public float getPosition_y() {
        return position_y;
    }

    public void setPosition_y(float position_y) {
        this.position_y = position_y;
    }

    public float getPosition_z() {
        return position_z;
    }

    public void setPosition_z(float position_z) {
        this.position_z = position_z;
    }

}
