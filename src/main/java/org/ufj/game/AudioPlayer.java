package org.ufj.game;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Logger;

public class AudioPlayer {
    private Clip clip;
    private final URL[] soundURL = new URL[30];

    private void setUpFiles() {
        soundURL[0] = getClass().getResource("/sound/effects/crash1.wav");
        soundURL[1] = getClass().getResource("/sound/effects/crash2.wav");
        soundURL[2] = getClass().getResource("/sound/theme/barryLeitchTopGear.wav");
        soundURL[3] = getClass().getResource("/sound/theme/topGear.wav");
    }

    public AudioPlayer(boolean useMixer) {
        setUpFiles();
        if (!useMixer) {
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                Logger.getLogger(AudioPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
            }
            return;
        }
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        System.out.println("Available Mixers:");
        for (int i = 0; i < mixers.length; i++) {
            System.out.println(i + ": " + mixers[i].getName() + " - " + mixers[i].getDescription());
        }
        // find the default mixer
        try {
            for (Mixer.Info info : mixers) {
                if (info.getName().contains("default")) {
                    Mixer mixer = AudioSystem.getMixer(info);
                    System.out.println("Using Mixer: " + info.getName());
                    clip = (Clip) mixer.getLine(new DataLine.Info(Clip.class, null));
                    break;
                }
            }
        } catch (LineUnavailableException e) {
            Logger.getLogger(AudioPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }
    }

    @SuppressWarnings("unused")
    public AudioPlayer(int mixerIndex) {
        setUpFiles();

        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        System.out.println("Available Mixers:");
        for (int i = 0; i < mixers.length; i++) {
            System.out.println(i + ": " + mixers[i].getName() + " - " + mixers[i].getDescription());
        }
        if (mixerIndex >= 0 && mixerIndex < mixers.length) {
            try {
                Mixer mixer = AudioSystem.getMixer(mixers[mixerIndex]);
                System.out.println("Using Mixer: " + mixers[mixerIndex].getName());
                clip = (Clip) mixer.getLine(new DataLine.Info(Clip.class, null));
            } catch (LineUnavailableException e) {
                Logger.getLogger(AudioPlayer.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
            }
        }
    }

    public void setFile(int i){
        try {
            clip.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(soundURL[i])));
        } catch (Exception ignored) {
        }
    }

    public void play() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        } else {
            System.err.println("Clip not set.");
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.err.println("Clip not set.");
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
