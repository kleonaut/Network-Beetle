package com.github.kleonaut.network_beetle;

import java.util.ArrayList;
import java.util.List;

public class Tune
{
    public String name;
    public boolean isDefault;
    public NetProfile netProfile = NetProfile.STAY;
    public List<String> conditions = new ArrayList<>();


    public Tune(String name)
    {
        this(name, false);
    }

    public Tune(String name, boolean isDefault)
    {
        this.name = name;
        this.isDefault = isDefault;
        if (isDefault)
        {
            conditions.add("Conditions can not be added");
            conditions.add("to the default mode");
        }
    }
}