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
            nowProfile = null;
            powerableGroup.turnOff();
        }
    }

    @Override
    public void setMode(Mode mode)
    {
        if (nowProfile == mode.netProfile()) return;
        nowProfile = mode.netProfile();
        Networks.setProfile(nowProfile);

        if (nowProfile == NetProfile.STAY)
        {
            verificationTimer.stop();
        }
        else if (nowProfile == NetProfile.DISCONNECT)
        {
            OverviewFrame.addToLog("Disconnecting from the Internet");
            verificationTimer.stop();
        }
        else
        {
            OverviewFrame.addToLog("Connecting to "+nowProfile.name());
            verificationTimer.restart();
        }
    }

    @Override
    public void setModeless() { verificationTimer.stop(); }
}
