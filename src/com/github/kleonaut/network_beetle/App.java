package com.github.kleonaut.network_beetle;

import javax.imageio.ImageIO;
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

    // TODO: consider having no lowProfile, instead switch to whatever network was there before!
    // TODO: switch to lambdas for listeners
    // TODO: use ProcessBuilder instead of Runtime.exec()
    // TODO: optimize using ProcessHandler.isAlive() to not invoke Tasklist

    private final Window window;
    private final SystemTray tray = SystemTray.getSystemTray();
    private final TrayIcon trayIcon;
    private final BackgroundActivity activity;
    CheckboxMenuItem powerButton;


    public App()
    {
        window = new Window(this);
        activity = new BackgroundActivity(new Protocol());

        // ---------------- Popup Menu
        PopupMenu menu = new PopupMenu();
        MenuItem revealButton = new MenuItem("Open");
        revealButton.addActionListener(revealAction);
        MenuItem quitButton = new MenuItem("Quit "+NAME);
        quitButton.addActionListener(quitAction);
        powerButton = new CheckboxMenuItem("Enabled");
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

        // ----------------- Start
        togglePower();
    }

    public void togglePower()
    {
        activity.togglePower();
        window.togglePower();
        powerButton.setState(!powerButton.getState());
    }

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
            togglePower();
        }
    };
}
