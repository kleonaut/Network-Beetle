package com.github.kleonaut.network_beetle;

import javax.imageio.ImageIO;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.*;

public class App
{
    public final String NAME = "Network Beetle";

    // TODO: switch to lambdas for listeners
    // TODO: use ProcessBuilder instead of Runtime.exec()
    // TODO: optimize using ProcessHandler.isAlive() to not invoke Tasklist

    private final Window window;
    private final SystemTray tray = SystemTray.getSystemTray();
    private final TrayIcon trayIcon;

    private final NetworkShell netsh = new NetworkShell();
    private final Tasklist tasklist = new Tasklist();
    private final NetworkProfile lowProfile  = new NetworkProfile("Kirklin_5GEXT");
    private final NetworkProfile highProfile = new NetworkProfile("Kirklin_2GEXT");
    private final MicrosoftProcess[] highProcesses = { new MicrosoftProcess("aces.exe") };

    private final Timer timer;

    public App()
    {
        window = new Window(this);

        // ---------------- Popup Menu
        PopupMenu menu = new PopupMenu();
        MenuItem revealButton = new MenuItem("Open");
        revealButton.addActionListener(revealAction);
        MenuItem quitButton = new MenuItem("Quit "+NAME);
        quitButton.addActionListener(quitAction);
        CheckboxMenuItem powerButton = new CheckboxMenuItem("Enabled");
        powerButton.setState(true);
        powerButton.addItemListener(powerAction);
        menu.add(revealButton);
        menu.add(powerButton);
        menu.addSeparator();
        menu.add(quitButton);

        // ----------------- Tray Icon
        try {
            BufferedImage image = ImageIO.read(Main.class.getResource("/resources/icon.png"));
            trayIcon = new TrayIcon(image, NAME, menu);
            trayIcon.addActionListener(revealAction);
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
    public void setEnabled(boolean flag)
    {
        if (flag == true)
            if(timer.isRunning() == false)
                timer.restart();
        if (flag == false)
            timer.stop();
    }
    public boolean isEnabled() { return timer.isRunning(); }

    // ---------------- Actions
    ActionListener revealAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            window.reveal();
        }
    };
    ActionListener quitAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            tray.remove(trayIcon);
            System.exit(0);
        }
    };
    ItemListener powerAction = new ItemListener() {

        @Override
        public void itemStateChanged(ItemEvent e) {
            window.powerAction();
        }
    };

    ActionListener runAction = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent event)
        {
            NetworkProfile nextProfile = lowProfile;
            try {
                 for (MicrosoftProcess process : highProcesses)
                    if (tasklist.hasProcess(process)) {
                        nextProfile = highProfile;
                        break;
                    }
                netsh.setProfile(nextProfile); // does nothing if is already in that profile
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    };
}
