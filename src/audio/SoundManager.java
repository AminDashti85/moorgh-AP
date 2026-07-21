package audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundManager {

    private Clip bgmClip;
    private boolean bgmOn = true;
    private boolean shotOn = true;
    private boolean crashOn = true;
    private boolean gameOverOn = true;

    public void updateSettings(String settings) {
        String[] parts = settings.split(",");
        if (parts.length == 4) {
            bgmOn = parts[0].equals("1");
            shotOn = parts[1].equals("1");
            crashOn = parts[2].equals("1");
            gameOverOn = parts[3].equals("1");
        }
        if (!bgmOn && bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        } else if (bgmOn && bgmClip != null && !bgmClip.isRunning()) {
            bgmClip.start();
        }
    }

    public void playBGM(String filepath) {
        if (!bgmOn) return;
        try {
            if (bgmClip != null && bgmClip.isRunning()) {
                bgmClip.stop();
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filepath));
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioStream);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        }
    }

    public void playShotSound(String filepath) {
        if (!shotOn) return;
        playSoundEffect(filepath);
    }

    public void playCrashSound(String filepath) {
        if (!crashOn) return;
        playSoundEffect(filepath);
    }

    public void playGameOverSound(String filepath) {
        if (!gameOverOn) return;
        playSoundEffect(filepath);
    }

    public void playWinSound(String filepath) {
        if (!gameOverOn) return;
        playSoundEffect(filepath);
    }

    private void playSoundEffect(String filepath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filepath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}