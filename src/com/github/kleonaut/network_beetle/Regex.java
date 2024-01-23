package com.github.kleonaut.network_beetle;

import java.util.regex.Pattern;

public enum Regex
{
    NEWLINE     (Pattern.compile("\\r\\n")),
    SSID        (Pattern.compile("(?!SSID.*:\\s\\r\\n)SSID.*:\\s")),
    ALL_PROFILES(Pattern.compile("All User Profile.*:\\s")),
    PROFILE     (Pattern.compile("Profile.*:\\s")),
    WINDOWS_DIR (Pattern.compile("C:\\\\Windows\\\\")),
    EXE_FILE    (Pattern.compile("[^\\\\]*\\.exe"));

    public Pattern get() { return pattern; }

    private final Pattern pattern;
    Regex(Pattern pattern) { this.pattern = pattern; }
}
