package com.github.kleonaut.network_beetle;

public class NetProfile
{
    private final String name;
    public static final NetProfile OFFLINE    = null,
                                       KIRKLIN_5G = new NetProfile("Kirklin_5GEXT"),
                                       KIRKLIN_2G = new NetProfile("Kirklin_2GEXT"),
                                       MOTOROLA   = new NetProfile("Motorola Edge '22");

    public NetProfile(String name)
    {
        this.name = name;
    }

    public String name() { return name; }
}
