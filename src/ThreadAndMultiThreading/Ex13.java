package ThreadAndMultiThreading;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

class SecondCounter extends JComponent implements Runnable {
    private volatile boolean keepRunning;

    private final Font paintFont = new Font("SansSerif", Font.BOLD, 14);

    private volatile String timeMsg = "never started";

    private volatile int arcLen = 0;

    public SecondCounter() {
    }

    public void run() {
        runClock();
    }

    public void runClock() {
        DecimalFormat fmt = new DecimalFormat("0.000");
        long normalSleepTime = 100;
        long nextSleepTime = normalSleepTime;

        int counter = 0;
        long startTime = System.currentTimeMillis();
        keepRunning = true;

        while (keepRunning) {
            try {
                Thread.sleep(nextSleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            counter++;
            double counterSecs = counter / 10.0;
            double elapsedSecs = (System.currentTimeMillis() - startTime) / 1000.0;

            double diffSecs = counterSecs - elapsedSecs;

            nextSleepTime = normalSleepTime + ((long) (diffSecs * 1000.0));

            if (nextSleepTime < 0) {
                nextSleepTime = 0;
            }

            timeMsg = fmt.format(counterSecs) + " - "
                    + fmt.format(elapsedSecs) + " = "
                    + fmt.format(diffSecs);

            arcLen = (((int) counterSecs) % 60) * 360 / 60;
            repaint();
        }
    }

    public void stopClock() {
        keepRunning = false;
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.setFont(paintFont);
        g.drawString(timeMsg, 0, 15);

        g.fillOval(0, 20, 100, 100);

        g.setColor(Color.white);
        g.fillOval(3, 23, 94, 94);

        g.setColor(Color.blue);
        g.fillArc(2, 22, 96, 96, 90, -arcLen);
    }
}


public class Ex13 extends JPanel {
    private final SecondCounter sc = new SecondCounter();

    private final JButton startB = new JButton("Start");

    private final JButton stopB = new JButton("Stop");

    public Ex13() {
        stopB.setEnabled(false);
        startB.addActionListener(e -> {
            startB.setEnabled(false);

            Thread counterThread = new Thread(sc, "Counter");
            counterThread.start();

            stopB.setEnabled(true);
            stopB.requestFocus();
        });

        stopB.addActionListener(e -> {
            stopB.setEnabled(false);
            sc.stopClock();
            startB.setEnabled(true);
            startB.requestFocus();
        });

        JPanel innerButtonP = new JPanel();
        innerButtonP.setLayout(new GridLayout(0, 1, 0, 3));
        innerButtonP.add(startB);
        innerButtonP.add(stopB);

        JPanel buttonP = new JPanel();
        buttonP.setLayout(new BorderLayout());
        buttonP.add(innerButtonP, BorderLayout.NORTH);

        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.add(buttonP, BorderLayout.WEST);
        this.add(sc, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        Ex13 scm = new Ex13();

        JFrame f = new JFrame("Second Counter");
        f.setContentPane(scm);
        f.setSize(320, 200);
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }


}
