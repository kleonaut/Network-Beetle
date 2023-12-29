package com.github.kleonaut.network_beetle;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Application
{
    // TODO: use ProcessBuilder instead of Runtime.exec()
    // TODO: use util.Timer instead of while(true) and Thread.sleep()

    private final JFrame frame = new JFrame("Network Beetle");
    private final CustomJPanel panel = new CustomJPanel();
    private final SystemTray tray = SystemTray.getSystemTray();
    private final TrayIcon trayIcon;

    private final NetworkShell netsh = new NetworkShell();
    private final Tasklist tasklist = new Tasklist();
    private final NetworkProfile lowProfile = null; // null is offline profile
    private final NetworkProfile highProfile = new NetworkProfile("Motorola Edge '22");
    private final MicrosoftProcess[] highProcesses = { new MicrosoftProcess("mspaint.exe"),
                                                       new MicrosoftProcess("calculatorapp.exe") };

    public Application() throws IOException, AWTException
    {
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
        MenuItem quitButton = new MenuItem("Quit");
        quitButton.addActionListener(quitAction);
        menu.add(showButton);
        menu.add(quitButton);

        // ----------------- Tray Icon
        BufferedImage image = ImageIO.read(Main.class.getResource("/resources/icon.png"));
        trayIcon = new TrayIcon(image, "Network Beetle", menu);
        trayIcon.addActionListener(showAction);
        tray.add(trayIcon);

        // ----------------- Load properties
        Properties networkProps = new Properties();
        networkProps.load(Main.class.getResourceAsStream("/resources/network.properties"));
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


    public void run() throws InterruptedException, IOException
    {
        while(true)
        {
            NetworkProfile nextProfile = lowProfile;
            boolean isGreen = false;
            for (MicrosoftProcess process : highProcesses)
                if (tasklist.hasProcess(process))
                {
                    nextProfile = highProfile;
                    isGreen = true;
                    break;
                }
            netsh.setProfile(nextProfile); // does nothing if is already in that profile
            panel.setIsGreen(isGreen);
            Thread.sleep(1000);
        }
    }
}
