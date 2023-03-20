import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

/**
 * Simple program to track multiple keyPressed events and only act on the first
 *
 * @author Bruce Collie
 * @version 17 Mar 2023
 */
public class KeyTest {
    public static void main(String[] args) {
        // Create a frame
        JFrame frame = new JFrame("Key Test");
        // Exit program when window closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Give it some dimensions
        frame.setSize(640,480);

        // Add a key listener to the frame

        frame.addKeyListener(new KeyListener() {
            // Create a set to hold which keys are currently being pressed
            HashSet<Character> times = new HashSet<>();
            @Override
            public void keyTyped(KeyEvent e) {
                // Ignore
            }

            @Override
            public void keyPressed(KeyEvent e) {
                Character ch = e.getKeyChar();
                if (!times.contains(ch)) {
                    // Key not being held down

                    // Add to set, so subsequent key presses ignored
                    times.add(ch);

                    // TODO: do what you want for a key press
                    System.out.println("keyPressed: "+ch);
                }
                // else ignore (being held down)
                else {
                    System.out.println("keyPressed: "+ch+" (Ignored)");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Character ch = e.getKeyChar();

                // Remove entry in set
                times.remove(ch);
                System.out.println("keyReleased: "+ch);
            }
        });

        // Open the frame
        frame.setVisible(true);
    }
}
