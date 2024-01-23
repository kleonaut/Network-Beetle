package com.github.kleonaut.network_beetle;

import java.util.ArrayList;
import java.util.List;

public class ModeAwareGroup
{
    private final List<ModeAware> items = new ArrayList<>();
    private Mode nowMode = null;

    public void add(ModeAware item) { items.add(item); }

    public void setMode(Mode mode)
    {
        if (nowMode == mode) return;
        nowMode = mode;
        for (ModeAware item : items) item.setMode(mode);
    }

    public void setModeless()
    {
        if (nowMode == null) return;
        nowMode = null;
        for (ModeAware item : items) item.setModeless();
    }
}
