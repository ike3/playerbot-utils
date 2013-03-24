package org.playerbot.ai.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.playerbot.ai.annotation.For;
import org.playerbot.ai.annotation.Key;
import org.playerbot.ai.annotation.Table;

@XmlAccessorType(XmlAccessType.NONE)
@Table("character_glyphs")
@For({"r2", "mangostwo"})
public class CharacterGlyph {
    @Key
    private long guid;
    private long spec;
    private long slot;
    private long glyph;

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

    public long getSlot() {
        return slot;
    }

    public void setSlot(long slot) {
        this.slot = slot;
    }

    public long getGlyph() {
        return glyph;
    }

    public void setGlyph(long glyph) {
        this.glyph = glyph;
    }

}
