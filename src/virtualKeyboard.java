import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.*;
import javax.swing.border.LineBorder;


public class virtualKeyboard {
    public static void main(String[] args) {
        String[] pianoKeys = {"C3", "D3", "E3", "F3", "G3", "A3", "B3", "C4", "D4", "E4", "F4", "G4", "A4", "B4"};
        String[] keybinds = {"q", "w", "e", "r", "t", "y", "u", "a", "s", "d", "f", "g", "h", "j"};
        int octave = 3;

        JFrame f = new JFrame("Virtual Keyboard");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 14));
        for(String pianoKey : pianoKeys) {





            JButton button = new JButton(pianoKey);
            button.setFocusable(false);


            button.setBorder(new LineBorder(Color.BLACK));
            button.setBackground(Color.WHITE);

            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalAlignment(SwingConstants.BOTTOM);

            button.setFont(new Font("Arial", Font.PLAIN, 40));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String pianoAudioFile = ("C:\\Users\\30185601\\OneDrive - NESCol\\HNC Software Development\\virtualKeyboard\\pianoAudio\\" + pianoKey + ".wav");
                    File pianoKeyAudio = new File(pianoAudioFile);
                    playKey(pianoKeyAudio);
                }
            });
            panel.add(button);
        }


        f.add(panel);
        f.setVisible(true);


    }
    static void playKey(File pianoKeyAudio) {
        try {
            Clip clip = AudioSystem.getClip();

            clip.open(AudioSystem.getAudioInputStream(pianoKeyAudio));
            clip.start();
        } catch (LineUnavailableException ignored) {

        } catch (UnsupportedAudioFileException e) {

        } catch (IOException e) {

        }

    }
}
