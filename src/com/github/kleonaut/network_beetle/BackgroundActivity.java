package com.github.kleonaut.network_beetle;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class BackgroundActivity implements ActionListener {

    private final NetworkShell netsh = new NetworkShell();
    private final Tasklist tasklist = new Tasklist();
    private final Dossier dossier;
    private final Timer timer;

    BackgroundActivity(Dossier dossier)
    {
        this.dossier = dossier;

        // ----------------- Timer
        timer = new Timer(1000, this);
        timer.setInitialDelay(0);
    }

    public void togglePower()
    {
        if (timer.isRunning())
            timer.stop();
        else
            timer.restart();
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        try {
            NetworkProfile profile = dossier.fallbackProfile();
            for (int i = 0; i < dossier.criteriaSize(); i++)
                if (tasklist.hasProcess(dossier.criterion(i))) {
                    profile = dossier.targetProfile();
                    break;
                }
            netsh.setProfile(profile); // does nothing if is already in that profile
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
