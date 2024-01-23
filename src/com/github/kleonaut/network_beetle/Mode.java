package com.github.kleonaut.network_beetle;

import java.util.ArrayList;
import java.util.List;

public class Mode
{
    private String name;
    private NetProfile netProfile = NetProfile.STAY;
    private List<String> conditions = List.copyOf(new ArrayList<>());

    public Mode(String name) { this.name = name; }

    public String name() { return name; }
    public NetProfile netProfile() { return netProfile; }
    public List<String> conditions() { return conditions; }

    public void setName(String name) { this.name = name; }
    public void setNetProfile(NetProfile netProfile) { this.netProfile = netProfile; }
    public void setConditions(List<String> conditions) { this.conditions = List.copyOf(conditions); }
}