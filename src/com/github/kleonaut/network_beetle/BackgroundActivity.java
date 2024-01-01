package com.github.kleonaut.network_beetle;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class BackgroundActivity implements ActionListener {

    private final NetShell netsh = new NetShell();
    private final Tasklist tasklist = new Tasklist();
    private final Protocol protocol;
    private final Timer timer;

    BackgroundActivity(Protocol protocol)
    {
        this.protocol = protocol;
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
            NetProfile profile = protocol.getFallback();
            String[] criteria = protocol.criteria();
            for (int i = 0; i < criteria.length; i++)
                if (tasklist.hasProcessName(criteria[i])) {
                    profile = protocol.getTarget();
                    break;
                }
            netsh.setProfile(profile); // does nothing if is already in that profile
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
