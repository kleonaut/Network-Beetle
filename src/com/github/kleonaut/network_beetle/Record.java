package com.github.kleonaut.network_beetle;

import java.util.ArrayList;
import java.util.List;

public class Record
{
    //private final Preferences registry = Preferences.userRoot().node(this.getClass().getName());

    public boolean isLaunchedOnStartup;
    public boolean isEnabledOnLaunch;
    public List<Tune> tunes = new ArrayList<>();

    public Record()
    {
        isLaunchedOnStartup = true;
        isEnabledOnLaunch = true;
        tunes.add(new Tune("Mode 0", true));
        tunes.add(new Tune("Mode 1"));
    }

    //public AppVariables save() {}
    //public AppVariables load() {}
}
