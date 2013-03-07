package org.playerbot.ai.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.playerbot.ai.annotation.Column;
import org.playerbot.ai.annotation.For;
import org.playerbot.ai.annotation.Key;
import org.playerbot.ai.annotation.Table;

@XmlAccessorType(XmlAccessType.NONE)
@Table("character_pet")
public class CharacterPet {
    private long id;
    private long entry;
    @Key
    private long owner;
    private long modelid;
    private long CreatedBySpell;
    private long PetType;
    private long level;
    private long exp;
    @For("r2")
    private long Reactstate;
    @For("mangoszero")
    @Column("Reactstate")
    private boolean ReactstateBoolean;
    private String name;
    @For("r2")
    private long renamed;
    @For("mangoszero")
    @Column("renamed")
    private boolean renamedBoolean;

    private long slot;
    private long curhealth;
    private long curmana;
    private long curhappiness;
    private long savetime;

    private String abdata;

    @For("mangoszero")
    private long loyaltypoints;
    @For("mangoszero")
    private long loyalty;
    @For("mangoszero")
    private long trainpoint;

    @For("mangoszero")
    private long resettalents_cost;
    @For("mangoszero")
    private long resettalents_time;
    @For("mangoszero")
    private String teachspelldata;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEntry() {
        return entry;
    }

    public void setEntry(long entry) {
        this.entry = entry;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public long getModelid() {
        return modelid;
    }

    public void setModelid(long modelid) {
        this.modelid = modelid;
    }

    public long getCreatedBySpell() {
        return CreatedBySpell;
    }

    public void setCreatedBySpell(long createdBySpell) {
        CreatedBySpell = createdBySpell;
    }

    public long getPetType() {
        return PetType;
    }

    public void setPetType(long petType) {
        PetType = petType;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public long getReactstate() {
        return Reactstate;
    }

    public void setReactstate(long reactstate) {
        Reactstate = reactstate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRenamed() {
        return renamed;
    }

    public void setRenamed(long renamed) {
        this.renamed = renamed;
    }

    public long getSlot() {
        return slot;
    }

    public void setSlot(long slot) {
        this.slot = slot;
    }

    public long getCurhealth() {
        return curhealth;
    }

    public void setCurhealth(long curhealth) {
        this.curhealth = curhealth;
    }

    public long getCurmana() {
        return curmana;
    }

    public void setCurmana(long curmana) {
        this.curmana = curmana;
    }

    public long getCurhappiness() {
        return curhappiness;
    }

    public void setCurhappiness(long curhappiness) {
        this.curhappiness = curhappiness;
    }

    public long getSavetime() {
        return savetime;
    }

    public void setSavetime(long savetime) {
        this.savetime = savetime;
    }

    public String getAbdata() {
        return abdata;
    }

    public void setAbdata(String abdata) {
        this.abdata = abdata;
    }

    public boolean isReactstateBoolean() {
        return ReactstateBoolean;
    }

    public void setReactstateBoolean(boolean reactstateBoolean) {
        ReactstateBoolean = reactstateBoolean;
    }

    public boolean isRenamedBoolean() {
        return renamedBoolean;
    }

    public void setRenamedBoolean(boolean renamedBoolean) {
        this.renamedBoolean = renamedBoolean;
    }

    public long getLoyaltypoints() {
        return loyaltypoints;
    }

    public void setLoyaltypoints(long loyaltypoints) {
        this.loyaltypoints = loyaltypoints;
    }

    public long getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(long loyalty) {
        this.loyalty = loyalty;
    }

    public long getTrainpoint() {
        return trainpoint;
    }

    public void setTrainpoint(long trainpoint) {
        this.trainpoint = trainpoint;
    }

    public long getResettalents_cost() {
        return resettalents_cost;
    }

    public void setResettalents_cost(long resettalents_cost) {
        this.resettalents_cost = resettalents_cost;
    }

    public long getResettalents_time() {
        return resettalents_time;
    }

    public void setResettalents_time(long resettalents_time) {
        this.resettalents_time = resettalents_time;
    }

    public String getTeachspelldata() {
        return teachspelldata;
    }

    public void setTeachspelldata(String teachspelldata) {
        this.teachspelldata = teachspelldata;
    }

}
