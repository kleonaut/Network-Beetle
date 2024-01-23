package com.github.kleonaut.network_beetle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tray implements Powerable, Disposable, ModeAware
{
    private final CheckboxMenuItem powerButton;
    private final SystemTray systemTray = SystemTray.getSystemTray();
    private final TrayIcon trayIcon;

    Tray(OverviewFrame window, PowerableGroup powerableGroup, DisposableGroup disposableGroup)
    {
        PopupMenu menu = new PopupMenu();
        MenuItem revealButton = new MenuItem("Open");
        revealButton.addActionListener(e -> window.setVisible(true));
        MenuItem quitButton = new MenuItem("Quit "+App.NAME);
        quitButton.addActionListener(e -> disposableGroup.killAll());
        powerButton = new CheckboxMenuItem("Enabled");
        powerButton.setState(true);
        powerButton.addItemListener(e -> powerableGroup.toggle());
        menu.add(revealButton);
        menu.add(powerButton);
        menu.addSeparator();
        menu.add(quitButton);

        try {
            BufferedImage image = ImageIO.read(Main.class.getResource("/resources/icon.png"));
            trayIcon = new TrayIcon(image, App.NAME, menu);
            trayIcon.addActionListener(e -> window.setVisible(true));
            systemTray.add(trayIcon);
        } catch (IOException | AWTException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dispose() { systemTray.remove(trayIcon); }

    @Override
    public void setPowered(boolean flag) { powerButton.setState(flag); }

    @Override
    public void setMode(Mode mode) { trayIcon.setToolTip(App.NAME+" "+mode.name()); }

    @Override
    public void setModeless() { trayIcon.setToolTip(App.NAME); }
}
