package com.github.kleonaut.network_beetle;

public record NetProfile(String name)
{
    public static final NetProfile DISCONNECT = new NetProfile("[Disconnect from the Internet]");
    public static final NetProfile STAY = new NetProfile("[Stay on current network]");
}