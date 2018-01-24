package org.mangosbot.sound;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.urish.openal.ALException;
import org.urish.openal.Buffer;
import org.urish.openal.OpenAL;
import org.urish.openal.Source;

public class MusicPlayer {
    private OpenAL openal;
    private Map<String, Buffer> buffers = new HashMap<String, Buffer>();
    private Map<String, Source> sources = new HashMap<String, Source>();
    private Map<Source, String> currents = new HashMap<Source, String>();
    private Source playing;
    private Source zoneChange;
    private float maxVolume = 0.3f;
    private Properties properties = new Properties();

    public MusicPlayer() throws ALException {
        openal = new OpenAL();

        try {
            properties.load(new FileReader("immersive.properties"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        maxVolume = Float.parseFloat((String) properties.get("maxVolume"));

        for (String type : new String[] {"rest", "combat", "default", "dead"}) {
            Source src = openal.createSource();
            src.setLooping(true);
            sources.put(type, src);
        }
        zoneChange = openal.createSource();
        zoneChange.setLooping(true);
    }

    public void transition(String state, String zone) throws ALException {
        String fileName = (String) properties.get(zone + "_" + state);
        if (fileName == null) fileName = (String) properties.get(state);
        if (fileName == null) throw new RuntimeException("Cannot find file for " + zone + ":" + state);

        Buffer buffer = buffers.get(fileName);
        if (buffer == null) {
            try {
                buffer = openal.createBuffer(new File(fileName));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            buffers.put(fileName, buffer);
        }

        Source source = sources.get(state);
        if (playing == source) {
            source = zoneChange;
            sources.put(state, zoneChange);
            zoneChange = playing;
        }

        if (!fileName.equals(currents.get(source))) {
            source.stop();
            source.setBuffer(buffer);
            currents.put(source, fileName);
        }

        if (playing == null || playing == source) {
            source.setGain(maxVolume);
            source.play();
            playing = source;
        } else {
            fadeTo(playing, source);
            playing = source;
        }
    }

    private void fadeTo(Source s1, Source s2) throws ALException {
        s2.setGain(0.01f);
        s2.play();

        for (float v = 0; v <= maxVolume; v+=0.01f) {
            s1.setGain(maxVolume - v);
            s2.setGain(v);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }
        s1.pause();
    }
}
