import com.sun.deploy.panel.JavaPanel;

import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class virtualKeyboard {
    public static void main(String[] args) {
        final int[] recording = {1};
        final boolean[] recordKeys = {false};
        String[] pianoKeys = {"C3", "D3", "E3", "F3", "G3", "A3", "B3", "C4", "D4", "E4", "F4", "G4", "A4", "B4"};
        char[] keybinds = new char[]{'q', 'w', 'e', 'r', 't', 'y', 'u', 'h', 'j', 'k', 'l', ';', '\'', '#'};
        JFrame f = new JFrame("Virtual Keyboard");

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());

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


            //button.setMnemonic(keybinds[i]);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String pianoAudioFile = ("C:\\Users\\30185601\\OneDrive - NESCol\\HNC Software Development\\virtualKeyboard\\pianoAudio\\" + pianoKey + ".wav");
                    File pianoKeyAudio = new File(pianoAudioFile);
                    playKey(pianoKeyAudio);
                    long time = System.currentTimeMillis();
                    if(recordKeys[0]) {
                        try {
                            recordKey(pianoKey, time);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                }
            });

            panel.add(button);
        }


        //Playback and Record Button Panel
        content.add(panel, BorderLayout.CENTER);
        JPanel controls = new JPanel();
        JButton record = new JButton("Record");
        record.setBackground(Color.red);
        JButton playback = new JButton("Playback");

        controls.add(record);
        controls.add(playback);
        content.add(controls, BorderLayout.NORTH);

        record.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(recording[0] == 0)
                {
                    recording[0] = 1;
                    record.setText("Record");
                    record.setBackground(Color.red);
                    recordKeys[0] = false;
                }
                else if(recording[0] == 1)
                {
                    recording[0] = 0;
                    record.setText("Recording");
                    record.setBackground(Color.green);
                    recordKeys[0] = true;
                }

            }
        });

        playback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playBack();
            }
        });



        f.add(content);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(f.getExtendedState() | Frame.MAXIMIZED_BOTH);

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
    static void recordKey(String keyPlayed, long timeBetweenKeys) throws IOException {
        ArrayList<Object> keysPlayed = new ArrayList<Object>();
        keysPlayed.add(keyPlayed);
        keysPlayed.add(timeBetweenKeys);
        String filename= "MyFile.txt";
        FileWriter fw = new FileWriter(filename,true);
        fw.write((keysPlayed) + "\n");
        fw.close();
    }
    static void playBack() {


    }

}
