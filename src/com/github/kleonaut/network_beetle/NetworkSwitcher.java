package com.github.kleonaut.network_beetle;

import javax.swing.Timer;

public class NetworkSwitcher implements ModeAware
{
    private final Timer timer;
    private NetProfile nowProfile;
    private final PowerableGroup powerableGroup;

    public NetworkSwitcher(PowerableGroup powerableGroup)
    {
        timer = new Timer(5000, e -> verify());
        timer.setRepeats(false);
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
        if (mode.netProfile() == NetProfile.DISCONNECT ||
            mode.netProfile() == NetProfile.STAY)
        {
            timer.stop();
            return;
        }
        nowProfile = mode.netProfile();
        timer.restart();
    }

    @Override
    public void setModeless() { timer.stop(); }
}
