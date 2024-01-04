package com.github.kleonaut.network_beetle;

// immutable
public class NetProfile
{
    public static final NetProfile OFFLINE = new NetProfile("Disconnect from the Internet");

    private final String name;

    public NetProfile(String name) { this.name = name; }

    public String name() { return name; }
}
