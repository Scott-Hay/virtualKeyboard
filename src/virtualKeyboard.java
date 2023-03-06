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
        char[] keybinds = new char[]{'q', 'w', 'e', 'r', 't', 'y', 'u', 'a', 's', 'd', 'f', 'g', 'h', 'j'};
        JFrame f = new JFrame("Virtual Keyboard");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 14));

        for(int i = 0; i < pianoKeys.length; i++) {
            String pianoKey = pianoKeys[i];

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
                    String pianoAudioFile = ("C:\\Users\\30187240\\OneDrive - NESCol\\homework\\virtualKeyboard\\pianoAudio\\" + pianoKey + ".wav");
                    File pianoKeyAudio = new File(pianoAudioFile);
                    playKey(pianoKeyAudio);
                }
            });
            panel.add(button);

            f.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    char keyPressed = e.getKeyChar();
                    for (int i = 0; i < keybinds.length; i++) {
                        if (keyPressed == keybinds[i]) {
                            JButton button = (JButton) panel.getComponent(i);
                            button.doClick();
                            break;
                        }
                    }
                }
            });
        }

        f.add(panel);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(f.getExtendedState() | Frame.MAXIMIZED_BOTH);
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
