package com.github.kleonaut.network_beetle;

import java.io.IOException;

public class NetShell
{
    // TODO: use 'netsh wlan show interfaces' to set initial profile
    // TODO: and a few seconds after connect() to check if it went thru (perhaps profile change is instant)

    private NetProfile profile = null;

    /** If profile is null, this disconnects from a network */
    public void setProfile(NetProfile profile) throws IOException
    {
        if (this.profile != profile) // note that this is comparing object addresses in memory
        {
            if (profile == null)
                Runtime.getRuntime().exec("netsh wlan disconnect");
            else
                Runtime.getRuntime().exec("netsh wlan connect name=\""+profile.name()+"\"");
            this.profile = profile;
        }
    }

}
