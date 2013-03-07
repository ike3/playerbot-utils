package org.playerbot.ai.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class DatabaseConfiguration {
    @XmlElement
    private String name;
    @XmlElement
    private String version;
    @XmlElement
    private String driver;
    @XmlElement
    private String url;
    @XmlElement
    private String username;
    @XmlElement
    private String password;
    @XmlElement
    private String worldDbName;
    @XmlElement
    private long maxLevel;
    @XmlElement
    private long maxSkill;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWorldDbName() {
        return worldDbName;
    }

    public void setWorldDbName(String worldDbName) {
        this.worldDbName = worldDbName;
    }

    public long getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(long maxLevel) {
        this.maxLevel = maxLevel;
    }

    public long getMaxSkill() {
        return maxSkill;
    }

    public void setMaxSkill(long maxSkill) {
        this.maxSkill = maxSkill;
    }

}
