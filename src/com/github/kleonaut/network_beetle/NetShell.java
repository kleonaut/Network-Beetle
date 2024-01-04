package com.github.kleonaut.network_beetle;

import java.io.IOException;

public class NetShell
{
    // TODO: use 'netsh wlan show interfaces' to set initial profile
    // TODO: and a few seconds after connect() to check if it went thru (perhaps profile change is instant)

    private NetProfile profile = NetProfile.OFFLINE;

    public void setProfile(NetProfile profile)
    {
        if (this.profile != profile) // note that this is comparing object addresses in memory
        {
            try {
                if (profile == NetProfile.OFFLINE)
                    Runtime.getRuntime().exec("netsh wlan disconnect");
                else
                    Runtime.getRuntime().exec("netsh wlan connect name=\""+profile.name()+"\"");
            } catch (IOException e) { throw new RuntimeException(e); }
            this.profile = profile;
        }
    }

}
