package com.github.kleonaut.network_beetle;

import java.util.ArrayList;
import java.util.List;

public class PowerableGroup implements Disposable
{
    private final List<Powerable> items = new ArrayList<>();
    private boolean state = false;

    public void add(Powerable powerable) { items.add(powerable); }

    public void turnOff()
    {
        if (state == false) return;
        state = false;
        for (Powerable item : items) item.setPowered(false);
    }

    public void turnOn()
    {
        if (state == true) return;
        state = true;
        for (Powerable item : items) item.setPowered(true);
    }

    public void toggle()
    {
        state = !state;
        for (Powerable item : items) item.setPowered(state);
    }

    @Override
    public void dispose() { turnOff(); }
}
