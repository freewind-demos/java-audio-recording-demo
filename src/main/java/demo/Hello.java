package demo;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;

public class Hello {

    public static void main(String[] args) throws Exception {
        JOptionPane.showMessageDialog(null, "Click to start, will record for 5 seconds.");
        Recorder recorder = new Recorder(new File("./data/audio.wav"));
        stopLater(recorder, 5 * 1000);
        recorder.start();
    }

    private static void stopLater(Recorder recorder, int millis) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(millis);
                recorder.stop();
                JOptionPane.showMessageDialog(null, "Finished.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

}

class Recorder {

    private static AudioFormat format = new AudioFormat(16000, 8, 2, true, true);
    private TargetDataLine line;
    private File audioFile;

    public Recorder(File audioFile) throws LineUnavailableException {
        this.audioFile = audioFile;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        this.line = (TargetDataLine) AudioSystem.getLine(info);
    }

    public void start() throws Exception {
        line.open(format);
        line.start();
        AudioInputStream ais = new AudioInputStream(line);
        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, audioFile);
    }

    public void stop() {
        line.stop();
        line.close();
    }
}
