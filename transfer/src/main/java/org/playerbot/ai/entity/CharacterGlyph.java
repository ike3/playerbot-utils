package org.playerbot.ai.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.playerbot.ai.annotation.For;
import org.playerbot.ai.annotation.Key;
import org.playerbot.ai.annotation.Table;

@XmlAccessorType(XmlAccessType.NONE)
@Table("character_glyphs")
@For({"r2", "mangostwo"})
public class CharacterGlyph implements PostProcessor {
    @Key
    private long guid;
    @For("r2")
    private long spec;
    @For("r2")
    private long slot;
    @For("r2")
    private long glyph;

    @For("tc")
    private long glyph1;
    @For("tc")
    private long glyph2;
    @For("tc")
    private long glyph3;
    @For("tc")
    private long glyph4;
    @For("tc")
    private long glyph5;
    @For("tc")
    private long glyph6;

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

    public long getGlyph1() {
        return glyph1;
    }

    public void setGlyph1(long glyph1) {
        this.glyph1 = glyph1;
    }

    public long getGlyph2() {
        return glyph2;
    }

    public void setGlyph2(long glyph2) {
        this.glyph2 = glyph2;
    }

    public long getGlyph3() {
        return glyph3;
    }

    public void setGlyph3(long glyph3) {
        this.glyph3 = glyph3;
    }

    public long getGlyph4() {
        return glyph4;
    }

    public void setGlyph4(long glyph4) {
        this.glyph4 = glyph4;
    }

    public long getGlyph5() {
        return glyph5;
    }

    public void setGlyph5(long glyph5) {
        this.glyph5 = glyph5;
    }

    public long getGlyph6() {
        return glyph6;
    }

    public void setGlyph6(long glyph6) {
        this.glyph6 = glyph6;
    }

    @Override
    public void postProcess() {
        if (glyph != 0) {
            glyph1 = glyph;
        }
    }

}
