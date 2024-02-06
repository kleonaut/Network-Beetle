package com.github.kleonaut.network_beetle;

import java.util.ArrayList;
import java.util.List;

public class PowerPublisher implements DisposeObserver
{
    private final List<PowerObserver> observers = new ArrayList<>();
    private boolean isOn = false;
    private boolean isBlocked = false;

    public void add(PowerObserver powerObserver) { observers.add(powerObserver); }

    public void turnOff() { publishPowered(false); }

    public void turnOn() { publishPowered(true); }

    public void toggle() { publishPowered(!isOn); }

    private void publishPowered(boolean flag)
    {
        if (isOn != flag)
        {
            isOn = flag;
            for (PowerObserver observer : observers) observer.setPowered(flag);
        }
    }

    public void block() { publishBlocked(true); }

    public void unblock() { publishBlocked(false); }

    private void publishBlocked(boolean flag)
    {
        if (isBlocked != flag)
        {
            isBlocked = flag;
            for (PowerObserver observer : observers) observer.setPowerBlocked(flag);
        }
    }

    @Override
    public void dispose() { turnOff(); }
}
