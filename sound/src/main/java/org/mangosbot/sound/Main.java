package org.mangosbot.sound;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.urish.openal.ALException;
import org.urish.openal.Buffer;
import org.urish.openal.OpenAL;
import org.urish.openal.Source;

public class Main {
    private static float maxVolume = 0.3f;

    public static void main(String[] args) throws InterruptedException, IOException, ALException, UnsupportedAudioFileException {
        File f1 = new File("zone.wav");
        File f2 = new File("battle.wav");

        OpenAL openal = new OpenAL();
        Buffer b1 = openal.createBuffer(f1);
        Buffer b2 = openal.createBuffer(f2);

        Source s1 = openal.createSource();
        s1.setLooping(true);
        s1.setGain(0.01f);
        Source s2 = openal.createSource();
        s2.setLooping(true);
        s2.setGain(0.01f);

        s1.setBuffer(b1);
        s2.setBuffer(b2);

        while (true) {
            int read = System.in.read();
            if (read == '2') {
                System.out.println("Playing s2");
                fadeTo(s1, s2);
            }
            else if (read == '1') {
                System.out.println("Playing s1");
                fadeTo(s2, s1);
            }
        }
    }

    private static void fadeTo(Source s1, Source s2) throws InterruptedException, ALException {
        s2.setGain(0.01f);
        s2.play();

        for (float v = 0; v <= maxVolume; v+=0.01f) {
            s1.setGain(maxVolume - v);
            s2.setGain(v);
            Thread.sleep(50);
        }
        s1.pause();
    }
}
