package TCPSocket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class Ex7 implements Runnable {
    public final static int NULL = 0;
    public final static int DISCONNECTED = 1;
    public final static int DISCONNECTING = 2;
    public final static int BEGIN_CONNECT = 3;
    public final static int CONNECTED = 4;

    public final static String[] statusMessages = {
            " Error! Could not connect!", " Disconnected",
            " Disconnecting...", " Connecting...", " Connected"
    };
    public final static Ex7 tcpObj = new Ex7();
    public final static String END_CHAT_SESSION = Character.toString((char) 0);

    public static String hostIP = "localhost";
    public static int port = 1234;
    public static int connectionStatus = DISCONNECTED;
    public static boolean isHost = true;
    public static String statusString = statusMessages[connectionStatus];
    public static final StringBuffer toAppend = new StringBuffer("");
    public static final StringBuffer toSend = new StringBuffer("");

    public static JFrame mainFrame = null;
    public static JTextArea chatText = null;
    public static JTextField chatLine = null;
    public static JPanel statusBar = null;
    public static JLabel statusField = null;
    public static JTextField statusColor = null;
    public static JTextField ipField = null;
    public static JTextField portField = null;
    public static JRadioButton hostOption = null;
    public static JRadioButton guestOption = null;
    public static JButton connectButton = null;
    public static JButton disconnectButton = null;

    public static ServerSocket hostServer = null;
    public static Socket socket = null;
    public static BufferedReader in = null;
    public static PrintWriter out = null;

    private static JPanel initOptionsPane() {
        JPanel pane = null;
        ActionAdapter buttonListener = null;
        JPanel optionsPane = new JPanel(new GridLayout(4, 1));

        pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pane.add(new JLabel("Host IP:"));
        ipField = new JTextField(10);
        ipField.setText(hostIP);
        ipField.setEnabled(false);
        ipField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                ipField.selectAll();
                if (connectionStatus != DISCONNECTED) {
                    changeStatusNTS(NULL, true);
                } else {
                    hostIP = ipField.getText();
                }
            }
        });
        pane.add(ipField);
        optionsPane.add(pane);

        pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pane.add(new JLabel("Port:"));
        portField = new JTextField(10);
        portField.setEditable(true);
        portField.setText(String.valueOf(port));
        portField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (connectionStatus != DISCONNECTED) {
                    changeStatusNTS(NULL, true);
                } else {
                    int temp;
                    try {
                        temp = Integer.parseInt(portField.getText());
                        port = temp;
                    } catch (NumberFormatException nfe) {
                        portField.setText(String.valueOf(port));
                        mainFrame.repaint();
                    }
                }
            }
        });
        pane.add(portField);
        optionsPane.add(pane);

        buttonListener = new ActionAdapter() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connectionStatus != DISCONNECTED) {
                    changeStatusNTS(NULL, true);
                } else {
                    isHost = e.getActionCommand().equals("host");
                    if (isHost) {
                        ipField.setEnabled(false);
                        ipField.setText("localhost");
                        hostIP = "localhost";
                    } else {
                        ipField.setEnabled(true);
                    }
                }
            }
        };
        ButtonGroup bg = new ButtonGroup();
        hostOption = new JRadioButton("Host", true);
        hostOption.setMnemonic(KeyEvent.VK_H);
        hostOption.setActionCommand("host");
        hostOption.addActionListener(buttonListener);
        guestOption = new JRadioButton("Guest", false);
        guestOption.setMnemonic(KeyEvent.VK_G);
        guestOption.setActionCommand("guest");
        guestOption.addActionListener(buttonListener);
        bg.add(hostOption);
        bg.add(guestOption);
        pane = new JPanel(new GridLayout(1, 2));
        pane.add(hostOption);
        pane.add(guestOption);
        optionsPane.add(pane);

        JPanel buttonPane = new JPanel(new GridLayout(1, 2));
        buttonListener = new ActionAdapter() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("connect")) {
                    changeStatusNTS(BEGIN_CONNECT, true);
                } else {
                    changeStatusNTS(DISCONNECTING, true);
                }
            }
        };
        connectButton = new JButton("Connect");
        connectButton.setMnemonic(KeyEvent.VK_C);
        connectButton.setActionCommand("connect");
        connectButton.addActionListener(buttonListener);
        connectButton.setEnabled(true);
        disconnectButton = new JButton("Disconnect");
        disconnectButton.setMnemonic(KeyEvent.VK_D);
        disconnectButton.setActionCommand("disconnect");
        disconnectButton.addActionListener(buttonListener);
        disconnectButton.setEnabled(false);
        buttonPane.add(connectButton);
        buttonPane.add(disconnectButton);
        optionsPane.add(buttonPane);

        return optionsPane;
    }

    private static void initGUI() {
        statusField = new JLabel();
        statusField.setText(statusMessages[DISCONNECTED]);
        statusColor = new JTextField(1);
        statusColor.setBackground(Color.red);
        statusColor.setEditable(false);
        statusBar = new JPanel(new BorderLayout());
        statusBar.add(statusColor, BorderLayout.WEST);
        statusBar.add(statusField, BorderLayout.CENTER);
        JPanel optionsPane = initOptionsPane();
        JPanel chatPane = new JPanel(new BorderLayout());
        chatText = new JTextArea(10, 20);
        chatText.setLineWrap(true);
        chatText.setEditable(false);
        chatText.setForeground(Color.blue);
        JScrollPane chatTextPane = new JScrollPane(chatText,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatLine = new JTextField();
        chatLine.setEnabled(false);
        chatLine.addActionListener(new ActionAdapter() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = chatLine.getText();
                if (!s.equals("")) {
                    appendToChatBox("OUTGOING: " + s + "\n");
                    chatLine.selectAll();
                    sendString(s);
                }
            }
        });
        chatPane.add(chatLine, BorderLayout.SOUTH);
        chatPane.add(chatTextPane, BorderLayout.CENTER);
        chatPane.setPreferredSize(new Dimension(200, 200));

        JPanel mainPane = new JPanel(new BorderLayout());
        mainPane.add(statusBar, BorderLayout.SOUTH);
        mainPane.add(optionsPane, BorderLayout.WEST);
        mainPane.add(chatPane, BorderLayout.CENTER);

        mainFrame = new JFrame("Simple TCP Chat");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setContentPane(mainPane);
        mainFrame.setSize(mainFrame.getPreferredSize());
        mainFrame.setLocation(200, 200);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private static void changeStatusTS(int newConnectStatus, boolean noError) {
        if (newConnectStatus != NULL) {
            connectionStatus = newConnectStatus;
        }
        if (noError) {
            statusString = statusMessages[connectionStatus];
        } else {
            statusString = statusMessages[NULL];
        }
        SwingUtilities.invokeLater(tcpObj);
    }

    private static void changeStatusNTS(int newConnectStatus, boolean noError) {
        if (newConnectStatus != NULL) {
            connectionStatus = newConnectStatus;
        }
        if (noError) {
            statusString = statusMessages[connectionStatus];
        } else {
            statusString = statusMessages[NULL];
        }
        tcpObj.run();
    }

    private static void appendToChatBox(String s) {
        synchronized (toAppend) {
            toAppend.append(s);
        }
    }

    private static void sendString(String s) {
        synchronized (toSend) {
            toSend.append(s).append("\n");
        }
    }

    private static void cleanUp() {
        try {
            if (hostServer != null) {
                hostServer.close();
                hostServer = null;
            }
        } catch (IOException e) {
            hostServer = null;
        }

        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (IOException e) {
            socket = null;
        }

        try {
            if (in != null) {
                in.close();
                in = null;
            }
        } catch (IOException e) {
            in = null;
        }

        if (out != null) {
            out.close();
            out = null;
        }
    }

    public void run() {
        switch (connectionStatus) {
            case DISCONNECTED -> {
                connectButton.setEnabled(true);
                disconnectButton.setEnabled(false);
                ipField.setEnabled(true);
                portField.setEnabled(true);
                hostOption.setEnabled(true);
                guestOption.setEnabled(true);
                chatLine.setText("");
                chatLine.setEnabled(false);
                statusColor.setBackground(Color.red);
            }
            case DISCONNECTING -> {
                connectButton.setEnabled(false);
                disconnectButton.setEnabled(false);
                ipField.setEnabled(false);
                portField.setEnabled(false);
                hostOption.setEnabled(false);
                guestOption.setEnabled(false);
                chatLine.setEnabled(false);
                statusColor.setBackground(Color.orange);
            }
            case CONNECTED -> {
                connectButton.setEnabled(false);
                disconnectButton.setEnabled(true);
                ipField.setEnabled(false);
                portField.setEnabled(false);
                hostOption.setEnabled(false);
                guestOption.setEnabled(false);
                chatLine.setEnabled(true);
                statusColor.setBackground(Color.green);
            }
            case BEGIN_CONNECT -> {
                connectButton.setEnabled(false);
                disconnectButton.setEnabled(false);
                ipField.setEnabled(false);
                portField.setEnabled(false);
                hostOption.setEnabled(false);
                guestOption.setEnabled(false);
                chatLine.setEnabled(false);
                chatLine.grabFocus();
                statusColor.setBackground(Color.orange);
            }
        }
        ipField.setText(hostIP);
        portField.setText(String.valueOf(port));
        hostOption.setSelected(isHost);
        guestOption.setSelected(!isHost);
        statusField.setText(statusString);
        chatText.append(toAppend.toString());
        toAppend.setLength(0);

        mainFrame.repaint();
    }

    public static void main(String[] args) {
        String s;
        initGUI();
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (connectionStatus) {
                case BEGIN_CONNECT:
                    try {
                        if (isHost) {
                            hostServer = new ServerSocket(port);
                            socket = hostServer.accept();
                        } else {
                            socket = new Socket(hostIP, port);
                        }

                        in = new BufferedReader(new
                                InputStreamReader(socket.getInputStream()));
                        out = new PrintWriter(socket.getOutputStream(), true);
                        changeStatusTS(CONNECTED, true);
                    } catch (IOException e) {
                        cleanUp();
                        changeStatusTS(DISCONNECTED, false);
                    }
                    break;

                case CONNECTED:
                    try {
                        if (toSend.length() != 0) {
                            out.print(toSend);
                            out.flush();
                            toSend.setLength(0);
                            changeStatusTS(NULL, true);
                        }

                        if (in.ready()) {
                            s = in.readLine();
                            if ((s != null) && (s.length() != 0)) {

                                if (s.equals(END_CHAT_SESSION)) {
                                    changeStatusTS(DISCONNECTING, true);
                                } else {
                                    appendToChatBox("INCOMING: " + s + "\n");
                                    changeStatusTS(NULL, true);
                                }
                            }
                        }
                    } catch (IOException e) {
                        cleanUp();
                        changeStatusTS(DISCONNECTED, false);
                    }
                    break;

                case DISCONNECTING:
                    out.print(END_CHAT_SESSION);
                    out.flush();
                    cleanUp();
                    changeStatusTS(DISCONNECTED, true);
                    break;

                default:
                    break;
            }
        }
    }
}

class ActionAdapter implements ActionListener {
    public void actionPerformed(ActionEvent e) {
    }
}

