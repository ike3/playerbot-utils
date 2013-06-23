package org.playerbot.ai.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.playerbot.ai.annotation.Column;
import org.playerbot.ai.annotation.For;
import org.playerbot.ai.annotation.Key;
import org.playerbot.ai.annotation.Table;
import org.playerbot.ai.annotation.Transient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table("characters")
public class Character {
    private Long guid;
    private Long account;
    @Key
    private String name;
    private Long race;
    @Column("class")
    private Long class1;
    private Long gender;
    private Long level;
    private Long xp;
    private Long money;
    private Long playerBytes;
    private Long playerBytes2;
    private Long playerFlags;
    private float position_x;
    private float position_y;
    private float position_z;
    private Long map;
    @For("r2")
    private Long dungeon_difficulty;
    private float orientation;
    private String taximask;
    private Long online;
    private Long cinematic;
    private Long totaltime;
    private Long leveltime;
    private Long logout_time;
    private Long is_logout_resting;
    private float rest_bonus;
    private Long resettalents_cost;
    private Long resettalents_time;
    private float trans_x;
    private float trans_y;
    private float trans_z;
    private float trans_o;
    private Long transguid;
    private Long extra_flags;
    private Long stable_slots;
    private Long at_login;
    private Long zone;
    private Long death_expire_time;
    private String taxi_path;
    @For("r2")
    private Long arenaPoints;
    @For("r2")
    private Long totalHonorPoints;
    @For("r2")
    private Long todayHonorPoints;
    @For("r2")
    private Long yesterdayHonorPoints;
    @For("r2")
    private Long totalKills;
    @For("r2")
    private Long todayKills;
    @For("r2")
    private Long yesterdayKills;
    @For("r2")
    private Long chosenTitle;
    @For("r2")
    private Long knownCurrencies;
    private Long watchedFaction;
    private Long drunk;
    private Long health;
    private Long power1;
    private Long power2;
    private Long power3;
    private Long power4;
    private Long power5;
    @For("r2")
    private Long power6;
    @For("r2")
    private Long power7;
    @For("r2")
    private Long specCount;
    @For("r2")
    private Long activeSpec;
    private String exploredZones;
    private String equipmentCache;
    private Long ammoId;
    @For("r2")
    private String knownTitles;
    private Long actionBars;
    @For("r2")
    private Long grantableLevels;
    private Long deleteInfos_Account;
    private String deleteInfos_Name;
    private Long deleteDate;

    @Transient
    private CharacterHomebind homebind;

    @Transient
    private List<CharacterPet> pets = new ArrayList<CharacterPet>();

    @Transient
    private List<ItemInstance> itemInstances = new ArrayList<ItemInstance>();

    @Transient
    private List<CharacterInventory> inventory = new ArrayList<CharacterInventory>();

    @Transient
    private List<CharacterReputation> reputation = new ArrayList<CharacterReputation>();

    @Transient
    private List<CharacterSpell> spells = new ArrayList<CharacterSpell>();

    @Transient
    private List<CharacterSkill> skills = new ArrayList<CharacterSkill>();

    @Transient
    private List<CharacterQuest> quests = new ArrayList<CharacterQuest>();

    @Transient
    private List<CharacterTalent> talents = new ArrayList<CharacterTalent>();

    @Transient
    private List<CharacterAchievement> achievements = new ArrayList<CharacterAchievement>();

    @Transient
    private List<CharacterAchievementProgress> achievementProgress = new ArrayList<CharacterAchievementProgress>();

    @Transient
    private List<CharacterGlyph> glyphs = new ArrayList<CharacterGlyph>();

    @Transient
    private List<CharacterAction> actions = new ArrayList<CharacterAction>();

    public Long getGuid() {
        return guid;
    }

    public void setGuid(Long guid) {
        this.guid = guid;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRace() {
        return race;
    }

    public void setRace(Long race) {
        this.race = race;
    }

    public Long getClass1() {
        return class1;
    }

    public void setClass1(Long class1) {
        this.class1 = class1;
    }

    public Long getGender() {
        return gender;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getXp() {
        return xp;
    }

    public void setXp(Long xp) {
        this.xp = xp;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getPlayerBytes() {
        return playerBytes;
    }

    public void setPlayerBytes(Long playerBytes) {
        this.playerBytes = playerBytes;
    }

    public Long getPlayerBytes2() {
        return playerBytes2;
    }

    public void setPlayerBytes2(Long playerBytes2) {
        this.playerBytes2 = playerBytes2;
    }

    public Long getPlayerFlags() {
        return playerFlags;
    }

    public void setPlayerFlags(Long playerFlags) {
        this.playerFlags = playerFlags;
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

    public Long getMap() {
        return map;
    }

    public void setMap(Long map) {
        this.map = map;
    }

    public Long getDungeon_difficulty() {
        return dungeon_difficulty;
    }

    public void setDungeon_difficulty(Long dungeon_difficulty) {
        this.dungeon_difficulty = dungeon_difficulty;
    }

    public float getOrientation() {
        return orientation;
    }

    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }

    public String getTaximask() {
        return taximask;
    }

    public void setTaximask(String taximask) {
        this.taximask = taximask;
    }

    public Long getOnline() {
        return online;
    }

    public void setOnline(Long online) {
        this.online = online;
    }

    public Long getCinematic() {
        return cinematic;
    }

    public void setCinematic(Long cinematic) {
        this.cinematic = cinematic;
    }

    public Long getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(Long totaltime) {
        this.totaltime = totaltime;
    }

    public Long getLeveltime() {
        return leveltime;
    }

    public void setLeveltime(Long leveltime) {
        this.leveltime = leveltime;
    }

    public Long getLogout_time() {
        return logout_time;
    }

    public void setLogout_time(Long logout_time) {
        this.logout_time = logout_time;
    }

    public Long getIs_logout_resting() {
        return is_logout_resting;
    }

    public void setIs_logout_resting(Long is_logout_resting) {
        this.is_logout_resting = is_logout_resting;
    }

    public float getRest_bonus() {
        return rest_bonus;
    }

    public void setRest_bonus(float rest_bonus) {
        this.rest_bonus = rest_bonus;
    }

    public Long getResettalents_cost() {
        return resettalents_cost;
    }

    public void setResettalents_cost(Long resettalents_cost) {
        this.resettalents_cost = resettalents_cost;
    }

    public Long getResettalents_time() {
        return resettalents_time;
    }

    public void setResettalents_time(Long resettalents_time) {
        this.resettalents_time = resettalents_time;
    }

    public float getTrans_x() {
        return trans_x;
    }

    public void setTrans_x(float trans_x) {
        this.trans_x = trans_x;
    }

    public float getTrans_y() {
        return trans_y;
    }

    public void setTrans_y(float trans_y) {
        this.trans_y = trans_y;
    }

    public float getTrans_z() {
        return trans_z;
    }

    public void setTrans_z(float trans_z) {
        this.trans_z = trans_z;
    }

    public float getTrans_o() {
        return trans_o;
    }

    public void setTrans_o(float trans_o) {
        this.trans_o = trans_o;
    }

    public Long getTransguid() {
        return transguid;
    }

    public void setTransguid(Long transguid) {
        this.transguid = transguid;
    }

    public Long getExtra_flags() {
        return extra_flags;
    }

    public void setExtra_flags(Long extra_flags) {
        this.extra_flags = extra_flags;
    }

    public Long getStable_slots() {
        return stable_slots;
    }

    public void setStable_slots(Long stable_slots) {
        this.stable_slots = stable_slots;
    }

    public Long getAt_login() {
        return at_login;
    }

    public void setAt_login(Long at_login) {
        this.at_login = at_login;
    }

    public Long getZone() {
        return zone;
    }

    public void setZone(Long zone) {
        this.zone = zone;
    }

    public Long getDeath_expire_time() {
        return death_expire_time;
    }

    public void setDeath_expire_time(Long death_expire_time) {
        this.death_expire_time = death_expire_time;
    }

    public String getTaxi_path() {
        return taxi_path;
    }

    public void setTaxi_path(String taxi_path) {
        this.taxi_path = taxi_path;
    }

    public Long getArenaPoints() {
        return arenaPoints;
    }

    public void setArenaPoints(Long arenaPoints) {
        this.arenaPoints = arenaPoints;
    }

    public Long getTotalHonorPoints() {
        return totalHonorPoints;
    }

    public void setTotalHonorPoints(Long totalHonorPoints) {
        this.totalHonorPoints = totalHonorPoints;
    }

    public Long getTodayHonorPoints() {
        return todayHonorPoints;
    }

    public void setTodayHonorPoints(Long todayHonorPoints) {
        this.todayHonorPoints = todayHonorPoints;
    }

    public Long getYesterdayHonorPoints() {
        return yesterdayHonorPoints;
    }

    public void setYesterdayHonorPoints(Long yesterdayHonorPoints) {
        this.yesterdayHonorPoints = yesterdayHonorPoints;
    }

    public Long getTotalKills() {
        return totalKills;
    }

    public void setTotalKills(Long totalKills) {
        this.totalKills = totalKills;
    }

    public Long getTodayKills() {
        return todayKills;
    }

    public void setTodayKills(Long todayKills) {
        this.todayKills = todayKills;
    }

    public Long getYesterdayKills() {
        return yesterdayKills;
    }

    public void setYesterdayKills(Long yesterdayKills) {
        this.yesterdayKills = yesterdayKills;
    }

    public Long getChosenTitle() {
        return chosenTitle;
    }

    public void setChosenTitle(Long chosenTitle) {
        this.chosenTitle = chosenTitle;
    }

    public Long getKnownCurrencies() {
        return knownCurrencies;
    }

    public void setKnownCurrencies(Long knownCurrencies) {
        this.knownCurrencies = knownCurrencies;
    }

    public Long getWatchedFaction() {
        return watchedFaction;
    }

    public void setWatchedFaction(Long watchedFaction) {
        this.watchedFaction = watchedFaction;
    }

    public Long getDrunk() {
        return drunk;
    }

    public void setDrunk(Long drunk) {
        this.drunk = drunk;
    }

    public Long getHealth() {
        return health;
    }

    public void setHealth(Long health) {
        this.health = health;
    }

    public Long getPower1() {
        return power1;
    }

    public void setPower1(Long power1) {
        this.power1 = power1;
    }

    public Long getPower2() {
        return power2;
    }

    public void setPower2(Long power2) {
        this.power2 = power2;
    }

    public Long getPower3() {
        return power3;
    }

    public void setPower3(Long power3) {
        this.power3 = power3;
    }

    public Long getPower4() {
        return power4;
    }

    public void setPower4(Long power4) {
        this.power4 = power4;
    }

    public Long getPower5() {
        return power5;
    }

    public void setPower5(Long power5) {
        this.power5 = power5;
    }

    public Long getPower6() {
        return power6;
    }

    public void setPower6(Long power6) {
        this.power6 = power6;
    }

    public Long getPower7() {
        return power7;
    }

    public void setPower7(Long power7) {
        this.power7 = power7;
    }

    public Long getSpecCount() {
        return specCount;
    }

    public void setSpecCount(Long specCount) {
        this.specCount = specCount;
    }

    public Long getActiveSpec() {
        return activeSpec;
    }

    public void setActiveSpec(Long activeSpec) {
        this.activeSpec = activeSpec;
    }

    public String getExploredZones() {
        return exploredZones;
    }

    public void setExploredZones(String exploredZones) {
        this.exploredZones = exploredZones;
    }

    public String getEquipmentCache() {
        return equipmentCache;
    }

    public void setEquipmentCache(String equipmentCache) {
        this.equipmentCache = equipmentCache;
    }

    public Long getAmmoId() {
        return ammoId;
    }

    public void setAmmoId(Long ammoId) {
        this.ammoId = ammoId;
    }

    public String getKnownTitles() {
        return knownTitles;
    }

    public void setKnownTitles(String knownTitles) {
        this.knownTitles = knownTitles;
    }

    public Long getActionBars() {
        return actionBars;
    }

    public void setActionBars(Long actionBars) {
        this.actionBars = actionBars;
    }

    public Long getGrantableLevels() {
        return grantableLevels;
    }

    public void setGrantableLevels(Long grantableLevels) {
        this.grantableLevels = grantableLevels;
    }

    public Long getDeleteInfos_Account() {
        return deleteInfos_Account;
    }

    public void setDeleteInfos_Account(Long deleteInfos_Account) {
        this.deleteInfos_Account = deleteInfos_Account;
    }

    public String getDeleteInfos_Name() {
        return deleteInfos_Name;
    }

    public void setDeleteInfos_Name(String deleteInfos_Name) {
        this.deleteInfos_Name = deleteInfos_Name;
    }

    public Long getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Long deleteDate) {
        this.deleteDate = deleteDate;
    }

    public CharacterHomebind getHomebind() {
        return homebind;
    }

    public void setHomebind(CharacterHomebind homebind) {
        this.homebind = homebind;
    }

    public List<CharacterPet> getPets() {
        return pets;
    }

    public void setPets(List<CharacterPet> pets) {
        this.pets = pets;
    }

    public List<ItemInstance> getItemInstances() {
        return itemInstances;
    }

    public void setItemInstances(List<ItemInstance> itemInstances) {
        this.itemInstances = itemInstances;
    }

    public List<CharacterInventory> getInventory() {
        return inventory;
    }

    public void setInventory(List<CharacterInventory> inventory) {
        this.inventory = inventory;
    }

    public List<CharacterReputation> getReputation() {
        return reputation;
    }

    public void setReputation(List<CharacterReputation> reputation) {
        this.reputation = reputation;
    }

    public List<CharacterSpell> getSpells() {
        return spells;
    }

    public void setSpells(List<CharacterSpell> spells) {
        this.spells = spells;
    }

    public List<CharacterSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<CharacterSkill> skills) {
        this.skills = skills;
    }

    public List<CharacterQuest> getQuests() {
        return quests;
    }

    public void setQuests(List<CharacterQuest> quests) {
        this.quests = quests;
    }

    public List<CharacterTalent> getTalents() {
        return talents;
    }

    public void setTalents(List<CharacterTalent> talents) {
        this.talents = talents;
    }

    public List<CharacterAchievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<CharacterAchievement> achievements) {
        this.achievements = achievements;
    }

    public List<CharacterAchievementProgress> getAchievementProgress() {
        return achievementProgress;
    }

    public void setAchievementProgress(List<CharacterAchievementProgress> achievementProgress) {
        this.achievementProgress = achievementProgress;
    }

    public List<CharacterGlyph> getGlyphs() {
        return glyphs;
    }

    public void setGlyphs(List<CharacterGlyph> glyphs) {
        this.glyphs = glyphs;
    }

    public List<CharacterAction> getActions() {
        return actions;
    }

    public void setActions(List<CharacterAction> actions) {
        this.actions = actions;
    }

}
