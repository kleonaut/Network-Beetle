package com.github.kleonaut.network_beetle;

import java.util.ArrayList;
import java.util.List;

public class Record
{
    //private final Preferences registry = Preferences.userRoot().node(this.getClass().getName());

    private boolean isLaunchedOnStartup;
    private boolean isEnabledOnLaunch;
    private List<Mode> extraModes; // list is to be ordered with first element as highest priority
    private Mode defaultMode;

    public Record()
    {
        isLaunchedOnStartup = true;
        isEnabledOnLaunch = true;
        List<Mode> modes = new ArrayList<>();
        modes.add(new Mode("Mode 1"));
        extraModes = List.copyOf(modes);
        defaultMode = genDefaultMode();
    }

    private Mode genDefaultMode()
    {
        Mode mode = new Mode("Mode 0");
        List<String> conditions = new ArrayList<>();
        conditions.add("Conditions can not be added");
        conditions.add("to the default mode");
        mode.setConditions(conditions);
        return mode;
    }

    // list is to be ordered with first element as highest priority
    public List<Mode> modes(boolean defaultIncluded)
    {
        if (defaultIncluded)
        {
            List<Mode> allModes = new ArrayList<>(this.extraModes);
            allModes.addLast(defaultMode);
            return List.copyOf(allModes);
        }
        return extraModes;
    }

    public Mode defaultMode() { return defaultMode; }

    //public AppVariables save() {}
    //public AppVariables load() {}
}
