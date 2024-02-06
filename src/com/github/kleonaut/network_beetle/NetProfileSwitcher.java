package com.github.kleonaut.network_beetle;

import javax.swing.Timer;

public class NetProfileSwitcher implements ModeObserver
{
    private final Timer verificationTimer;
    private NetProfile expectedProfile;
    private final PowerPublisher powerPublisher;

    public NetProfileSwitcher(PowerPublisher powerPublisher)
    {
        verificationTimer = new Timer(5000, e -> verify());
        verificationTimer.setRepeats(false);
        this.powerPublisher = powerPublisher;
    }

    private void verify()
    {
        if (Networks.fetchNowProfile() != expectedProfile)
        {
            MainWindow.addToLog("Failure to connect to "+ expectedProfile.name());
            powerPublisher.turnOff();
        }
    }

    @Override
    public void setMode(Mode mode)
    {
        verificationTimer.stop();
        if (mode.netProfile() != Networks.fetchNowProfile())
        {
            Networks.setProfile(mode.netProfile());
            expectedProfile = mode.netProfile();
            if (expectedProfile != NetProfile.STAY && expectedProfile != NetProfile.DISCONNECT)
                verificationTimer.restart();
        }
    }

    @Override
    public void setModeless()
    {
        verificationTimer.stop();
    }
}
