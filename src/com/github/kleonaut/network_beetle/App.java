package com.github.kleonaut.network_beetle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App
{
    public final String NAME = "Network Beetle";

    private final RegimenScout2 scout;
    private final NetShell netShell = new NetShell();
    private boolean isPowered;

    private final Window window = new Window(this);
    private final SystemTray tray = SystemTray.getSystemTray();
    private final TrayIcon trayIcon;
    private final CheckboxMenuItem powerButton;

    private List<Regimen> regimens = new ArrayList<>();
    private Regimen regimen;

    public App()
    {
        // ---------------- Regimens
        List<String> conditionTasks = new ArrayList<>();
        conditionTasks.add("calculatorapp.exe");
        conditionTasks.add("mspaint.exe");
        conditionTasks.add("notepad.exe");
        conditionTasks.add("sublime_text.exe");
        conditionTasks.add("obsidian.exe");
        conditionTasks.add("taskmgr.exe");
        regimens.add(new Regimen("Productivity", new NetProfile("Motorolla"), conditionTasks));
        regimens.add(new Regimen("Default", NetProfile.OFFLINE, new ArrayList<String>()));
        regimens = List.copyOf(regimens); // makes it unmodifiable
        regimen = regimens.getLast();
        scout = new RegimenScout2(regimens, this);

        // ---------------- Popup Menu
        PopupMenu menu = new PopupMenu();
        MenuItem revealButton = new MenuItem("Open");
        revealButton.addActionListener(e -> revealWindow());
        MenuItem quitButton = new MenuItem("Quit "+NAME);
        quitButton.addActionListener(e -> quitApp());
        powerButton = new CheckboxMenuItem("Enabled");
        powerButton.setState(true);
        powerButton.addItemListener(e -> togglePower());
        menu.add(revealButton);
        menu.add(powerButton);
        menu.addSeparator();
        menu.add(quitButton);

        // ----------------- Tray Icon
        try {
            BufferedImage image = ImageIO.read(Main.class.getResource("/resources/icon.png"));
            trayIcon = new TrayIcon(image, NAME, menu);
            trayIcon.addActionListener(e -> revealWindow());
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
        isPowered = !isPowered;
        window.togglePower();
        powerButton.setState(isPowered);
        scout.togglePower();
    }

    public void revealWindow() { window.reveal(); }

    public void quitApp()
    {
        tray.remove(trayIcon);
        window.kill();
    }

    public void setRegimen(Regimen regimen)
    {
        if (this.regimen != regimen)
        {
            netShell.setProfile(regimen.netProfile());
            this.regimen = regimen;
            System.out.println("Setting regimen to "+regimen.name());
        }
    }
}
