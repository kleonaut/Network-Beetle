package com.github.kleonaut.network_beetle;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class NetShell
{

    public enum Inquery
    {
        NEARBY_NETWORKS("netsh wlan show networks", Pattern.compile("(?!SSID.*:\\s\\r\\n)SSID.*:\\s")),
        ALL_PROFILES("netsh wlan show profiles", Pattern.compile("All User Profile.*:\\s")),
        CURRENT_PROFILE("netsh wlan show interfaces", Pattern.compile("Profile.*:\\s"));

        public final String COMMAND;
        public final Pattern IDENTIFIER_PTRN;

        Inquery(String command, Pattern identifier)
        {
            this.COMMAND = command;
            this.IDENTIFIER_PTRN = identifier;
        }
    }

    private static final Pattern NEWLINE_PTRN = java.util.regex.Pattern.compile("\\r\\n");

    private NetShell() {}

    // supply a null String to disconnect
    // this method is not aware of connection failure
    // to verify connection, first wait a few seconds for connection to be established
    // then use fetchInfo(Inquery.CURRENT_PROFILE)
    public static void setProfile(String networkProfileName)
    {
        try {
            if (networkProfileName == null)
                Runtime.getRuntime().exec("netsh wlan disconnect");
            else
                Runtime.getRuntime().exec("netsh wlan connect name=\""+networkProfileName+"\"");
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    // when inquery = CURRENT_PROFILE this returns a list with one element; that element is the current profile
        // if the returned list has no elements, the user is currently disconnected
    public static List<String> fetchInfo(Inquery inquery)
    {
        List<String> info = new ArrayList<>();
        try {
            Process netshProcess = Runtime.getRuntime().exec(inquery.COMMAND);
            try (
                    InputStream byteIn = netshProcess.getInputStream();
                    Scanner scanner = new Scanner(byteIn).useDelimiter(NEWLINE_PTRN);
            ) {
                // search for "Profile : " pattern with no limit (horizon=0)
                // if it doesn't exit then PC is not connected to any profile
                while (scanner.findWithinHorizon(inquery.IDENTIFIER_PTRN, 0) != null)
                    info.add(scanner.next().trim());
            }
        } catch (IOException e) { throw new RuntimeException(e); }
        return List.copyOf(info);
    }
}
