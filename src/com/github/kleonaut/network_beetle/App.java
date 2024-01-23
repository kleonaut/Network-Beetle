package com.github.kleonaut.network_beetle;

public class App
{
    public static final String NAME = "Network Beetle";

    public App()
    {
        Record record = new Record();

        ModeAwareGroup modeAwareGroup = new ModeAwareGroup();
        PowerableGroup powerableGroup = new PowerableGroup();
        DisposableGroup disposableGroup = new DisposableGroup();

        OverviewFrame window = new OverviewFrame(powerableGroup, modeAwareGroup, record);
        Tray tray = new Tray(window, powerableGroup, disposableGroup);
        ModeUpdater updater = new ModeUpdater(record, modeAwareGroup);
        NetworkSwitcher netSwitcher = new NetworkSwitcher(powerableGroup);

        // objects will switch mode in this order
        modeAwareGroup.add(window);
        modeAwareGroup.add(netSwitcher);
        modeAwareGroup.add(tray);

        // objects will be disposed of in this order
        disposableGroup.add(powerableGroup);
        disposableGroup.add(tray);
        disposableGroup.add(window);

        // objects will be powered in this order
        powerableGroup.add(tray);
        powerableGroup.add(window);
        powerableGroup.add(updater);

        powerableGroup.turnOn();
    }
}
