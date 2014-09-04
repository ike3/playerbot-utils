package org.playerbot.ai.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.playerbot.ai.annotation.For;
import org.playerbot.ai.annotation.Key;
import org.playerbot.ai.annotation.Table;

@XmlAccessorType(XmlAccessType.NONE)
@Table("character_queststatus")
public class CharacterQuest {
    @Key
    private long guid;
    private long quest;
    private long status;
    @For({"r2", "mangosone", "mangoszero"})
    private boolean rewarded;
    private boolean explored;
    private long timer;
    private long mobcount1;
    private long mobcount2;
    private long mobcount3;
    private long mobcount4;
    private long itemcount1;
    private long itemcount2;
    private long itemcount3;
    private long itemcount4;
    @For("r2")
    private long itemcount5;
    @For("r2")
    private long itemcount6;

    public long getGuid() {
        return guid;
    }

    public void setGuid(long guid) {
        this.guid = guid;
    }

    public long getQuest() {
        return quest;
    }

    public void setQuest(long quest) {
        this.quest = quest;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public boolean isRewarded() {
        return rewarded;
    }

    public void setRewarded(boolean rewarded) {
        this.rewarded = rewarded;
    }

    public boolean isExplored() {
        return explored;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public long getMobcount1() {
        return mobcount1;
    }

    public void setMobcount1(long mobcount1) {
        this.mobcount1 = mobcount1;
    }

    public long getMobcount2() {
        return mobcount2;
    }

    public void setMobcount2(long mobcount2) {
        this.mobcount2 = mobcount2;
    }

    public long getMobcount3() {
        return mobcount3;
    }

    public void setMobcount3(long mobcount3) {
        this.mobcount3 = mobcount3;
    }

    public long getMobcount4() {
        return mobcount4;
    }

    public void setMobcount4(long mobcount4) {
        this.mobcount4 = mobcount4;
    }

    public long getItemcount1() {
        return itemcount1;
    }

    public void setItemcount1(long itemcount1) {
        this.itemcount1 = itemcount1;
    }

    public long getItemcount2() {
        return itemcount2;
    }

    public void setItemcount2(long itemcount2) {
        this.itemcount2 = itemcount2;
    }

    public long getItemcount3() {
        return itemcount3;
    }

    public void setItemcount3(long itemcount3) {
        this.itemcount3 = itemcount3;
    }

    public long getItemcount4() {
        return itemcount4;
    }

    public void setItemcount4(long itemcount4) {
        this.itemcount4 = itemcount4;
    }

    public long getItemcount5() {
        return itemcount5;
    }

    public void setItemcount5(long itemcount5) {
        this.itemcount5 = itemcount5;
    }

    public long getItemcount6() {
        return itemcount6;
    }

    public void setItemcount6(long itemcount6) {
        this.itemcount6 = itemcount6;
    }
}
