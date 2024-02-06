package com.github.kleonaut.network_beetle;

import java.util.List;

public class Mode
{
    private final String name;
    private final NetProfile netProfile;
    private final List<String> conditions;

    public Mode(String name, NetProfile netProfile, List<String> conditions)
    {
        this.name = name;
        this.netProfile = netProfile;
        this.conditions = List.copyOf(conditions);
    }

    public String name() { return name; }
    public NetProfile netProfile() { return netProfile; }
    public List<String> conditions() { return conditions; }
}