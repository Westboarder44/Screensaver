import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {

    private static int dx = 1;
    private static int dy = 1;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setUndecorated(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setBackground(Color.BLACK);
            frame.setAlwaysOnTop(true);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            int width = screenSize.width;
            int height = screenSize.height;

            JPanel box = new JPanel();
            box.setBounds(0, height - 200, 200, 200);
            box.setBackground(Color.RED);
            frame.setLayout(null);
            frame.add(box);

            frame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent event) {
                    if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        frame.dispose();
                        System.exit(0);
                    }
                }
            });

            frame.setVisible(true);

            new Timer(10, event -> changeColor(box)).start();
            new Thread(() -> {
                while (true) {
                    moveBox(box);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        });
    }

    static float hue = 0;

    private static void changeColor(JPanel box) {
        hue += 0.001f;
        if (hue > 1) {
            hue = 0;
        }
        box.setBackground(Color.getHSBColor(hue, 1, 1));
    }

    private static void moveBox(JPanel box) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        if (box.getX() + dx < 0 || box.getX() + box.getWidth() + dx > screenWidth) {
            dx = -dx;
        }
        if (box.getY() + dy < 0 || box.getY() + box.getHeight() + dy > screenHeight) {
            dy = -dy;
        }

        box.setLocation(box.getX() + dx, box.getY() + dy);
    }
}