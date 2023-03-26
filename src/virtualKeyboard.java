import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.border.LineBorder;

public class virtualKeyboard {
    static boolean RECORDING = false;
    static String FILENAME = null;
    static String filePath = System.getProperty("user.dir");
    public static void main(String[] args) {

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
                    String pianoAudioFile = (filePath + "\\pianoAudio\\" + pianoKey + ".wav");
                    File pianoKeyAudio = new File(pianoAudioFile);
                    playKey(pianoKeyAudio);

                    if(RECORDING) {
                        try {
                            long time = System.currentTimeMillis();
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
                if(RECORDING)
                {
                    // Stop recording
                    RECORDING = false;
                    record.setText("Record");
                    record.setBackground(Color.red);
                }
                else
                {
                    // Start recording
                    RECORDING = true;
                    FILENAME = null;
                    record.setText("Recording");
                    record.setBackground(Color.green);
                    if (FILENAME == null) {
                        FILENAME = (String)JOptionPane.showInputDialog("Please give this file a name");
                    }
                }

            }
        });

        playback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int result = fileChooser.showOpenDialog(f);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        playBack(String.valueOf(selectedFile));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
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

        boolean append = true;
        if (FILENAME == null) {
            FILENAME = (String)JOptionPane.showInputDialog("Please give this file a name");
            append = false;
        }

        FileWriter fw = new FileWriter(FILENAME,append);
        fw.write((keysPlayed) + "\n");
        fw.close();
    }
    static void playBack(String file) throws IOException, InterruptedException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        long previousTime = 0;
        while (line != null) {
            String[] arraySplit = line.split(",");
            String pianoKey = (arraySplit[0].substring(1));
            long timeBetweenKeys = Long.parseLong(arraySplit[1].substring(2, 14));
            File pianoKeyAudio = new File((filePath + "\\pianoAudio\\" + pianoKey + ".wav"));
            if (previousTime != 0) {
                Thread.sleep(timeBetweenKeys - previousTime);
            }
            previousTime = timeBetweenKeys;
            playKey(pianoKeyAudio);
            line = reader.readLine();
        }
    }
}
