package ThreadAndMultiThreading;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;

public class Ex12 extends JLabel {
    private volatile String timeText;

    private final Thread internalThread;

    private volatile boolean noStopRequested;

    public Ex12() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setHorizontalAlignment(SwingConstants.RIGHT);
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setText("00000.0");
        setMinimumSize(getPreferredSize());
        setPreferredSize(getPreferredSize());
        setSize(getPreferredSize());

        timeText = "0.0";
        setText(timeText);

        noStopRequested = true;
        Runnable r = () -> {
            try {
                runWork();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        internalThread = new Thread(r, "DigitalTimer");
        internalThread.start();
    }

    private void runWork() {
        long startTime = System.currentTimeMillis();
        int tenths = 0;
        long normalSleepTime = 100;
        long nextSleepTime = 100;
        DecimalFormat fmt = new DecimalFormat("0.0");

        Runnable updateText = () -> setText(timeText);

        while (noStopRequested) {
            try {
                Thread.sleep(nextSleepTime);

                tenths++;
                long currTime = System.currentTimeMillis();
                long elapsedTime = currTime - startTime;

                nextSleepTime = normalSleepTime + ((tenths * 100L) - elapsedTime);

                if (nextSleepTime < 0) {
                    nextSleepTime = 0;
                }

                timeText = fmt.format(elapsedTime / 1000.0);
                SwingUtilities.invokeAndWait(updateText);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopRequest() {
        noStopRequested = false;
        internalThread.interrupt();
    }

    public boolean isAlive() {
        return internalThread.isAlive();
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.getContentPane().setLayout(new FlowLayout());
        f.getContentPane().add(new Ex12());
        f.setSize(250, 100);
        f.setVisible(true);
    }

}
