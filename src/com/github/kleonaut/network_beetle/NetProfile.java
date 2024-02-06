package com.github.kleonaut.network_beetle;

import java.util.ArrayList;
import java.util.List;

// capable of == comparison for all instances
public class NetProfile
{
    private static final List<NetProfile> allProfiles = new ArrayList<>();
    public static final NetProfile DISCONNECT = new NetProfile("Disconnect from the Internet", "[disconnect]");
    public static final NetProfile STAY = new NetProfile("Remain on the same network", "[stay]");

    private final String name;
    private final String id;

    public static NetProfile get(String identifier)
    {
        for (NetProfile profile : allProfiles)
            if (profile.id.equals(identifier) || profile.name.equals(identifier))
                return profile;
        return new NetProfile(identifier, identifier);
    }

    private NetProfile(String name, String id)
    {
        this.name = name;
        this.id = id;
        allProfiles.add(this);
    }

    public String name() { return name; }
    public String id() { return id; }
}