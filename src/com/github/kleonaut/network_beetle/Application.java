package com.github.kleonaut.network_beetle;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.*;

public class Application
{
    // TODO: use ProcessBuilder instead of Runtime.exec()

    private final JFrame frame = new JFrame("Network Beetle");
    private final CustomJPanel panel = new CustomJPanel();
    private final SystemTray tray = SystemTray.getSystemTray();
    private final TrayIcon trayIcon;

    private final NetworkShell netsh = new NetworkShell();
    private final Tasklist tasklist = new Tasklist();
    private final NetworkProfile lowProfile  = new NetworkProfile("Kirklin_5GEXT");
    private final NetworkProfile highProfile = new NetworkProfile("Kirklin_2GEXT");
    private final MicrosoftProcess[] highProcesses = { new MicrosoftProcess("aces.exe") };

    private boolean isEnabled = true;
    private final Timer timer;

    public Application()
    {
        // ---------------- Window
        frame.setSize(300, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel);


        // ---------------- Popup Menu
        PopupMenu menu = new PopupMenu();
        MenuItem showButton = new MenuItem("Open");
        showButton.addActionListener(showAction);
        MenuItem quitButton = new MenuItem("Quit Network Beetle");
        quitButton.addActionListener(quitAction);
        MenuItem disableButton = new MenuItem("Disable");
        disableButton.addActionListener(disableAction);
        MenuItem enableButton = new MenuItem("Enable");
        enableButton.addActionListener(enableAction);
        menu.add(enableButton);
        menu.add(disableButton);
        menu.add(showButton);
        menu.add(quitButton);

        // ----------------- Tray Icon
        try {
            BufferedImage image = ImageIO.read(Main.class.getResource("/resources/icon.png"));
            trayIcon = new TrayIcon(image, "Network Beetle", menu);
            trayIcon.addActionListener(showAction);
            tray.add(trayIcon);
        } catch (IOException | AWTException e)
        {
            throw new RuntimeException(e);
        }

        // ----------------- Timer
        timer = new Timer(1000, runAction);
        timer.setInitialDelay(0);
    }

    public void start()
    {
        timer.start();
    }

    // ---------------- Actions
    ActionListener showAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(true);
        }
    };
    ActionListener quitAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            tray.remove(trayIcon);
            System.exit(0);
        }
    };
    ActionListener disableAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            panel.setColor(Color.GRAY);
        }
    };
    ActionListener enableAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(timer.isRunning() == false)
                timer.restart();
        }
    };

    ActionListener runAction = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            if(isEnabled)
            {
                Color c = Color.RED;
                NetworkProfile nextProfile = lowProfile;
                try {
                     for (MicrosoftProcess process : highProcesses)
                        if (tasklist.hasProcess(process)) {
                            nextProfile = highProfile;
                            c = Color.GREEN;
                            break;
                        }
                    netsh.setProfile(nextProfile); // does nothing if is already in that profile
                } catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
                panel.setColor(c);
            }
        }
    };
}
