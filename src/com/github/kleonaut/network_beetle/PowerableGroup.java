package com.github.kleonaut.network_beetle;

import java.util.ArrayList;
import java.util.List;

public class PowerableGroup
{
    private final List<Powerable> powerables = new ArrayList<>();
    private boolean state = false;

    public void add(Powerable powerable) {powerables.add(powerable); }
    public void turnOff() { state = false;  conduct(); }
    public void turnOn()  { state = true;   conduct(); }
    public void toggle()  { state = !state; conduct(); }

    private void conduct() { for (Powerable item : powerables) item.setPowered(state); }
}
