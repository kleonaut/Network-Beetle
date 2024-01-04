package com.github.kleonaut.network_beetle.regimen;

import deprecated.NetProfile;

import java.util.List;

// immutable
public class Regimen
{
    private final String name;
    private final NetProfile netProfile;
    private final List<TaskCondition> conditions;
    private final int enterDelay = 0;
    private final int exitDelay = 0;
    private final boolean willNotifyOnEnter = false;

    public Regimen(String name, NetProfile profile, List<TaskCondition> conditions)
    {
        this.name = name;
        this.netProfile = profile;
        this.conditions = conditions;
    }

    public String name() { return name; }
    public NetProfile netProfile() { return netProfile; }
    public List<TaskCondition> conditions() { return List.copyOf(conditions); }


}
