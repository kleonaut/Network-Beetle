package com.github.kleonaut.network_beetle;

import java.util.ArrayList;
import java.util.List;

public class DisposePublisher
{
    private final List<DisposeObserver> observer = new ArrayList<>();

    public void add(DisposeObserver observer){ this.observer.add(observer); }
    public void killAll() { for (DisposeObserver item : observer) item.dispose(); }
}