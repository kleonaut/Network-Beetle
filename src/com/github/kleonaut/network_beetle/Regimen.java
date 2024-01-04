package com.github.kleonaut.network_beetle;

import java.util.List;

// immutable
public class Regimen
{
    private final String name;
    private final NetProfile netProfile;
    private final List<String> conditions;
    private final int enterDelay = 0;
    private final int exitDelay = 0;
    private final boolean willNotifyOnEnter = false;

    public Regimen(String name, NetProfile profile, List<String> conditions)
    {
        this.name = name;
        this.netProfile = profile;
        this.conditions = conditions;
    }

    public String name() { return name; }
    public NetProfile netProfile() { return netProfile; }
    public List<String> conditions() { return List.copyOf(conditions); }
}
