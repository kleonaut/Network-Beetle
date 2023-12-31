package com.github.kleonaut.network_beetle;

public class NetworkProfile
{
    private final String name;
    public static final NetworkProfile OFFLINE    = null,
                                       KIRKLIN_5G = new NetworkProfile("Kirklin_5GEXT"),
                                       KIRKLIN_2G = new NetworkProfile("Kirklin_2GEXT"),
                                       MOTOROLA   = new NetworkProfile("Motorola Edge '22");

    public NetworkProfile(String name)
    {
        this.name = name;
    }

    public String name() { return name; }
}
