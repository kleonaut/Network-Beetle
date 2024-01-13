package com.github.kleonaut.network_beetle;

import java.util.ArrayList;
import java.util.List;

public class DisposableGroup
{
    private final List<Disposable> disposables = new ArrayList<>();

    public void add(Disposable disposable) {disposables.add(disposable); }
    public void killAll() { for (Disposable item : disposables) item.dispose(); }
}