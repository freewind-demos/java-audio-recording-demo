package demo;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;

public class Hello {
    private static AudioFormat format = new AudioFormat(16000, 8, 2, true, true);
    private static File audioFile = new File("./data/audio.wav");
    private static TargetDataLine line;

    static {
        if (audioFile.exists()) audioFile.delete();
    }

    public static void main(String[] args) throws Exception {
        JOptionPane.showMessageDialog(null, "Click to start, will record for 5 seconds.");
        stopLater(5 * 1000);

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        line = (TargetDataLine) AudioSystem.getLine(info);
        line.open(format);
        line.start();
        AudioInputStream ais = new AudioInputStream(line);
        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, audioFile);
    }

    private static void stopLater(int millis) {
        new Thread(() -> {
            try {
                Thread.sleep(millis);
                line.stop();
                line.close();
                JOptionPane.showMessageDialog(null, "Finished.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
