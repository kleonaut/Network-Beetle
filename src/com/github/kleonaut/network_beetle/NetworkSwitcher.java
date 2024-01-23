package com.github.kleonaut.network_beetle;

import javax.swing.Timer;

public class NetworkSwitcher implements ModeAware
{
    private final Timer verificationTimer;
    private NetProfile nowProfile;
    private final PowerableGroup powerableGroup;

    public NetworkSwitcher(PowerableGroup powerableGroup)
    {
        verificationTimer = new Timer(5000, e -> verify());
        verificationTimer.setRepeats(false);
        this.powerableGroup = powerableGroup;
    }

    private void verify()
    {
        if (!Networks.fetchNowProfile().name().equals(nowProfile.name()))
        {
            OverviewFrame.addToLog("Failure to connect to "+nowProfile.name());
            powerableGroup.turnOff();
        }
    }

    @Override
    public void setMode(Mode mode)
    {
        nowProfile = mode.netProfile();
        Networks.setProfile(mode.netProfile());

        if (mode.netProfile() == NetProfile.DISCONNECT)
        {
            OverviewFrame.addToLog("Disconnecting from the Internet");
            verificationTimer.stop();
        }
        else if (mode.netProfile() != NetProfile.STAY)
        {
            OverviewFrame.addToLog("Connecting to "+mode.netProfile().name());
            verificationTimer.restart();
        }
        else
            verificationTimer.stop();

    }

    @Override
    public void setModeless() { verificationTimer.stop(); }
}
