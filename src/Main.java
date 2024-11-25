import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main { // The Main class is the entry point of the application and contains the main method

    private static int dx = 1; // The speed of the box in the x direction
    private static int dy = 1; // The speed of the box in the y direction

    public static void main(String[] args) { // The main method contains the code that will be executed when the application starts
        SwingUtilities.invokeLater(() -> { // Run the code in the event dispatch thread
            JFrame frame = new JFrame(); // Create a new JFrame (new window)
            frame.setUndecorated(true); // Remove the title bar
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Make the window full screen
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the window is closed
            frame.getContentPane().setBackground(Color.BLACK); // Set the background color to black
            frame.setAlwaysOnTop(true); // Make the window always on top of other windows

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Get the screen size

            int width = screenSize.width; // Get the width of the Window and put it in the variable width
            int height = screenSize.height; // Get the height of the Window and put it in the variable height

            JPanel box = new JPanel(); // Create a new JPanel (a container for other components)
            box.setBounds(0, height - 200, 200, 200); // Set the position and size of the box
            box.setBackground(Color.RED); // Set the background color of the box to red
            frame.setLayout(null); // Set the layout of the frame to null (no layout manager)
            frame.add(box); // Add the box to the frame (draw the box in our JFrame window)

            frame.addKeyListener(new KeyAdapter() { // Add a key listener to the frame (listen for key presses)
                @Override // Override the keyPressed method
                public void keyPressed(KeyEvent event) { // The keyPressed method is called when any key is pressed
                    if (event.getKeyCode() == KeyEvent.VK_ESCAPE) { // If the pressed key is the escape key
                        frame.dispose(); // Close the window
                        System.exit(0); // Exit the application
                    }
                }
            });

            frame.setVisible(true); // Make the frame visible (show the window)

            new Timer(10, event -> changeColor(box)).start(); // Create a new timer that changes the color of the box every 10 milliseconds
            new Thread(() -> { // Create a new thread that moves the box
                while (true) { // Infinite loop
                    moveBox(box); // call the moveBox method (line 67)
                    try {
                        Thread.sleep(5); // Sleep for 5 milliseconds
                    } catch (InterruptedException e) { // Catch any exceptions
                        throw new RuntimeException(e); // Throw a runtime exception if interrupted
                    }
                }
            }).start(); // Start the thread
        });
    }

    static float hue = 0; // Set the hue value of the color

    private static void changeColor(JPanel box) { // Method to change the color of the box (called in line 43)
        hue += 0.001f; // Increase the hue value of the color
        if (hue > 1) { // If the hue value is greater than 1
            hue = 0; // Reset the hue value to 0
        }
        box.setBackground(Color.getHSBColor(hue, 1, 1)); // Set the background color of the box to a color with the specified hue, saturation, and brightness
    }

    private static void moveBox(JPanel box) { // Method to move the box (called in line 46)
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Get the screen size
        int screenWidth = screenSize.width; // Get the width of the screen and put it in the variable screenWidth
        int screenHeight = screenSize.height; // Get the height of the screen and put it in the variable screenHeight

        if (box.getX() + dx < 0 || box.getX() + box.getWidth() + dx > screenWidth) { // If the box is at the left or right edge of the screen
            dx = -dx; // Reverse the direction of the box in the x direction
        }
        if (box.getY() + dy < 0 || box.getY() + box.getHeight() + dy > screenHeight) { // If the box is at the top or bottom edge of the screen
            dy = -dy; // Reverse the direction of the box in the y direction
        }

        box.setLocation(box.getX() + dx, box.getY() + dy); // Move the box by dx pixels in the x direction and dy pixels in the y direction
    }
}