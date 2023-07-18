import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickSpeedTracker {
    private JFrame frame;
    private JButton startButton;
    private JButton stopButton;
    private JLabel clickCountLabel;
    private int clickCount;
    private Robot robot;
    private boolean autoClicking;

    private int startX;
    private int startY;

    public ClickSpeedTracker() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        frame = new JFrame("ClickSpeed Tracker");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        clickCountLabel = new JLabel("Click Count: 0");
        clickCountLabel.setBounds(100, 50, 100, 30);
        frame.add(clickCountLabel);

        startButton = new JButton("Start Clicking");
        startButton.setBounds(80, 100, 120, 30);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!autoClicking) {
                    autoClicking = true;
                    startX = frame.getLocationOnScreen().x; // Get the x-coordinate of the frame
                    startY = frame.getLocationOnScreen().y; // Get the y-coordinate of the frame
                    autoClick(100); // Specify the desired number of clicks
                }
            }
        });
        frame.add(startButton);

        stopButton = new JButton("Stop Clicking");
        stopButton.setBounds(80, 140, 120, 30);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoClicking = false;
            }
        });
        frame.add(stopButton);

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (autoClicking) {
                    return;
                }

                clickCount++;
                updateClickCountLabel();
            }
        });
    }

    private void updateClickCountLabel() {
        clickCountLabel.setText("Click Count: " + clickCount);
    }

    public void autoClick(int numClicks) {
        robot.mouseMove(startX, startY); // Move the mouse to the specified starting position

        for (int i = 0; i < numClicks && autoClicking; i++) {
            robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);

            clickCount++;
            updateClickCountLabel();

            try {
                Thread.sleep(100); // Adjust the delay between clicks (in milliseconds)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        autoClicking = false;
    }

    public static void main(String[] args) {
        ClickSpeedTracker clickSpeedTracker = new ClickSpeedTracker();
        clickSpeedTracker.frame.setVisible(true);
    }
}
