package ThreadAndMultiThreading;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ex11 extends JFrame implements ActionListener {
    private final JLabel counter;

    private int tickCounter = 0;

    private static Ex11 edt;

    public Ex11() {
        super("Swing Threading");

        JButton freezer = new JButton("Increment");
        freezer.addActionListener(this);

        counter = new JLabel("0");

        add(freezer, BorderLayout.CENTER);
        add(counter, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        incrementLabel();
    }

    private void incrementLabel() {
        tickCounter++;
        Runnable code = () -> counter.setText(String.valueOf(tickCounter));

        if (SwingUtilities.isEventDispatchThread()) {
            code.run();
        } else {
            SwingUtilities.invokeLater(code);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            edt = new Ex11();
            edt.setVisible(true);

            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    edt.incrementLabel();
                }
            }).start();
        });
    }
}
