package com.github.kleonaut.network_beetle;

import javax.swing.Timer;

public class App implements Powerable
{
    public static final String NAME = "Network Beetle";

    private final PowerableGroup powerableGroup = new PowerableGroup();
    private final DisposableGroup disposableGroup = new DisposableGroup();
    private final TunableGroup tunableGroup;

    private final Record record;
    private final OverviewWindow window;
    private final Tray tray;

    private final Timer scoutTimer = new Timer(2000, e -> scout());
    //private final Timer verifyTimer = new Timer(5000, e -> verify());

    public App()
    {
        record = new Record();
        window = new OverviewWindow(powerableGroup, record);
        tray = new Tray(window, powerableGroup, disposableGroup);

        tunableGroup = new TunableGroup(record);
        tunableGroup.add(window);

        scoutTimer.setRepeats(false);
        //verifyTimer.setRepeats(false);

        // objects will be disposed in this order
        disposableGroup.add(tray);
        disposableGroup.add(window);

        // objects will be powered in this order
        powerableGroup.add(tray);
        powerableGroup.add(window);
        powerableGroup.add(this);

        powerableGroup.turnOn();
    }

    public void scout()
    {
        int i = (int)(Math.random()*2);
        System.out.println(i);
        tunableGroup.setTune(i);
        scoutTimer.start();
    }

    @Override
    public void setPowered(boolean flag)
    {
        if (flag)
            scoutTimer.start();
        else
            scoutTimer.stop();
    }

}
